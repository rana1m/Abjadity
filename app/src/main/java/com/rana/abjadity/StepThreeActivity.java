package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_three);

        initialization();
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
                            }
                        }
                    });
                    textView2.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View v) {
                            if(textView2.getText().equals(correctLetter)){
                                textView2.setBackgroundColor(R.color.green);
                            }
                        }
                    });
                    textView3.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onClick(View v) {
                            if(textView3.getText().equals(correctLetter)){
                                textView3.setBackgroundColor(R.color.green);
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
                Intent i = new Intent(StepThreeActivity.this,StepTowActivity.class);
                startActivity(i);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepThreeActivity.this,StepFourActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });
    }

    private void loadChoices(String name) {
        correctIndex = rand.nextInt(3)+1;


        if(correctIndex==1){
            textView1.setText(name);
            textView2.setText(letters.get(rand.nextInt(28)));
            textView3.setText(letters.get(rand.nextInt(28)));
        }

        if(correctIndex==2){
            textView2.setText(name);
            textView1.setText(letters.get(rand.nextInt(28)));
            textView3.setText(letters.get(rand.nextInt(28)));
        }

        if(correctIndex==3){
            textView3.setText(name);
            textView2.setText(letters.get(rand.nextInt(28)));
            textView1.setText(letters.get(rand.nextInt(28)));
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
        textView1=findViewById(R.id.text1);
        textView2=findViewById(R.id.text2);
        textView3=findViewById(R.id.text3);
        rand = new Random();
        storageReference = FirebaseStorage.getInstance().getReference();
        letters=new ArrayList<String>(Arrays.asList(
                "أ","ب","ت","ث","ج","ح","خ","د","ذ","ر",
                "ز","س","ش","ص","ض","ط","ظ","ع","غ","ق","ك","ل",
                "م","ن","ه","و","ي"));

    }
}