//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//Description       : This class is the third screen of a debate of player one in the debate stream Round 1
//Inner Workings    :
//                  1) Gets and displays players previous response from last round
//                  2) Waits for player 2 Intro Argument for Round 2
public class a1_responsepreview_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"



    String b2_open;
    TextView B1_Close;

    Button submitB;

    game currentGame;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_responsepreview_a1);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");








        currentGame.getStages().getStage1().getResp();


        B1_Close = (TextView) findViewById(R.id.resp_from_b1);
        B1_Close.setText(currentGame.getStages().getStage1().getResp());


        final Timer myArgTimer = new Timer();
        TimerTask untilArgMade = new TimerTask() {
            @Override
            public void run()
            {


                databaseCurrentGames.child(currentGame.getGameID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot topicInfo : dataSnapshot.getChildren())
                        {

                            b2_open = "";

                            Object b2o= dataSnapshot.child("stages").child("stage2").child("arg").getValue();
                            if (b2o!=null)
                            {
                                b2_open = b2o.toString();
                                if (!b2_open.equals("0")) {
                                    currentGame.getStages().getStage2().setArg(b2_open);
                                    myArgTimer.cancel();
                                    myArgTimer.purge();
                                    openA2_close_debate();
                                }

                            }
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };//Every Second
        myArgTimer.schedule(untilArgMade, 0, 3000);

        //No timer immediately sent to waiting screen with pre view of next topic header

        //Retrieve Topic//End//

    }


    public void openA2_close_debate(){
        Intent intent = new Intent(this, a2_close_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
