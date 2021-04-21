package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CharacterActivity";


    ImageButton back;
    String childId,parentId;
    ImageView char1, char2, char3, char4, char5;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    Trace tracer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        initialization();

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent backIntent = getIntent();
//                setResult(RESULT_OK,backIntent);
//                finish();
//            }
//        });


        char1.setOnClickListener(this);
        char2.setOnClickListener(this);
        char3.setOnClickListener(this);
        char4.setOnClickListener(this);
        char5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.char1:
                uploadImage("1");
                finish();
                break;
            case R.id.char2:
                uploadImage("2");
                finish();
                break;
            case R.id.char3:
                uploadImage("3");
                finish();
                break;
            case R.id.char4:
                uploadImage("4");
                finish();
                break;
            case R.id.char5:
                uploadImage("5");
                finish();
                break;

        }
    }

    private void uploadImage(String s) {
        tracer.start();
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                        Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){
                            theChild.getRef().child("character").setValue(s);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        tracer.stop();
    }

    private void initialization(){
        back = findViewById(R.id.back);
        char1 = findViewById(R.id.char1);
        char2 = findViewById(R.id.char2);
        char3 = findViewById(R.id.char3);
        char4 = findViewById(R.id.char4);
        char5 = findViewById(R.id.char5);
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        tracer= FirebasePerformance.getInstance().newTrace("CharacterActivity");

    }

}