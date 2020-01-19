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

public class a2_close_debate extends AppCompatActivity {
    //1 Data is saved to Current Games gamed id
    //2 Waits for "b"

    String resp_a2,arg_b2;

    EditText resp_a2Input;

    Button submitB;


    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debate_close_a2);


        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = fireUser.getUid();
        DatabaseReference realtimeUserProfile =databaseUsers.child(userid);


        resp_a2Input = (EditText) findViewById(R.id.resp_a2);

        submitB = (Button) findViewById(R.id.submitButton);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resp_a2 = resp_a2Input.getText().toString();

                //Set Value arg_b2 to database value
                displayText(arg_b2);
                displayText(resp_a2);
                addRespa2();
            }
        });
    }
    private void displayText(String text){
        Toast.makeText(a2_close_debate.this, text, Toast.LENGTH_LONG).show();

    }
    private void  addRespa2(){

        if(!TextUtils.isEmpty(resp_a2)){

        }else {
            resp_a2="I cant think of an argument for this topic header";
        }
    }
}
