package com.rana.abjadity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class NumStepThreeActivity extends AppCompatActivity {

    FloatingActionButton back,forward;
    String childId,parentId,childLevel,button;
    VideoView character;
    MediaPlayer mediaPlayer;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    TextView level,scores;
    int score;
    ImageView pic1,pic2,pic3,pic4,pic5,pic6,pic7,pic8,pic9;
    View dialogView;
    Button SaveButton;
    Window window ;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NumStepThreeActivity.this, NumStepTowActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        i.putExtra("button",button);
        startActivity(i);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_num_step);
        initialization();
        characterInitialization(button);
        StepNumber(button);
        scoresAndLevel();

     //   Toast.makeText(getApplicationContext(),button,Toast.LENGTH_LONG).show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NumStepThreeActivity.this, NumStepTowActivity.class);
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
    //Score And Level
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

    public void StepNumber(String button) {
        if (button.equals("1")) {
            return;
        }
        switch (button) {
            case "2":
                pic1.setImageResource(R.drawable.tws);
                break;
            case "3":
                pic1.setImageResource(R.drawable.three);
                break;
            case "4":
                pic1.setImageResource(R.drawable.four);
                break;
            case "6":
                pic1.setImageResource(R.drawable.sixeggs);
                break;
            case "8":
                pic1.setImageResource(R.drawable.eight);
                break;
            case "10":
                pic1.setImageResource(R.drawable.tencandles);
                break;
            case "5":
                pic1.setImageResource(R.drawable.fiveee);
                pic2.setImageResource(R.drawable.fiveee);
                pic3.setImageResource(R.drawable.fiveee);
                pic4.setImageResource(R.drawable.fiveee);
                pic5.setImageResource(R.drawable.fiveee);
                pic2.setVisibility(View.VISIBLE);
                pic3.setVisibility(View.VISIBLE);
                pic4.setVisibility(View.VISIBLE);
                pic5.setVisibility(View.VISIBLE);
                break;
            case "7":
                pic1.setImageResource(R.drawable.balls);
                pic6.setImageResource(R.drawable.balls);
                pic7.setImageResource(R.drawable.balls);
                pic4.setImageResource(R.drawable.balls);
                pic5.setImageResource(R.drawable.balls);
                pic8.setImageResource(R.drawable.balls);
                pic9.setImageResource(R.drawable.balls);

                pic6.setVisibility(View.VISIBLE);
                pic7.setVisibility(View.VISIBLE);
                pic4.setVisibility(View.VISIBLE);
                pic5.setVisibility(View.VISIBLE);
                pic8.setVisibility(View.VISIBLE);
                pic9.setVisibility(View.VISIBLE);
                break;
            case "9":

                pic1.setImageResource(R.drawable.balls);
                pic2.setImageResource(R.drawable.balls);
                pic3.setImageResource(R.drawable.balls);
                pic6.setImageResource(R.drawable.balls);
                pic7.setImageResource(R.drawable.balls);
                pic4.setImageResource(R.drawable.balls);
                pic5.setImageResource(R.drawable.balls);
                pic8.setImageResource(R.drawable.balls);
                pic9.setImageResource(R.drawable.balls);

                pic2.setVisibility(View.VISIBLE);
                pic3.setVisibility(View.VISIBLE);
                pic6.setVisibility(View.VISIBLE);
                pic7.setVisibility(View.VISIBLE);
                pic4.setVisibility(View.VISIBLE);
                pic5.setVisibility(View.VISIBLE);
                pic8.setVisibility(View.VISIBLE);
                pic9.setVisibility(View.VISIBLE);
                break;


        }
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

    public void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(NumStepThreeActivity.this);
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

                Intent i = new Intent(NumStepThreeActivity.this, NumStepFourActivity.class);
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

    private void characterInitialization(String name) {
        String path;
        Uri uri;
        switch (name){
            case "1":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num1;
                uri =Uri.parse(path);
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
                break;
            case "2":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num2;
                uri =Uri.parse(path);
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
                break;
            case "3":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num3;
                uri =Uri.parse(path);
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
                break;
            case "4":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num4;
                uri =Uri.parse(path);
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
                break;
            case "5":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num5;
                uri =Uri.parse(path);
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
                break;
            case "6":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num6;
                uri =Uri.parse(path);
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
            case "7":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num7;
                uri =Uri.parse(path);
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
                break;
            case "8":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num8;
                uri =Uri.parse(path);
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
                break;
            case "9":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num9;
                uri =Uri.parse(path);
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
                break;
            case "10":
                path = "android.resource://"+getPackageName()+"/"+ R.raw.num10;
                uri =Uri.parse(path);
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
                break;
        }
        // String path = "android.resource://"+getPackageName()+"/"+ R.raw.num1;
        // Uri uri =Uri.parse(path);
        //character.setVideoURI(uri);

    }


    private void initialization (){
        back = findViewById(R.id.backIcon);
        forward = findViewById(R.id.forward2);

        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");
        character=findViewById(R.id.character2);

        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");

        score=0;
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);

        pic1 = findViewById(R.id.image_);
        pic2 = findViewById(R.id.imageView15);
        pic3 = findViewById(R.id.imageView14);
        pic4 = findViewById(R.id.imageView10);
        pic5 = findViewById(R.id.imageView12);
        pic6 = findViewById(R.id.imageView9);
        pic7 = findViewById(R.id.imageView7);
        pic8 = findViewById(R.id.imageView11);
        pic9 = findViewById(R.id.imageView13);

        window = this.getWindow();
    }
}