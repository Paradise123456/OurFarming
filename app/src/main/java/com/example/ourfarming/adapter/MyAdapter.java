package com.example.ourfarming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourfarming.dataclass.DetailProduct;
import com.example.ourfarming.R;
import com.example.ourfarming.fragment.SingleProductDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<DetailProduct> product;

    public MyAdapter(Context c , ArrayList<DetailProduct> p)
    {
        context = c;
        product = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.product_details,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(product.get(position).getTitle());
        holder.price.setText(product.get(position).getAmount());
        holder.description.setText(product.get(position).getDescription());
        Picasso.get().load(product.get(position).getFirstImageUrl()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container,new SingleProductDetails(DetailProduct.getTitle(),DetailProduct.getAmount(),DetailProduct.getDeliverableLocation() ,DetailProduct.getDescription(),DetailProduct.getMFD(),DetailProduct.getEXP(),DetailProduct.getLocation(), DetailProduct.getQuantity(),DetailProduct.getFirstImageUrl(),DetailProduct.getSecondImageUrl(),DetailProduct.getThirdImageUrl(),DetailProduct.getFourthImageUrl())).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,price,description;
        public MyViewHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.product_details_image);
            name=(TextView)itemView.findViewById(R.id.product_details_name);
            price=(TextView)itemView.findViewById(R.id.product_details_price);
            description=(TextView)itemView.findViewById(R.id.product_details_description);
//            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
