package com.rana.abjadity;

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
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Random;

public class NumStepFourActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "StepFourNumActivity";
    public int counter;
    private FloatingActionButton forward, backword;
    public ImageView balon1,balon2,balon3,balon4,balon5,balon6,balon7,balon8,balon9,balon10,balon11,balon12,right;
    private int num, score;
    final int random = new Random().nextInt((4 - 1) + 1) + 1;
    VideoView character;
    String numberId;
    String finalNum;
    String childId,parentId,childLevel,button;
    TextView level,scores;
    Window window ;
    Button SaveButton;
    View dialogView;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    MediaPlayer mediaPlayer;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        scoresAndLevel();
        Intent i = new Intent(NumStepFourActivity.this, NumStepThreeActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        i.putExtra("button",button);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_four_num);

        //Haifa here put the key of the intent , send to me the number of the step
        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            button = extras.getString("button");
        }
        num = Integer.parseInt(button);

        initialization();
        scoresAndLevel();
        characterInitialization();
        setImage(random);
        finalNum = checkNum(button);

        // perform click event on images's
        balon1.setOnClickListener(this);
        balon2.setOnClickListener(this);
        balon3.setOnClickListener(this);
        balon4.setOnClickListener(this);
        balon5.setOnClickListener(this);
        balon6.setOnClickListener(this);
        balon7.setOnClickListener(this);
        balon8.setOnClickListener(this);
        balon9.setOnClickListener(this);
        balon10.setOnClickListener(this);
        balon11.setOnClickListener(this);
        balon12.setOnClickListener(this);

        check_Next_Step();

        backword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(NumStepFourActivity.this, NumStepThreeActivity.class);
                i.putExtra("button",button);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                startActivity(i);

            }


        });






    }

    public void check_Next_Step() {
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(counter==num){
                    winningFunction();
                    scoresAndLevel();
                    visableImages();
                    counter=0;

                }else {
                    tryAgain();
                    visableImages();
                    counter=0;
                }


            }


        });
    }

    private void characterInitialization() {
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.stepnum4;
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
    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);

    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        character=findViewById(R.id.character2);
        counter = 0;
        balon1 = (ImageView) findViewById(R.id.balon13);
        balon2 = (ImageView) findViewById(R.id.balon14);
        balon3 = (ImageView) findViewById(R.id.balon3);
        balon4 = (ImageView) findViewById(R.id.balon15);
        balon5 = (ImageView) findViewById(R.id.balon16);
        balon6 = (ImageView) findViewById(R.id.balon17);
        balon7 = (ImageView) findViewById(R.id.balon18);
        balon8 = (ImageView) findViewById(R.id.balon19);
        balon9 = (ImageView) findViewById(R.id.balon20);
        balon10 = (ImageView) findViewById(R.id.balon22);
        balon11 = (ImageView) findViewById(R.id.balon23);
        balon12 = (ImageView) findViewById(R.id.balon24);
        forward = findViewById(R.id.forward2);
        backword = findViewById(R.id.backIcon);
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");
        score=0;
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);
        window = this.getWindow();
        right = (ImageView) findViewById(R.id.right);
    }


    private void visableImages() {
        balon1.setVisibility(View.VISIBLE);
        balon2.setVisibility(View.VISIBLE);
        balon3.setVisibility(View.VISIBLE);
        balon4.setVisibility(View.VISIBLE);
        balon5.setVisibility(View.VISIBLE);
        balon6.setVisibility(View.VISIBLE);
        balon7.setVisibility(View.VISIBLE);
        balon8.setVisibility(View.VISIBLE);
        balon9.setVisibility(View.VISIBLE);
        balon10.setVisibility(View.VISIBLE);
        balon11.setVisibility(View.VISIBLE);
        balon12.setVisibility(View.VISIBLE);
    }

    private void setImage(int random) {

        if(random==1){
            return;
        }else if(random==2){
            balon1.setImageResource(R.drawable.car);
            balon2.setImageResource(R.drawable.car);
            balon3.setImageResource(R.drawable.car);
            balon4.setImageResource(R.drawable.car);
            balon5.setImageResource(R.drawable.car);
            balon6.setImageResource(R.drawable.car);
            balon7.setImageResource(R.drawable.car);
            balon8.setImageResource(R.drawable.car);
            balon9.setImageResource(R.drawable.car);
            balon10.setImageResource(R.drawable.car);
            balon11.setImageResource(R.drawable.car);
            balon12.setImageResource(R.drawable.car);
        }else if(random==3){
            balon1.setImageResource(R.drawable.flower);
            balon2.setImageResource(R.drawable.flower);
            balon3.setImageResource(R.drawable.flower);
            balon4.setImageResource(R.drawable.flower);
            balon5.setImageResource(R.drawable.flower);
            balon6.setImageResource(R.drawable.flower);
            balon7.setImageResource(R.drawable.flower);
            balon8.setImageResource(R.drawable.flower);
            balon9.setImageResource(R.drawable.flower);
            balon10.setImageResource(R.drawable.flower);
            balon11.setImageResource(R.drawable.flower);
            balon12.setImageResource(R.drawable.flower);
        }else if(random==4){
            balon1.setImageResource(R.drawable.cake);
            balon2.setImageResource(R.drawable.cake);
            balon3.setImageResource(R.drawable.cake);
            balon4.setImageResource(R.drawable.cake);
            balon5.setImageResource(R.drawable.cake);
            balon6.setImageResource(R.drawable.cake);
            balon7.setImageResource(R.drawable.cake);
            balon8.setImageResource(R.drawable.cake);
            balon9.setImageResource(R.drawable.cake);
            balon10.setImageResource(R.drawable.cake);
            balon11.setImageResource(R.drawable.cake);
            balon12.setImageResource(R.drawable.cake);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.balon13:
                balon1.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon14:
                balon2.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon3:
                balon3.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon15:
                balon4.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon16:
                balon5.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon17:
                balon6.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon18:
                balon7.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon19:
                balon8.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon20:
                balon9.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon22:
                balon10.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon23:
                balon11.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon24:
                balon12.setVisibility(View.INVISIBLE);
                counter++;
                break;


        }
    }

    // Scores
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

    //UpdateScore
    private void updateScores() {
        score=5;
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

    private void playVoice(String s) throws IOException {
        mediaPlayer=new MediaPlayer();
        String path="";
        if(s.equals("try_again")){
            path = "android.resource://" + getPackageName() + "/" + R.raw.try_again;
        }
        else {
            path = "android.resource://" + getPackageName() + "/" + R.raw.correct_answer;
        }
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

    // winning when correct answer choosed
    public void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(NumStepFourActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.correct_answer_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initializationForDialog();
        //play voice
        try {
            character.pause();
            playVoice("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(), finalNum+"", Toast.LENGTH_LONG).show();
        //update scores
        updateScores();
        scoresAndLevel();
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NumStepFourActivity.this,FinalNumActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                i.putExtra("num",finalNum);
                startActivity(i);
                alertDialog.dismiss();
            }
            public boolean onTouchEvent(MotionEvent event)
            {

                if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    visableImages();
                    counter=0;
                    characterInitialization();
                }
                return false;
            }
        });
    }

    //InCorrect answer
    private void tryAgain() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(NumStepFourActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.wrong_answer_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initializationForDialog();
        //play voice
        try {
            playVoice("try_again");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public String checkNum(String Digits){
        switch(Digits){
            case "2":
                return "30";
            case "3":
                return "31";
            case "4":
                return "32";
            case "5":
                return "33";
            case "6":
                return "34";
            case "7":
                return "35";
            case "8":
                return "36";
            case "9":
                return "37";
            case "10":
                return "38";

        }
        return "29";
    }




}