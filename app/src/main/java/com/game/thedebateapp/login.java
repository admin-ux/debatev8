package com.game.thedebateapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

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


//Description       :
//Inner Workings    :
public class login extends AppCompatActivity {

    //****************GOOGLE
    private SignInButton googleSignInButton;
    //private Button emailSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    //    private GoogleSignInAccount mGoogleSignInAccount;
    private  String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    //private Button previousSignIn;
    private int RC_SIGN_IN = 1;
    //***************GOOGLE

    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mSignUpBtn;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //************************GOOGLE

        googleSignInButton = (SignInButton) findViewById(R.id.signInGoogle);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Games.SCOPE_GAMES_LITE)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        //************************GOOGLE

        mEmail = findViewById(R.id.emailAddress);
        mPassword = findViewById(R.id.password);
        mSignUpBtn = findViewById(R.id.SignUp);
        mLoginBtn = findViewById(R.id.Login);
        fAuth = FirebaseAuth.getInstance();


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 6){
                    mPassword.setError("Password must be greater than or equal to 6 characters.");
                    return;
                }

                // Authenticate the User
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Starts this class if correctly signed in

                            startActivity(new Intent(getApplicationContext(),choice_home.class));
                        }else {
                            Log.d("AAAAAAAAAAAAAAAAAAAAAZ", "DANG IT?");

                        }
                    }
                });

            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
    }
    public void openRegister(){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
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
            Log.d("AAAAAAAAAAAAAAAAAAAAAD", "HERE?");
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
            Log.d("AAAAAAAAAAAAAAAAAAAAAC", "HERE?");



        }
        catch (ApiException e){
            Log.d("AAAAAAAAAAAAAAAAAAAAAG", "apiException");
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
                        Log.d("AAAAAAAAAAAAAAAAAAAAAB", "DANG IT");

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

