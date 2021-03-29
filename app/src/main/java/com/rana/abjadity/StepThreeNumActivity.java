package com.rana.abjadity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class StepThreeNumActivity extends AppCompatActivity {

    private static final String TAG = "StepFourActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef,digitsRef;
    String childId,parentId,childLevel,button;
    VideoView character;
    FloatingActionButton back,forward;
    MediaPlayer mediaPlayer;
    Window window ;
    Button SaveButton;
    View dialogView;
    int score;
    TextView level,scores;
    public ImageView Image1, Image2, Image3,Image4, Image5, Image6, Image7, Image8, Image9;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StepThreeNumActivity.this,OneActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        i.putExtra("button",button);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_three_num);


        initialization();
        scoresAndLevel();
        characterInitialization();
        checkNum(button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepThreeNumActivity.this,OneActivity.class);
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
                winningFunction();
            }
        });

    }


    public void checkNum(String button){
        if(button.equals("1"))
            return;

        switch (button){
            case "2":
                Image1.setImageResource(R.drawable.tws);

                break;
            case "3":
                Image1.setImageResource(R.drawable.three);

                break;
            case "4":
                Image1.setImageResource(R.drawable.four);

                break;
            case "5":
                Image1.setImageResource(R.drawable.fiveee);

                Image6.setImageResource(R.drawable.fiveee);
                Image7.setImageResource(R.drawable.fiveee);
                Image8.setImageResource(R.drawable.fiveee);
                Image9.setImageResource(R.drawable.fiveee);
                Image6.setVisibility(View.VISIBLE);
                Image7.setVisibility(View.VISIBLE);
                Image8.setVisibility(View.VISIBLE);
                Image9.setVisibility(View.VISIBLE);
                break;
            case "6":
                Image1.setImageResource(R.drawable.sixeggs);

                break;
            case "7":
                Image1.setImageResource(R.drawable.balls);
                Image2.setVisibility(View.VISIBLE);
                Image3.setVisibility(View.VISIBLE);
                Image4.setVisibility(View.VISIBLE);
                Image5.setVisibility(View.VISIBLE);
                Image8.setVisibility(View.VISIBLE);
                Image9.setVisibility(View.VISIBLE);
                break;
            case "8":
                Image1.setImageResource(R.drawable.eight);
                break;
            case "9":
                Image1.setImageResource(R.drawable.balls);
                Image2.setVisibility(View.VISIBLE);
                Image3.setVisibility(View.VISIBLE);
                Image4.setVisibility(View.VISIBLE);
                Image5.setVisibility(View.VISIBLE);
                Image6.setVisibility(View.VISIBLE);
                Image7.setVisibility(View.VISIBLE);
                Image8.setVisibility(View.VISIBLE);
                Image9.setVisibility(View.VISIBLE);
                break;
            case "10":
                Image1.setImageResource(R.drawable.tencandles);
                break;
        }
    }

    private void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepThreeNumActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);

        dialogView = getLayoutInflater().inflate(R.layout.next_step_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initializationForDialog();
        //play voice
        try {
//            character.pause();
            playVoice();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update scores
                updateScores();
                Intent i = new Intent(StepThreeNumActivity.this,StepFourNumActivity.class);
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
                    Intent i = new Intent(StepThreeNumActivity.this,StepFourNumActivity.class);
                    i.putExtra("childId",childId);
                    i.putExtra("parentId",parentId);
                    i.putExtra("childLevel",childLevel);
                    i.putExtra("button",button);
                    startActivity(i);
                    alertDialog.dismiss();
                }
                return false;
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

    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
    }

    private void initialization () {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        digitsRef = database.getReference("digits");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");
        character = findViewById(R.id.character2);
        back = findViewById(R.id.backIcon);
        forward = findViewById(R.id.forward2);
        window = this.getWindow();
        level = findViewById(R.id.level);
        scores = findViewById(R.id.score);
        //Image
        Image1 = (ImageView) findViewById(R.id.image_);
        Image2 = (ImageView) findViewById(R.id.imageView7);
        Image3 = (ImageView) findViewById(R.id.imageView13);
        Image4 = (ImageView) findViewById(R.id.imageView9);
        Image5 = (ImageView) findViewById(R.id.imageView11);
        Image6 = (ImageView) findViewById(R.id.imageView14);
        Image7 = (ImageView) findViewById(R.id.imageView15);
        Image8 = (ImageView) findViewById(R.id.imageView10);
        Image9 = (ImageView) findViewById(R.id.imageView12);
    }

}