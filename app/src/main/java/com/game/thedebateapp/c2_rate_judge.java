//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

//Description       : This class is the second screen of the judge stream
//Inner Workings    :
//                  1) Waits for a judge to select scores for each player
//                  2) Adds scores to gameBeingJudged object
public class c2_rate_judge extends AppCompatActivity {

    SeekBar seekbar1,seekbar2;
    TextView Argb2, Respa2, TopicHeader2, TopicTitle;
    Boolean ValueChanged1=Boolean.FALSE;
    Boolean ValueChanged2=Boolean.FALSE;
    Integer JudgeA=0;
    Integer JudgeR=0;
    Button submitB;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");

    game gameBeingJudged;

    int timeriterations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_rate_c2);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setVisibility(View.GONE);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        gameBeingJudged = (game) getIntent().getSerializableExtra("gameBeingJudged");


        TopicHeader2= (TextView) findViewById(R.id.topicheader);
        TopicHeader2.setText(gameBeingJudged.getStages().getStage2().getTopicHeader());

        TopicTitle= (TextView) findViewById(R.id.topictitle);
        TopicTitle.setText(gameBeingJudged.getStages().getTopicTitle());

        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);
        //final TextView seekbar1Value = (TextView)findViewById(R.id.seekbarvalue);
        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        //Value of Arg/ Resp changed by database
        Argb2 = (TextView) findViewById(R.id.b2leadpoint);
        Argb2.setText(gameBeingJudged.getStages().getStage2().getArg());

        Respa2 = (TextView) findViewById(R.id.a2responsepoint);
        Respa2.setText(gameBeingJudged.getStages().getStage2().getResp());


        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openC3Rate();
            }
        });

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ValueChanged1=Boolean.TRUE;
                if (ValueChanged2){
                    submitB.setVisibility(View.VISIBLE);}

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (ValueChanged1 && ValueChanged2){
                    submitB.setVisibility(View.VISIBLE);}
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (ValueChanged1 && ValueChanged2){
                    submitB.setVisibility(View.VISIBLE);}

            }
        });
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ValueChanged2=Boolean.TRUE;
                if (ValueChanged1){
                    submitB.setVisibility(View.VISIBLE);}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (ValueChanged1 && ValueChanged2){
                    submitB.setVisibility(View.VISIBLE);}
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (ValueChanged1 && ValueChanged2){
                    submitB.setVisibility(View.VISIBLE);}
            }
        });

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
                        JudgeA = seekbar1.getProgress();
                        JudgeR = seekbar2.getProgress();
                        gameBeingJudged.getStages().getStage2().setJudgeScoreA(JudgeA);
                        gameBeingJudged.getStages().getStage2().setJudgeScoreR(JudgeR);
                        myArgTimer.cancel();
                        myArgTimer.purge();
                        openC3Rate();

                    }
                });
                if (timeriterations>1)
                {
                    myArgTimer.cancel();
                    myArgTimer.purge();
                    openC3Rate();
                }
            }
        };//Every Second
        myArgTimer.schedule(untilArgMade, 0, 20000000);

        //No timer immediately sent to waiting screen with pre view of next topic header
    }



    public void openC3Rate(){
        Intent intent = new Intent(this, c3_rate_judge.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("gameBeingJudged",gameBeingJudged);
        intent.putExtras(bundle);
        startActivity(intent);;
    }

}
