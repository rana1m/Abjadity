package com.rana.abjadity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StepOneNumActivity extends AppCompatActivity {

    private static final String TAG = "StepOneNumActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef,digitsRef;
    String childId,parentId,childLevel,button;
    Child desiredChild;
    VideoView numberChant,character;
    FloatingActionButton back,forward;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one_num);

        initialization();
        retrieveNumber();
        numberChantInitialization();
        characterInitialization();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepOneNumActivity.this,MapActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                startActivity(i);
            }
        });
    }

    private void characterInitialization() {
//        String path = "android.resource://"+getPackageName()+"/"+ R.raw.v2;
        Uri uri =Uri.parse("https://a.top4top.io/m_18705z3h81.mov");
        character.setVideoURI(uri);
        character.setZOrderOnTop(true);
        character.start();
        character.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View sv, MotionEvent event) {
                character.start();
                return false;
            }
        });
    }

    private void numberChantInitialization() {
        Uri uri =Uri.parse("https://k.top4top.io/m_1870o5x061.mov");
        numberChant.setVideoURI(uri);
        numberChant.setZOrderOnTop(true);
        numberChant.start();
        MediaController mediaController = new MediaController(this);
        numberChant.setMediaController(mediaController);
        mediaController.setAnchorView(numberChant);
    }

    private void retrieveNumber() {

        digitsRef.orderByChild("id").equalTo(childLevel).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //loop through numbers to
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Log.e(TAG,userSnapshot.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        digitsRef = database.getReference("digits");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");
        numberChant = findViewById(R.id.numberChant);
        character = findViewById(R.id.character2);
        back = findViewById(R.id.backIcon);
        forward = findViewById(R.id.forward2);
    }
}