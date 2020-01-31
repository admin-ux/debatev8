//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PlayGamesAuthProvider;

public class google_signin2 extends AppCompatActivity {


    private SignInButton signin;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private  String TAG = "MainActivity";
    //Button singin;
    private Button singout;
    private int RC_SIGN_IN=1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_google);




        signin = findViewById(R.id.signInGoogle);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
                //= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                //.requestServerAuthCode(getString(R.string.default_web_client_id))

//Original line from YT Guide
                //            .requestIdToken(getString(R.string.default_web_client_id))
                //.requestEmail()
                //.build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,signInOptions);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                //openChoice_home();
            }
        });
        singout = (Button) findViewById(R.id.signOutGoogle);
        singout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText(google_signin2.this, "Signed Out", Toast.LENGTH_SHORT).show();
                singout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void signIn(){
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN ) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                GoogleSignInAccount signedInAccount = result.getSignInAccount();

            }
            else {
                Log.i("dddddddddddddddddd", "Was unsuccessful onActivityResult");
            }



//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);

        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completetask){
        try {

            GoogleSignInAccount acc = completetask.getResult(ApiException.class);
            Toast.makeText(google_signin2.this, "Signed in Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(google_signin2.this, "Signed in Failed #####", Toast.LENGTH_SHORT).show();
            Log.i("aaaaaaaaaaaaaaaaaaaaa", e.toString());
            Log.i("bbbbbbbbbbbbbbbbbbbbb", "Sign in Result = "+e.getStatusCode());
            //FirebaseGoogleAuth(null);

        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        //Original YT Guides Line
        //AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        //New may remove it
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential authCredential = PlayGamesAuthProvider.getCredential(acct.getServerAuthCode());
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(google_signin2.this, "Successfull", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    Toast.makeText(google_signin2.this, "Failed", Toast.LENGTH_SHORT).show();

                    updateUI(null);
                }
            }
        });
    }
    private  void updateUI (FirebaseUser fUser){
        singout.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if(account != null ){
            //String personName = account.getDisplayName();
            //String personGivenName = account.getGivenName();
            //String personFamilyName = account.getFamilyName();
            //String personEmail = account.getEmail();
            //String personId = account.getId();

            Toast.makeText(google_signin2.this,  "Here((((", Toast.LENGTH_SHORT).show();

        }

    }


    public void openChoice_home(){
        Intent intent = new Intent(this, choice_home.class);
        startActivity(intent);
    }



    private void displayText(String text){
        Toast.makeText(google_signin2.this, text, Toast.LENGTH_LONG).show();
    }
}
