package com.example.ourfarming.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourfarming.R;
import com.example.ourfarming.activity.AddProduct;
import com.example.ourfarming.adapter.AdminProductFirebaseAdapter;
import com.example.ourfarming.adapter.ProductFirebaseAdapter;
import com.example.ourfarming.dataclass.DetailProduct;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProductFragment extends Fragment {
    FloatingActionButton actionbutton;
    RecyclerView recyclerView;
    ArrayList<DetailProduct> list;
    AdminProductFirebaseAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProductFragment newInstance(String param1, String param2) {
        AdminProductFragment fragment = new AdminProductFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_product, container, false);
        actionbutton = view.findViewById(R.id.adminaddproductfloatingbutton);
        recyclerView= view.findViewById(R.id.adminproductrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list=new ArrayList<>();
        FirebaseRecyclerOptions<DetailProduct> options =
                new FirebaseRecyclerOptions.Builder<DetailProduct>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product"), DetailProduct.class)
                        .build();

        adapter=new AdminProductFirebaseAdapter(options);
        recyclerView.setAdapter(adapter);

        actionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdminAddProduct.class);
                getActivity().startActivity(intent);

            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    }