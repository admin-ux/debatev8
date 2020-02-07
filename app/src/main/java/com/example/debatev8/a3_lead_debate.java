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

public class a3_lead_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"



    String arg_a3;

    EditText arg_a3Input;
    TextView TopicTitle;
    TextView TopicHeader3;

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
        setContentView(R.layout.debate_lead_a3);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");
//        Log.i("bbbbbbbbbbbbbbbbbbbb", currentGame.toString());
//        Log.i("ccccccccccccccccccc", currentGame.getPlayer1Topics());
//        Log.i("ddddddddddddddddddd", currentGame.getPlayer2Topics());
//        Log.i("eeeeeeeeeeeeeeeeeee", currentGame.getGameID());

        //Calculate Topic//Start//


        arg_a3Input = (EditText) findViewById(R.id.arg_a3);

        TopicHeader3= (TextView) findViewById(R.id.topicheader);
        TopicHeader3.setText(currentGame.getStages().getStage3().getTopicHeader());

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
                        arg_a3 = arg_a3Input.getText().toString();
                        currentGame.getStages().getStage3().setArg(arg_a3);

//                        displayText(arg_a3);

                        currentGame.getStages().getStage3().setArg(arg_a3);
                        databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage3").child("arg").setValue(currentGame.getStages().getStage3().getArg());
                        myArgTimer.cancel();
                        myArgTimer.purge();
                        OpenA3_waitinglastresp_debate();

                    }
                });
                if (timeriterations>1)
                {
                    myArgTimer.cancel();
                    myArgTimer.purge();
                    currentGame.getStages().getStage3().setArg("I could not think of an argument");
                    databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage3").child("arg").setValue(currentGame.getStages().getStage3().getArg());
                    OpenA3_waitinglastresp_debate();
                }
            }
        };//Every Second
        myArgTimer.schedule(untilArgMade, 0, 15000000);

        //No timer immediately sent to waiting screen with pre view of next topic header
    }







//    private void displayText(String text){
//        Toast.makeText(a3_lead_debate.this, text, Toast.LENGTH_LONG).show();
//
//    }
    public void OpenA3_waitinglastresp_debate(){
        Intent intent = new Intent(this, a3_waitinglastresp_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
