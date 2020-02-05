//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.games.Game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Database: Saves User/ Finds User individual ID
//1 a) Initial -> Stores User Class Object In database
//1 b) Non-initial -> Gets Users Object
//
public class return_repeat_judge extends AppCompatActivity {

    Button judgeAgainButton;
    Button quitButton;

    game gameBeingJudged;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_repeat_return);



        gameBeingJudged = (game) getIntent().getSerializableExtra("gameBeingJudged");
        Log.i("111111111111111111111", gameBeingJudged.toString());

        //***Scoring
        int aTotal = gameBeingJudged.getStages().getStage1().getJudgeScoreA()+gameBeingJudged.getStages().getStage2().getJudgeScoreR()+gameBeingJudged.getStages().getStage3().getJudgeScoreA();
        int bTotal= gameBeingJudged.getStages().getStage1().getJudgeScoreR()+gameBeingJudged.getStages().getStage2().getJudgeScoreA()+gameBeingJudged.getStages().getStage3().getJudgeScoreR();

        int a_A_avg=(gameBeingJudged.getStages().getStage1().getJudgeScoreA()+gameBeingJudged.getStages().getStage3().getJudgeScoreA())/2;
        int b_A_avg=gameBeingJudged.getStages().getStage2().getJudgeScoreA();
        int a_R_avg=gameBeingJudged.getStages().getStage2().getJudgeScoreR();
        int b_R_avg=(gameBeingJudged.getStages().getStage1().getJudgeScoreR()+gameBeingJudged.getStages().getStage3().getJudgeScoreR())/2;
        int a_r_w=0;
        int b_r_w=0;
        //**Round 1
        if (gameBeingJudged.getStages().getStage1().getJudgeScoreA()>gameBeingJudged.getStages().getStage1().getJudgeScoreR())
        {
            a_r_w++;
        }
        if  (gameBeingJudged.getStages().getStage1().getJudgeScoreA()<gameBeingJudged.getStages().getStage1().getJudgeScoreR()){
            b_r_w++;
        }
        //*
        //**Round 2
        if (gameBeingJudged.getStages().getStage2().getJudgeScoreR()>gameBeingJudged.getStages().getStage2().getJudgeScoreA())
        {
            a_r_w++;
        }
        if (gameBeingJudged.getStages().getStage2().getJudgeScoreR()<gameBeingJudged.getStages().getStage2().getJudgeScoreA())
        {
            b_r_w++;
        }
        //*
        //**Round 3
        if (gameBeingJudged.getStages().getStage3().getJudgeScoreA()>gameBeingJudged.getStages().getStage3().getJudgeScoreR())
        {
            a_r_w++;
        }
        if (gameBeingJudged.getStages().getStage3().getJudgeScoreA()<gameBeingJudged.getStages().getStage3().getJudgeScoreR())
        {
            b_r_w++;
        }
        //*
        //**Winner Determined

        if (a_r_w!=b_r_w)
        {
            if (a_r_w>b_r_w)
            {
                //Add Win to player1
            }
            else{
                //Add Win to player2
            }
        }
        else {
            if (aTotal!=bTotal)
            {
                if (aTotal>bTotal)
                {
                    //Add Win to player1 (Lesser Points)
                }
                else {
                    //Add Win to player2 (Lesser Points)
                    }
            }
            else {
               //Add a Tie Game
            }
        }
        //*

        //**
        //***Deleting Game

        //**




        judgeAgainButton = (Button) findViewById(R.id.judgeAgain);
        judgeAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJudgechoice();


            }

        });
        quitButton = (Button) findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitChoice();

            }
        });

    }
    //May change this to not allow choice of debate and use algorithm instead
    public void openJudgechoice(){
        Intent intent = new Intent(this, searching_completed_matches.class);


        startActivity(intent);
    }
    //opens judge wait screen
    public void quitChoice(){
        Intent intent = new Intent(this, choice_home.class);
        startActivity(intent);
    }
    //This leads to the leaderboards_debate page

}
