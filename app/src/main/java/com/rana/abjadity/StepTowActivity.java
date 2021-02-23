package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class StepTowActivity extends AppCompatActivity {

    private static final String TAG = "StepTowActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef,alphabetsRef,digitsRef;
    String childId,parentId,childLevel,button;
    VideoView character;
    FloatingActionButton back,forward,play;
    FrameLayout frameLayout;
    MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_tow);

        try {
            initialization();
        } catch (IOException e) {
            e.printStackTrace();
        }

        characterInitialization();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepTowActivity.this,ArActivity.class);
                i.putExtra("button",button);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepTowActivity.this,StepOneActivity.class);
                startActivity(i);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepTowActivity.this,StepThreeActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });

    }


    private void characterInitialization() {
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.v2;
        Uri uri =Uri.parse(path);
        character.setVideoURI(uri);
        character.setZOrderOnTop(true);
        //character.start();
        character.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View sv, MotionEvent event) {
                character.start();
                return false;
            }
        });
    }

    private void initialization () throws IOException {
        database = FirebaseDatabase.getInstance();
        alphabetsRef = database.getReference("alphabets");

        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");

        character=findViewById(R.id.character);
        back=findViewById(R.id.back);
        forward=findViewById(R.id.forward);
        play=findViewById(R.id.playIcon);
        mediaPlayer=new MediaPlayer();
        String path = "https://f.top4top.io/m_1877uwe3c1.m4a";
        Uri uri =Uri.parse(path);
        mediaPlayer.setDataSource(this,uri);
        mediaPlayer.prepareAsync();

    }


}