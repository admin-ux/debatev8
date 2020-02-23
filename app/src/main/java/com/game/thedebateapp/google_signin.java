package com.game.thedebateapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


//Description       : This class is the signin page
//Inner Workings    :
//                  1) User is signed into google games and google play

public class google_signin extends AppCompatActivity {
    private SignInButton signInButton;
    private Button emailSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    //    private GoogleSignInAccount mGoogleSignInAccount;
    private  String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private Button previousSignIn;
    private int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_google);

        signInButton = (SignInButton) findViewById(R.id.signInGoogle);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Games.SCOPE_GAMES_LITE)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();


            }
        });
        emailSignIn = (Button) findViewById(R.id.emailSignInButton);
        emailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();


            }
        });
        previousSignIn = (Button) findViewById(R.id.previouslySignedInButton);
        previousSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogle_signin();


            }
        });


    }

    private void signIn(){


        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);



        }
        catch (ApiException e){

            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        //updateUI(user);
                        //Starts next screen
                        openChoice_home();
                    } else {

                        // updateUI(null);
                    }
                }
            });
        }

    }



    public void openChoice_home(){
        Intent intent = new Intent(this, choice_home.class);
        startActivity(intent);
    }
    public void openLogin(){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
    public void openGoogle_signin(){
        Intent intent = new Intent(this, google_signin.class);
        startActivity(intent);
    }
}










