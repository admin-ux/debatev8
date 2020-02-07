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

public class choice_leaderboards extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***

    Button debateLeaderBoard;
    Button judgeLeaderBoard;
    Button quit;

    int iterations =0;

    int totalScore,wins,A_avg,R_avg,numGamesPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_choice);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);

        //*Display All Users Debate Scores*//
        debateLeaderBoard = (Button) findViewById(R.id.debateLeaderBoardChoices);
        debateLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDebate_Leaderboards();

            }
        });
        judgeLeaderBoard = (Button) findViewById(R.id.judgeLeaderBoard);
        judgeLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJudgeLeaderboard();

            }
        });
        quit = (Button) findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChoice_home();

            }
        });

        databaseUsers.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot gameInfo : dataSnapshot.getChildren()) {

                    Object ngp = dataSnapshot.child("numGamesPlayed").getValue();
                    Object w = dataSnapshot.child("wins").getValue();
                    Object ts = dataSnapshot.child("totalScore").getValue();
                    Object aa = dataSnapshot.child("a_avg").getValue();
                    Object ra = dataSnapshot.child("r_rvg").getValue();




                    if (iterations==0&&ngp!=null&&w!=null&&ts!=null&&aa!=null&&ra!=null)
                    {
                        iterations=1;

                        totalScore = (int) (long) ts;
                        wins = (int) (long) w;
                        A_avg = (int) (long) aa;

                        R_avg = (int) (long) ra;
                        numGamesPlayed = (int) (long) ngp;
//                        totalScore = (Integer) ngp;
//                        wins = (Integer) w;
//                        A_avg = (Integer) ts;
//                        R_avg = (Integer) aa;
//                        numGamesPlayed = (Integer) ra;

                        //postDebateWins();
                        postDebateTotalScore();
                        //postDebateR_avg();
                        //postDebateA_avg();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    private static final int RC_LEADERBOARD_UI = 9004;

    public void openDebate_Leaderboards(){
        Intent intent = new Intent(this, debate_leaderboards.class);
        startActivity(intent);
    }

    private void showJudgeLeaderboard() {
        //GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
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
    private void postDebateTotalScore ()
    {
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .submitScore(getString(R.string.debate_leaderboard_total_score_id), totalScore);


        }
    }
    private void postDebateWins ()
    {
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .submitScore(getString(R.string.debate_leaderboard_wins_id), wins);


        }
    }
    private void postDebateA_avg ()
    {
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .submitScore(getString(R.string.debate_leaderboard_argument_average_id), A_avg);


        }
    }
    private void postDebateR_avg ()
    {
        GoogleSignInAccount acc2 = GoogleSignIn.getLastSignedInAccount(this);
        if (acc2 != null) {
            Games.getLeaderboardsClient(this, acc2)
                    .submitScore(getString(R.string.debate_leaderboard_respond_average_id), R_avg);


        }
    }
}
