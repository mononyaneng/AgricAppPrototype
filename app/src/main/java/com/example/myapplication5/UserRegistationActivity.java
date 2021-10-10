package com.example.myapplication5;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserRegistationActivity extends AppCompatActivity {


    EditText user_firstname;
    EditText user_lastname;
    EditText user_phone;
    EditText user_email;
    EditText user_password;
    EditText user_passwordconf;
    EditText userPhysAddr;
    EditText userNationalId;
    Button createAccountBtn;
    Button yesRegBusinessBtn;
    Button noRegBusinessBtn;
    ImageButton userProfilePicBtn;
    ImageView userProfilePic;
    LinearLayout regBusinessLayout;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        user_firstname = (EditText)findViewById(R.id.user_firstname_textinput);
        user_lastname = (EditText)findViewById(R.id.user_lastname_textinput);
        user_phone = (EditText)findViewById(R.id.user_phonenum_textinput);
        user_email = (EditText)findViewById(R.id.user_email_textinput);
        user_password = (EditText)findViewById(R.id.user_password_textinput);
        user_passwordconf = (EditText)findViewById(R.id.user_passwordconf_textinput);
        userPhysAddr = (EditText)findViewById(R.id.user_phys_add_textinput);
        userNationalId = (EditText)findViewById(R.id.user_id_textinput);

        createAccountBtn = (Button)findViewById(R.id.create_account_btn);
        yesRegBusinessBtn = (Button)findViewById(R.id.yes_reg_business_btn);
        noRegBusinessBtn = (Button)findViewById(R.id.no_reg_business_btn);

        userProfilePicBtn = (ImageButton)findViewById(R.id.pick_profilepic_btn);
        userProfilePic = (ImageView)findViewById(R.id.use_profilepic_imageview);

        regBusinessLayout = (LinearLayout)findViewById(R.id.business_reg_layout);

        final String names = user_firstname.getText().toString();
        final String surname = user_lastname.getText().toString();
        final String phone = user_phone.getText().toString();
        final String email = user_email.getText().toString();
        final String pswrd = user_password.getText().toString();
        final String conf_pswrd = user_passwordconf.getText().toString();
        final String physAddrr = userPhysAddr.getText().toString();
        final String nationalId = userNationalId.getText().toString();

        userProfilePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog alertDialogBuilder;
                alertDialogBuilder = new SweetAlertDialog(UserRegistationActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                alertDialogBuilder.setTitleText("Image Source");

                alertDialogBuilder
                        //.setCancelable(false)
                        .setConfirmText("Camera")
                        .setContentText("Choose image source")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, 0);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Browse", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);
                                sDialog.dismissWithAnimation();
                            }
                        }).show();

            }
        });


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Check if all necessary user info has been provided
                if(user_firstname.getText().toString().trim().length() != 0 &&
                        user_lastname.getText().toString().trim().length() != 0 &&
                        user_phone.getText().toString().trim().length() != 0 &&
                        user_email.getText().toString().trim().length() != 0 &&
                        user_password.getText().toString().trim().length() != 0 &&
                        userPhysAddr.getText().toString().trim().length() != 0 &&
                        userNationalId.getText().toString().trim().length() != 0 ) {



                    //Check for password confirmation match
                    if (user_password.getText().toString().equals( user_passwordconf.getText().toString())) {
                        firebaseAuth.createUserWithEmailAndPassword(
                                user_email.getText().toString(), user_password.getText().toString())
                                .addOnCompleteListener(UserRegistationActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {


                                            //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();



                                            final String imageLocation = new String();
                                            //Upload profile pic
                                            if(imageUri != null) {
                                                final StorageReference imageFileRef = FirebaseStorage.getInstance().getReference().child("UserProfilePics/" + System.currentTimeMillis()
                                                        + "." + getFileExtension(imageUri));
                                                imageFileRef.putFile(imageUri)
                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                

                                                                user newUser = new user(user_firstname.getText().toString(),
                                                                        user_lastname.getText().toString(),
                                                                        user_phone.getText().toString(),
                                                                        user_email.getText().toString(),
                                                                        userPhysAddr.getText().toString(),
                                                                        "",
                                                                        userNationalId.getText().toString(),
                                                                        taskSnapshot.getMetadata().getReference().toString());

                                                                firebaseDatabase.getReference("user/" + user_phone.getText()
                                                                        .toString()).setValue(newUser);
                                                            }
                                                        });

                                            }




                                            SweetAlertDialog alertDialogBuilder;
                                            alertDialogBuilder = new SweetAlertDialog(UserRegistationActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                            alertDialogBuilder.setTitleText("Auth Successful!");

                                            alertDialogBuilder
                                                    //.setCancelable(false)
                                                    .setConfirmText("Thanks!, Take me to Chatroom")
                                                    .setContentText("Registration Successful!")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {
                                                            sDialog.dismissWithAnimation();

                                                        }
                                                    })
                                                   .show();

                                        } else {
                                            Toast.makeText(UserRegistationActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(UserRegistationActivity.this, "Passwords don't match",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    SweetAlertDialog alertDialogBuilder;
                    alertDialogBuilder = new SweetAlertDialog(UserRegistationActivity.this, SweetAlertDialog.WARNING_TYPE);
                    alertDialogBuilder.setTitleText("Invalid Info");

                    alertDialogBuilder
                            //.setCancelable(false)
                            .setConfirmText("Ok")
                            .setContentText("The given information is incomplete. Please fill in all required information")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            }).show();

                }

            }
        });

        yesRegBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regBusinessLayout.getVisibility() != View.VISIBLE){
                    regBusinessLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        noRegBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regBusinessLayout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(UserRegistationActivity.this, "Already Signed-In!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 || requestCode == 1 && resultCode ==
                RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(userProfilePic);
        } else {
            Toast.makeText(this,"Image was not picked successfully",Toast.LENGTH_SHORT).show();
        }

    }


}