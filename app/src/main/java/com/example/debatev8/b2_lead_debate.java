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
import android.widget.TextView;
//import android.widget.Toast;

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

public class b2_lead_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"



    String arg_b2;

    EditText arg_b2Input;
    TextView TopicTitle;
    TextView TopicHeader2;

    Button submitB;

    int timeriterations=0;

    game currentGame;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_lead_b2);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");

        //Calculate Topic//Start//


                arg_b2Input = (EditText) findViewById(R.id.arg_b2);

                TopicHeader2= (TextView) findViewById(R.id.topicheader);
                TopicHeader2.setText(currentGame.getStages().getStage2().getTopicHeader());


                TopicTitle= (TextView) findViewById(R.id.topictitle);
                TopicTitle.setText(currentGame.getStages().getTopicTitle());

                submitB = (Button) findViewById(R.id.submitButton);

                final Timer myArgTimer = new Timer();
                TimerTask untilArgMade = new TimerTask() {
                    @Override
                    public void run()
                    {
                        timeriterations++;
//
                        submitB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                arg_b2 = arg_b2Input.getText().toString();
                                currentGame.getStages().getStage2().setArg(arg_b2);



                                currentGame.getStages().getStage2().setArg(arg_b2);
                                //Log.i("fffffffffffffffffff", databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage2").child("arg").toString());
                                //Log.i("*****************", currentGame.getStages().getStage2().getArg());
                                databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage2").child("arg").setValue(currentGame.getStages().getStage2().getArg());
                                myArgTimer.cancel();
                                myArgTimer.purge();
                                openB2_topicheaderpreview_debate();

                            }
                        });
                        if (timeriterations>1)
                        {
                            myArgTimer.cancel();
                            myArgTimer.purge();
                            currentGame.getStages().getStage2().setArg("I could not think of an argument");
                            //Log.i("gggggggggggggggggggggg", currentGame.getGameID());
                            databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage2").child("arg").setValue(currentGame.getStages().getStage2().getArg());
                            openB2_topicheaderpreview_debate();
                        }
                    }
                };//Every Second
                myArgTimer.schedule(untilArgMade, 0, 15000000);

                //No timer immediately sent to waiting screen with pre view of next topic header
            }







    public void openB2_topicheaderpreview_debate(){
        Intent intent = new Intent(this, b2_topicheaderpreview_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
