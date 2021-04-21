package com.example.ourfarming.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourfarming.R;
import com.example.ourfarming.activity.BottomNavigation;
import com.example.ourfarming.dataclass.AdminData;
import com.example.ourfarming.dataclass.DetailUser;
import com.example.ourfarming.fragment.LoginFragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminLogin extends Fragment {
    TextView user;
    EditText phone, password;
    Button adminlogin;
    ProgressDialog loadingbar;
    DatabaseReference reference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminLogin newInstance(String param1, String param2) {
        AdminLogin fragment = new AdminLogin();
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
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);
        phone = view.findViewById(R.id.adminphone);
        password = view.findViewById(R.id.adminpassword);
        adminlogin = view.findViewById(R.id.adminloginbutton);
        user = view.findViewById(R.id.user);
        loadingbar = new ProgressDialog(getContext());
        reference= FirebaseDatabase.getInstance().getReference();


        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone1 = phone.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                if (TextUtils.isEmpty(phone1)) {
                    phone.setError("phone is Required");
                    phone.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(password1)) {
                    password.setError("Password is Required");
                    password.requestFocus();
                    return;
                }

                else if(phone1.equals("9806646844") && (password1.equals("12345678"))) {
                    loadingbar.setTitle("Login ");
                    loadingbar.setMessage("Please wait while checking information");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    Intent bottomnavigationintentIntent = new Intent(getContext(), AdminBottomNavigation.class);
                    startActivity(bottomnavigationintentIntent);
                    loadingbar.dismiss();


                }
            }

        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginfragment = new LoginFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.myloginfragmentcontainer, loginfragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }


}