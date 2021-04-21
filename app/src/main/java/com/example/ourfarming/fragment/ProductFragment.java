package com.example.ourfarming.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourfarming.R;
import com.example.ourfarming.activity.AddProduct;
import com.example.ourfarming.adapter.ProductFirebaseAdapter;
import com.example.ourfarming.dataclass.DetailProduct;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton actionbutton;
    RecyclerView recyclerView;
    ArrayList<DetailProduct> list;
    DatabaseReference reference;
    ProductFirebaseAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        actionbutton = view.findViewById(R.id.floating_button);
        reference = FirebaseDatabase.getInstance().getReference("Product");
        recyclerView = view.findViewById(R.id.productrecyclerview);
        list = new ArrayList<>();
//        searchtext.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            if(!s.toString().isEmpty()){
//                search(s.toString());
//            }
//            }
//        });
//        reference.addListenerForSingleValueEvent(listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        adapter=new ProductFirebaseAdapter(option);
//        recyclerView.setAdapter(adapter);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    DetailProduct p = snapshot1.getValue(DetailProduct.class);
//                    list.add(p);
//                }
//                adapter=new MyAdapter(getContext(),list);
//                recyclerView.setAdapter(adapter);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(),"Something went Wrong!!!",Toast.LENGTH_SHORT).show();
//            }
//        });
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<DetailProduct> options
                = new FirebaseRecyclerOptions.Builder<DetailProduct>()
                .setQuery(reference, DetailProduct.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new ProductFirebaseAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
        actionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProduct.class);
                getActivity().startActivity(intent);

            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_product);
        SearchView searchView = (SearchView) menuItem.getActionView();
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void Search(String toString) {
        FirebaseRecyclerOptions<DetailProduct> option = new FirebaseRecyclerOptions.Builder<DetailProduct>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("Title").startAt(toString).endAt(toString + "\uf8ff"), DetailProduct.class)
                .build();
        adapter = new ProductFirebaseAdapter(option);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

}
