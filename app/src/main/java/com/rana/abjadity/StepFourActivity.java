package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class StepFourActivity extends AppCompatActivity {

    private static final String TAG = "StepFourActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef,alphabetsRef,digitsRef;
    String childId,parentId,childLevel,button;
    VideoView character;
    FloatingActionButton back,forward;
    TextView textView1,textView2,textView3,textView4;
    StorageReference storageReference ;
    MediaPlayer mediaPlayer,letterChant;
    Window window ;
    Button SaveButton;
    View dialogView;
    TextView correct;
    int score;
    TextView level,scores;
    TextToSpeech textToSpeech;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StepFourActivity.this,StepTowActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        i.putExtra("button",button);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_four);


        initialization();
        scoresAndLevel();
        characterInitialization();
        

        alphabetsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //loop through letters to
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Letter letter = userSnapshot.getValue(Letter.class);
                    Log.e(TAG,letter.getName());

                    loadImages(1);
                    loadImages(2);
                    loadImages(3);
                    loadImages(4);
                    loadWords(letter.getWord1(),letter.getWord2(),letter.getWord3(),letter.getWord4());
                    
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepFourActivity.this,StepTowActivity.class);
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

    private void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepFourActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);

        dialogView = getLayoutInflater().inflate(R.layout.next_step_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
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
                Intent i = new Intent(StepFourActivity.this,StepFiveActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                mediaPlayer.stop();
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

    private void tryAgain() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepFourActivity.this);
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

    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        correct = dialogView.findViewById(R.id.correct);

    }
    private void playLetterChant() throws IOException {

        letterChant=new MediaPlayer();
        String path;
        if(button.equals("1")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l1;
        }else if(button.equals("2")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l2;
        }else if(button.equals("3")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l3;
        }  else if(button.equals("4")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l4;
        }  else if(button.equals("5")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l5;
        }  else if(button.equals("6")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l6;
        }  else if(button.equals("7")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l7;
        }  else if(button.equals("8")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l8;
        }  else if(button.equals("9")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l9;
        }  else if(button.equals("10")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l10;
        }  else if(button.equals("11")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l11;
        }  else if(button.equals("12")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l12;
        }  else if(button.equals("13")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l13;
        }  else if(button.equals("14")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l14;
        }  else if(button.equals("15")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l15;
        }  else if(button.equals("16")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l16;
        }  else if(button.equals("17")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l17;
        }  else if(button.equals("18")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l18;
        }  else if(button.equals("19")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l19;
        }  else if(button.equals("20")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l20;
        }  else if(button.equals("21")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l21;
        }  else if(button.equals("22")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l22;
        }  else if(button.equals("23")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l23;
        }  else if(button.equals("24")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l24;
        }  else if(button.equals("25")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l25;
        }  else if(button.equals("26")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l26;
        }  else if(button.equals("27")){
            path = "android.resource://"+getPackageName()+"/"+ R.raw.l27;
        }  else if(button.equals("28")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.l28;
        }
        else {
            path = "android.resource://" + getPackageName() + "/" + R.raw.l1;
        }
        Uri uri =Uri.parse(path);
        letterChant.setDataSource(this,uri);
        letterChant.prepareAsync();
        letterChant.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }
    private void loadWords(String word1, String word2, String word3, String word4) {
        textView1.setText(word1);
        textView2.setText(word2);
        textView3.setText(word3);
        textView4.setText(word4);
    }

    private void loadImages(int index) {
        storageReference.child("/images/"+button+"/"+index+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri)
                        .into((ImageView) findViewById(getResources()
                                .getIdentifier("imageView" + index, "id",getPackageName())
                        ));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void characterInitialization() {
        String path = "android.resource://"+getPackageName()+"/"+R.raw.step_one;
        Uri uri =Uri.parse(path);
        character.setVideoURI(uri);
        character.setZOrderOnTop(true);
        character.pause();
        character.start();
        character.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View sv, MotionEvent event) {
                character.start();
                try {
                    playLetterChant();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private void initialization () {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        alphabetsRef = database.getReference("alphabets");
        digitsRef = database.getReference("digits");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");
        character=findViewById(R.id.character);
        back=findViewById(R.id.back);
        forward=findViewById(R.id.forward);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        storageReference = FirebaseStorage.getInstance().getReference();
        window = this.getWindow();
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);
        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
//                if (status==TextToSpeech.SUCCESS){
//
//                }

            }
        });
    }
}