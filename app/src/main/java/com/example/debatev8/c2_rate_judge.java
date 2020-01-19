//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class c2_rate_judge extends AppCompatActivity {

    SeekBar seekbar1,seekbar2;
    TextView Argb2, Respa2;

    Button submitB;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_rate_c2);


        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        //Value of Arg/ Resp changed by database
        Argb2 = (TextView) findViewById(R.id.b2leadpoint);
        Respa2 = (TextView) findViewById(R.id.a2responsepoint);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openC3Rate();
            }
        });
    }

    public void openC3Rate(){
        Intent intent = new Intent(this, c3_rate_judge.class);
        startActivity(intent);
    }



    private void displayText(String text){
        Toast.makeText(c2_rate_judge.this, text, Toast.LENGTH_LONG).show();
    }
}
