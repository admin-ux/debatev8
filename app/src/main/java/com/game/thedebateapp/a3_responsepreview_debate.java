//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;


//Description       : This class is the sixth screen of a debate of player one in the debate stream Round 3
//Inner Workings    :
//                  1) Gets and displays players previous response from last round
//                  2) Waits for player 2 Intro Argument for Round 3
public class a3_responsepreview_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"



    TextView B3_Close;

    Button submitB;

    game currentGame;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_responsepreview_a3);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");








        currentGame.getStages().getStage3().getResp();


        B3_Close = (TextView) findViewById(R.id.resp_from_b3);
        B3_Close.setText(currentGame.getStages().getStage3().getResp());

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseCurrentGames.child(currentGame.getGameID()).child("gameFinished").setValue(true);
                openRepeat_return_debate();

            }
        });

        //No timer immediately sent to waiting screen with pre view of next topic header

        //Retrieve Topic//End//

    }









    public void openRepeat_return_debate(){
        Intent intent = new Intent(this, repeat_return_debate.class);
        startActivity(intent);
    }


}
