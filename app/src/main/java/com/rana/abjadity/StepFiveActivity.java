package com.rana.abjadity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class StepFiveActivity extends AppCompatActivity {

    private static final String TAG = "StepFiveActivity";
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    FirebaseDatabase database;
    static DatabaseReference accountRef,alphabetsRef,digitsRef;
    static String childId,parentId,childLevel,button,letterChant,letterName;
    VideoView character;
    FloatingActionButton back,forward;
    ImageView microphoneIcon;
    TextView speechToText;
    Window window ;
    MediaPlayer mediaPlayer;
    static int score;
    View dialogView;
    private Button SaveButton;
     TextView level,scores;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StepFiveActivity.this,StepFourActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        i.putExtra("button",button);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_five);

        initialization();
        scoresAndLevel();

        characterInitialization();


        alphabetsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //loop through letters to
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Letter letter = userSnapshot.getValue(Letter.class);
//                    Log.e(TAG,letter.getChant());

                    microphoneIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            speak(letter.getChant(),letter.getName());
                        }
                    });

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
                Intent i = new Intent(StepFiveActivity.this,StepFourActivity.class);
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
                Intent i = new Intent(StepFiveActivity.this,StepThreeActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                character.pause();
                startActivity(i);
            }
        });
    }

    private void speak(String letterChant, String letterName) {
        this.letterChant =letterChant;
        this.letterName =letterName;

        //speech to text dialogs
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL ,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "تحدث الآن ...");


        // Start Intent
        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(requestCode != RESULT_OK && null != data){
                    // get the text array from voice intent
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    speechToText.setText(result.get(0));

                    // check for chant
                    if(result.get(0).equals(letterChant)||result.get(0).equals(letterName)){
                        winningFunction();

                    }else {
                        tryAgain();
                    }
                }
                break;
            }
        }
    }

    private void tryAgain() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepFiveActivity.this);
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

    private void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepFiveActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.correct_answer_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        initializationForDialog();
        //play voice
        try {
//            character.pause();
            playVoice("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update scores
        updateScores();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(StepFiveActivity.this,StepThreeActivity.class);
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

    private static void updateScores() {
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

    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);

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
    private void characterInitialization() {
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.step_five;
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
        microphoneIcon=findViewById(R.id.microphoneIcon);
        speechToText=findViewById(R.id.speechToText);
        window = this.getWindow();
        score=0;
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);

    }
}