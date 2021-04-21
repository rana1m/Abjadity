package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.Random;

public class StepThreeActivity extends AppCompatActivity {

    private static final String TAG = "StepThreeActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef,alphabetsRef,digitsRef;
    String childId,parentId,childLevel,button,correctLetter;
    VideoView character;
    FloatingActionButton back,forward;
    int correctIndex;
    Random rand;
    TextView textView1,textView2,textView3;
    ArrayList<String> letters;
    StorageReference storageReference ;
    Window window ;
    MediaPlayer mediaPlayer;
    int score;
    View dialogView;
    private Button SaveButton;
    TextView level,scores;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StepThreeActivity.this,StepFiveActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        i.putExtra("button",button);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_three);

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
                    correctLetter=letter.getName();

                    loadImages(1);
                    loadImages(2);
                    loadImages(3);
                    loadImages(4);
                    loadChoices(correctLetter);

                    textView1.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View v) {
                            if(textView1.getText().equals(correctLetter)){
                                textView1.setBackgroundColor(R.color.green);
                                     winningFunction();
                            }else{
                                tryAgain();
                            }
                        }
                    });
                    textView2.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View v) {
                            if(textView2.getText().equals(correctLetter)){
                                textView2.setBackgroundColor(R.color.green);
                                winningFunction();
                            }else{
                                tryAgain();
                            }
                        }
                    });
                    textView3.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View v) {
                            if(textView3.getText().equals(correctLetter)){
                                textView3.setBackgroundColor(R.color.green);
                                winningFunction();
                            }else{
                                tryAgain();
                            }
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
                Intent i = new Intent(StepThreeActivity.this,StepFiveActivity.class);
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
                Intent i = new Intent(StepThreeActivity.this,StepSevenActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                character.pause();
                startActivity(i);
            }
        });
    }

    private void tryAgain() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepThreeActivity.this);
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

    private void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(StepThreeActivity.this);
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

                Intent i = new Intent(StepThreeActivity.this,StepSevenActivity.class);
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

    private void loadChoices(String name) {
        String randomLetter="";
        correctIndex = rand.nextInt(3)+1;


        if(correctIndex==1){
            textView1.setText(name);
            //choice1
            randomLetter=letters.get(rand.nextInt(28));
            if(!randomLetter.equals(name)){
                textView2.setText(randomLetter);
            } else {
                textView2.setText(letters.get(rand.nextInt(28)));
            }
            //choice2
            randomLetter=letters.get(rand.nextInt(28));
            if(!randomLetter.equals(name)){
                textView3.setText(randomLetter);
            } else {
                textView3.setText(letters.get(rand.nextInt(28)));
            }
        }

        if(correctIndex==2){
            textView2.setText(name);
            //choice1
            randomLetter=letters.get(rand.nextInt(28));
            if(!randomLetter.equals(name)){
                textView1.setText(randomLetter);
            } else {
                textView1.setText(letters.get(rand.nextInt(28)));
            }
            //choice2
            randomLetter=letters.get(rand.nextInt(28));
            if(!randomLetter.equals(name)){
                textView3.setText(randomLetter);
            } else {
                textView3.setText(letters.get(rand.nextInt(28)));
            }
        }

        if(correctIndex==3){
            textView3.setText(name);     //choice1
            randomLetter=letters.get(rand.nextInt(28));
            if(!randomLetter.equals(name)){
                textView2.setText(randomLetter);
            } else {
                textView2.setText(letters.get(rand.nextInt(28)));
            }
            //choice2
            randomLetter=letters.get(rand.nextInt(28));
            if(!randomLetter.equals(name)){
                textView1.setText(randomLetter);
            } else {
                textView1.setText(letters.get(rand.nextInt(28)));
            }
        }
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
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.step_three;
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
        textView1=findViewById(R.id.text1);
        textView2=findViewById(R.id.text2);
        textView3=findViewById(R.id.text3);
        rand = new Random();
        window = this.getWindow();
        score=0;
        storageReference = FirebaseStorage.getInstance().getReference();
        letters=new ArrayList<String>(Arrays.asList(
                "أ","ب","ت","ث","ج","ح","خ"
                ,"د","ذ","ر", "ز","س","ش","ص"
                ,"ض","ط","ظ","ع","ف","غ","ق","ك","ل",
                "م","ن","ه","و","ي"));
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);

    }
}