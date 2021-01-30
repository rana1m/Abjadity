package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class CharacterActivity extends AppCompatActivity {

    TextView header;
    ImageButton back;
    ImageView char1, char2, char3, char4;
    ScrollView charScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        initialization();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CharacterActivity.this,ChildProfileActivity.class);
                startActivity(i);
            }
        });
        charScroll.post(new Runnable() {
            public void run() {
                charScroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private void initialization(){
        back = findViewById(R.id.back);
        char1 = findViewById(R.id.char1);
        char2 = findViewById(R.id.char2);
        char3 = findViewById(R.id.char3);
        char4 = findViewById(R.id.char4);
        charScroll = findViewById(R.id.charScroll);

    }
}