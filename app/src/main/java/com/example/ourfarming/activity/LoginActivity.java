package com.example.ourfarming.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ourfarming.R;
import com.example.ourfarming.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.myloginfragmentcontainer, loginFragment).commit();
    }
}