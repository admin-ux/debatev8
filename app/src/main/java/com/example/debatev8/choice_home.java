//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_choice);



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
}
