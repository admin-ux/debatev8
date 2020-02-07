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

//Description       : This class is the seventh screen of a debate of player one in the debate stream Round 3
//Inner Workings    :
//                  1) Displays player 2 Response from Round 3
//                  2) Waits for user to click continue
public class a3_waitinglastresp_debate extends AppCompatActivity {

    String b3_close;


    game currentGame;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_waitinglastresp_a3);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");




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


                            b3_close = "";

                            Object b3c= dataSnapshot.child("stages").child("stage3").child("resp").getValue();
                            if (b3c!=null)
                            {
                                b3_close = b3c.toString();
                                if (!b3_close.equals("0")) {

                                    currentGame.getStages().getStage3().setResp(b3_close);
                                    myArgTimer.cancel();
                                    myArgTimer.purge();
                                    openA3_responsepreview_debate();
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










    public void openA3_responsepreview_debate(){
        Intent intent = new Intent(this, a3_responsepreview_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
