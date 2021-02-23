package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class StepFourActivity extends AppCompatActivity {

    private static final String TAG = "StepThreeActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef,alphabetsRef,digitsRef;
    String childId,parentId,childLevel,button;
    VideoView character;
    FloatingActionButton back,forward;
    ImageView imageView1,imageView2,imageView3,imageView4;
    TextView textView1,textView2,textView3,textView4;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_four);


        initialization();
        characterInitialization();
        

        alphabetsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //loop through letters to
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    Letter letter = userSnapshot.getValue(Letter.class);
                    Log.e(TAG,letter.getName());

                    loadImages(letter.getImage1(),letter.getImage2(),letter.getImage3(),letter.getImage4());
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
                startActivity(i);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepFourActivity.this,StepThreeActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });
    }

    private void loadWords(String word1, String word2, String word3, String word4) {
        textView1.setText(word1);
        textView2.setText(word2);
        textView3.setText(word3);
        textView4.setText(word4);
    }


    private void loadImages(String image1, String image2, String image3, String image4) {
        Picasso.get().load(image1).into(imageView1);
        Picasso.get().load(image2).into(imageView2);
        Picasso.get().load(image3).into(imageView3);
        Picasso.get().load(image4).into(imageView4);
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
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
  

        
    }
}