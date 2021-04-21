package com.example.ourfarming.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourfarming.R;
import com.example.ourfarming.activity.MySingleProduct;
import com.example.ourfarming.dataclass.DetailProduct;
import com.example.ourfarming.activity.LoginActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UserProductAdapter extends FirebaseRecyclerAdapter<DetailProduct, UserProductAdapter.productviewholder> {

Context context;
    public UserProductAdapter(@NonNull FirebaseRecyclerOptions<DetailProduct> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull productviewholder holder,final int position, @NonNull final DetailProduct model) {
        holder.name.setText(DetailProduct.getTitle());
        holder.price.setText(DetailProduct.getAmount());
        holder.description.setText(DetailProduct.getDescription());
        Picasso.get().load(DetailProduct.getFirstImageUrl()).fit().centerCrop().into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), MySingleProduct.class);
                intent.putExtra("Product", position);
                context.startActivity(intent);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.cardView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_content))
                        .setExpanded(true,1100)
                        .create();
                View myView=dialogPlus.getHeaderView();
                EditText title=myView.findViewById(R.id.dialog_content_title);
                EditText price=myView.findViewById(R.id.dialog_content_price);
                EditText quantity=myView.findViewById(R.id.dialog_content_quantity);
                EditText deliverlocation=myView.findViewById(R.id.dialog_content_deliverlocation);
                EditText description=myView.findViewById(R.id.dialog_content_description);
                EditText mfd=myView.findViewById(R.id.dialog_content_mfd);
                EditText exp=myView.findViewById(R.id.dialog_content_exp);
                EditText phoneno=myView.findViewById(R.id.dialog_content_phoneno);
                Button update=myView.findViewById(R.id.editdetail);

                title.setText(DetailProduct.getTitle());
                price.setText(DetailProduct.getAmount());
                quantity.setText(DetailProduct.getQuantity());
                deliverlocation.setText(DetailProduct.getDeliverableLocation());
                description.setText(DetailProduct.getDescription());
                exp.setText(DetailProduct.getEXP());
                mfd.setText(DetailProduct.getMFD());
                phoneno.setText(DetailProduct.getSellerPhone());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("Title",title.getText().toString());
                        map.put("Amount",title.getText().toString());
                        map.put("Quantity",title.getText().toString());
                        map.put("Description",title.getText().toString());
                        map.put("DeliverableLocation",title.getText().toString());
                        map.put("MFD",title.getText().toString());
                        map.put("EXP",title.getText().toString());
                        map.put("SellerPhone",title.getText().toString());
                        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase.getInstance().getReference().child("Product").child(uid).child(getRef(position).getKey())
                                .updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialogPlus.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               dialogPlus.dismiss();
                            }
                        });
                        holder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(holder.cardView.getContext());
                                builder.setTitle("Delete your Product?");
                                builder.setMessage("Really want to delete your Product???");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference().child("Product").child(uid).child(getRef(position).getKey())
                                                .removeValue();
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                    }
                                });
                            }
                        });

                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_products_details,parent,false);
        return new productviewholder(view);
    }

    public class productviewholder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView img,edit,delete;
        TextView name,price,description;
        public productviewholder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.product_details_image);
            name=(TextView)itemView.findViewById(R.id.product_details_name);
            price=(TextView)itemView.findViewById(R.id.product_details_price);
            description=(TextView)itemView.findViewById(R.id.product_details_description);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            edit=(ImageView)itemView.findViewById(R.id.product_details_editproduct);
            delete=(ImageView)itemView.findViewById(R.id.product_details_deleteproduct);

        }
    }

}
