package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton back;
    ImageView char1, char2, char3, char4, char5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        initialization();

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent backIntent = getIntent();
//                setResult(RESULT_OK,backIntent);
//                finish();
//            }
//        });

        ////ghkjmjcjcvbchcgcg
        char1.setOnClickListener(this);
        char2.setOnClickListener(this);
        char3.setOnClickListener(this);
        char4.setOnClickListener(this);
        char5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.char1:
                Intent char1 = new Intent(CharacterActivity.this,ChildProfileActivity.class);
                char1.putExtra("char",R.drawable.char1);
                startActivity(char1);
                finish();
                break;
            case R.id.char2:
                Intent char2 = new Intent(CharacterActivity.this,ChildProfileActivity.class);
                char2.putExtra("char",R.drawable.char2);
                startActivity(char2);
                finish();
                break;
            case R.id.char3:
                Intent char3 = new Intent(CharacterActivity.this,ChildProfileActivity.class);
                char3.putExtra("char",R.drawable.char3);
                startActivity(char3);
                finish();
                break;
            case R.id.char4:
                Intent char4 = new Intent(CharacterActivity.this,ChildProfileActivity.class);
                char4.putExtra("char",R.drawable.char4);
                startActivity(char4);
                finish();
                break;
            case R.id.char5:
                Intent char5 = new Intent(CharacterActivity.this,ChildProfileActivity.class);
                char5.putExtra("char",R.drawable.char5);
                startActivity(char5);
                finish();
                break;

        }
    }

    private void initialization(){
        back = findViewById(R.id.back);
        char1 = findViewById(R.id.char1);
        char2 = findViewById(R.id.char2);
        char3 = findViewById(R.id.char3);
        char4 = findViewById(R.id.char4);
        char5 = findViewById(R.id.char5);
    }

}