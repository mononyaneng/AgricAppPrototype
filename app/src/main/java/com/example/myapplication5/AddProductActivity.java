package com.example.myapplication5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddProductActivity extends AppCompatActivity {
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private EditText productDesc;
    private  EditText productPrice;
    private  EditText percentOff;
    private EditText deliveryPrice;
    private ImageView productImageView;
    private ProgressBar productUploadProgressBar;
    private Button uploadBtn;
    private Button chooseImgBtn;
    private product newProduct;
    private Uri imageUri;
    private int PICIMAGE_REQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productDesc = (EditText)findViewById(R.id.product_desc_textinput);
        productPrice = (EditText)findViewById(R.id.product_price_textinput);
        percentOff = (EditText)findViewById(R.id.product_percent_off_textinput);
        deliveryPrice = (EditText)findViewById(R.id.product_delivery_price_textinput);
        productImageView = (ImageView)findViewById(R.id.product_image);
        productUploadProgressBar = (ProgressBar)findViewById(R.id.product_uplouad_progressbar);
        chooseImgBtn = (Button)findViewById(R.id.choose_product_pic_btn);
        uploadBtn = (Button)findViewById(R.id.upload_product_btn);

        storageReference = FirebaseStorage.getInstance().getReference("ProductImages");
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        chooseImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICIMAGE_REQUEST);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProduct(newProduct);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICIMAGE_REQUEST && resultCode ==
                RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(productImageView);
        } else {
            Toast.makeText(this,"Image was not picked successfully",Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadProduct(final product product){
        if(imageUri != null){
            final StorageReference imageFileRef = storageReference.child(System.currentTimeMillis()
            + "."+getFileExtension(imageUri));
            imageFileRef.putFile(imageUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            productUploadProgressBar.setProgress(0);
                        }
                    },500);

                    Toast.makeText(AddProductActivity.this, "File Upload successful", Toast.LENGTH_SHORT).show();
                    final String[] imgUrl = {new String()};

                    imageFileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgUrl[0] = uri.toString();
                        }
                    });
                    product uploadProduct = new product(productDesc.getText().toString(),
                            Double.parseDouble( productPrice.getText().toString()),
                            Double.parseDouble(percentOff.getText().toString()),
                            Double.parseDouble(deliveryPrice.getText().toString()),
                            taskSnapshot.getMetadata().getReference().toString());

                    String uploadId = databaseReference.push().getKey();

                    //product.setImageUrl(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    databaseReference.child(uploadId).setValue(uploadProduct);

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProductActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0* snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                    productUploadProgressBar.setProgress((int)progress);
                }
            });
        }else {
            Toast.makeText(this,
                    "Image file was not selected",Toast.LENGTH_SHORT).show();
        }
    }
}