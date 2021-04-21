package com.example.ourfarming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.example.ourfarming.dataclass.DetailProduct;
import com.example.ourfarming.R;
import com.example.ourfarming.adapter.UserProductAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyProducts extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DetailProduct> list;
    DatabaseReference reference;
    UserProductAdapter adapter;
    String uid;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem= menu.findItem(R.id.search_product);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Search(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void Search(String toString) {
        FirebaseRecyclerOptions<DetailProduct> option=new FirebaseRecyclerOptions.Builder<DetailProduct>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("Title").startAt(toString).endAt(toString+"\uf8ff"),DetailProduct.class)
                .build();
        adapter=new UserProductAdapter(option);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        recyclerView=findViewById(R.id.myproductsrecyclerview);
        uid= FirebaseAuth.getInstance().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Product").child(uid);
        list=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<DetailProduct> option=new FirebaseRecyclerOptions.Builder<DetailProduct>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Product").child(uid),DetailProduct.class)
                .build();
        adapter=new UserProductAdapter(option);
        recyclerView.setAdapter(adapter);

    }
}