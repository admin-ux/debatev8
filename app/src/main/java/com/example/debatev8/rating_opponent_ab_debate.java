//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.TextView;

public class rating_opponent_ab_debate extends AppCompatActivity {

    SeekBar seekbar1;

    Button submitB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_ab_opponent_rating);

        //Value Submitted to database depending on player1 or 2
        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);



        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openRepeatReturn();
            }
        });
    }

    public void openRepeatReturn(){
        Intent intent = new Intent(this, repeat_return_debate.class);
        startActivity(intent);
    }



    private void displayText(String text){
        Toast.makeText(rating_opponent_ab_debate.this, text, Toast.LENGTH_LONG).show();
    }
}
