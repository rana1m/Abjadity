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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
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
    DatabaseReference alphabetsRef,accountRef;
    String childId,parentId,childLevel,button;
    VideoView letterChant,character;
    FloatingActionButton back,forward,play;
    FrameLayout letterChantFrame;
    Button SaveButton;
    TextView level,scores;
    View dialogView;
    StorageReference storageReference ;
    Window window ;
    MediaPlayer mediaPlayer;
    int score;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);


        initialization();
        scoresAndLevel();

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

                        Intent i = new Intent(StepOneActivity.this,StepTowActivity.class);
                        i.putExtra("childId",childId);
                        i.putExtra("parentId",parentId);
                        i.putExtra("childLevel",childLevel);
                        i.putExtra("button",button);
                        startActivity(i);
            }
        });
    }

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
                if(letterChant.isPlaying()){
                    character.pause();
                }
                MediaController mediaController = new MediaController(StepOneActivity.this);
                letterChant.setMediaController(mediaController);
                mediaController.setAnchorView(letterChant);

                letterChant.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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

    public void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepOneActivity.this);
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

                Intent i = new Intent(StepOneActivity.this,StepTowActivity.class);
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
        alphabetsRef = database.getReference("alphabets");
        accountRef = database.getReference("accounts");
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
        window = this.getWindow();
        score=0;
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);
    }






}