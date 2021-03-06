//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

//Description       : This class is the forth screen of a debate of player one in the debate stream Round 2
//Inner Workings    :
//                  1) Reads player 2's opening argument
//                  2) Waits for 15 seconds for player to give and submit a response
public class a2_close_debate extends AppCompatActivity {

    String resp_a2;

    EditText resp_a2Input;
    TextView TopicTitle;
    TextView TopicHeader2;
    TextView open_b2;

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
        setContentView(R.layout.debate_close_a2);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");
//





        resp_a2Input = (EditText) findViewById(R.id.resp_a2);

        TopicHeader2= (TextView) findViewById(R.id.topicheader);
        TopicHeader2.setText(currentGame.getStages().getStage2().getTopicHeader());

        TopicTitle= (TextView) findViewById(R.id.topictitle);
        TopicTitle.setText(currentGame.getStages().getTopicTitle());

        open_b2= (TextView) findViewById(R.id.arg_b2);
        open_b2.setText(currentGame.getStages().getStage2().getArg());

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
                        resp_a2 = resp_a2Input.getText().toString();
                        currentGame.getStages().getStage2().setArg(resp_a2);

                        //displayText(resp_a2);

                        currentGame.getStages().getStage2().setResp(resp_a2);
                        databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage2").child("resp").setValue(currentGame.getStages().getStage2().getResp());
                        myArgTimer.cancel();
                        myArgTimer.purge();
                        openA3_lead_debate();

                    }
                });
                if (timeriterations>2)
                {
                    myArgTimer.cancel();
                    myArgTimer.purge();
                    currentGame.getStages().getStage2().setResp("I could not think of an argument");
                    databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage2").child("resp").setValue(currentGame.getStages().getStage2().getResp());
                    openA3_lead_debate();
                }
            }
        };//Every Second
        myArgTimer.schedule(untilArgMade, 0, 15000);

        //No timer immediately sent to waiting screen with pre view of next topic header
    }


    public void openA3_lead_debate(){
        Intent intent = new Intent(this, a3_lead_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
