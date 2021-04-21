package com.example.ourfarming.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.ourfarming.R;
import com.example.ourfarming.fragment.HomeFragment;
import com.example.ourfarming.fragment.LoginFragment;
import com.example.ourfarming.fragment.NotificationFragment;
import com.example.ourfarming.fragment.PriceFragment;
import com.example.ourfarming.fragment.ProductFragment;
import com.example.ourfarming.fragment.ProfileFragment;

public class AdminBottomNavigation extends AppCompatActivity  implements View.OnClickListener {
    ImageView home, products, profile, todaysprice, mSelected;
    AdminHomeFragment adminhomeFragment;
    AdminProductFragment adminproductFragment;
    AdminProfileFragment adminprofileFragment;
    AdminPriceFragment adminpriceFragment;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bottom_navigation);
        home = findViewById(R.id.admin_navigation_home);
        products = findViewById(R.id.admin_navigation_product);

        todaysprice = findViewById(R.id.admin_navigation_priceList);
        profile = findViewById(R.id.admin_navigation_profile);
        setSelected(home);
        setClickListeners();


        adminhomeFragment = new AdminHomeFragment();
        adminproductFragment = new AdminProductFragment();
        adminpriceFragment = new AdminPriceFragment();
        adminprofileFragment = new AdminProfileFragment();

        currentFragment = adminhomeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.admin_bottom_navigation_fragment_container, adminhomeFragment, "adminhomeFragments").commit();


    }

    private void setClickListeners() {
        home.setOnClickListener((View.OnClickListener) this);
        products.setOnClickListener((View.OnClickListener) this);
        profile.setOnClickListener((View.OnClickListener) this);
        todaysprice.setOnClickListener((View.OnClickListener) this);


    }


    private void setSelected(ImageView imageView) {
        if (mSelected != null)
            mSelected.setColorFilter(ContextCompat.getColor(this, R.color.de_selected_color), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.selected_color), android.graphics.PorterDuff.Mode.SRC_IN);
        mSelected = imageView;

    }


    @Override
    public void onClick(View v) {
        if (v == home) {
            setSelected(home);
            changeFragment(adminhomeFragment);
        } else if (v == products) {

            setSelected(products);
            changeFragment(adminproductFragment);

        } else if (v == profile) {
            setSelected(profile);
            changeFragment(adminprofileFragment);

        } else if (v == todaysprice) {
            setSelected(todaysprice);
            changeFragment(adminpriceFragment);

        }


    }

    private void changeFragment(Fragment fragment) {
        if (fragment == currentFragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        if (fragment.isAdded())
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        else
            getSupportFragmentManager().beginTransaction().add(R.id.admin_bottom_navigation_fragment_container, fragment, "homeFragments").commit();
        currentFragment = fragment;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


}