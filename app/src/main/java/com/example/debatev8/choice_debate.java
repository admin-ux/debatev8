package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class choice_debate extends AppCompatActivity {

    Button topicButton;
    Button popButton;
    Button favButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_choice);


        topicButton = (Button) findViewById(R.id.todaystopicButton);
        topicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodaysTopic();


            }

        });
        popButton = (Button) findViewById(R.id.popularButton);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopular();

            }
        });
        favButton = (Button) findViewById(R.id.favouritesButton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFavourites();


            }

        });

    }


    //These three Buttons lead to debate_lead_a1 or debate_close_b1 pages
    //This is determined by the algorithm/ database
    public void openTodaysTopic(){
        Intent intent = new Intent(this, a1_lead_debate.class);
        startActivity(intent);
    }

    public void openPopular(){
        Intent intent = new Intent(this, b1_close_debate.class);
        startActivity(intent);
    }
    public void openFavourites(){
        Intent intent = new Intent(this, choice_debate.class);
        startActivity(intent);
    }



}
