package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PlayGamesAuthProvider;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URI;

public class google_signin3_yt_easylearn extends AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;
    private  String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private Button btnSignOut, showLeaderBoard;
    private int RC_SIGN_IN = 1;
    private Games games;
    private LeaderboardsClient leaderboardsClient;
    private FirebaseAnalytics mFirebaseAnalytics;
    private GoogleSignInAccount acc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_google);

        signInButton = findViewById(R.id.signInGoogle);
        mAuth = FirebaseAuth.getInstance();
        showLeaderBoard = findViewById(R.id.showLeaderBoard);
        btnSignOut = findViewById(R.id.signOutGoogle);
//
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestServerAuthCode(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
//        GoogleSignInOptions gso = new GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(Games.SCOPE_GAMES_LITE)
//                .requestEmail()
//                .build();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
//                .requestServerAuthCode(getString(R.string.default_web_client_id))
//                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        Bundle bundle = new Bundle();
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);


//        leaderboardsClient.getLeaderboardsClient(this,gso);
//        leaderboardsClient.getAllLeaderboardsIntent();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });
        showLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLeaderboard();
//                Bundle bundle = new Bundle();
//                bundle.putLong(FirebaseAnalytics.Param.SCORE, score);
//                bundle.putString("leaderboard_id", getString(R.string.leaderboard_id));
//                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.POST_SCORE, bundle);
//                mFirebaseAnalytics.getFirebaseInstanceId().

            }
        });


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Toast.makeText(google_signin3_yt_easylearn.this,"You are Logged Out",Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void signIn(){
//        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
//                GoogleSignInOptions.DEFAULT_SIGN_IN);

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
            Toast.makeText(google_signin3_yt_easylearn.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();

            FirebaseGoogleAuth(acc);
//            if (acc !=null) {
//                //Games games1 = new Games();
//                Games.getLeaderboardsClient(this,acc);
//                //leaderboardsClient.
//            }




        }
        catch (ApiException e){
            Toast.makeText(google_signin3_yt_easylearn.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            Log.i("aaaaaaaaaaaaaaaaaaaaa", e.toString());
            Log.i("bbbbbbbbbbbbbbbbbbbbb", "Sign in Result = "+e.getStatusCode());
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
                        Toast.makeText(google_signin3_yt_easylearn.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(google_signin3_yt_easylearn.this, "Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(google_signin3_yt_easylearn.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser fUser){
        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Toast.makeText(google_signin3_yt_easylearn.this,personName + personEmail ,Toast.LENGTH_SHORT).show();
        }

    }
    private static final int RC_LEADERBOARD_UI = 9004;

    private void showLeaderboard() {
        //GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);

        if (acc != null) {
            Log.i("bbbbbbbbbbbbbbbbbbbbb", "Not Null");
            Games.getLeaderboardsClient(this, acc)
                    .getLeaderboardIntent(getString(R.string.leaderboard_id))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADERBOARD_UI);
                        }
                    });
        }
        else{
            Log.i("eeeeeeeeeeeeeee", "Is Null");
        }
    }
}










