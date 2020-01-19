//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class b3_close_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    String resp_b3, arg_a3;

    EditText resp_b3Input;

    Button submitB;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_close_b3);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);


        resp_b3Input = (EditText) findViewById(R.id.resp_b3);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resp_b3= resp_b3Input.getText().toString();

                //Set Value arg_a3 to database value
                displayText(arg_a3);
                displayText(resp_b3);
                addRespb3();
            }
        });
    }
    private void displayText(String text){
        Toast.makeText(b3_close_debate.this, text, Toast.LENGTH_LONG).show();

    }
    private void  addRespb3(){

        if(!TextUtils.isEmpty(resp_b3)){

        }else {
            resp_b3="I cant think of an argument for this topic header";
        }
    }
}
