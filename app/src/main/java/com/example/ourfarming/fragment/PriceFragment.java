package com.example.ourfarming.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourfarming.R;
import com.example.ourfarming.adapter.PriceAdapter;
import com.example.ourfarming.dataclass.ListProductMarketPrice;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class PriceFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    PriceAdapter adapter;
    public PriceFragment() {

    }

    public static PriceFragment newInstance(String param1, String param2) {
        PriceFragment fragment = new PriceFragment();
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

        View view=inflater.inflate(R.layout.price_fragment, container, false);

        recview=(RecyclerView)view.findViewById(R.id.priceRecyclerView);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<ListProductMarketPrice> options =
                new FirebaseRecyclerOptions.Builder<ListProductMarketPrice>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("MarketPrice"), ListProductMarketPrice.class)
                        .build();

        adapter=new PriceAdapter(options);
        recview.setAdapter(adapter);

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
