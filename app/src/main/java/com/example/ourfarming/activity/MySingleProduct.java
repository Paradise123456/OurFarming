package com.example.ourfarming.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ourfarming.R;

import java.util.List;

public class MySingleProduct extends AppCompatActivity {
    String title,amount,description,quantity,mfd,exp,location,deliverablelocation, firstimageurl,secondimageurl,thirdimageurl,fourthimageurl;
    TextView Title,Amount,Description,Quantity,Mfd,Exp,Location,Deliverablelocation;
    ImageSlider slider;
    List<SlideModel> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_single_product);
        Intent intent = getIntent();
        title= intent.getStringExtra("Title");
        amount = intent.getStringExtra("Amount");
        quantity = intent.getStringExtra("Quantity");
        description = intent.getStringExtra("SellerPhone");
        mfd = intent.getStringExtra("MFD");
        exp = intent.getStringExtra("EXP");
        location = intent.getStringExtra("Location");
        deliverablelocation = intent.getStringExtra("DeliverableLocation");
        firstimageurl = intent.getStringExtra("FirstImageUrl");
        secondimageurl = intent.getStringExtra("SecondImageUrl");
        thirdimageurl = intent.getStringExtra("ThirdImageUrl");
        fourthimageurl = intent.getStringExtra("FourthImageUrl");

        slider=(ImageSlider)findViewById(R.id.product_image_slider);

        if(firstimageurl!=null){
            images.add(new SlideModel(firstimageurl,title, ScaleTypes.FIT));
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

        Title.setText(title);
        Amount.setText(amount);
        Quantity.setText(quantity);
        Description.setText(description);
        Mfd.setText(mfd);
        Exp.setText(exp);
        Location.setText(location);
        Deliverablelocation.setText(deliverablelocation);

    }
}