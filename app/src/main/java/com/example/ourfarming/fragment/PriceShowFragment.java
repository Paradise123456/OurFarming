package com.example.ourfarming.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ourfarming.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PriceShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PriceShowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String productName, productPrice, productImage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PriceShowFragment(String productName, String productPrice, String productImage) {
        this.productName=productName;
        this.productPrice=productPrice;
        this.productImage=productImage;
    }
    public PriceShowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PriceShowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PriceShowFragment newInstance(String param1, String param2) {
        PriceShowFragment fragment = new PriceShowFragment();
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

        View view=inflater.inflate(R.layout.fragment_price_show, container, false);

        ImageView imageholder=view.findViewById(R.id.productImagePrice);
        TextView nameholder=view.findViewById(R.id.productNamePrice);
        TextView priceholder=view.findViewById(R.id.productPricePrice);

        nameholder.setText(productName);
        priceholder.setText(productPrice);
        Glide.with(getContext()).load(productImage).into(imageholder);


        return  view;
    }

    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container,new PriceFragment()).addToBackStack(null).commit();

    }
}