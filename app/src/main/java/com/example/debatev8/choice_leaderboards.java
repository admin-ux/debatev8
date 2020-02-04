//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class choice_leaderboards extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***

    Button debateLeaderBoard;
    Button judgeLeaderBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_choice);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);

        //*Display All Users Debate Scores*//
        debateLeaderBoard = (Button) findViewById(R.id.debateLeaderBoard);
        debateLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDebateLeaderboard();

            }
        });
        judgeLeaderBoard = (Button) findViewById(R.id.judgeLeaderBoard);
        judgeLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJudgeLeaderboard();

            }
        });
    }
    private void displayText(String text){
        Toast.makeText(choice_leaderboards.this, text, Toast.LENGTH_LONG).show();

    }

    private static final int RC_LEADERBOARD_UI = 9004;
    private void showDebateLeaderboard() {
        //GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Log.i("bbbbbbbbbbbbbbbbbbbbb", "Not Null");
            Games.getLeaderboardsClient(this, acc2)
                    .getLeaderboardIntent(getString(R.string.debateleaderboard_id))
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
    private void showJudgeLeaderboard() {
        //GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Log.i("bbbbbbbbbbbbbbbbbbbbb", "Not Null");
            Games.getLeaderboardsClient(this, acc2)
                    .getLeaderboardIntent(getString(R.string.judgeleaderboard_id))
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
