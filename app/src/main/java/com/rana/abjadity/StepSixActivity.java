package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StepSixActivity extends AppCompatActivity {


    private static final String TAG = "StepSixActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef, alphabetsRef, digitsRef;
    String childId, parentId, childLevel, button, letterName;
    VideoView character;
    FloatingActionButton back, forward;
    CameraView cameraView;
    Button detect;
    TextView level,scores;
    TextView textView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StepSixActivity.this, StepFiveActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i.putExtra("childLevel",childLevel);
        i.putExtra("button",button);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_six);

        initialization();
        scoresAndLevel();

        characterInitialization();

        alphabetsRef.orderByChild("id").equalTo(button).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //loop through letters to
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Letter letter = userSnapshot.getValue(Letter.class);

                    cameraView.addCameraKitListener(new CameraKitEventListener() {
                        @Override
                        public void onEvent(CameraKitEvent cameraKitEvent) {

                        }

                        @Override
                        public void onError(CameraKitError cameraKitError) {

                        }

                        @Override
                        public void onImage(CameraKitImage cameraKitImage) {
                            Bitmap bitmap = cameraKitImage.getBitmap();
                            bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                            cameraView.stop();
                            runDetector(bitmap, letter.getName());
                        }

                        @Override
                        public void onVideo(CameraKitVideo cameraKitVideo) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepSixActivity.this, StepFiveActivity.class);
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
                Intent i = new Intent(StepSixActivity.this, StepSevenActivity.class);
                i.putExtra("childId", childId);
                i.putExtra("parentId", parentId);
                i.putExtra("childLevel", childLevel);
                i.putExtra("button", button);
                startActivity(i);
            }
        });
    }

    private void runDetector(Bitmap bitmap, String name) {
        letterName = name;
        final FirebaseVisionImage img = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionOnDeviceImageLabelerOptions options =
                new FirebaseVisionOnDeviceImageLabelerOptions.Builder()
                        .setConfidenceThreshold(0.7f)
                        .build();
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                .getOnDeviceImageLabeler(options);

        labeler.processImage(img)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        ProcessResult(labels,name);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }


    private void ProcessResult(List<FirebaseVisionImageLabel> FirebaseVisionImageLabel, String name) {
        for (FirebaseVisionImageLabel lable : FirebaseVisionImageLabel) {
            Log.e(TAG,lable.getText());
            translateAndCheck(lable.getText(),name);
        }
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

    private void translateAndCheck(String text,String name) {

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.AR)
                        .build();
        final FirebaseTranslator englishArabicTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);


        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishArabicTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                englishArabicTranslator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(String s) {
                                        textView.append("-"+s);
                                        char firstLetter=s.charAt(0);
                                        char letter=name.charAt(0);
                                        if(firstLetter==letter){
                                            Toast.makeText(StepSixActivity.this,"corrrrrect",Toast.LENGTH_LONG).show();

                                        }else {
                                            Toast.makeText(StepSixActivity.this,"try again",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                            }
                        });
    }

    private void characterInitialization() {
        String path = "android.resource://" + getPackageName() + "/" + R.raw.v2;
        Uri uri = Uri.parse(path);
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

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        alphabetsRef = database.getReference("alphabets");
        digitsRef = database.getReference("digits");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childLevel = getIntent().getStringExtra("childLevel");
        button = getIntent().getStringExtra("button");
        character = findViewById(R.id.character);
        back = findViewById(R.id.back);
        forward = findViewById(R.id.forward);
        cameraView = findViewById(R.id.camera_view);
        detect = findViewById(R.id.btn_detect);
        textView = findViewById(R.id.textView9);
        level=findViewById(R.id.level);
        scores=findViewById(R.id.score);

    }

}