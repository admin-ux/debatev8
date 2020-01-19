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

public class c1_rate_judge extends AppCompatActivity {

    SeekBar seekbar1,seekbar2;
    TextView Arga1, Respb1;

    Button submitB;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_rate_c1);


        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        //Value of Arg/ Resp changed by database
        Arga1 = (TextView) findViewById(R.id.a1leadpoint);
        Respb1 = (TextView) findViewById(R.id.b1responsepoint);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openC2Rate();
            }
        });
    }

    public void openC2Rate(){
        Intent intent = new Intent(this, c2_rate_judge.class);
        startActivity(intent);
    }



    private void displayText(String text){
        Toast.makeText(c1_rate_judge.this, text, Toast.LENGTH_LONG).show();
    }
}
