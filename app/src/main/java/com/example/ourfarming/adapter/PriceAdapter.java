package com.example.ourfarming.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ourfarming.R;
import com.example.ourfarming.dataclass.ListProductMarketPrice;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PriceAdapter extends FirebaseRecyclerAdapter<ListProductMarketPrice,PriceAdapter.myviewholder> {


    public PriceAdapter(@NonNull FirebaseRecyclerOptions<ListProductMarketPrice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final ListProductMarketPrice model) {
        holder.productName.setText(model.getProductName());
        holder.productPrice.setText(model.getProductPrice());
        Glide.with(holder.productImage.getContext()).load(model.getProductImage()).into(holder.productImage);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView productImage;
        TextView productName,productPrice;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.productImagePrice);
            productName=itemView.findViewById(R.id.productNamePrice);
            productPrice=itemView.findViewById(R.id.productPricePrice);
        }
    }

}
