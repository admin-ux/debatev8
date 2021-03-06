//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.game.thedebateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//Description       : This class is the match making algorithm that finds other users and builds debates
//Inner Workings    :
//                  1) PlayerPosition is checked on firebase
//                  2) If Even added to waiting list
//                  3) If odd search waiting list
//                  4) If good match is found by querying firebase the odd player -> player 1 creates a game and adds player 2 to it
//                  5) Player 2 is removed from waiting list
//                  6) If even waits to be found by even player and added to a game
//TODO Fix resuming without permission
public class match_finding extends AppCompatActivity {


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databasePlayerPosition = databaseRoot.child("PlayerPosition");
    DatabaseReference databasePlayersWaiting = databaseRoot.child("PlayerWaitingList");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");
    DatabaseReference databaseUnfulfilled = databaseRoot.child("UnfulfilledPositions");

    game newGame = new game("0", false, false,false, null, "0", "0", "0", "0",0,0,0,0,0);
    int playerCurrentPosition;
    String newplayerID;
    int iterations=0;
    int goingBack=0;
    Long newPlayerCurrentPosition;
    boolean newPlayer=false;
    boolean inUse=true;
    String userid;
    int StartingActivityIf_1=0;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finding_match);


        title = (TextView) findViewById(R.id.title_finding_match);

        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        final DatabaseReference currentPlayer =databaseUsers.child(userid);
        goingBack=0;



        databasePlayerPosition.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        final Long position = dataSnapshot.getValue(Long.class);



                        final Query unfulfilledPosition = FirebaseDatabase.getInstance().getReference().child("UnfulfilledPositions").limitToFirst(1);



                        //************Currently Here*********************
                        //Query for UnfulfilledPositions List
                        //ELSE NULL -> use PlayerPosition
                        //If Not NULL -> Remove First Query and use as Position
                        //Continue as normal
                        //*****
                        //Later Add in positions if user quits when not matched
                        //or if user is kicked add to Unfulfilled list
                        //***************************************************

                        unfulfilledPosition.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
                                for (DataSnapshot value : dataSnapshot.getChildren()) {
                                    final Long unfulfilledPositionCollected = (Long) value.getValue();


                                    //**********************************************************************************
                                    //TODO Add all code to here than continue above plan
                                    //**********************************************************************************


                                    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

                                    //Checking if unfulfilledPosisitions List is empty
                                    if (dataSnapshot.getValue() !=null && unfulfilledPositionCollected.intValue()!=0 && unfulfilledPositionCollected.intValue()!=9999)
                                    {
                                        //If not empty first number is used as new position
                                        playerCurrentPosition=unfulfilledPositionCollected.intValue();
                                        //Then unfulfilledPosition Value is removed from its own list as now it
                                        //is being used and hopefully, depending if the player finds a match,
                                        //fulfilled
                                        value.getRef().setValue(null);


                                    }
                                    else {
                                        playerCurrentPosition = position.intValue() + 1;
                                        databasePlayerPosition.setValue(playerCurrentPosition);


                                    }

                                    //Adding 1 to position because 1 user is now added




                                    //*1*Queries Not Matched Players List Finds an empty match *//
                                    //This player is odd and will do the searching

                                    if (playerCurrentPosition % 2 != 0)
                                    {
//                            displayText("Should Add Game");



                                        //This timer is used to rerun this section of code every second
                                        //It will be used to re-query incase our previous query was a "Bad Choice"
                                        final Timer myTimerReQuery = new Timer();
                                        TimerTask untilReQuery= new TimerTask()
                                        {

                                            @Override
                                            public void run()
                                            {
                                                //Toast.makeText(match_finding.this, "a", Toast.LENGTH_LONG).show();
                                                //displayMessage();
                                                iterations++;
                                                if(iterations>5)
                                                {
                                                    //String String_playerCurrentPosition=String.valueOf(playerCurrentPosition);
                                                    databaseUnfulfilled.child("Value"+playerCurrentPosition).setValue(playerCurrentPosition);

                                                    goingBack=1;
                                                    myTimerReQuery.cancel();
                                                    myTimerReQuery.purge();
                                                    openChoice_home();


                                                }

                                                final Query player = FirebaseDatabase.getInstance().getReference().child("PlayerWaitingList").orderByChild("positionInList").limitToFirst(1);

                                                player.addListenerForSingleValueEvent(

                                                        new ValueEventListener() {

                                                            @Override

                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                for (DataSnapshot userInPlayersWaiting : dataSnapshot.getChildren()) {
                                                                    newPlayerCurrentPosition = (Long) userInPlayersWaiting.child("positionInList").getValue();
                                                                    newplayerID = (String) userInPlayersWaiting.child("userID").getValue();
                                                                    inUse = (boolean) userInPlayersWaiting.child("inUse").getValue();
                                                                }
                                                                String stringNewPlayerCurrentPosition="Null";
                                                                if (newPlayerCurrentPosition!=null) {
                                                                    stringNewPlayerCurrentPosition = Long.toString(newPlayerCurrentPosition);
                                                                }

                                                                newPlayer = true;


                                                                if (!inUse && !stringNewPlayerCurrentPosition.equals("Null") )
                                                                {
                                                                    databasePlayersWaiting.child(stringNewPlayerCurrentPosition).child("inUse").setValue(true);

                                                                    //Good choice
                                                                    if (newplayerID.compareTo(userid) != 0  && !inUse && newplayerID != null)
                                                                    {


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
                                                                        //newGame.setTopicChoosen(emptytopic);
                                                                        newGame.setPlayer1Topics("0");
                                                                        newGame.setPlayer2Topics("0");


                                                                        currentPlayer.child("inGame").setValue(1);
                                                                        newPlayer.child("inGame").setValue(1);
//
                                                                        //Saving the new created game
                                                                        //databaseUsers.child(userid).setValue(newUser);
                                                                        databaseCurrentGames.child(gameID).setValue(newGame);
                                                                        //game(String gameID, boolean gameFull, boolean beenJudged, debate_stages stages, String player1, String player2, topic topicChoosen)


                                                                        databasePlayersWaiting.child(stringNewPlayerCurrentPosition).removeValue();
                                                                        //Query removingNewPlayer = FirebaseDatabase.getInstance().getReference().child("PlayerWaitingList").equalTo(position);

                                                                        //New game created, players added to game
                                                                        //TODO Remove listener should be placed here
                                                                        myTimerReQuery.cancel();
                                                                        myTimerReQuery.purge();

                                                                        opentopic_vote();


                                                                    }

                                                                    //Bad choice of new user
                                                                    else
                                                                    {
                                                                        try {
                                                                            databasePlayersWaiting.child(stringNewPlayerCurrentPosition).child("inUse").setValue(false);
                                                                        }
                                                                        catch(Exception e) {
                                                                            //  Block of code to handle errors
                                                                            //System.out.println("Player was deleted");
                                                                        }
                                                                    }
                                                                    System.out.println("Done Waiting");
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                //Should not result in a database error -> should keep searching until list contains


                                                            }
                                                        });
                                                //Flush out game/add players

                                            }
                                            //TODO Optimize the reset of finding a newplayer

                                        };//Every Second
                                        myTimerReQuery.schedule(untilReQuery,0,3000);
//                            warningMessage.setVisibility(View.VISIBLE);
//                            title.setVisibility(View.GONE);




                                    }
                                    //This player is even and will be added to the database and wait, while continualy
                                    //checking if player has been added to a game
                                    //May need to switch the names of the
                                    else
                                    {
                                        //displayText("Avoiding Everything");
                                        //player_InUse=false;
                                        player_waiting_list evenPlayer = new player_waiting_list(userid, playerCurrentPosition, false);
                                        String stringCurrentPosition = "";
                                        stringCurrentPosition = Integer.toString(playerCurrentPosition);
                                        //databasePlayersWaiting.setValue(userid);
                                        databasePlayersWaiting.child(stringCurrentPosition).setValue(evenPlayer);
                                        //On data change of inGame Value to true start new activity

                                        currentPlayer.child("inGame").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                //if in game is true move to next screen
                                                Long inGame= (Long) dataSnapshot.getValue();
                                                String stringInGame = Long.toString(inGame);

                                                if (stringInGame.equals("1")) {
                                                    opentopic_vote();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????














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



    }
    //TODO: If back button is pressed more management is needed
    @Override
    public void onBackPressed() {
        iterations=6;
        //String String_playerCurrentPosition=String.valueOf(playerCurrentPosition);
        databaseUnfulfilled.child("Value"+playerCurrentPosition).setValue(playerCurrentPosition);
        //*********************************************************************************************************
        //TODO Add playerCurrentPosition to databaseUnfulfilled list
        //*********************************************************************************************************

        if (playerCurrentPosition%2==0){
            String a= ""+playerCurrentPosition;
            databasePlayersWaiting.child(a).removeValue();
        }


        Intent intent = new Intent(this, choice_home.class);
        startActivity(intent);
    }

    public void onPause() {
        super.onPause();
        if (StartingActivityIf_1!=1) {

            iterations = 6;
            //String String_playerCurrentPosition = String.valueOf(playerCurrentPosition);
            databaseUnfulfilled.child("Value" + playerCurrentPosition).setValue(playerCurrentPosition);
            //*********************************************************************************************************
            //TODO Add playerCurrentPosition to databaseUnfulfilled list
            //*********************************************************************************************************

            if (playerCurrentPosition % 2 == 0) {
                String a = "" + playerCurrentPosition;
                databasePlayersWaiting.child(a).removeValue();
            }
            Intent intent = new Intent(this, choice_home.class);
            startActivity(intent);
        }
    }

    public void opentopic_vote(){
        StartingActivityIf_1=1;

        Intent intent = new Intent(this, topic_vote.class);
        //Should send completed object of newGame
        //TODO only responds to 1st player
        Bundle bundle = new Bundle();
        bundle.putSerializable("newGame",newGame);
        intent.putExtras(bundle);

        startActivity(intent);
    }
    public void openChoice_home(){
        StartingActivityIf_1=1;
        Intent intent = new Intent(this, choice_home.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goingBack",goingBack);
        intent.putExtras(bundle);

        startActivity(intent);
    }


}
