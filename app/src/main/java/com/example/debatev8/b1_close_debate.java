//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class b1_close_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"



    String resp_b1;

    EditText resp_b1Input;
    TextView TopicTitle;
    TextView TopicHeader1;
    TextView open_a1;

    Button submitB;

    int timeriterations=0;

    game currentGame;

    //int myvaluesList[];
    int[] myvaluesList = new int[4];

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_close_b1);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");
        Log.i("bbbbbbbbbbbbbbbbbbbb", currentGame.toString());
        Log.i("ccccccccccccccccccc", currentGame.getPlayer1Topics());
        Log.i("ddddddddddddddddddd", currentGame.getPlayer2Topics());
        Log.i("eeeeeeeeeeeeeeeeeee", currentGame.getGameID());





        resp_b1Input = (EditText) findViewById(R.id.resp_b1);

        TopicHeader1= (TextView) findViewById(R.id.topicheader);
        TopicHeader1.setText(currentGame.getStages().getStage1().getTopicHeader());

        TopicTitle= (TextView) findViewById(R.id.topictitle);
        TopicTitle.setText(currentGame.getStages().getTopicTitle());

        open_a1= (TextView) findViewById(R.id.open_a1);
        open_a1.setText(currentGame.getStages().getStage1().getArg());

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
                        resp_b1 = resp_b1Input.getText().toString();
                        currentGame.getStages().getStage1().setArg(resp_b1);

                        displayText(resp_b1);

                        currentGame.getStages().getStage1().setResp(resp_b1);
                        databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage1").child("resp").setValue(currentGame.getStages().getStage1().getResp());
                        myArgTimer.cancel();
                        myArgTimer.purge();
                        openB2_lead_debate();

                    }
                });
                if (timeriterations>1)
                {
                    myArgTimer.cancel();
                    myArgTimer.purge();
                    currentGame.getStages().getStage1().setResp("I could not think of an argument");
                    databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage1").child("resp").setValue(currentGame.getStages().getStage1().getResp());
                    openB2_lead_debate();
                }
            }
        };//Every Second
        myArgTimer.schedule(untilArgMade, 0, 15000);

        //No timer immediately sent to waiting screen with pre view of next topic header
    }





    private void displayText(String text){
        Toast.makeText(b1_close_debate.this, text, Toast.LENGTH_LONG).show();

    }
    public void openB2_lead_debate(){
        Intent intent = new Intent(this, b2_lead_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
