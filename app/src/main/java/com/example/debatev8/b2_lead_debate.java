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

public class b2_lead_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    String arg_b2;

    EditText arg_b2Input;

    Button submitB;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_lead_b2);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);


        arg_b2Input = (EditText) findViewById(R.id.arg_b2);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arg_b2 = arg_b2Input.getText().toString();


                displayText(arg_b2);
                addArgb2();
            }
        });
    }
    private void displayText(String text){
        Toast.makeText(b2_lead_debate.this, text, Toast.LENGTH_LONG).show();

    }
    private void  addArgb2(){

        if(!TextUtils.isEmpty(arg_b2)){

        }else {
            arg_b2="I cant think of an argument for this topic header";
        }
    }
}
