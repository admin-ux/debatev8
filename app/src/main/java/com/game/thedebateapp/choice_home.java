//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//Description       : This class is the home selection screen
//Inner Workings    :
//                  1) User selects from the 3 streams


//Database: Saves User/ Finds User individual ID
//1 a) Initial -> Stores User Class Object In database
//1 b) Non-initial -> Gets Users Object
//
public class choice_home extends AppCompatActivity {

    private Button debateButton;
    private Button judgeButton;
    private Button leaderButton;


    int goingBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_choice);


        if (getIntent().getSerializableExtra("goingBack")!=null){
            goingBack = (Integer) getIntent().getSerializableExtra("goingBack");
            if (goingBack==1)
            {
                displayMessage();
            }

        }
        debateButton = (Button) findViewById(R.id.debatestreamButton);
        debateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDebatechoice();


            }

        });
        judgeButton = (Button) findViewById(R.id.judgestreamButton);
        judgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJudgechoice();

            }
        });

        leaderButton = (Button) findViewById(R.id.leaderboardButton);
        leaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeaderBooard();

            }
        });
    }
    //May change this to not allow choice of debate and use algorithm instead
    public void openDebatechoice(){
        Intent intent = new Intent(this, match_finding.class);


        startActivity(intent);
    }

    public void openJudgechoice(){
        Intent intent = new Intent(this, searching_completed_matches.class);
        startActivity(intent);
    }
    //This leads to the leaderboards_debate page
    public void openLeaderBooard(){
        Intent intent = new Intent(this, choice_leaderboards.class);
        startActivity(intent);
    }
    public void displayMessage(){
        Toast.makeText(choice_home.this, "There were no players available, please try agin in a little while.", Toast.LENGTH_LONG).show();
    }
}
