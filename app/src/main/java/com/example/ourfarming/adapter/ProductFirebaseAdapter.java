package com.example.ourfarming.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourfarming.R;
import com.example.ourfarming.dataclass.DetailProduct;
import com.example.ourfarming.fragment.SingleProductDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ProductFirebaseAdapter extends FirebaseRecyclerAdapter<DetailProduct, ProductFirebaseAdapter.productviewholder> {


    public ProductFirebaseAdapter(@NonNull FirebaseRecyclerOptions<DetailProduct> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull productviewholder holder, int position, @NonNull DetailProduct model) {
        holder.name.setText(DetailProduct.getTitle());
        holder.price.setText(DetailProduct.getAmount());
        holder.description.setText(DetailProduct.getDescription());
        Picasso.get().load(DetailProduct.getFirstImageUrl()).fit().centerCrop().into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container,new SingleProductDetails(DetailProduct.getTitle(),DetailProduct.getAmount(),DetailProduct.getDeliverableLocation() ,DetailProduct.getDescription(),DetailProduct.getMFD(),DetailProduct.getEXP(),DetailProduct.getLocation(), DetailProduct.getQuantity(),DetailProduct.getFirstImageUrl(),DetailProduct.getSecondImageUrl(),DetailProduct.getThirdImageUrl(),DetailProduct.getFourthImageUrl())).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_details,parent,false);
        return new productviewholder(view);
    }

    public class productviewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name,price,description;
        public productviewholder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.product_details_image);
            name=(TextView)itemView.findViewById(R.id.product_details_name);
            price=(TextView)itemView.findViewById(R.id.product_details_price);
            description=(TextView)itemView.findViewById(R.id.product_details_description);
        }
    }

}
