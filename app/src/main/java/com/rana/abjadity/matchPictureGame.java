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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class matchPictureGame extends AppCompatActivity {

    String childId,parentId;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    TextView level,scores;
    int score;
    ImageView curView = null;
    private int countPair = 0;
    int matchNum=0;
    FloatingActionButton back;
    MediaPlayer mediaPlayer;
    Window window ;
    View dialogView;
    Button SaveButton, ContinuesButton;
    LottieAnimationView animationView;
    final int[] drawable = new int[] {
            R.drawable.sample_000,
            R.drawable.sample_1,
            R.drawable.sample_2222,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_66,
            R.drawable.sample_7
    };
    int[] pos = {0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7};
    int currentPos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_picture_game);

        initilaization();
        scoresAndLevel();

        GridView gridView = (GridView)findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentPos < 0 ) {
                    currentPos = position;
                    curView = (ImageView) view;
                    ((ImageView) view).setImageResource(drawable[pos[position]]);
                }
                else {
                    if (currentPos == position) {
                        // ((ImageView) view).setImageResource(R.drawable.hidden);
                        Toast.makeText(matchPictureGame.this, "الرجاء اختيار الصورة المشابهة", Toast.LENGTH_LONG).show();
                    } else if (pos[currentPos] != pos[position]) {
                        curView.setImageResource(R.drawable.idean);
                        Toast.makeText(matchPictureGame.this, "ليست كما تعتقد!", Toast.LENGTH_LONG).show();
                    } else if (pos[currentPos] == pos[position]) {
                        ((ImageView) view).setImageResource(drawable[pos[position]]);
                        matchNum++;
                        if (matchNum==8){
                           // winningFunction();
                            Toast.makeText(matchPictureGame.this, "لقد فزت", Toast.LENGTH_LONG).show();
                            PopUpWinning();

                        }
                        currentPos=position;
                    }
                    currentPos = -1;
                }
            }
        });

    }


    public void PopUpWinning(){
        AlertDialog.Builder builder = new AlertDialog.Builder(matchPictureGame.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.correct_answer_game, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initializationForDialog();
        try {
            playVoice();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateScores();

        ContinuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(matchPictureGame.this, gameMenuActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                startActivity(i);
                alertDialog.dismiss();
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
    private void playVoice() throws IOException {
        mediaPlayer=new MediaPlayer();
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.correct_answer;
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


/*
    public void winningFunction() {
        window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(matchPictureGame.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.correct_answer_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        initializationForDialog();
        //play voice
        try {
            playVoice();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update scores
        updateScores();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(matchPictureGame.this,gameMenuActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
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
*/

    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        ContinuesButton = dialogView.findViewById(R.id.completeButton);

    }

    private void initilaization() {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);
        back=findViewById(R.id.back);
        window = this.getWindow();
        animationView = findViewById(R.id.animationView);
    }





}