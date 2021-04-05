package com.rana.abjadity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Random;

public class HaifaGameActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference accountRef;
    String childId, parentId;
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
    Button SaveButton;
    MediaPlayer mediaPlayer;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haifa_game);

        initialization();
        setImageAndKeys(random);

        keys = shuffleArray(keys);

        for (String key : keys){
            addView(((LinearLayout)findViewById(R.id.layoutParent)),key,((EditText)findViewById(R.id.word)));
        }
        maxpresCounter = 3;

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
                        //Toast.makeText(HaifaGameActivity.this, "HI I AM HERE", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(HaifaGameActivity.this,"صحيح", Toast.LENGTH_LONG).show();
            winningFunction();
            editText.setText("");
        }else {
            Toast.makeText(HaifaGameActivity.this,"خاطئ", Toast.LENGTH_LONG).show();
            tryAgain();
            editText.setText("");
        }
        keys = shuffleArray(keys);
        linearLayout.removeAllViews();
        for(String key: keys){
            addView(linearLayout,key,editText);
        }
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
            gameImageView.setImageResource(R.drawable.carrot2);
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
            gameImageView.setImageResource(R.drawable.grape);
            keys = new String[]{"ع", "ن", "ب"};
            textAnswer = "عنب";
        }else if(random==8){
            gameImageView.setImageResource(R.drawable.huney);
            keys = new String[]{"ع", "س", "ل"};
            textAnswer = "عسل";
        }
    }
    private void tryAgain() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(HaifaGameActivity.this);
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
    public void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(HaifaGameActivity.this);
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

                Intent i = new Intent(HaifaGameActivity.this,gameMenuActivity.class);
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
    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);

    }
    private void initialization() {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        score = 0;
        gameImageView = findViewById(R.id.gameImageView);
    }
}