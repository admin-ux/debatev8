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

public class a3_lead_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    String arg_a3;

    EditText arg_a3Input;

    Button submitB;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_lead_a3);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);


        arg_a3Input = (EditText) findViewById(R.id.arg_a3);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arg_a3 = arg_a3Input.getText().toString();


                displayText(arg_a3);
                addArga3();
            }
        });
    }
    private void displayText(String text){
        Toast.makeText(a3_lead_debate.this, text, Toast.LENGTH_LONG).show();

    }
    private void  addArga3(){

        if(!TextUtils.isEmpty(arg_a3)){

        }else {
            arg_a3="I cant think of an argument for this topic header";
        }
    }
}
