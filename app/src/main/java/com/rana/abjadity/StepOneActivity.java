package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.io.IOException;

public class StepOneActivity extends AppCompatActivity {

    private static final String TAG = "StepOneActivity";
    FirebaseDatabase database;
    DatabaseReference alphabetsRef;
    String childId,parentId,childLevel,button;
    VideoView letterChant,character;
    FloatingActionButton back,forward,play;
    FrameLayout letterChantFrame;
    StorageReference storageReference ;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);


        initialization();


        alphabetsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //loop through letters to
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Letter letter = userSnapshot.getValue(Letter.class);
                    Log.e(TAG,letter.getName());
                    letterChatInitialization();

                }
            }

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
                letterChantFrame.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepOneActivity.this,MapActivity.class);
                startActivity(i);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepOneActivity.this,StepTowActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });
    }


    private void characterInitialization() {
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.v2;
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

    private void letterChatInitialization() {
        storageReference.child("lettersChant/"+button+".mp4").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                letterChant.setVideoURI(uri);
                letterChant.setZOrderOnTop(true);
                letterChant.start();
                MediaController mediaController = new MediaController(StepOneActivity.this);
                letterChant.setMediaController(mediaController);
                mediaController.setAnchorView(letterChant);
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
        alphabetsRef = database.getReference("alphabets");

        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");

        letterChant=findViewById(R.id.letterChant);
        character=findViewById(R.id.character);
        back=findViewById(R.id.back);
        forward=findViewById(R.id.forward);
        play=findViewById(R.id.playIcon);
        letterChantFrame=findViewById(R.id.letterChatFrame);
        storageReference = FirebaseStorage.getInstance().getReference();



    }






}