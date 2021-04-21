package com.example.ourfarming.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.ourfarming.activity.Splash;
import com.example.ourfarming.admin.AdminLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;
import static android.view.View.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    EditText email, password;
    TextView sign_up, forget_password,admin;
    CheckBox remember_my_data;
    Button login;
    SharedPreferences userPrefs;
    SharedPreferences.Editor editor;
    ProgressBar progressbar;
    FirebaseAuth fauth;
    boolean is_LoggedIn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        userPrefs=getActivity().getSharedPreferences("USERDATA",Context.MODE_PRIVATE);
        editor=userPrefs.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.email);
        admin=view.findViewById(R.id.admin);
        password = view.findViewById(R.id.password);
        forget_password = view.findViewById(R.id.forgetpassword);
        progressbar = view.findViewById(R.id.progressbar_login);
        fauth = FirebaseAuth.getInstance();
        sign_up = view.findViewById(R.id.signup);
        login = view.findViewById(R.id.loginbutton);
        remember_my_data=view.findViewById(R.id.remembermecheckbox);
        email.setText(userPrefs.getString("Email",""));
        password.setText(userPrefs.getString("Password",""));
        is_LoggedIn=userPrefs.getBoolean("IS_LOGGEDIN",false);
        if(is_LoggedIn){
            Intent bottomnavigationintentIntent = new Intent(getActivity(), BottomNavigation.class);
            startActivity(bottomnavigationintentIntent);
            getActivity().finish();
        }
//        checkConnection();
        // Inflate the layout for this fragment
        forget_password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.myloginfragmentcontainer, resetPasswordFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        admin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLogin adminLogin = new AdminLogin();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.myloginfragmentcontainer, adminLogin);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                if (TextUtils.isEmpty(email1)) {
                    email.setError("Email is Required");
                    return;
                } else if (TextUtils.isEmpty(password1)) {
                    password.setError("Password is Required");
                    return;
                } else{progressbar.setVisibility(View.VISIBLE);

                    fauth.signInWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        if(fauth.getCurrentUser().isEmailVerified()) {
                                            if (remember_my_data.isChecked()) {
                                                editor.putString("Email", email1);
                                                editor.putString("Password", password1);
                                                editor.putBoolean("IS_LOGGEDIN",true);
                                                editor.apply();
                                                openHome();
                                            } else {
                                                // Sign in success, update UI with the signed-in user's ProductFragment
                                                Log.d(TAG, "signInWithEmail:success");
                                                openHome();
                                            }
                                        }
                                        else{
                                            Toast.makeText(getActivity(), "Please Verify your email before login. Check your Email for Verification!",
                                                    Toast.LENGTH_SHORT).show();
                                        }


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
            }

            }

        });

        sign_up.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment signUpFragment = new SignupFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.myloginfragmentcontainer, signUpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });

        return view;
    }

    private void checkConnection() {
        if(!isConnected(this)){
            setCustomDialog();
        }
    }

    private void setCustomDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      startActivity(new Intent(getContext(), Splash.class));
                      getActivity().finish();

                    }
                });
    }

    private boolean isConnected(LoginFragment login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }


    void openHome() {
        Intent bottomnavigationintentIntent = new Intent(getActivity(), BottomNavigation.class);
        startActivity(bottomnavigationintentIntent);
        getActivity().finish();
    }

}
