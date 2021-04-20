package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class gameMenuActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Intent i;
    String childId,parentId;
    ConstraintLayout catchingGame, matchPicturesGame, arrangmentOfLettersGame;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    int level;
    View dialogView;
    Button SaveButton;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        initialization();
        RetreiveLevel();

        catchingGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level==0){
                    popUpDialog1();
                }else {
                    Intent i = new Intent(gameMenuActivity.this, catchingGame.class);
                    i.putExtra("childId",childId);
                    i.putExtra("parentId",parentId);
                    startActivity(i);
                }

            }
        });

        matchPicturesGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(gameMenuActivity.this, LastGame.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                startActivity(i);

            }
        });
        arrangmentOfLettersGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level>=28){
                    Intent i = new Intent(gameMenuActivity.this, HaifaGameActivity.class);
                    i.putExtra("childId",childId);
                    i.putExtra("parentId",parentId);
                    startActivity(i);
                }else{
                    popUpDialog2();
                }
            }
        });



        bottomNavigationView.setSelectedItemId(R.id.gameActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.back:
                        i = new Intent(gameMenuActivity.this,MapActivity.class);
                        startActivity(i);
                        break;
                    case R.id.profileActivity:
                        i = new Intent(gameMenuActivity.this,ChildProfileActivity2.class);
                        i.putExtra("childId",childId);
                        i.putExtra("parentId",parentId);
                        startActivity(i);
                        break;
                    case R.id.mapActivity:
                        i = new Intent(gameMenuActivity.this,MapActivity.class);
                        i.putExtra("childId",childId);
                        i.putExtra("parentId",parentId);
                        startActivity(i);
                        break;

                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(gameMenuActivity.this,MapActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        startActivity(i);
    }

    private void popUpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(gameMenuActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.play_previous_stages_first, viewGroup, false);
        initializationForDialog();
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    private void popUpDialog1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(gameMenuActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.play_previous_stages_first, viewGroup, false);
        initializationForDialog();
        textView.setText("يجب أن تنهي المرحلة الأولى أولاً");
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    private void popUpDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(gameMenuActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.play_previos_stage2, viewGroup, false);
        initializationForDialog();
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void RetreiveLevel() {
        // to get level
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot userchildren : userSnapshot.child("children").getChildren()) {
                        Child child = userchildren.getValue(Child.class);

                        if (child.getId().equals(childId)) {
                            level = child.getLevel();
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
        textView =dialogView.findViewById(R.id.correct);
    }

    private void initialization() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        catchingGame=findViewById(R.id.catchingGame);
        matchPicturesGame = findViewById(R.id.matchPictures);
        arrangmentOfLettersGame = findViewById(R.id.constraintLacgyout2);
    }
}