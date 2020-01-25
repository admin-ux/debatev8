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

public class b1_topicheaderpreview_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    String a1_open;

    TextView TopicTitle;
    TextView TopicHeader1;

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
        setContentView(R.layout.debate_topicheaderpreview_b1);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");
        Log.i("bbbbbbbbbbbbbbbbbbbb", currentGame.toString());
        Log.i("ccccccccccccccccccc", currentGame.getPlayer1Topics());
        Log.i("ddddddddddddddddddd", currentGame.getPlayer2Topics());
        Log.i("eeeeeeeeeeeeeeeeeee", currentGame.getGameID());

        //Calculate Topic//Start//

        int one=0;
        int two=0;
        int three=0;
        int four=0;

        int score=15;
        int i=0;
        while (currentGame.getPlayer1Topics().length()>i)
        {
            String num=String.valueOf(i);
            Log.i("jjjjjjjjjjjjjjjjjj", num);
            if(currentGame.getPlayer1Topics().charAt(i)=='1')
            {
                one=one+score;
            }
            else if (currentGame.getPlayer1Topics().charAt(i)=='2')
            {
                two=two+score;
            }
            else if (currentGame.getPlayer1Topics().charAt(i)=='3')
            {
                three=three+score;
            }
            else
            {
                four=four+score;
            }
            i++;
            if (i==1)
            {
                score=score-5;
            }
            else if (i==2)
            {
                score=score-7;
            }
            else if (i==3)
            {
                score=score-2;
            }

        }
        score=15;
        i=0;
        while (currentGame.getPlayer2Topics().length()>i)
        {
            String num2=String.valueOf(i);
            Log.i("kkkkkkkkkkkkkkkkkkkkkk", num2);
            if(currentGame.getPlayer2Topics().charAt(i)=='1')
            {
                one=one+score;
            }
            else if (currentGame.getPlayer2Topics().charAt(i)=='2')
            {
                two=two+score;
            }
            else if (currentGame.getPlayer2Topics().charAt(i)=='3')
            {
                three=three+score;
            }
            else
            {
                four=four+score;
            }
            i++;
            if (i==1)
            {
                score=score-5;
            }
            else if (i==2)
            {
                score=score-7;
            }
            else if (i==3)
            {
                score=score-2;
            }

        }
        myvaluesList[0]=one;
        myvaluesList[1]=two;
        myvaluesList[2]=three;
        myvaluesList[3]=four;
        int largest = 0;
        int largestvalue=myvaluesList[0];
        int same = -1;
        i=1;
        while (i<4)
        {
            String num3=String.valueOf(i);
            Log.i("lllllllllllllllll", num3);
            if (myvaluesList[i]>largestvalue)
            {
                largest = i;
            }
            else if (myvaluesList[i]==largestvalue)
            {
                same=i;
            }
            i++;
        }
        if (same!=-1)
        {
            Random random = new Random();
            int randomInteger = random.nextInt(1);
            String n=String.valueOf(randomInteger);
            Log.i("ooooooooooooooooooo", n);
            if (randomInteger==1)
            {
                largest=same;
            }
        }
        String num=String.valueOf(i);

        largest++;
        String choose=String.valueOf(largest);
        Log.i("nnnnnnnnnnnnnnnnnnnnnnn", choose);
        //Calculate Topic//End//a
        //Retrieve Topic//Start//
        //TODO CHANGE THIS VALUE TO "choose"
        Log.i("qqqqqqqqqqqqqqqqqqq", databaseTopics.child("1").toString());
        databaseTopics.child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot topicInfo : dataSnapshot.getChildren())
                {
                    Log.i("rrrrrrrrrrrrrrrrrrrrr", "GOT HERE");

                    String TopicTitle = "";
                    String TopicHeader1 = "";
                    String TopicHeader2 = "";
                    String TopicHeader3 = "";


                    Object tt= dataSnapshot.child("Topic Title").getValue();
                    Object th1= dataSnapshot.child("Topic Header1").getValue();
                    Object th2= dataSnapshot.child("Topic Header2").getValue();
                    Object th3= dataSnapshot.child("Topic Header3").getValue();

                    if (tt!=null&&th1!=null&&th2!=null&&th3!=null) {
                        Log.i("ssssssssssssssssssssss", tt.toString());
                        Log.i("tttttttttttttttttttttt", th1.toString());
                        Log.i("uuuuuuuuuuuuuuuuuuuuuu", th2.toString());
                        Log.i("vvvvvvvvvvvvvvvvvvvvvvv", th3.toString());
                        TopicTitle=tt.toString();
                        TopicHeader1=th1.toString();
                        TopicHeader2=th2.toString();
                        TopicHeader3=th3.toString();


                    }

                    debate_stages retrievedDebateStages=new debate_stages();
                    stage stage1=new stage();
                    stage stage2=new stage();
                    stage stage3=new stage();
                    stage1.setTopicHeader(TopicHeader1);
                    stage2.setTopicHeader(TopicHeader2);
                    stage3.setTopicHeader(TopicHeader3);

                    retrievedDebateStages.setTopicTitle(TopicTitle);

                    retrievedDebateStages.setStage1(stage1);
                    retrievedDebateStages.setStage2(stage2);
                    retrievedDebateStages.setStage3(stage3);
                    currentGame.setStages(retrievedDebateStages);
                }

                //databaseCurrentGames.child(currentGame.getGameID()).child("stages").setValue(currentGame.getStages());


                Log.i("jjjjjjjTTjjjjjjjjj", currentGame.getStages().getTopicTitle());

                TopicHeader1= (TextView) findViewById(R.id.topicheader);
                TopicHeader1.setText(currentGame.getStages().getStage1().getTopicHeader());

                TopicTitle= (TextView) findViewById(R.id.topictitle);
                TopicTitle.setText(currentGame.getStages().getTopicTitle());



                final Timer myArgTimer = new Timer();
                TimerTask untilArgMade = new TimerTask() {
                    @Override
                    public void run()
                    {

                        Log.i("8888888888888888888888", "Here?");
                        Log.i("8888888888888888888888", currentGame.getGameID());
                        databaseCurrentGames.child(currentGame.getGameID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot topicInfo : dataSnapshot.getChildren())
                                {
                                    Log.i("{{{{{{{{{{{{{{{{{{{{{", "GOT HERE");
                                    Log.i("%%%%%%%%%%%%%%%%5%%%%", dataSnapshot.toString());
                                    a1_open = "";

                                    Object a1o= dataSnapshot.child("stages").child("stage1").child("arg").getValue();
                                    if (a1o!=null)
                                    {
                                        a1_open = a1o.toString();
                                        if (!a1_open.equals("0"))
                                        {
                                            currentGame.getStages().getStage1().setResp(a1_open);
                                            myArgTimer.cancel();
                                            myArgTimer.purge();
                                            openB1_close_debate();
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Retrieve Topic//End//



    }



    private void displayText(String text){
        Toast.makeText(b1_topicheaderpreview_debate.this, text, Toast.LENGTH_LONG).show();

    }
    public void openB1_close_debate(){
        Intent intent = new Intent(this, b1_close_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
