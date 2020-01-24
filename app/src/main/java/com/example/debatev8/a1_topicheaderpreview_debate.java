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

public class a1_topicheaderpreview_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"



    String b1_close;
    String header2;

    EditText arg_a1Input;

    TextView TopicHeader2;



    int timeriterations=0;

    game currentGame;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_topicheaderpreview_a1);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");

        //Calculate Topic//Start//







        TopicHeader2 = (TextView) findViewById(R.id.topicheader);
        TopicHeader2.setText(currentGame.getStages().getStage2().getTopicHeader());





        final Timer myArgTimer = new Timer();
        TimerTask untilArgMade = new TimerTask() {
            @Override
            public void run()
            {

                databaseCurrentGames.child(currentGame.getGameID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot topicInfo : dataSnapshot.getChildren())
                        {
                            Log.i("rrrrrrrrrrrrrrrrrrrrr", "GOT HERE");

                            b1_close = "";

                            Object b1c= dataSnapshot.child("stages").child("stage1").child("resp").getValue();
                            if (b1c!=null)
                            {
                                b1_close = b1c.toString();
                                if (!b1_close.equals("0")) {
                                    Log.i("qqqqqqqqqqqqqqqqqqqq", b1_close);
                                    currentGame.getStages().getStage1().setResp(b1_close);
                                    myArgTimer.cancel();
                                    myArgTimer.purge();
                                    openA1_responsepreview_debate();
                                }

                            }
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






            }
        };//Every Second
        myArgTimer.schedule(untilArgMade, 0, 3000);

                //No timer immediately sent to waiting screen with pre view of next topic header
    }










    public void openA1_responsepreview_debate(){
        Intent intent = new Intent(this, a1_responsepreview_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
