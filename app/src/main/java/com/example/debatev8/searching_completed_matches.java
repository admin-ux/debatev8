package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

//Description       : This class is the algorithm that searches for completed games in the currentGames list to judge
//Inner Workings    :
//                  1) creates gameBeingJudged object
//                  2) Fills gameBeingJudged object with firebase values of the found game
public class searching_completed_matches extends AppCompatActivity {

    Boolean beenJudged;
    game gameBeingJudged= new game("0", false, false,false, null, "0", "0", "0", "0",0,0,0,0,0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_completed_matches);



        final Timer myTimerReQuery = new Timer();
        TimerTask untilReQuery= new TimerTask()
        {

            @Override
            public void run()
            {

                Query finishedGame = FirebaseDatabase.getInstance().getReference().child("CurrentGames").orderByChild("gameFinished").equalTo(true).limitToFirst(1);


                finishedGame.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot gameInfo : dataSnapshot.getChildren()) {
                            game currentGame = dataSnapshot.getValue(game.class);
                            //beenJudged = (Boolean) userInPlayersWaiting.child("beenJudged").getValue();

                            if (currentGame!=null) {
                                beenJudged = currentGame.isBeenJudged();
                            }




                            if (!beenJudged && currentGame!=null) {

                                String gameID = "";
                                Object gid=gameInfo.child("gameID").getValue();
                                if (gid!=null)
                                {
                                    gameID=gid.toString();
                                    if(!gameID.equals("")) {

                                        FirebaseDatabase.getInstance().getReference().child("CurrentGames").child(gameID).child("beenJudged").setValue("true");
                                    }
                                }

//
                                String player1ID = "";
                                String player2ID = "";


                                String TopicTitle = "";
                                String TopicHeader1 = "";
                                String TopicHeader2 = "";
                                String TopicHeader3 = "";

                                String a1_Arg = "";
                                String a2_Resp = "";
                                String a3_Arg = "";
                                String b1_Resp = "";
                                String b2_Arg = "";
                                String b3_Resp = "";

                                Object p1 = gameInfo.child("player1").getValue();
                                Object p2 = gameInfo.child("player2").getValue();

                                Object tt = gameInfo.child("stages").child("topicTitle").getValue();
                                Object th1 = gameInfo.child("stages").child("stage1").child("topicHeader").getValue();
                                Object th2 = gameInfo.child("stages").child("stage2").child("topicHeader").getValue();
                                Object th3 = gameInfo.child("stages").child("stage3").child("topicHeader").getValue();


                                Object a1a = gameInfo.child("stages").child("stage1").child("arg").getValue();
                                Object a2r = gameInfo.child("stages").child("stage2").child("resp").getValue();
                                Object a3a = gameInfo.child("stages").child("stage3").child("arg").getValue();
                                Object b1r = gameInfo.child("stages").child("stage1").child("resp").getValue();
                                Object b2a = gameInfo.child("stages").child("stage2").child("arg").getValue();
                                Object b3r = gameInfo.child("stages").child("stage3").child("resp").getValue();

                                if (tt != null && th1 != null && th2 != null && th3 != null && p1 != null && p2 != null && a1a != null && a2r != null && a3a != null && b1r != null && b2a != null && b3r != null) {

                                    player1ID = p1.toString();
                                    player2ID = p2.toString();

                                    TopicTitle = tt.toString();
                                    TopicHeader1 = th1.toString();
                                    TopicHeader2 = th2.toString();
                                    TopicHeader3 = th3.toString();

                                    a1_Arg = a1a.toString();
                                    a2_Resp = a2r.toString();
                                    a3_Arg = a3a.toString();
                                    b1_Resp = b1r.toString();
                                    b2_Arg = b2a.toString();
                                    b3_Resp = b3r.toString();

                                }

                                debate_stages retrievedDebateStages = new debate_stages();
                                stage stage1 = new stage();
                                stage stage2 = new stage();
                                stage stage3 = new stage();
                                stage1.setTopicHeader(TopicHeader1);
                                stage2.setTopicHeader(TopicHeader2);
                                stage3.setTopicHeader(TopicHeader3);
                                stage1.setResp(b1_Resp);
                                stage1.setArg(a1_Arg);
                                stage2.setResp(a2_Resp);
                                stage2.setArg(b2_Arg);
                                stage3.setResp(b3_Resp);
                                stage3.setArg(a3_Arg);

                                retrievedDebateStages.setTopicTitle(TopicTitle);

                                retrievedDebateStages.setStage1(stage1);
                                retrievedDebateStages.setStage2(stage2);
                                retrievedDebateStages.setStage3(stage3);
                                gameBeingJudged.setStages(retrievedDebateStages);
                                gameBeingJudged.setPlayer1(player1ID);
                                gameBeingJudged.setPlayer2(player2ID);
                                gameBeingJudged.setGameID(gameID);
                                myTimerReQuery.cancel();
                                myTimerReQuery.purge();
                                openC1_rate_debate();
                            }

                        }
                        //Handle Data





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            };//Every Second
            myTimerReQuery.schedule(untilReQuery,0,3000);

    }
    public void openC1_rate_debate(){
        Intent intent = new Intent(this, c1_rate_judge.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("gameBeingJudged",gameBeingJudged);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
