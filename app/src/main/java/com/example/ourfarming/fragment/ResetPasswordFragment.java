package com.example.ourfarming.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ourfarming.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText registeredemail;
    private Button resetpasswordbutton;
    private TextView goback;
    private FirebaseAuth fauth;
    private ViewGroup emailIconContainer;
    private ImageView emailSent, emailSending;
    private TextView emailSentText;
    private ProgressBar progressBar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        registeredemail = view.findViewById(R.id.forget_password_emailid);
        resetpasswordbutton = view.findViewById(R.id.forget_password_button);
        goback = view.findViewById(R.id.forget_password_goback);
        fauth = FirebaseAuth.getInstance();
        emailSent = view.findViewById(R.id.forget_password_emailSent);
        emailSending = view.findViewById(R.id.forget_password_emailSending);
        emailSentText = view.findViewById(R.id.forget_password_emailSentText);
        progressBar = view.findViewById(R.id.forget_password_progressbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.myloginfragmentcontainer, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        registeredemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        resetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSentText.setVisibility(View.GONE);
                emailSentText.setVisibility(View.GONE);
                emailSending.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                resetpasswordbutton.setEnabled(false);
                resetpasswordbutton.setTextColor(Color.argb(50, 255, 255, 255));
                fauth.sendPasswordResetEmail(registeredemail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    emailSentText.setVisibility(View.VISIBLE);
                                    emailSent.setVisibility(View.VISIBLE);
                                    emailSending.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    String error = task.getException().getMessage();
                                    progressBar.setVisibility(View.GONE);
                                    emailSending.setVisibility(View.GONE);
                                    emailSentText.setText(error);
                                    emailSentText.setVisibility(View.VISIBLE);
                                    emailSentText.setTextColor(getResources().getColor(R.color.colorRed));
                                    resetpasswordbutton.setEnabled(true);
                                    resetpasswordbutton.setTextColor(Color.rgb(255, 255, 255));
                                }

                                progressBar.setVisibility(View.GONE);

                            }
                        });
            }
        });
    }

    private void checkInput() {
        if (TextUtils.isEmpty(registeredemail.getText())) {
            resetpasswordbutton.setEnabled(false);
            resetpasswordbutton.setTextColor(Color.argb(50, 255, 255, 255));
        } else {
            resetpasswordbutton.setEnabled(true);
            resetpasswordbutton.setTextColor(Color.rgb(255, 255, 255));
        }
    }
}