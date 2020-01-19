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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//4 SECTIONS
//1) DETERMINE PLAYER NUMBER TO REDUCE WRITES
//2) PLAYER VOTES
//3) VOTES ARE POSTED
//4) PLAYER WAITS FOR OTHER PLAYER TO POST VOTES THEN TOPIC IS CALCULATED AND NEXT ACTIVITY IS CALLED
public class topic_vote extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    Button topic1, topic2, topic3, topic4;
    //ArrayList<Integer> VotesCast = new ArrayList<Integer>();
    String VotesCastList = "";
    int lenOfArray;
    String gameID;
    String name;
    boolean timer1Ended=false;
    boolean firstloop=true;
    Boolean firstPlayer;
    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");
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
        final game currentGame = (game) getIntent().getSerializableExtra("newGame");
        Log.i("aaaaaaaaaaaaaaaaaaa", currentGame.getGameID());

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


        //1A) DETERMINE PLAYER NUMBER TO REDUCE WRITES//START//
        if (!firstPlayer)
        {
            realtimeUserProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i("<<<<<<<<<<<<<<<<<<<<<<", dataSnapshot.toString());
                    for (DataSnapshot realtimeUserCurrentGameInfo : dataSnapshot.getChildren())
                    {

                        Object b= dataSnapshot.child("GameID").getValue();

                        if (b!=null) {
                            Log.i("nnnnnnnnnnnnnnnnnnnn", b.toString());
                            gameID=b.toString();
                        }

                    }
                    realtimeUserCurrentGame=databaseCurrentGames.child(gameID);

                    //2A) PLAYER VOTES//START//
                    //Topic1 vote
                    lenOfArray = 0;
                    final Timer myVoteTimer = new Timer();
                    TimerTask untilVotesCast = new TimerTask() {
                        @Override
                        public void run()
                        {

                            Log.i("oooooooooooooooo", "While Loop");

                            topic1 = (Button) findViewById(R.id.Topic1);
                            topic1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    displayText("Clicked1");
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
                                    displayText("Clicked2");
                                    topic2.setEnabled(false);
                                }
                            });
                            //Topic3 vote
                            topic3 = (Button) findViewById(R.id.Topic3);
                            topic3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    VotesCastList=VotesCastList.concat("3");
                                    displayText("Clicked3");
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
                                    displayText("Clicked4");
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

                    Log.i("iiiiiiiiiiiiiiiiiiiiiii", "After Timer");

                    //4A) PLAYER WAITS FOR OTHER PLAYER TO POST VOTES THEN TOPIC IS CALCULATED AND NEXT ACTIVITY IS CALLED//START//
                    final Timer myTimerReQuery = new Timer();
                    TimerTask untilReQuery = new TimerTask() {
                        @Override
                        public void run()
                        {
                            Log.i("sssssssssssssssssss", "stop");
                            if (timer1Ended)
                            {
                                //3A) VOTES ARE POSTED//START//
                                if (firstloop)
                                {
                                    realtimeUserCurrentGame.child("player2Topics").setValue(VotesCastList);
                                    firstloop=false;
                                }
                                //3A) VOTES ARE POSTED//END//
                                realtimeUserCurrentGame.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot realtimeUserCurrentGameInfo : dataSnapshot.getChildren()) {
                                            //Check if first player

                                            //String player1ID = (String) realtimeUserCurrentGameInfo.child("player1Topics").getValue();
                                            //Log.i("sssssssssssssssssss", player1ID);
                                            String player1ID="";

                                            Object b= dataSnapshot.child("player1Topics").getValue();

                                            if (b!=null) {
                                                Log.i("nnnnnnnnnnnnnnnnnnnn", b.toString());
                                                player1ID=b.toString();
                                            }
                                            Log.i("qqqqqqqqqqqqqqqq", dataSnapshot.toString());
                                            if (!player1ID.equals("0")) {

                                                //TODO Do player 2 calculation before timer quit
                                                myTimerReQuery.cancel();
                                                myTimerReQuery.purge();
                                                //TODO Add getActivity.b1_close_debate()
                                                openB1_close_debate();
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
            Log.i("ttttttttttttttttttttt", "Here???");
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
                            displayText("Clicked1");
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
                            displayText("Clicked2");
                            topic2.setEnabled(false);
                        }
                    });
                    //Topic3 vote
                    topic3 = (Button) findViewById(R.id.Topic3);
                    topic3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VotesCastList=VotesCastList.concat("3");
                            displayText("Clicked3");
                            topic3.setEnabled(false);
                        }
                    });
                    //Topic4 vote
                    topic4 = (Button) findViewById(R.id.Topic4);
                    topic4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VotesCastList=VotesCastList.concat("4");
                            displayText("Clicked4");
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
                            firstloop=false;
                        }
                        //3B) VOTES ARE POSTED//END//
                        realtimeUserCurrentGame.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot realtimeUserCurrentGameInfo : dataSnapshot.getChildren()) {
                                    //Check if first player


//                                    String player2ID = (String) realtimeUserCurrentGameInfo.child("player2Topics").getValue();
//                                    Log.i("sssssssssssssssssss", player2ID);

                                    String player2ID="";

                                    Object b= dataSnapshot.child("player2Topics").getValue();

                                    if (b!=null) {
                                        Log.i("nnnnnnnnnnnnnnnnnnnn", b.toString());
                                        player2ID=b.toString();
                                    }
                                    Log.i("pppppppppppppppppppppp", dataSnapshot.toString());
                                    if (!player2ID.equals("0")) {
                                        //TODO Do player 1 calculation before timer quit
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
    private void displayText(String text){
        Toast.makeText(topic_vote.this, text, Toast.LENGTH_LONG).show();

    }
    public void openA1_lead_debate(){
        Intent intent = new Intent(this, a1_lead_debate.class);
        startActivity(intent);
    }
    public void openB1_close_debate(){
        Intent intent = new Intent(this, b1_close_debate.class);
        startActivity(intent);
    }

}
