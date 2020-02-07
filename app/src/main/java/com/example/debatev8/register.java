package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Description       : This class is not used
//Inner Workings    :
public class register extends AppCompatActivity {

    EditText mFullname,mEmail,mPassword1,mPassword2;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //***

        mFullname = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.emailAddress);
        mPassword1 = findViewById(R.id.password1);
        mPassword2 = findViewById(R.id.password2);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText1);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),choice_home.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password1 = mPassword1.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();
                final String fullname = mFullname.getText().toString().trim();//***

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    mPassword1.setError("Password is Required.");
                    return;
                }
                if((password1.compareTo(password2))!= 0){
                    mPassword1.setError("Passwords are not the same.");
                    return;
                }
                if(password1.length() < 6){
                    mPassword1.setError("Password must be greater than or equal to 6 characters.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            //Starts this class if correctly signed in
                            FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userid = fireUser.getUid();//***
                            //This should never show up due to its already being signed in
                            if (fireUser != null) {
                                // User is signed in
                            }

                            // String id = databaseUsers.push().getKey();//***
                             int inGame = 0;
                            //This sets the users Uid as the individual id in the database
                            user newUser = new user(userid,fullname,inGame,email,"0");//***

                            databaseUsers.child(userid).setValue(newUser);//***

                            startActivity(new Intent(getApplicationContext(),choice_home.class));

                        }else {
                            progressBar.setVisibility(View.GONE);
                        }



                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

    }
}
