//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class b1_close_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    String resp_b1,arg_a1;

    EditText resp_b1Input;

    Button submitB;


    game currentGame;

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_close_b1);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);
        currentGame = (game) getIntent().getSerializableExtra("currentGame");
        Log.i("bbbbbbbbbbbbbbbbbbbb", currentGame.toString());
        Log.i("ccccccccccccccccccc", currentGame.getPlayer1Topics());
        Log.i("ddddddddddddddddddd", currentGame.getPlayer2Topics());
        Log.i("eeeeeeeeeeeeeeeeeee", currentGame.getGameID());

        resp_b1Input = (EditText) findViewById(R.id.resp_b1);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resp_b1= resp_b1Input.getText().toString();

                //Set Value arg_a1 to database value
                displayText(arg_a1);
                displayText(resp_b1);
                addRespb1();
            }
        });
    }
    private void displayText(String text){
        Toast.makeText(b1_close_debate.this, text, Toast.LENGTH_LONG).show();

    }
    private void  addRespb1(){

        if(!TextUtils.isEmpty(resp_b1)){

        }else {
            resp_b1="I cant think of an argument for this topic header";
        }
    }
}
