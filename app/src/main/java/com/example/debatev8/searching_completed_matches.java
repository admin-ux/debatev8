package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class searching_completed_matches extends AppCompatActivity {

    Boolean beenJudged;
    game gameBeingJudged= new game("0", false, false,false, null, "0", "0", null, "0", "0");


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
                        for (DataSnapshot userInPlayersWaiting : dataSnapshot.getChildren()) {
                            game currentGame = dataSnapshot.getValue(game.class);
                            //beenJudged = (Boolean) userInPlayersWaiting.child("beenJudged").getValue();
                            if (currentGame!=null) {
                                beenJudged = currentGame.isBeenJudged();
                                Log.i("rrrrrrrrrrrrrrrrrrrrr", beenJudged.toString());
                            }

                            if (!beenJudged && currentGame!=null) {
                                Log.i("QQQQQQQQQQQQQQQQQQQQQQ", dataSnapshot.toString());
                                //FirebaseDatabase.getInstance().getReference().child("CurrentGames").child(currentGame.getGameID()).child("beenJudged").setValue("true");

                                //Values to be used if beingJudged==false
                                Log.i("rrrrrrrrrrrrrrrrrrrrr", "GOT HERE");
//                                String player1ID = currentGame.getPlayer1();
//                                String player2ID =currentGame.getPlayer2();
//
//
//                                String TopicTitle = currentGame.getStages().getTopicTitle();
//                                String TopicHeader1 = currentGame.getStages().getStage1().getTopicHeader();
//                                String TopicHeader2 = currentGame.getStages().getStage2().getTopicHeader();
//                                String TopicHeader3 = currentGame.getStages().getStage3().getTopicHeader();
//
//                                String a1_Arg = currentGame.getStages().getStage1().getArg();
//                                String a2_Resp = currentGame.getStages().getStage1().getResp();
//                                String a3_Arg = currentGame.getStages().getStage2().getArg();
//                                String b1_Resp = currentGame.getStages().getStage2().getResp();
//                                String b2_Arg = currentGame.getStages().getStage3().getArg();
//                                String b3_Resp = currentGame.getStages().getStage3().getResp();

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

                                Object p1 = dataSnapshot.child("player1").getValue();
                                Object p2 = dataSnapshot.child("player2").getValue();

                                Object tt = dataSnapshot.child("stages").child("topicTitle").getValue();
                                Object th1 = dataSnapshot.child("stages").child("stage1").child("topicHeader").getValue();
                                Object th2 = dataSnapshot.child("stages").child("stage2").child("topicHeader").getValue();
                                Object th3 = dataSnapshot.child("stages").child("stage3").child("topicHeader").getValue();

                                Object a1a = dataSnapshot.child("stages").child("stage1").child("arg").getValue();
                                Object a2r = dataSnapshot.child("stages").child("stage2").child("resp").getValue();
                                Object a3a = dataSnapshot.child("stages").child("stage3").child("arg").getValue();
                                Object b1r = dataSnapshot.child("stages").child("stage1").child("resp").getValue();
                                Object b2a = dataSnapshot.child("stages").child("stage2").child("arg").getValue();
                                Object b3r = dataSnapshot.child("stages").child("stage3").child("resp").getValue();

                                if (tt != null && th1 != null && th2 != null && th3 != null && p1 != null && p2 != null && a1a != null && a2r != null && a3a != null && b1r != null && b2a != null && b3r != null) {
                                    Log.i("ssssssssssssssssssssss", tt.toString());
                                    Log.i("tttttttttttttttttttttt", th1.toString());
                                    Log.i("uuuuuuuuuuuuuuuuuuuuuu", th2.toString());
                                    Log.i("vvvvvvvvvvvvvvvvvvvvvvv", th3.toString());
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
