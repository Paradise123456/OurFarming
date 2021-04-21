package com.example.ourfarming.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ourfarming.R;
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

public class AddProduct extends AppCompatActivity {
    static ArrayList<Uri> uri_ArrayList;
    private int PICK_IMAGE_RC = 2;
    private int tag;
    private ImageView imgOne;
    private ImageView imgTwo;
    private ImageView imgThree;
    private ImageView imgFour;
    private int imageCounter = 0;
    private Spinner unit_spinner;
    private ArrayList<String> units_ArrayList;
    private ArrayAdapter<String> unit_Adapter;
    private Toolbar mToolbar;
    private String productTitle;
    private String productUnit;
    private String productMfd;
    private String productExp;
    private String productLocation;
    private String productDeliverableArea;
    private String productQuantity;
    private String productSellerPhone;
    private String productPrice;
    private String productDescription;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText sellerphoneEditText;
    private EditText quantityEditText;
    private EditText descriptionEditText;
    private EditText locationEditText;
    private EditText mfdEditText;
    private EditText expEditText;
    private EditText deliverablelocationEditText;
    private Button btn_add;
    private Uri capturedImage;
    private FirebaseAuth mAuth;
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
        setContentView(R.layout.activity_add_product);
        progressDialog = new ProgressDialog(this);
        productDetail_Map = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();
        productsFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Product").child(userid);
        storageReference = FirebaseStorage.getInstance().getReference("Products");
        randomItemId = productsFirebaseRef.push().getKey();
        productsFirebaseRef.child(randomItemId);
        uri_ArrayList = new ArrayList<>(4);
        btn_add = findViewById(R.id.add_product_button);
        nameEditText = findViewById(R.id.add_product_name);
        mfdEditText = findViewById(R.id.add_product_mfgdate);
        expEditText = findViewById(R.id.add_product_expdate);
        sellerphoneEditText = findViewById(R.id.add_product_sellerphone);
        quantityEditText = findViewById(R.id.add_product_quantity);
        locationEditText = findViewById(R.id.add_product_locationprocuct);
        deliverablelocationEditText = findViewById(R.id.add_product_delivery);
        priceEditText = findViewById(R.id.add_product_price);
        descriptionEditText = findViewById(R.id.add_product_detail);
        imgOne = findViewById(R.id.img1);
        imgTwo = findViewById(R.id.img2);
        imgThree = findViewById(R.id.img3);
        imgFour = findViewById(R.id.img4);

