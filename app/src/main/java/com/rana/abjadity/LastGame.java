package com.rana.abjadity;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
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

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class LastGame extends Activity {
    public String color = "";
    public String finalColor = "";
    FirebaseDatabase database;
    DatabaseReference accountRef;
    Button v_red, v_blue, v_green, v_move, v_yellow, v_white,result;
    ImageView img;
    String childId, parentId;
    int num = 28, score;
    TextView word;
    TextView level,scores;
    final int random = new Random().nextInt((num - 1) + 1) + 1;
    Window window ;
    MediaPlayer mediaPlayer;
    Button SaveButton;
    View dialogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_game);
        // myView = new MyView(this);

        //  findViewById(R.id.dashBoard);
        initilaization();

        randomPicture(random);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(color.equals("red")){
                    setTint(img,R.color.red);
                    finalColor = "red";

                }else if(color.equals("green")){
                    setTint(img,R.color.green);
                    finalColor = "green";
                }else if(color.equals("yellow")){
                    setTint(img,R.color.yellow);
                    finalColor = "yellow";
                }else if(color.equals("blue")){
                    setTint(img,R.color.purple_500);
                    finalColor = "blue";
                }else if(color.equals("move")){
                    setTint(img,R.color.purple_200);
                    finalColor = "move";
                }else if(color.equals("white")){
                    setTint(img,R.color.pink);
                    finalColor = "white";
                }else{
                    Toast.makeText(getApplicationContext(),"اختر لون",Toast.LENGTH_SHORT).show();
                }
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalColor!=""){
                    winningFunction();
                   // Toast.makeText(getApplicationContext(),"Good"+random,Toast.LENGTH_LONG).show();
                }else {
                    tryAgain();
                }
            }
        });




    } // EndOnCreate

    public void setTint(@NotNull ImageView $this$setTint, @ColorRes int colorRes) {
        Intrinsics.checkNotNullParameter($this$setTint, "$this$setTint");
        ImageViewCompat.setImageTintList($this$setTint, ColorStateList.valueOf(ContextCompat.getColor($this$setTint.getContext(), colorRes)));
    }

    public void checkColor(View v){
        if(v == v_red){
            color = "red";
        }else if (v == v_green){
            color = "green";
        }else if(v == v_yellow){
            color = "yellow";
        }else if(v == v_blue){
            color = "blue";
        }else if(v == v_move){
            color = "move";
        }else if(v == v_white){
            color = "white";
        }else{
            color ="";
        }
    }




    private void randomPicture(int random) {
        switch (random){
            case 1: case 2: case 3: case 4:
                img.setImageResource(R.drawable.crown);
                word.setText("حرف التاء");
                break;
            case 5: case 6: case 7:
                img.setImageResource(R.drawable.carrot);
                word.setText("حرف الجيم");
            break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                img.setImageResource(R.drawable.banda);
                word.setText("حرف الزاء");
                break;
            case 15: case 16: case 17: case 18:
                img.setImageResource(R.drawable.frog);
                word.setText("حرف الضاد");
                break;
            case 19: case 20: case 21:
                img.setImageResource(R.drawable.strwbary);
                word.setText("حرف الفاء");
                break;
            case 22: case 23: case 24: case 25:
                img.setImageResource(R.drawable.onnne);
                word.setText("رقم 1");
                break;
            case 26: case 27: case 28:
            img.setImageResource(R.drawable.hand);
            word.setText("حرف الياء");
        }

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

    public void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(LastGame.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.correct_answer_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        initializationForDialog();
        //play voice
        try {
           // character.pause();
            playVoice("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update scores
        updateScores();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LastGame.this,gameMenuActivity.class);
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


    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);

    }

    private void tryAgain() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(LastGame.this);
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



    private void initilaization() {
        v_red = (Button) findViewById(R.id.v_red);
        v_blue = (Button) findViewById(R.id.v_blue);
        v_move = (Button) findViewById(R.id.v_move);
        v_yellow = (Button) findViewById(R.id.v_yellow);
        v_white = (Button) findViewById(R.id.v_white);
        v_green = (Button) findViewById(R.id.v_green);
        word = (TextView) findViewById(R.id.textView15);
        img = (ImageView) findViewById(R.id.img);
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        result = findViewById(R.id.result);
        window = this.getWindow();
    }





}