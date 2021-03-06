//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

//4 SECTIONS
//1) DETERMINE PLAYER NUMBER TO REDUCE WRITES
//2) PLAYER VOTES
//3) VOTES ARE POSTED
//4) PLAYER WAITS FOR OTHER PLAYER TO POST VOTES THEN TOPIC IS CALCULATED AND NEXT ACTIVITY IS CALLED



//Description       : This class is the topic vote algorithm class, finds and stores players votes
//Inner Workings    :
//                  1) User picks in order the topics they most want to debate
//                  2) class wait for other user before continuing
public class topic_vote extends AppCompatActivity {

    Button topic1, topic2, topic3, topic4;
    String VotesCastList = "";
    int lenOfArray;
    String gameID;
    game currentGame;
    boolean timer1Ended=false;
    boolean firstloop=true;
    Boolean firstPlayer;
    int itterations=0;

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");
    DatabaseReference databaseCurrentTopics = databaseRoot.child("Topics");
    DatabaseReference realtimeUserCurrentGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_vote);


        final FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        final DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        DatabaseReference realtimeUserGameID =databaseUsers.child(userid).child("GameID");
        //TODO make sure this works gets right value and is allowed to be declared final
        currentGame = (game) getIntent().getSerializableExtra("newGame");

        //Checking what player number the user is for readability of code
        if (currentGame.getPlayer1().equals("0"))
        {
            firstPlayer=false;
        }
        else
        {
            firstPlayer=true;
        }

        //Starts

        databaseCurrentTopics.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot topics : dataSnapshot.getChildren())
                {
                    itterations=0;

                    String Topic1 = "";
                    String Topic2 = "";
                    String Topic3 = "";
                    String Topic4 = "";

                    Object tt1 = dataSnapshot.child("1").child("Topic Title").getValue();
                    Object tt2 = dataSnapshot.child("2").child("Topic Title").getValue();
                    Object tt3 = dataSnapshot.child("3").child("Topic Title").getValue();
                    Object tt4 = dataSnapshot.child("4").child("Topic Title").getValue();

                    if (itterations==0&&tt1 != null && tt2 != null && tt3 != null && tt4 != null)
                    {

                        itterations++;
                        Topic1 = tt1.toString();
                        Topic2 = tt2.toString();
                        Topic3 = tt3.toString();
                        Topic4 = tt4.toString();

                        topic1 = (Button) findViewById(R.id.Topic1);
                        topic2 = (Button) findViewById(R.id.Topic2);
                        topic3 = (Button) findViewById(R.id.Topic3);
                        topic4 = (Button) findViewById(R.id.Topic4);
                        topic1.setText(Topic1);
                        topic2.setText(Topic2);
                        topic3.setText(Topic3);
                        topic4.setText(Topic4);



                        //1A) DETERMINE PLAYER NUMBER TO REDUCE WRITES//START//
                        if (!firstPlayer)
                        {
                            realtimeUserProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot realtimeUserCurrentGameInfo : dataSnapshot.getChildren())
                                    {

                                        Object b= dataSnapshot.child("GameID").getValue();

                                        if (b!=null) {
                                            gameID=b.toString();
                                        }

                                    }
                                    realtimeUserCurrentGame=databaseCurrentGames.child(gameID);
                                    currentGame.setGameID(gameID);

                                            //2A) PLAYER VOTES//START//
                                            //Topic1 vote
                                            lenOfArray = 0;
                                            final Timer myVoteTimer = new Timer();
                                            TimerTask untilVotesCast = new TimerTask() {
                                                @Override
                                                public void run()
                                                {


                                                    topic1 = (Button) findViewById(R.id.Topic1);
                                                    topic1.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            VotesCastList=VotesCastList.concat("1");
                                                            topic1.setEnabled(false);
                                                        }
                                                    });
                                                    //Topic2 vote
                                                    topic2 = (Button) findViewById(R.id.Topic2);
                                                    topic2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            VotesCastList=VotesCastList.concat("2");
                                                            topic2.setEnabled(false);
                                                        }
                                                    });
                                                    //Topic3 vote
                                                    topic3 = (Button) findViewById(R.id.Topic3);
                                                    topic3.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            VotesCastList=VotesCastList.concat("3");
                                                            topic3.setEnabled(false);

                                                        }
                                                    });
                                                    //Topic4 vote
                                                    topic4 = (Button) findViewById(R.id.Topic4);
                                                    topic4.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            //VotesCast.add(4);
                                                            //lenOfArray++;
                                                            VotesCastList=VotesCastList.concat("4");
                                                            topic4.setEnabled(false);

                                                        }
                                                    });
                                                    if (VotesCastList.length()>3)
                                                    {
                                                        myVoteTimer.cancel();
                                                        myVoteTimer.purge();
                                                        timer1Ended=true;
                                                    }
                                                }
                                            };//Every Second
                                            myVoteTimer.schedule(untilVotesCast, 0, 3000);
                                            //2A) PLAYER VOTES//END//



                                    //4A) PLAYER WAITS FOR OTHER PLAYER TO POST VOTES THEN TOPIC IS CALCULATED AND NEXT ACTIVITY IS CALLED//START//
                                    final Timer myTimerReQuery = new Timer();
                                    TimerTask untilReQuery = new TimerTask() {
                                        @Override
                                        public void run()
                                        {

                                            if (timer1Ended)
                                            {
                                                //3A) VOTES ARE POSTED//START//
                                                if (firstloop)
                                                {
                                                    realtimeUserCurrentGame.child("player2Topics").setValue(VotesCastList);
                                                    currentGame.setPlayer2Topics(VotesCastList);
                                                    firstloop=false;
                                                }
                                                //3A) VOTES ARE POSTED//END//
                                                realtimeUserCurrentGame.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot realtimeUserCurrentGameInfo : dataSnapshot.getChildren()) {
                                                            //Check if first player


                                                            String player1TopicsString="";

                                                            Object b= dataSnapshot.child("player1Topics").getValue();

                                                            if (b!=null) {
                                                                player1TopicsString=b.toString();
                                                            }
                                                            if (!player1TopicsString.equals("0")) {

                                                                currentGame.setPlayer1Topics(player1TopicsString);
                                                                myTimerReQuery.cancel();
                                                                myTimerReQuery.purge();
                                                                //TODO Add getActivity.b1_close_debate()
                                                                openB1_topicheaderpreview_debate();
                                                            }

                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        }

                                    };//Every Second
                                    myTimerReQuery.schedule(untilReQuery, 0, 3000);
                                    //4A) PLAYER WAITS FOR OTHER PLAYER TO POST VOTES THEN TOPIC IS CALCULATED AND NEXT ACTIVITY IS CALLED//END//

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //final DatabaseReference realtimeUserCurrentGame = databaseCurrentGames.child(gameID);
                        }
                        //1A) DETERMINE PLAYER NUMBER TO REDUCE WRITES//END//

                        //1B) DETERMINE PLAYER NUMBER TO REDUCE WRITES//START//
                        else
                        {
                            final DatabaseReference realtimeUserCurrentGame = databaseCurrentGames.child(currentGame.getGameID());

                            //2B) PLAYER VOTES//START//
                            //Topic1 vote
                            lenOfArray = 0;
                            final Timer myVoteTimer = new Timer();
                            TimerTask untilVotesCast = new TimerTask() {
                                @Override
                                public void run() {


                                    topic1 = (Button) findViewById(R.id.Topic1);
                                    topic1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            VotesCastList=VotesCastList.concat("1");
                                            topic1.setEnabled(false);
                                        }
                                    });
                                    //Topic2 vote
                                    topic2 = (Button) findViewById(R.id.Topic2);
                                    topic2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            VotesCastList=VotesCastList.concat("2");
                                            topic2.setEnabled(false);
                                        }
                                    });
                                    //Topic3 vote
                                    topic3 = (Button) findViewById(R.id.Topic3);
                                    topic3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            VotesCastList=VotesCastList.concat("3");
                                            topic3.setEnabled(false);
                                        }
                                    });
                                    //Topic4 vote
                                    topic4 = (Button) findViewById(R.id.Topic4);
                                    topic4.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            VotesCastList=VotesCastList.concat("4");
                                            topic4.setEnabled(false);
                                        }
                                    });
                                    if (VotesCastList.length()>3)
                                    {
                                        myVoteTimer.cancel();
                                        myVoteTimer.purge();
                                        timer1Ended=true;
                                    }
                                }
                            };//Every Second
                            myVoteTimer.schedule(untilVotesCast, 0, 3000);

                            //2B) PLAYER VOTES//END//

                            //4B) PLAYER WAITS FOR OTHER PLAYER TO POST VOTES THEN TOPIC IS CALCULATED AND NEXT ACTIVITY IS CALLED//START//
                            final Timer myTimerReQuery = new Timer();
                            TimerTask untilReQuery = new TimerTask() {
                                @Override
                                public void run() {
                                    if (timer1Ended)
                                    {
                                        //3B) VOTES ARE POSTED//START//
                                        if(firstloop)
                                        {
                                            realtimeUserCurrentGame.child("player1Topics").setValue(VotesCastList);
                                            currentGame.setPlayer1Topics(VotesCastList);
                                            firstloop=false;
                                        }
                                        //3B) VOTES ARE POSTED//END//
                                        realtimeUserCurrentGame.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot realtimeUserCurrentGameInfo : dataSnapshot.getChildren()) {
                                                    //Check if first player


                                                    String player2TopicsString="";

                                                    Object b= dataSnapshot.child("player2Topics").getValue();


                                                    if (b!=null) {
                                                        player2TopicsString=b.toString();
                                                    }
                                                    if (!player2TopicsString.equals("0")) {
                                                        currentGame.setPlayer2Topics(player2TopicsString);
                                                        myTimerReQuery.cancel();
                                                        myTimerReQuery.purge();
                                                        //TODO Add getActivity.a1_lead_debate()
                                                        openA1_lead_debate();
                                                    }


                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                            };//Every Second
                            myTimerReQuery.schedule(untilReQuery, 0, 3000);
                            //4B) PLAYER WAITS FOR OTHER PLAYER TO POST VOTES THEN TOPIC IS CALCULATED AND NEXT ACTIVITY IS CALLED//END//


                        }
        //1B) DETERMINE PLAYER NUMBER TO REDUCE WRITES//END//
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }


    public void openA1_lead_debate(){
        Intent intent = new Intent(this, a1_lead_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void openB1_topicheaderpreview_debate(){
        Intent intent = new Intent(this, b1_topicheaderpreview_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
