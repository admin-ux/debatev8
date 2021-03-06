//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
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

//Description       : This class is the fourth screen of a debate of player two in the debate stream Round 2
//Inner Workings    :
//                  1) Builds currentGames object from firebase
//                  2) Waits for player one to submit an opening argument
public class b2_topicheaderpreview_debate extends AppCompatActivity {

    String a2_close;
    TextView TopicHeader3;
    TextView TopicTitle;
    game currentGame;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_topicheaderpreview_b2);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");

        //Calculate Topic//Start//


        TopicHeader3 = (TextView) findViewById(R.id.topicheader);
        TopicHeader3.setText(currentGame.getStages().getStage3().getTopicHeader());

        TopicTitle= (TextView) findViewById(R.id.topictitle);
        TopicTitle.setText(currentGame.getStages().getTopicTitle());


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

                            a2_close = "";

                            Object a2c= dataSnapshot.child("stages").child("stage2").child("resp").getValue();
                            if (a2c!=null)
                            {
                                a2_close = a2c.toString();
                                if (!a2_close.equals("0")) {
                                    currentGame.getStages().getStage2().setResp(a2_close);
                                    myArgTimer.cancel();
                                    myArgTimer.purge();
                                    openB2_responsepreview_debate();
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
    }



    public void openB2_responsepreview_debate(){
        Intent intent = new Intent(this, b2_responsepreview_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
