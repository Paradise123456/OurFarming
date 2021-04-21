package com.example.ourfarming.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ourfarming.R;
import com.example.ourfarming.fragment.LoginFragment;
import com.example.ourfarming.fragment.ProfileFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class UpdateProfile extends AppCompatActivity {

    private CircleImageView profileimage;
    private Button backbutton, updatebutton;
    private TextView changeprofiletext;
    private DatabaseReference reference;
    private FirebaseAuth fauth;
    private Uri imageUri;
    private StorageTask uploadTask;
    private String myUri = "";
    private StorageReference storageReference;
    private String Name, Email, Phone,userId;
    private EditText name, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        fauth = FirebaseAuth.getInstance();
        userId=fauth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(userId);
        storageReference = FirebaseStorage.getInstance().getReference().child("ProfilePicture");
        profileimage = findViewById(R.id.profilepicture);
        backbutton = findViewById(R.id.update_profile_backbutton);
        updatebutton = findViewById(R.id.update_profile_updatebutton);
        changeprofiletext = findViewById(R.id.update_profile_changeprofiletext);
        name = (EditText) findViewById(R.id.update_profile_name);
        email = (EditText) findViewById(R.id.update_profile_email);
        phone = (EditText) findViewById(R.id.update_profile_phone);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = new ProfileFragment();
                FragmentManager manager=getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.bottom_navigation_fragment_container,fragment).addToBackStack(null).commit();
            }
        });
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileImage();
                if (isNameChanged()) {
                    Toast.makeText(getApplicationContext(), "Your Name has Successfully changed",
                            Toast.LENGTH_SHORT).show();
                }
                if (isEmailChanged()) {
                    Toast.makeText(getApplicationContext(), "Your Email has Successfully changed",
                            Toast.LENGTH_SHORT).show();
                }
                if (isPhoneChanged()) {
                    Toast.makeText(getApplicationContext(), "Your Phone has Successfully changed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        changeprofiletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1, 1).start(UpdateProfile.this);
            }
        });
        showUserData();
        getUserinfo();

    }

    private boolean isPhoneChanged() {
        if (!Phone.equals(phone.getText().toString())) {
            reference.child("phone").setValue(phone.getText().toString());
            Phone = phone.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private void showUserData() {
        Intent intent = getIntent();
        Name = intent.getStringExtra("name");
        Email = intent.getStringExtra("email");
        Phone = intent.getStringExtra("phone");
        email.setText(Email);
        phone.setText(Phone);
        name.setText(Name);
    }

    private boolean isEmailChanged() {
        if (!Email.equals(email.getText().toString())) {
            reference.child("email").setValue(email.getText().toString());
            Email = email.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!Name.equals(name.getText().toString())) {
            reference.child("name").setValue(name.getText().toString());
            Name = name.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private void getUserinfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&&snapshot.getChildrenCount()>0){
                    if(snapshot.hasChild("image")){
                        String image=snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileimage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();
            profileimage.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this,"Error,Try Again",Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadProfileImage() {
final ProgressDialog progressDialog=new ProgressDialog(this);
progressDialog.setTitle("Set Your Profile");
progressDialog.setMessage("Please wait, Your data is Updating...");
progressDialog.show();
if(imageUri!=null){
    final StorageReference fileref=storageReference.child(fauth.getCurrentUser().getUid()+".jpg");
    uploadTask=fileref.putFile(imageUri);
    uploadTask.continueWithTask(new Continuation() {
        @Override
        public Object then(@NonNull Task task) throws Exception {
            if(!task.isSuccessful()){
                throw task.getException();
            }
            return fileref.getDownloadUrl();
        }
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            Uri downloadUri=(Uri)task.getResult();
            myUri=downloadUri.toString();
            HashMap<String,Object> userMap=new HashMap<>();
            userMap.put("image",myUri);
            reference.updateChildren(userMap);
            progressDialog.dismiss();
        }
    });
}
else{
    progressDialog.dismiss();
}
    }
}


