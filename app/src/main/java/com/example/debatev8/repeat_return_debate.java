//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Database: Saves User/ Finds User individual ID
//1 a) Initial -> Stores User Class Object In database
//1 b) Non-initial -> Gets Users Object
//
public class repeat_return_debate extends AppCompatActivity {

    private Button debateAgainButton;
    private Button quitButton;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
    final String userid = fireUser.getUid();
    final DatabaseReference currentPlayer =databaseUsers.child(userid);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_return_repeat);


        currentPlayer.child("GameID").setValue("0");
        currentPlayer.child("inGame").setValue(0);


        debateAgainButton = (Button) findViewById(R.id.debateAgain);
        debateAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDebatechoice();
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
    public void openDebatechoice(){
        Intent intent = new Intent(this, match_finding.class);
        startActivity(intent);
    }
    //opens judge wait screen
    public void quitChoice(){
        Intent intent = new Intent(this, choice_home.class);
        startActivity(intent);
    }
    //This leads to the leaderboards_debate page

}
