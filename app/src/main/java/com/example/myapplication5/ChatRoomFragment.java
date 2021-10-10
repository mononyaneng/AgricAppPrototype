package com.example.myapplication5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatRoomFragment extends Fragment {
    DatabaseReference databaseReference;
    DatabaseReference usersCloudEdnPoint;
    Button signInBtn;
    Button signUpBtn;
    EditText signInEmail;
    EditText signInPassword;
    user user;

    private FirebaseAuth firebaseAuth;
    private View view;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatRoomFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatRoomFragment newInstance(String param1, String param2) {
        ChatRoomFragment fragment = new ChatRoomFragment();
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
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){

            view = inflater.inflate(R.layout.fragment_cart, container, false);



        }else {
            view = inflater.inflate(R.layout.fragment_chat_room, container, false);
            signUpBtn = (Button)view.findViewById(R.id.signup_btn);
            signInBtn = (Button)view.findViewById(R.id.signin_btn);
            signInEmail = (EditText)view.findViewById(R.id.signin_email_textinput);
            signInPassword = (EditText)view.findViewById(R.id.signin_paswd_textinput);


            databaseReference = FirebaseDatabase.getInstance().getReference();
            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),UserRegistationActivity.class);
                    startActivity(intent);

                }
            });


            firebaseAuth = FirebaseAuth.getInstance();

            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = signInEmail.getText().toString();
                    String password = signInPassword.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        Toast.makeText(getContext(), "Authentication Successful!",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Authentication Failed!",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            });


        }


        return view;

    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){

            Toast.makeText(getContext(), "Already Signed-In!", Toast.LENGTH_SHORT).show();
        }
    }*/
}