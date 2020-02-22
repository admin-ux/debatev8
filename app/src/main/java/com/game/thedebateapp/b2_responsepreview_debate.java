//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;


//Description       : This class is the fifth screen of a debate of player two in the debate stream Round 2
//Inner Workings    :
//                  1) Gets and displays players previous response from last round
//                  2) Waits for player 2 Intro Argument for Round 3
public class b2_responsepreview_debate extends AppCompatActivity {


    String a3_open;
    TextView A2_Close;

    Button submitB;

    game currentGame;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_responsepreview_b2);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");








        currentGame.getStages().getStage2().getResp();


        A2_Close = (TextView) findViewById(R.id.resp_from_a2);
        A2_Close.setText(currentGame.getStages().getStage2().getResp());


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

                            a3_open = "";

                            Object a3o= dataSnapshot.child("stages").child("stage3").child("arg").getValue();
                            if (a3o!=null)
                            {
                                a3_open = a3o.toString();
                                if (!a3_open.equals("0")) {
                                    currentGame.getStages().getStage3().setArg(a3_open);
                                    myArgTimer.cancel();
                                    myArgTimer.purge();
                                    openB3_close_debate();
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




    public void openB3_close_debate(){
        Intent intent = new Intent(this, b3_close_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
