package com.example.ourfarming.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.telephony.PhoneNumberUtils;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
    TextView alreadyregistered;
    EditText email, password, confirmpassword,username,phone;
    Button register;
    CheckBox acceptterms;
    FirebaseAuth fauth;
    ProgressBar progressbar;
    FirebaseUser fuser;
    DatabaseReference reference;
    FirebaseDatabase databse;
    String userId="";


    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        email = view.findViewById(R.id.email);
        phone=view.findViewById(R.id.phone);
        password = view.findViewById(R.id.password);
        confirmpassword = view.findViewById(R.id.confirmpassword);
        acceptterms = view.findViewById(R.id.accepttermsandcondition);
        register = view.findViewById(R.id.registerbutton);
        alreadyregistered = view.findViewById(R.id.alreadyregistered);
        progressbar = view.findViewById(R.id.progressbar_signup);
        fauth = FirebaseAuth.getInstance();
        username=view.findViewById(R.id.username);
        databse = FirebaseDatabase.getInstance();
        reference= databse.getReference();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                String phone1=phone.getText().toString().trim();
                String username1 = username.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                String password2 = confirmpassword.getText().toString().trim();
                if (TextUtils.isEmpty(email1)) {
                    email.setError("Email is Required");
                    email.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(password1)) {
                    password.setError("Password is Required");
                    password.requestFocus();
                    return;
                }
                else if (password.length() < 8) {
                    password.setError("Password most have more than 7 letters");
                    password.requestFocus();
                    return;
                } else if (!password2.equals(password1)) {
                    confirmpassword.setError("Password doesnot match with eachother");
                    confirmpassword.requestFocus();
                    return;
                }
                else if (!validateEmail(email1)) {
                    email.setError("Please provide the valid Email");
                    email.requestFocus();
                    return;
                }
                else if(!PhoneNumberUtils.isGlobalPhoneNumber(phone1)){
                    phone.setError("Please Enter Valid Number");
                    phone.requestFocus();
                    return;
                }
                else {
                    progressbar.setVisibility(View.VISIBLE);


//                    reference.push().setValue(user);
                    fauth.createUserWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    fauth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Log.d(TAG, "Email sent.");
                                            }
                                            else{
                                                Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    if (task.isSuccessful()) {
                                        userId=fauth.getCurrentUser().getUid();
                                        insertdata(username1,phone1,email1,userId);
                                        openLogin();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }
            }

        });
        alreadyregistered.setOnClickListener(new View.OnClickListener() {
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



    private void openLogin() {
        LoginFragment loginfragment1 = new LoginFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.myloginfragmentcontainer, loginfragment1);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    private void sendVerificationCode(){
//        String phone1=phone.getText().toString();
//        if(phone1.isEmpty()){
//            phone.setError("Please Enter you Phone Number");
//            phone.requestFocus();
//            return;
//        }
//        else if(phone1.length()<10){
//            phone.setError("Please Enter Valid Number");
//            phone.requestFocus();
//            return;
//        }
//        else if(!PhoneNumberUtils.isGlobalPhoneNumber(phone1)){
//            phone.setError("Please Enter Valid Number");
//            phone.requestFocus();
//            return;
//        }
//        else {
//            PhoneAuthOptions options =
//                    PhoneAuthOptions.newBuilder(fauth)
//                            .setPhoneNumber(phone1)       // Phone number to verify
//                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                            .setActivity(getActivity())                 // Activity (for callback binding)
//                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                            .build();
//            PhoneAuthProvider.verifyPhoneNumber(options);
//        }
//    }
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            codeSent=s;
//        }
//    };


    private boolean validateEmail(String email1) {
        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = email1;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
    private void insertdata(final String name, final String phone, final String email,final String userId) {
        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child(userId).exists()))
                {
                    HashMap<String, Object> userdatamap= new HashMap <>();
                    userdatamap.put("phone",phone);
                    userdatamap.put("name",name);
                    userdatamap.put("email",email);
                    userdatamap.put("userId",userId);
                    reference.child(userId).updateChildren(userdatamap)
                            .addOnCompleteListener(new OnCompleteListener <Void>() {
                                @Override
                                public void onComplete(@NonNull Task <Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Registered Successfully! Check your email to verify your account for Our Farming!!!",Toast.LENGTH_SHORT).show();
                                    }
                                }


                            });
                }
                else
                {
                    Toast.makeText(getContext(), "This "+userId+" Alredy Exist",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}