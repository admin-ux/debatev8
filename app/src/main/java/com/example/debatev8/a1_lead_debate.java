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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


//Description       : This class is the first screen of a debate of player one in the debate stream Round 1
//Inner Workings    :
//                  1) Calculates the chosen topic from topic_vote's currentGames object
//                  2) Builds currentGames object from firebase
//                  3) Waits 15 seconds for a response from player if not entered continues to next screen
public class a1_lead_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"


    String arg_a1;

    EditText arg_a1Input;
    TextView TopicTitle;
    TextView TopicHeader1;

    Button submitB;

    int timeriterations=0;

    game currentGame;

    int[] myvaluesList = new int[4];

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_lead_a1);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");

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

            if (randomInteger==1)
            {
                largest=same;
            }
        }
        String num=String.valueOf(i);

        largest++;
        String choose=String.valueOf(largest);

        //Calculate Topic//End//a
        //Retrieve Topic//Start//
        //TODO CHANGE THIS VALUE TO "choose"

        databaseTopics.child(choose).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot topicInfo : dataSnapshot.getChildren())
                {


                    String TopicTitle = "";
                    String TopicHeader1 = "";
                    String TopicHeader2 = "";
                    String TopicHeader3 = "";


                    Object tt= dataSnapshot.child("Topic Title").getValue();
                    Object th1= dataSnapshot.child("Topic Header1").getValue();
                    Object th2= dataSnapshot.child("Topic Header2").getValue();
                    Object th3= dataSnapshot.child("Topic Header3").getValue();

                    if (tt!=null&&th1!=null&&th2!=null&&th3!=null) {
//                        Log.i("ssssssssssssssssssssss", tt.toString());
//                        Log.i("tttttttttttttttttttttt", th1.toString());
//                        Log.i("uuuuuuuuuuuuuuuuuuuuuu", th2.toString());
//                        Log.i("vvvvvvvvvvvvvvvvvvvvvvv", th3.toString());
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
                    stage1.setResp("0");
                    stage1.setArg("0");
                    stage2.setResp("0");
                    stage2.setArg("0");
                    stage3.setResp("0");
                    stage3.setArg("0");

                    retrievedDebateStages.setTopicTitle(TopicTitle);

                    retrievedDebateStages.setStage1(stage1);
                    retrievedDebateStages.setStage2(stage2);
                    retrievedDebateStages.setStage3(stage3);
                    currentGame.setStages(retrievedDebateStages);
                }
                //For the database
                databaseCurrentGames.child(currentGame.getGameID()).child("stages").setValue(currentGame.getStages());


                arg_a1Input = (EditText) findViewById(R.id.arg_a1);

                TopicHeader1= (TextView) findViewById(R.id.topicheader);
                TopicHeader1.setText(currentGame.getStages().getStage1().getTopicHeader());

                TopicTitle= (TextView) findViewById(R.id.topictitle);
                TopicTitle.setText(currentGame.getStages().getTopicTitle());

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
                                arg_a1 = arg_a1Input.getText().toString();
                                currentGame.getStages().getStage1().setArg(arg_a1);

                                displayText(arg_a1);

                                currentGame.getStages().getStage1().setArg(arg_a1);
                                databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage1").child("arg").setValue(currentGame.getStages().getStage1().getArg());
                                myArgTimer.cancel();
                                myArgTimer.purge();
                                openA1_topicheaderpreview_debate();

                            }
                        });
                        if (timeriterations>1)
                        {
                            myArgTimer.cancel();
                            myArgTimer.purge();
                            currentGame.getStages().getStage1().setArg("I could not think of an argument");
                            databaseCurrentGames.child(currentGame.getGameID()).child("stages").child("stage1").child("arg").setValue(currentGame.getStages().getStage1().getArg());
                            openA1_topicheaderpreview_debate();
                        }
                    }
                };//Every Second
                myArgTimer.schedule(untilArgMade, 0, 15000000);

            //No timer immediately sent to waiting screen with pre view of next topic header
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void displayText(String text){
        Toast.makeText(a1_lead_debate.this, text, Toast.LENGTH_LONG).show();

    }
    public void openA1_topicheaderpreview_debate(){
        Intent intent = new Intent(this, a1_topicheaderpreview_debate.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("currentGame",currentGame);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
