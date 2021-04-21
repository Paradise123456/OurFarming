package com.example.ourfarming.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ourfarming.R;
import com.example.ourfarming.activity.MyProducts;
import com.example.ourfarming.activity.UpdateProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    TextView updateprofile, displayname, logoutbtn,myproducts;
    CircleImageView imageView;
    FirebaseAuth fauth;
    FirebaseUser user;
    FirebaseDatabase databse;
    DatabaseReference reference, reference1;
    String uid, name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        updateprofile = view.findViewById(R.id.profile_fragment_updateprofile);
        myproducts=view.findViewById(R.id.profile_fragment_myproducts);
        fauth = FirebaseAuth.getInstance();
        imageView = view.findViewById(R.id.profilepicture);
        uid = fauth.getCurrentUser().getUid();
        displayname = view.findViewById(R.id.displayName);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        databse = FirebaseDatabase.getInstance();
        sharedPreferences=getActivity().getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        reference = databse.getReference().child("UserInfo").child(uid);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                LoginFragment loginfragment = new LoginFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.myloginfragmentcontainer, loginfragment);
                transaction.addToBackStack(null);
                transaction.commit();
                Signout();
            }
        });
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String name = snapshot.child("name").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String phone = snapshot.child("phone").getValue(String.class);

                            Intent intent = new Intent(getContext(), UpdateProfile.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("phone", phone);
                            getActivity().startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        myproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getContext(), MyProducts.class);
               startActivity(intent);

            }
        });
        getUserInfo();
        return view;

    }

    private void Signout() {
        fauth.signOut();
    }

    private void getUserInfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&&snapshot.getChildrenCount()>0){
                    String name=snapshot.child("name").getValue().toString();
                    displayname.setText(name);
                    if(snapshot.hasChild("image")){
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
