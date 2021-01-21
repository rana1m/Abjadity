package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef,alphabetsRef,digitsRef;
    Button logIn,Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        accountRef.setValue("s");

//        ArrayList<Child> childs = new ArrayList<Child>();
//        childs.add(new Child("id","type", "character", "name","email",  "password",  "level",  "score",  "alarm"));

        // Read from the database
       // accountRef.push().setValue(new Parent("id","d","f","ff","f","f"));


        // Read from the database
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Parent parent = data.getValue(Parent.class);
                    Log.e(TAG, "Value is: " + parent.getEmail());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });

//        accountRef.child("ID").setValue("1");
//        accountRef.child("type").setValue("1");
//        accountRef.child("child").setValue("1");
//        accountRef.child("character").setValue("1");
//        accountRef.child("name").setValue("1");
//        accountRef.child("email").setValue("1");
//        accountRef.child("password").setValue("1");



//        DatabaseReference myReff = database.getReference("sec");
//        myReff.setValue("l");
//        myReff.push().setValue("o");

    logIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this,LogInActivity.class);
            startActivity(i);

        }
    });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });
    }

    private void initialization() {
        logIn=findViewById(R.id.LogInButton);
        Register=findViewById(R.id.RegisterButton);
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        alphabetsRef = database.getReference("alphabets");
        digitsRef = database.getReference("digits");
    }

}