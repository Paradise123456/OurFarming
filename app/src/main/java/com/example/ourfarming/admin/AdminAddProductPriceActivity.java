package com.example.ourfarming.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ourfarming.R;
import com.example.ourfarming.activity.AddProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class AdminAddProductPriceActivity extends AppCompatActivity {
    static ArrayList<Uri> uri_ArrayList;
    private int PICK_IMAGE_RC = 2;
    private int tag;
    private ImageView imgOne;
    private int imageCounter = 0;
    private Spinner unit_spinner;
    private ArrayList<String> units_ArrayList;
    private ArrayAdapter<String> unit_Adapter;
    private Toolbar mToolbar;
    private String productTitle;
    private String productUnit;
    private String productPrice;
    private EditText nameEditText;
    private EditText priceEditText;
    private Button btn_add;
    private Uri capturedImage;
    private DatabaseReference productsFirebaseRef;
    private StorageReference storageReference;
    private int uploadedImagesCount = 0;
    private String randomItemId;
    private String userid;
    private HashMap<String, Object> productDetail_Map;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product_price);
        progressDialog = new ProgressDialog(this);
        productDetail_Map = new HashMap<>();


        productsFirebaseRef = FirebaseDatabase.getInstance().getReference().child("DailyMarketPrice");
        randomItemId = productsFirebaseRef.push().getKey();
        productsFirebaseRef.child(randomItemId);
        storageReference = FirebaseStorage.getInstance().getReference("DailyMarketPrice");
        uri_ArrayList = new ArrayList<>(4);
        btn_add = findViewById(R.id.admin_add_marketproduct_button);
        nameEditText = findViewById(R.id.admin_add_marketproduct_price);
        priceEditText = findViewById(R.id.admin_add_marketproduct_price);
        imgOne = findViewById(R.id.adminaddmarketproductImage);

        displayUnits();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productTitle = nameEditText.getText().toString();
                productPrice = priceEditText.getText().toString();

                if (!isEmpty()) {
                    saveDataToFirebase();
                }
            }
        });
    }


    private boolean isEmpty() {
        if (uri_ArrayList.size() == 0) {
            openDialog("Add First Photo!");
            return true;
        } else if (TextUtils.isEmpty(productTitle)) {
            openDialog("Enter title");
            return true;
        } else if (TextUtils.isEmpty(productPrice)) {
            openDialog("Add Price");
            return true;
        } else if (TextUtils.isEmpty(productUnit)) {
            openDialog("Choose Unit");
            return true;
        }

        return false;
    }


    private void displayUnits() {

        unit_spinner = findViewById(R.id.admin_unit_spinner);
        defineUnits();
        unit_Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units_ArrayList);
        unit_spinner.setAdapter(unit_Adapter);

        unit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    productUnit = units_ArrayList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void CaptureImg(final View view) {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a photo by:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (uri_ArrayList.size() <= 4) {
                    if (options[item].equals("Choose from Gallery")) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_IMAGE_RC);
                        tag = Integer.parseInt(view.getTag().toString());

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You have reached the number of photos that can be added", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PICK_IMAGE_RC) && resultCode == Activity.RESULT_OK) {
            capturedImage = data.getData();

            switch (tag) {
                case 1:
                    imgOne.setImageResource(R.drawable.ic_ready);
                    imgOne.setClickable(false);
                    uri_ArrayList.add(capturedImage);
                    imageCounter++;
                    break;
            }
        }
    }


    private void defineUnits() {
        units_ArrayList = new ArrayList<>();
        units_ArrayList.add("/Kilogram");
        units_ArrayList.add("/Unit");

        Collections.sort(units_ArrayList);
        units_ArrayList.add(0, "Select Unit");
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void saveDataToFirebase() {
        progressDialog.setTitle("Uploading Your Product's Data");
        progressDialog.setMessage("Please Wait, Your ProductFragment Data is Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        for (int i = 0; i < uri_ArrayList.size(); i++) {
//            uploadImage(i);
            long date = Calendar.getInstance().getTimeInMillis();
            final StorageReference fileReference = storageReference.child("marketprice" + date
            );
            int finalI = i;
            fileReference.putFile(uri_ArrayList.get(i))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imagename[] = {"FirstImageUrl"};
                                    String url = String.valueOf(uri);
                                    productDetail_Map.put(imagename[finalI], url);


                                    if ((uploadedImagesCount + 1) == uri_ArrayList.size()) {
                                        productDetail_Map.put("Amount", "Rs."+productPrice+"|"+productUnit);
                                        productDetail_Map.put("Title", productTitle);
                                        productDetail_Map.put("ProductId", randomItemId);
                                        productsFirebaseRef.updateChildren(productDetail_Map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progressDialog.dismiss();
                                                        finish();
                                                        Log.w(TAG, "Added to Database");
                                                        Toast.makeText(getApplicationContext(), "Product's Information Added Successfully...", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container, new ProductFragment()).commit();
                                                    }

                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(AdminAddProductPriceActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                                Log.w(TAG, "createUserWithEmail:failure" + e);
                                            }
                                        });
                                    } else {
                                        uploadedImagesCount++;
                                    }
                                }
                            });
                        }

                    });

        }
    }


    public void openDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AdminAddProductPriceActivity.this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Got it",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}