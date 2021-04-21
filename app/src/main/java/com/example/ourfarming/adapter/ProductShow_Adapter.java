//package com.example.ourfarming.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.ourfarming.R;
//import com.example.ourfarming.dataclass.DetailProduct;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class ProductShow_Adapter extends RecyclerView.Adapter<ProductShow_Adapter.viewholder> {
//    private Context context;
//    private ArrayList<DetailProduct> list;
//    private LayoutInflater layoutInflater;
//
//    public ProductShow_Adapter(Context context, ArrayList<DetailProduct> list) {
//        this.list = list;
//        this.context = context;
//        layoutInflater = LayoutInflater.from(context);
//    }
//
//    @NonNull
//    @Override
//    public ProductShow_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.product_details, parent, false);
//        return new viewholder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductShow_Adapter.viewholder holder, int position) {
//        DetailProduct product = list.get(position);
//        holder.product_details_name.setText(product.getTitle());
//        holder.product_details_price.setText(product.getAmount());
//        holder.product_details_description.setText(product.getDescription());
//        Picasso.get().load(product.getImages().get(0)).fit().centerCrop().into(holder.product_details_image);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.putExtra("Product", position);
//
//                context.startActivity(intent);
//                Toast.makeText(context.getApplicationContext(), list.get(position).getTitle(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class viewholder extends RecyclerView.ViewHolder {
//        CardView cardView;
//        private TextView product_details_name, product_details_description, product_details_price;
//        private ImageView product_details_image;
//
//        public viewholder(@NonNull View itemView) {
//            super(itemView);
//
//            product_details_name = itemView.findViewById(R.id.product_details_name);
//            product_details_description = itemView.findViewById(R.id.product_details_description);
//            product_details_price = itemView.findViewById(R.id.product_details_price);
//            product_details_image = (ImageView) itemView.findViewById(R.id.product_details_image);
//            cardView = (CardView) itemView.findViewById(R.id.card_view);
//        }
//    }
//}
//
//
