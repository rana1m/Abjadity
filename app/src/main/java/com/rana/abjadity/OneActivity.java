package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OneActivity extends AppCompatActivity {

    private static final String TAG = "OneActivity";
    FirebaseDatabase database;
    DatabaseReference digitsRef;
    String childId,parentId,childLevel,button;
    VideoView numberChant,character;
    FloatingActionButton back,forward,play;
    FrameLayout numberChantFrame;
    StorageReference storageReference ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        initialization();

        digitsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //loop through letters to
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Digit digit = userSnapshot.getValue(Digit.class);
                    Log.e(TAG,digit.getName());
                    numberChatInitialization();

                }
            }
//
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        characterInitialization();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                numberChantFrame.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OneActivity.this,MapActivity.class);
                startActivity(i);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OneActivity.this,StepFourNumActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });

    }

    private void characterInitialization() {
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.h1;
        Uri uri =Uri.parse(path);
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
    private void numberChatInitialization() {
        storageReference.child("numbersChant/"+button+".mov").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                numberChant.setVideoURI(uri);
                numberChant.setZOrderOnTop(true);
                numberChant.start();
                MediaController mediaController = new MediaController(OneActivity.this);
                numberChant.setMediaController(mediaController);
                mediaController.setAnchorView(numberChant);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void initialization () {
        database = FirebaseDatabase.getInstance();
        digitsRef = database.getReference("digits");

        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");

        numberChant=findViewById(R.id.numberChant);
        character=findViewById(R.id.character2);
        back=findViewById(R.id.back2);
        forward=findViewById(R.id.forward2);
        play=findViewById(R.id.PlayIcon2);
        numberChantFrame=findViewById(R.id.letterChatFrame);
        storageReference = FirebaseStorage.getInstance().getReference();



    }
}