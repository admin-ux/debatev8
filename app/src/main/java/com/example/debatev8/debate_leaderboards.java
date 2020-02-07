//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class debate_leaderboards extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***

    Button debateLeaderBoardTotalScore;
    Button debateLeaderBoardWins;
    Button debateLeaderBoardA_avg;
    Button debateLeaderBoardR_avg;
    Button quit;

    int iterations =0;

    int totalScore,wins,A_avg,R_avg,numGamesPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_debate);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);

        //*Display All Users Debate Scores*//
        debateLeaderBoardTotalScore = (Button) findViewById(R.id.debateLeaderBoardTotalScore);
        debateLeaderBoardTotalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDebate_Leaderboard_Total_Score();

            }
        });
        debateLeaderBoardWins = (Button) findViewById(R.id.debateLeaderBoardWins);
        debateLeaderBoardWins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDebate_Leaderboard_Wins();

            }
        });
        debateLeaderBoardA_avg = (Button) findViewById(R.id.debateLeaderBoardA_avg);
        debateLeaderBoardA_avg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDebate_Leaderboard_Argument_Average();

            }
        });
        debateLeaderBoardR_avg = (Button) findViewById(R.id.debateLeaderBoardR_avg);
        debateLeaderBoardR_avg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDebate_Leaderboard_Respond_Average();

            }
        });
        quit = (Button) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChoice_home();

            }
        });



    }



    private static final int RC_LEADERBOARD_UI = 9004;
    private void showDebate_Leaderboard_Total_Score() {
        //GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .getLeaderboardIntent(getString(R.string.debate_leaderboard_total_score_id))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADERBOARD_UI);
                        }
                    });
        }

    }
    private void showDebate_Leaderboard_Wins() {
        //GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .getLeaderboardIntent(getString(R.string.debate_leaderboard_wins_id))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADERBOARD_UI);
                        }
                    });
        }

    }
    private void showDebate_Leaderboard_Argument_Average() {
        //GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .getLeaderboardIntent(getString(R.string.debate_leaderboard_argument_average_id))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADERBOARD_UI);
                        }
                    });
        }

    }
    private void showDebate_Leaderboard_Respond_Average() {

        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .getLeaderboardIntent(getString(R.string.debate_leaderboard_respond_average_id))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADERBOARD_UI);
                        }
                    });
        }

    }

    private void showJudgeLeaderboard() {

        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .getLeaderboardIntent(getString(R.string.judgeleaderboard_id))
                    .addOnSuccessListener(new OnSuccessListener<Intent>() {
                        @Override
                        public void onSuccess(Intent intent) {
                            startActivityForResult(intent, RC_LEADERBOARD_UI);
                        }
                    });
        }

    }
    public void openChoice_home(){
        Intent intent = new Intent(this, choice_home.class);
        startActivity(intent);
    }

}
