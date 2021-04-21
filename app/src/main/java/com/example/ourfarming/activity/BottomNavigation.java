package com.example.ourfarming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ourfarming.R;
import com.example.ourfarming.databinding.ActivityBottomNavigationBinding;
import com.example.ourfarming.fragment.HomeFragment;
import com.example.ourfarming.fragment.NotificationFragment;
import com.example.ourfarming.fragment.PriceFragment;
import com.example.ourfarming.fragment.ProductFragment;
import com.example.ourfarming.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BottomNavigation extends AppCompatActivity implements View.OnClickListener {
    ImageView home, products, notifications, profile, todaysprice, mSelected;
    HomeFragment homeFragment;
    ProductFragment productFragment;
    ProfileFragment profileFragment;
    PriceFragment priceFragment;
    Fragment currentFragment;

// ...


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//////        BadgeDrawable notification_badge = binding.bottomNavigation.getOrCreateBadge(R.id.navigation_notification);
////        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
////        bottomnav.setSelectedItemId(R.id.navigation_home);
////        //TO REPLACE STATUS BAR
////        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//////        notification_badge.setBackgroundColor(Color.RED);
//////            notification_badge.setBadgeTextColor(Color.WHITE);
//////            notification_badge.setMaxCharacterCount(4);
//////            notification_badge.setNumber(1);
//////            notification_badge.setVisible(true);
////
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container, new HomeFragment()).commit();
//        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedfragment = null;
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        selectedfragment = new HomeFragment();
//                        break;
//                    case R.id.navigation_profile:
//                        selectedfragment = new ProfileFragment();
//                        break;
//                    case R.id.navigation_product:
//                        selectedfragment = new ProductFragment();
//                        break;
//                    case R.id.navigation_priceList:
//                        selectedfragment = new PriceFragment();
//                        break;
//                    case R.id.navigation_notification:
//                        selectedfragment = new NotificationFragment();
//                        break;
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container, selectedfragment).commit();
//                return true;
//            }
//
//        });


        setContentView(R.layout.activity_bottom_navigation);
        home = findViewById(R.id.navigation_home);
        products = findViewById(R.id.navigation_product);
        todaysprice = findViewById(R.id.navigation_priceList);
        profile = findViewById(R.id.navigation_profile);
        setSelected(home);
        setClickListeners();


        homeFragment = new HomeFragment();
        productFragment = new ProductFragment();
        profileFragment = new ProfileFragment();
        priceFragment=new PriceFragment();

        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.bottom_navigation_fragment_container, homeFragment, "homeFragments").commit();


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
            changeFragment(homeFragment);
        } else if (v == products) {

            setSelected(products);
            changeFragment(productFragment);

        } else if (v == profile) {
            setSelected(profile);
            changeFragment(profileFragment);

        }
        else if (v == todaysprice) {
            setSelected(todaysprice);
            changeFragment(priceFragment);

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
            getSupportFragmentManager().beginTransaction().add(R.id.bottom_navigation_fragment_container, fragment, "homeFragments").commit();
        currentFragment = fragment;

    }



}