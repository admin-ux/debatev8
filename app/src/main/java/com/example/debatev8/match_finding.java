//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.Serializable;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class match_finding extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databasePlayerPosition = databaseRoot.child("PlayerPosition");
    DatabaseReference databasePlayersWaiting = databaseRoot.child("PlayerWaitingList");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");

    game newGame = new game("0", false, false, null, "0", "0", null, "0", "0");
    int playerCurrentPosition;
    String newplayerID;
    Long newPlayerCurrentPosition;
    int count=0;
    boolean newPlayer=false;
    boolean player_InUse;
    boolean inUse=true;
    String inGame;
    //***********************************************************************************************Must save query data
    //Query player = databasePlayersWaiting.orderByValue().limitToLast(1);
   //**********************************************************************************************Have to get player there longest


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        final DatabaseReference currentPlayer =databaseUsers.child(userid);


        //This should happen immediately
        //Getting Player Position Data and incrementing by 1
        //Not Working **********************************************************************
        databasePlayerPosition.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        Log.i("!!!!!!!!!!!!!!!", "*************************************");
                        final Long position = dataSnapshot.getValue(Long.class);

                        //longPlayer = position.getPlayerPosition();
                        playerCurrentPosition = position.intValue() + 1;
                        Log.i("cccccccccccccc", String.valueOf(playerCurrentPosition));

                        //Adding 1 to position because 1 user is now added

                        databasePlayerPosition.setValue(playerCurrentPosition);


                        //*1*Queries Not Matched Players List Finds an empty match *//
                        //This player is odd and will do the searching

                        if (playerCurrentPosition % 2 != 0)
                        {
                            displayText("Should Add Game");


                            Log.i("QQQQQQQQQQQQ", "right outside of loop");

                            //This timer is used to rerun this section of code every second
                            //It will be used to re-query incase our previous query was a "Bad Choice"
                            final Timer myTimerReQuery = new Timer();
                            TimerTask untilReQuery= new TimerTask()
                            {

                                @Override
                                public void run()
                                {

                                    Query player = FirebaseDatabase.getInstance().getReference().child("PlayerWaitingList").orderByChild("positionInList").limitToFirst(1);

                                    //player = databasePlayersWaiting.orderByChild("positionInList").limitToFirst(1);
                                    Log.i("wwwwwwwwwwwwwwwwww", "1 iteration");
                                    Log.d("&&&&&&PLAYER&&&&&&&", player.toString());

                                    //player.addValueEventListener(new ValueEventListener() {
                                    player.addListenerForSingleValueEvent(

                                            new ValueEventListener() {

                                                @Override

                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    //TODO Will be removed just for developer benefit
                                                    if (dataSnapshot.exists()) {
                                                        Log.i("xxxxxxxxx", "it exists");
                                                    }

                                                    Log.i("RRRRRRRRRRRRRRRRRR", "Inside Data Snap");
                                                    for (DataSnapshot userInPlayersWaiting : dataSnapshot.getChildren()) {
                                                        newPlayerCurrentPosition = (Long) userInPlayersWaiting.child("positionInList").getValue();
                                                        newplayerID = (String) userInPlayersWaiting.child("userID").getValue();
                                                        inUse = (boolean) userInPlayersWaiting.child("inUse").getValue();
                                                    }

                                                    String stringNewPlayerCurrentPosition = Long.toString(newPlayerCurrentPosition);

                                                    Log.i("aaaaaaaaaaaaaaaaaaaa", "newplayerID________________________________________________________________________________________________");
                                                    Log.i("aaaaaaaaaaaaaaaaaaaa", newplayerID);
                                                    Log.i("aaaaaaaaaaaaaaaaaaaa", "newPlayerCurrentPosition________________________________________________________________________________________________");
                                                    Log.i("aaaaaaaaaaaaaaaaaaaa", stringNewPlayerCurrentPosition);
                                                    Log.i("bbbbbbbbbbbbbbbbbbbb", "currentuserID|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                                                    Log.i("bbbbbbbbbbbbbbbbbbbb", userid);
                                                    //String newplayerID = hashNewPlayerID.get(0);

                                                    Log.i("LLLLLLLL", "Leaving the loop");
                                                    newPlayer = true;




                                                    if (!inUse)
                                                    {
                                                       databasePlayersWaiting.child(stringNewPlayerCurrentPosition).child("inUse").setValue(true);

                                                        Log.i("yyyyyyyyyyyyyy", "Not In Use");
                                                        //Good choice
                                                        if (newplayerID.compareTo(userid) != 0  && !inUse && newplayerID != null) {//&& newplayerID != null may be a problem
                                                            Log.i("gggggggggggggggg", "Good Choice");
                                                            //##################################
                                                            //Conditions from above need to be read through and adjusted
                                                            //##################################
                                                            //**Need to set a condition if player is null wait 1 second then search again -> ie cant find partner imediately
                                                            //find new players user data with id
                                                            DatabaseReference newPlayer = databaseUsers.child(newplayerID);
                                                            //Generate unique id for game
                                                            String gameID = databaseCurrentGames.push().getKey();

                                                            //Set both players gameId to generated one -> might remove if redundancy
                                                            currentPlayer.child("GameID").setValue(gameID);
                                                            newPlayer.child("GameID").setValue(gameID);

                                                            //Debate Stages and topic have no value will be set as match progresses
                                                            debate_stages emptystages = new debate_stages();
                                                            topic emptytopic = new topic();

                                                            newGame.setGameID(gameID);
                                                            newGame.setGameFull(true);
                                                            newGame.setBeenJudged(false);
                                                            newGame.setStages(emptystages);
                                                            newGame.setPlayer1(userid);
                                                            newGame.setPlayer2(newplayerID);
                                                            newGame.setTopicChoosen(emptytopic);
                                                            newGame.setPlayer1Topics("0");
                                                            newGame.setPlayer2Topics("0");

                                                            //Old object
                                                            //game(gameID, true, false, emptystages, userid, newplayerID, emptytopic);
                                                            //In game set to true=1
                                                            currentPlayer.child("inGame").setValue(1);
                                                            newPlayer.child("inGame").setValue(1);
//                                                            DatabaseReference newPlayerInGame = databaseUsers.child(newplayerID).child("inGame");
//                                                            newPlayerInGame.setValue(true);


                                                            //Saving the new created game
                                                            //databaseUsers.child(userid).setValue(newUser);
                                                            databaseCurrentGames.child(gameID).setValue(newGame);
                                                            //game(String gameID, boolean gameFull, boolean beenJudged, debate_stages stages, String player1, String player2, topic topicChoosen)

                                                            //TODO May switch to a query
                                                            Log.i("KKKKKKKKKKKKKK", stringNewPlayerCurrentPosition);
                                                            databasePlayersWaiting.child(stringNewPlayerCurrentPosition).removeValue();
                                                            //Query removingNewPlayer = FirebaseDatabase.getInstance().getReference().child("PlayerWaitingList").equalTo(position);

                                                            //New game created, players added to game
                                                            //TODO Remove listener should be placed here
                                                            myTimerReQuery.cancel();
                                                            myTimerReQuery.purge();
                                                            Log.i("CCCCCCCCCCCCCCCC", "After listener removed");
                                                            opentopic_vote();
                                                            //openA1_lead_debate();
                                                            //opentopic_vote();
                                                            Log.i("mmmmmmmmmmmmmmmmmm", "should not be here 3g");


                                                        }

                                                        //Bad choice of new user
                                                        else
                                                        {
                                                            try {
                                                                databasePlayersWaiting.child(stringNewPlayerCurrentPosition).child("inUse").setValue(false);
                                                            }
                                                            catch(Exception e) {
                                                                //  Block of code to handle errors
                                                                System.out.println("Player was deleted");
                                                            }

                                                        }

                                                        System.out.println("Done Waiting");


                                                    }
                                                    else
                                                    {


                                                    }


                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    //Should not result in a database error -> should keep searching until list contains
                                                    Log.i("sssssssssssssss", "should not be here");

                                                }
                                            });


                                    //Flush out game/add players

                                }
                            //TODO Optimize the reset of finding a newplayer

                            };//Every Second
                            myTimerReQuery.schedule(untilReQuery,0,3000);




                        }
                        //This player is even and will be added to the database and wait, while continualy
                        //checking if player has been added to a game
                        //May need to switch the names of the
                        else
                        {
                            displayText("Avoiding Everything");
                            Log.i("zzzzzzzzzzzzzzzzzzzz", "Should be added +++++++++++++++++++++++++++++++++++++");
                            //player_InUse=false;
                            player_waiting_list evenPlayer = new player_waiting_list(userid, playerCurrentPosition, false);
                            String stringCurrentPosition = "";
                            stringCurrentPosition = Integer.toString(playerCurrentPosition);
                            //databasePlayersWaiting.setValue(userid);
                            databasePlayersWaiting.child(stringCurrentPosition).setValue(evenPlayer);
                            //On data change of inGame Value to true start new activity
                            Log.i("bbbbbbbbbbbbbbbbbbbb", "currentuserID|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                            Log.i("bbbbbbbbbbbbbbbbbbbb", userid);


                            currentPlayer.child("inGame").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //if in game is true move to next screen

//                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
//                                        inGame = (String) messageSnapshot.child("inGame").getValue();
//                                        Log.i("#######################", messageSnapshot.toString());
//
//                                    }
                                    Long inGame= (Long) dataSnapshot.getValue();
                                    String stringInGame = Long.toString(inGame);
                                    Log.i("(((((((((((((((((((((((", stringInGame);
                                    //Log.i("#######################", dataSnapshot.getValue().toString());
                                    if (stringInGame.equals("1")) {
                                        opentopic_vote();
                                        //openB1_close_debate();
                                        //
                                    }
                                    else{
                                        Log.i("bbbbbbbbbbbbbbbbbbbb", "problem");
                                    }



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Should never error
                    }
                }
        );

//        This removes the listener from above and stops the reading of database
//        data being added or removed once new user selected
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
//        }



        //*2*Once Player is found match created and Topic chosen*//



    }
    private void displayText(String text){
        Toast.makeText(match_finding.this, text, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onBackPressed() {

    }

    public void opentopic_vote(){
        Intent intent = new Intent(this, topic_vote.class);
        //Should send completed object of newGame
        //TODO only responds to 1st player
        Bundle bundle = new Bundle();
        bundle.putSerializable("newGame",newGame);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
