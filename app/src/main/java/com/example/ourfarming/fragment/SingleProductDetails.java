package com.example.ourfarming.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ourfarming.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleProductDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleProductDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageSlider slider;
    private String title,amount,description,quantity,mfd,exp,location,deliverablelocation,firstimageurl,secondimageurl,thirdimageurl,fourthimageurl;
    private List<SlideModel> images;

    public SingleProductDetails() {
        // Required empty public constructor
    }

    public SingleProductDetails(String title, String amount, String deliverablelocation, String description, String mfd, String exp, String location, String quantity, String firstimageurl, String secondimageurl, String thirdimageurl, String fourthimageurl ) {
        this.title=title;
        this.amount=amount;
        this.description=description;
        this.quantity=quantity;
        this.mfd=mfd;
        this.exp=exp;
        this.location=location;
        this.deliverablelocation=deliverablelocation;
        this.images=images;
        this.firstimageurl=firstimageurl;
        this.secondimageurl=secondimageurl;
        this.thirdimageurl=thirdimageurl;
        this.fourthimageurl=fourthimageurl;
    }


    // TODO: Rename and change types and number of parameters
    public static SingleProductDetails newInstance(String param1, String param2) {
        SingleProductDetails fragment = new SingleProductDetails();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.single_product_details, container, false);
        TextView title1=view.findViewById(R.id.detail_name);
        TextView amount1=view.findViewById(R.id.detail_price);
        TextView description1=view.findViewById(R.id.detail_description);
        TextView mfd1=view.findViewById(R.id.detail_manufacturedate );
        TextView location1=view.findViewById(R.id.detail_location);
        TextView delivery1=view.findViewById(R.id.detail_location_deliverable);
        slider=(ImageSlider)view.findViewById(R.id.product_image_slider);

        if(firstimageurl!=null){
            images.add(new SlideModel(firstimageurl,title,ScaleTypes.FIT));
        }
        if(secondimageurl!=null){
            images.add(new SlideModel(secondimageurl,title,ScaleTypes.FIT));
        }
        if(thirdimageurl!=null){
            images.add(new SlideModel(thirdimageurl,title,ScaleTypes.FIT));
        }
        if(fourthimageurl!=null){
            images.add(new SlideModel(fourthimageurl,title,ScaleTypes.FIT));
        }
        slider.setImageList(images,ScaleTypes.FIT);
        title1.setText(title);
        mfd1.setText(mfd);
        location1.setText(location);
        delivery1.setText(deliverablelocation);
        description1.setText(description);
        amount1.setText(amount);

        return view;
    }
    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container,new ProductFragment()).addToBackStack(null).commit();

    }

}