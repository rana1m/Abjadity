package com.rana.abjadity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class StepFourNumActivity extends AppCompatActivity implements View.OnClickListener {

    public int counter;
    private FloatingActionButton forward, backword;
    public ImageView balon1,balon2,balon3,balon4,balon5,balon6,balon7,balon8,balon9,balon10,balon11,balon12;
    private int num;
    final int random = new Random().nextInt((4 - 1) + 1) + 1;
    VideoView character;
    String numberId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_four_num);

        //Haifa here put the key of the intent , send to me the number of the step
        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            numberId = extras.getString("button");
        }
        num = Integer.parseInt(numberId);

        initialization();
        characterInitialization();
        setImage(random);
// perform click event on images's
        balon1.setOnClickListener(this);
        balon2.setOnClickListener(this);
        balon3.setOnClickListener(this);
        balon4.setOnClickListener(this);
        balon5.setOnClickListener(this);
        balon6.setOnClickListener(this);
        balon7.setOnClickListener(this);
        balon8.setOnClickListener(this);
        balon9.setOnClickListener(this);
        balon10.setOnClickListener(this);
        balon11.setOnClickListener(this);
        balon12.setOnClickListener(this);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(counter==num){
                    Toast.makeText(StepFourNumActivity.this, "تهانينا حصلت على 3 نقاط", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(StepFourNumActivity.this, MapActivity.class);
                    startActivity(intent);
                    counter=0;
                }else {
                    Toast.makeText(StepFourNumActivity.this, "حاول مرة أخرى!", Toast.LENGTH_LONG).show();
                    visableImages();
                    counter=0;
                }


            }


        });

        backword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StepFourNumActivity.this, OneActivity.class);
                intent.putExtra("button",num);
                startActivity(intent);

            }


        });






    }

    private void characterInitialization() {
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.h1;
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

    private void initialization() {
        character=findViewById(R.id.character2);
        counter = 0;
        balon1 = (ImageView) findViewById(R.id.balon13);
        balon2 = (ImageView) findViewById(R.id.balon14);
        balon3 = (ImageView) findViewById(R.id.balon3);
        balon4 = (ImageView) findViewById(R.id.balon15);
        balon5 = (ImageView) findViewById(R.id.balon16);
        balon6 = (ImageView) findViewById(R.id.balon17);
        balon7 = (ImageView) findViewById(R.id.balon18);
        balon8 = (ImageView) findViewById(R.id.balon19);
        balon9 = (ImageView) findViewById(R.id.balon20);
        balon10 = (ImageView) findViewById(R.id.balon22);
        balon11 = (ImageView) findViewById(R.id.balon23);
        balon12 = (ImageView) findViewById(R.id.balon24);
        forward = findViewById(R.id.forward2);
        backword = findViewById(R.id.backIcon);
    }


    private void visableImages() {
        balon1.setVisibility(View.VISIBLE);
        balon2.setVisibility(View.VISIBLE);
        balon3.setVisibility(View.VISIBLE);
        balon4.setVisibility(View.VISIBLE);
        balon5.setVisibility(View.VISIBLE);
        balon6.setVisibility(View.VISIBLE);
        balon7.setVisibility(View.VISIBLE);
        balon8.setVisibility(View.VISIBLE);
        balon9.setVisibility(View.VISIBLE);
        balon10.setVisibility(View.VISIBLE);
        balon11.setVisibility(View.VISIBLE);
        balon12.setVisibility(View.VISIBLE);
    }

    private void setImage(int random) {

        if(random==1){
            return;
        }else if(random==2){
            balon1.setImageResource(R.drawable.car);
            balon2.setImageResource(R.drawable.car);
            balon3.setImageResource(R.drawable.car);
            balon4.setImageResource(R.drawable.car);
            balon5.setImageResource(R.drawable.car);
            balon6.setImageResource(R.drawable.car);
            balon7.setImageResource(R.drawable.car);
            balon8.setImageResource(R.drawable.car);
            balon9.setImageResource(R.drawable.car);
            balon10.setImageResource(R.drawable.car);
            balon11.setImageResource(R.drawable.car);
            balon12.setImageResource(R.drawable.car);
        }else if(random==3){
            balon1.setImageResource(R.drawable.flower);
            balon2.setImageResource(R.drawable.flower);
            balon3.setImageResource(R.drawable.flower);
            balon4.setImageResource(R.drawable.flower);
            balon5.setImageResource(R.drawable.flower);
            balon6.setImageResource(R.drawable.flower);
            balon7.setImageResource(R.drawable.flower);
            balon8.setImageResource(R.drawable.flower);
            balon9.setImageResource(R.drawable.flower);
            balon10.setImageResource(R.drawable.flower);
            balon11.setImageResource(R.drawable.flower);
            balon12.setImageResource(R.drawable.flower);
        }else if(random==4){
            balon1.setImageResource(R.drawable.cake);
            balon2.setImageResource(R.drawable.cake);
            balon3.setImageResource(R.drawable.cake);
            balon4.setImageResource(R.drawable.cake);
            balon5.setImageResource(R.drawable.cake);
            balon6.setImageResource(R.drawable.cake);
            balon7.setImageResource(R.drawable.cake);
            balon8.setImageResource(R.drawable.cake);
            balon9.setImageResource(R.drawable.cake);
            balon10.setImageResource(R.drawable.cake);
            balon11.setImageResource(R.drawable.cake);
            balon12.setImageResource(R.drawable.cake);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.balon13:
                balon1.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon14:
                balon2.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon3:
                balon3.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon15:
                balon4.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon16:
                balon5.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon17:
                balon6.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon18:
                balon7.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon19:
                balon8.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon20:
                balon9.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon22:
                balon10.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon23:
                balon11.setVisibility(View.INVISIBLE);
                counter++;
                break;
            case R.id.balon24:
                balon12.setVisibility(View.INVISIBLE);
                counter++;
                break;


        }
    }
}