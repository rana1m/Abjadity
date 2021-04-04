package com.rana.abjadity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

class HaifaGameActivity extends AppCompatActivity {
    private int presCounter=0;
    private int maxpresCounter=3;
    private int score;
    TextView level,scores;
    View dialogView;
    final int random = new Random().nextInt((8 - 1) + 1) + 1;
    String[] keys = new String[]{"س", "د", "أ"};
    String textAnswer = "أسد";
    TextView title;
    ImageView gameImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        setImageAndKeys(random);

        keys = shuffleArray(keys);

        for (String key : keys){
            addView(((LinearLayout)findViewById(R.id.layoutParent)),key,((EditText)findViewById(R.id.word)));
        }
        maxpresCounter = 3;


    }

    private void setImageAndKeys(int random) {
        if(random==1){
            return;
        }else if(random==2){
            gameImageView.setImageResource(R.drawable.camel);
            keys = new String[]{"ج", "م", "ل"};
            textAnswer = "جمل";
        }else if(random==3){
            gameImageView.setImageResource(R.drawable.bread);
            keys = new String[]{"خ", "ب", "ز"};
            textAnswer = "خبز";
        }else if(random==4){
            gameImageView.setImageResource(R.drawable.carrot);
            keys = new String[]{"ج", "ز", "ر"};
            textAnswer = "جزر";
        }else if(random==5){
            gameImageView.setImageResource(R.drawable.duck);
            keys = new String[]{"ة", "ط", "ب"};
            textAnswer = "بطة";
        }else if(random==6){
            gameImageView.setImageResource(R.drawable.sun);
            keys = new String[]{"س", "م", "ش"};
            textAnswer = "شمس";
        }else if(random==7){
            gameImageView.setImageResource(R.drawable.sun);
            keys = new String[]{"ع", "ن", "ب"};
            textAnswer = "عنب";
        }else if(random==8){
            gameImageView.setImageResource(R.drawable.sun);
            keys = new String[]{"ع", "س", "ل"};
            textAnswer = "عسل";
        }
    }


    private String[] shuffleArray(String[] ar){
        Random random = new Random();
        for (int i = ar.length-1 ; i>0 ; i--){
            int index = random.nextInt(i+1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    private void addView(LinearLayout viewParent, final String text, final EditText editText){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.rightMargin = 20;

        final TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.purple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        gameImageView = findViewById(R.id.gameImageView);
        title = findViewById(R.id.title);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(presCounter < maxpresCounter){
                    if(presCounter == 0)
                        editText.setText("");

                    editText.setText(editText.getText().toString() + text);
                    //textView.setAnimation(bigsmallforth);
                    //textView.animate().alpha(0).setDuration(300);
                    presCounter++;

                    if(presCounter == maxpresCounter)
                        doValidate();
                }
            }
        });

        viewParent.addView(textView);

    }

    private void doValidate(){
        presCounter = 0;
        EditText editText = findViewById(R.id.word);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);

        if(editText.getText().toString().equals(textAnswer)){
            Toast.makeText(HaifaGameActivity.this,"Correct", Toast.LENGTH_LONG).show();
            //Intent i = new Intent(getApplicationContext(),MainActivity2.class);
            //startActivity(i);
            editText.setText("");
        }else {
            Toast.makeText(HaifaGameActivity.this,"Wrong", Toast.LENGTH_LONG).show();
            editText.setText("");
        }
        keys = shuffleArray(keys);
        linearLayout.removeAllViews();
        for(String key: keys){
            addView(linearLayout,key,editText);
        }
    }
    private void initialization() {
        score = 0;
        gameImageView = findViewById(R.id.gameImageView);
    }
}