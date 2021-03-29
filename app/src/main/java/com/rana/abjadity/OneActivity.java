package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
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

import java.io.IOException;

public class OneActivity extends AppCompatActivity {

    private static final String TAG = "OneActivity";
    FirebaseDatabase database;
    DatabaseReference digitsRef,accountRef;
    String childId,parentId,childLevel,button;
    VideoView numberChant,character;
    FloatingActionButton back,forward,play;
    FrameLayout numberChantFrame;
    StorageReference storageReference ;
    Window window ;
    MediaPlayer mediaPlayer;
    int score;
    Button SaveButton;
    TextView level,scores;
    View dialogView;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(OneActivity.this,MapActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        startActivity(i);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            button = extras.getString("button");
        }

        initialization();
        scoresAndLevel();

        numberChatInitialization();
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
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OneActivity.this,StepThreeNumActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });

    }
     // end OnCreate
     private void updateScores() {
         score=3;
         accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 //loop through accounts to find the parent with that id
                 for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                     //loop through parent children to add them to adapter ArrayList
                     for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                         Child child = theChild.getValue(Child.class);
                         if(child.getId().equals(childId)){
                             score += child.getScore();
                             theChild.getRef().child("score").setValue(score);
                         }
                     }
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
                 throw databaseError.toException();
             }
         });
     }

    private void playVoice() throws IOException {
        mediaPlayer=new MediaPlayer();
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.good_job;
        Uri uri =Uri.parse(path);
        mediaPlayer.setDataSource(this,uri);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
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

                numberChant.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        winningFunction();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    //Score

    private void scoresAndLevel() {
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                        Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){
                            scores.setText(child.getScore().toString());
                            level.setText(child.getLevel().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }


    public void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(OneActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.next_step_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initializationForDialog();
        //play voice
        try {
            character.pause();
            playVoice();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update scores
        updateScores();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OneActivity.this,StepThreeNumActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
                alertDialog.dismiss();
            }
            public boolean onTouchEvent(MotionEvent event)
            {

                if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    alertDialog.dismiss();
                }
                return false;
            }
        });
    }


    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);

    }


    private void initialization () {
        database = FirebaseDatabase.getInstance();
        digitsRef = database.getReference("digits");
        accountRef = database.getReference("accounts");

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

        window = this.getWindow();
        score=0;
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);
    }
}