        displayUnits();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productTitle = nameEditText.getText().toString();
                productPrice = priceEditText.getText().toString();
                productQuantity = quantityEditText.getText().toString();
                productDescription = descriptionEditText.getText().toString();
                productSellerPhone = sellerphoneEditText.getText().toString();
                productMfd = mfdEditText.getText().toString();
                productExp = expEditText.getText().toString();
                productLocation = locationEditText.getText().toString();
                productDeliverableArea = deliverablelocationEditText.getText().toString();

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
        } else if (TextUtils.isEmpty(productQuantity)) {
            openDialog("Enter Quantity");
            return true;
        } else if (TextUtils.isEmpty(productMfd)) {
            openDialog("Enter Manufactured Date");
            return true;
        } else if (TextUtils.isEmpty(productExp)) {
            openDialog("Enter Expiary Date");
            return true;
        } else if (TextUtils.isEmpty(productDescription)) {
            openDialog("Add description");
            return true;
        } else if (TextUtils.isEmpty(productDeliverableArea)) {
            openDialog("Add Deliverable Area");
            return true;
        } else if (TextUtils.isEmpty(productSellerPhone)) {
            openDialog("Add Seller Phone Number");
            return true;
        }

        return false;
    }


    private void displayUnits() {

        unit_spinner = findViewById(R.id.unit_spinner);
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
                case 2:
                    imgTwo.setImageResource(R.drawable.ic_ready);
                    imgTwo.setClickable(false);
                    uri_ArrayList.add(capturedImage);
                    imageCounter++;
                    break;
                case 3:
                    imgThree.setImageResource(R.drawable.ic_ready);
                    imgThree.setClickable(false);
                    uri_ArrayList.add(capturedImage);
                    imageCounter++;
                    break;
                case 4:
                    imgFour.setImageResource(R.drawable.ic_ready);
                    imgFour.setClickable(false);
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
            final StorageReference fileReference = storageReference.child("products" + date
            );
            int finalI = i;
            fileReference.putFile(uri_ArrayList.get(i))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imagename[] = {"FirstImageUrl", "SecondImageUrl", "ThirdImageUrl", "FourthImageUrl"};
                                    String url = String.valueOf(uri);
                                    productDetail_Map.put(imagename[finalI], url);


                                    if ((uploadedImagesCount + 1) == uri_ArrayList.size()) {
                                        productDetail_Map.put("State", "Available");
                                        productDetail_Map.put("Amount", "Rs."+productPrice+"|"+productUnit);
                                        productDetail_Map.put("Description", productDescription);
                                        productDetail_Map.put("Title", productTitle);
                                        productDetail_Map.put("Quantity", productQuantity);
                                        productDetail_Map.put("MFD", productMfd);
                                        productDetail_Map.put("EXP", productExp);
                                        productDetail_Map.put("Location", productLocation);
                                        productDetail_Map.put("SellerPhone", productSellerPhone);
                                        productDetail_Map.put("DeliverableLocation", productDeliverableArea);
                                        productDetail_Map.put("SellerId", userid);
                                        productDetail_Map.put("ProductId", randomItemId);
                                        productsFirebaseRef.child(randomItemId).updateChildren(productDetail_Map)
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
                                                Toast.makeText(AddProduct.this, e.toString(), Toast.LENGTH_SHORT).show();
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

//    private void uploadImage(final int num) {
//        long date = Calendar.getInstance().getTimeInMillis();
//        final StorageReference fileReference = storageReference.child("products" + date
//        );
//        fileReference.putFile(uri_ArrayList.get(num))
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String imagename[] = {"FirstImageUrl", "SecondImageUrl", "ThirdImageUrl", "FourthImageUrl"};
//                                String url = String.valueOf(uri);
//                                productDetail_Map.put(""+num, url);
//
//
//                                if ((uploadedImagesCount + 1) == uri_ArrayList.size()) {
//                                    productDetail_Map.put("state", "Available");
//                                    productDetail_Map.put("Amount", productUnit + " | " + productPrice + "NRS");
//                                    productDetail_Map.put("Description", productDescription);
//                                    productDetail_Map.put("Title", productTitle);
//                                    productDetail_Map.put("Quantity", productQuantity);
//                                    productDetail_Map.put("MFD", productMfd);
//                                    productDetail_Map.put("EXP", productExp);
//                                    productDetail_Map.put("location", productLocation);
//                                    productDetail_Map.put("sellerphone", productSellerPhone);
//                                    productDetail_Map.put("deliverablelocation", productDeliverableArea);
//                                    productDetail_Map.put("SellerId", userid);
//                                    productDetail_Map.put("ProductId", randomItemId);
//                                    productsFirebaseRef.child(randomItemId).updateChildren(productDetail_Map)
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    progressDialog.dismiss();
//                                                    finish();
//                                                    Log.w(TAG, "Added to DAtabase");
//                                                    Toast.makeText(getApplicationContext(), "Product's Information Added Successfully...", Toast.LENGTH_SHORT).show();
////                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container, new ProductFragment()).commit();
//                                                }
//
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            progressDialog.dismiss();
//                                            Toast.makeText(AddProduct.this, e.toString(), Toast.LENGTH_SHORT).show();
//                                            Log.w(TAG, "createUserWithEmail:failure" + e);
//                                        }
//                                    });
//
//                                } else {
//                                    uploadedImagesCount++;
//                                }
//                            }
//                        });
//                    }
//
//                });
//
//    }

//    private void SendLink() {
//        //productsFirebaseRef.child(mAuth.getUid()).child(randomItemId).updateChildren(hashMap);
//        productDetail_Map.put("state", "Available");
//        productDetail_Map.put("Amount", productUnit + " | " + productPrice + "NRS");
//        productDetail_Map.put("Description", productDescription);
//        productDetail_Map.put("Title", productTitle);
//        productDetail_Map.put("Quantity", productQuantity);
//        productDetail_Map.put("MFD", productMfd);
//        productDetail_Map.put("EXP", productExp);
//        productDetail_Map.put("location", productLocation);
//        productDetail_Map.put("sellerphone", productSellerPhone);
//        productDetail_Map.put("deliverablelocation", productDeliverableArea);
//        productDetail_Map.put("SellerId", userid);
//        productDetail_Map.put("ProductId", randomItemId);
//        productsFirebaseRef.child(randomItemId).updateChildren(productDetail_Map)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        progressDialog.dismiss();
//                        finish();
//                        Log.w(TAG, "Added to DAtabase");
//                        Toast.makeText(getApplicationContext(), "Product's Information Added Successfully...", Toast.LENGTH_SHORT).show();
////                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation_fragment_container, new ProductFragment()).commit();
//                    }
//
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(AddProduct.this, e.toString(), Toast.LENGTH_SHORT).show();
//                Log.w(TAG, "createUserWithEmail:failure" + e);
//            }
//        });
//
//    }


    /*//To get the extension of the file: JPEG, ..
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }*/

    public void openDialog(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddProduct.this);
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
