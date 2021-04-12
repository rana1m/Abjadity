package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Date;

public class MapActivity extends AppCompatActivity {

    private static final String TAG = "MapActicity";
    FirebaseDatabase database;
    Animation scaleUp,scaleDown;
    DatabaseReference accountRef,alphabetsRef,digitsRef;
    String childId,parentId;
    Child desiredChild;
    String id;
    int level;
    Button SaveButton;
    View dialogView;
    Intent i;
    Intent i2;
    Button buttonGray1,buttonGray2,buttonGray3,buttonGray4,buttonGray5,buttonGray6,buttonGray7,buttonGray8,buttonGray9,buttonGray10,buttonGray11,buttonGray12,buttonGray13,buttonGray14,
            buttonGray15,buttonGray16,buttonGray17,buttonGray18,buttonGray19,buttonGray20,buttonGray21,buttonGray22,buttonGray23,buttonGray24,buttonGray25,buttonGray26,buttonGray27,buttonGray28,
            buttonGray29,buttonGray30,buttonGray31,buttonGray32,buttonGray33,buttonGray34,buttonGray35,buttonGray36,buttonGray37,buttonGray38;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.activity_map);

        //   Toast.makeText(this, "chant", Toast.LENGTH_SHORT).show();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = true;
        options.inScaled = false;
        options.inDither = false;
        options.inPurgeable = true;
        Bitmap preparedBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.map, options);
        Drawable background = new BitmapDrawable(preparedBitmap);
        ( findViewById(R.id.map))
                .setBackgroundDrawable(background);




        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }

        });

        initialization();
        retrieveChildInfo();
        addLettersToDatabase();
        addNumbersToDatabase();
//        bottomNavigationView.setSelectedItemId(R.id.mapActivity);
        bottomNavigationView.setSelectedItemId(R.id.mapActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.back:
                        break;
                    case R.id.profileActivity:
                        Intent h = new Intent(MapActivity.this,ChildProfileActivity2.class);
                        h.putExtra("childId",childId);
                        h.putExtra("parentId",parentId);
                        startActivity(h);
                        break;
                    case R.id.gameActivity:
                        Intent i = new Intent(MapActivity.this,gameMenuActivity.class);
                        i.putExtra("childId",childId);
                        i.putExtra("parentId",parentId);
                        startActivity(i);
                        break;

                }
                return true;
            }
        });

    }

    private void addLettersToDatabase() {
        alphabetsRef.child(1+"").setValue(new Letter(1+"", "أ",  "الف", "إبرة", "أرنب","أناناس","أسد","أ","ـا","ـا"));
        alphabetsRef.child(2+"").setValue(new Letter(2+"", "ب",  "باء", "بطة", "برتقال","بطيخ","بحر","بـ","ـبـ","ــب"));
        alphabetsRef.child(3+"").setValue(new Letter(3+"", "ت",  "تاء", "تمساح", "تاج","تفاح","توت","تـ","ـتـ","ـت"));
//
        alphabetsRef.child(4+"").setValue(new Letter(4+"", "ث",  "ثاء", "ثعلب", "ثلج","ثلاجة","ثمار","ثـ","ـثـ","ـث"));
        alphabetsRef.child(5+"").setValue(new Letter(5+"", "ج",  "جيم", "جرس", "جوارب","جمل","جزر","جـ","ـجـ","ــج"));
        alphabetsRef.child(6+"").setValue(new Letter(6+"", "ح",  "حاء", "حائط", "حذاء","حجر","حطب","حـ","ـحـ","ــح"));
        alphabetsRef.child(7+"").setValue(new Letter(7+"", "خ",  "خاء", "خيمة", "خضار","خاتم","خيل",  "خـ","ـخـ","ــخ"));
        alphabetsRef.child(8+"").setValue(new Letter(8+"", "د",  "دال", "دب", "دراجة","دلفين","دجاجة","د","ـد","ـد"));

        alphabetsRef.child(9+"").setValue(new Letter(9+"", "ذ",  "ذال", "ذهب", "ذرة","ذئب","ذبابة","ذ","ـذ","ـذ"));
        alphabetsRef.child(10+"").setValue(new Letter(10+"", "ر",  "راء", "ريشة", "رغيف","رمان","رأس","ر","ـر","ـر"));
        alphabetsRef.child(11+"").setValue(new Letter(11+"", "ز",  "زاء", "زورق", "زرافة","زهرة","زيت", "ز","ـز","ـز"));
        alphabetsRef.child(12+"").setValue(new Letter(12+"", "س",  "سين", "سفينة", "سيارة","سياج","سمك","سـ","ـسـ","ـس"));
        alphabetsRef.child(13+"").setValue(new Letter(13+"", "ش",  "شين", "شرطي", "شجرة","شمعة","شمس","شـ","ـشـ","ـش"));

        alphabetsRef.child(14+"").setValue(new Letter(14+"", "ص",  "صاد", "صاروخ", "صنبور","صورة","صندوق", "صـ","ـصـ","ـص"));
        alphabetsRef.child(15+"").setValue(new Letter(15+"", "ض",  "ضاد", "ضماد", "ضوء","ضفدع","ضابط","ضـ","ـضـ","ـض"));
        alphabetsRef.child(16+"").setValue(new Letter(16+"", "ط",  "طاء", "طفل", "طاووس","طاولة","طريق","طـ","ـطـ","ـط"));
        alphabetsRef.child(17+"").setValue(new Letter(17+"", "ظ",  "'ظاء'", "ظبي", "ظرف","ظفر","ظهر", "ظـ","ـظـ","ـظ"));

        alphabetsRef.child(18+"").setValue(new Letter(18+"", "ع",  "عين", "عين", "علبة","عسل","عنب","عـ","ـعـ","ـع"));









        alphabetsRef.child(19+"").setValue(new Letter(19+"", "غ",  "غين", "غواص", "غيوم","غرس","غابة","غـ","ـغـ","ـغ"));
        alphabetsRef.child(20+"").setValue(new Letter(20+"", "ف",  "فاء", "فضاء", "فواكة","فأر","فأس",  "فـ","ـفـ","ـف"));
        alphabetsRef.child(21+"").setValue(new Letter(21+"", "ق",  "قاف", "قلم", "قبعة","قميص","قمر","قـ","ـقـ","ـق"));
        alphabetsRef.child(22+"").setValue(new Letter(22+"", "ك",  "كاف", "كاميرا", "كرسي","كعكة","كمثرى","كـ","ـكـ","ـك"));

        alphabetsRef.child(23+"").setValue(new Letter(23+"", "ل",  "لام", "لوز", "لهب","لعبة","ليمون","لـ","ـلـ","ـل"));
        alphabetsRef.child(24+"").setValue(new Letter(24+"", "م",  "ميم", "ماء", "مظلة","ملعقة","مقص","مـ","ـمـ","ـم"));
        alphabetsRef.child(25+"").setValue(new Letter(25+"", "ن",  "نون", "نافذة", "نهر","نمر","نورس", "نـ","ـنـ","ـن"));
        alphabetsRef.child(26+"").setValue(new Letter(26+"", "ه",  "هاء", "هرة", "هدية","هلال","هرم","هـ","ـهـ","ـه"));
        alphabetsRef.child(27+"").setValue(new Letter(27+"", "و",  "واو", "وعاء", "ورقة","وزة","وردة","و","ـو","ـو"));
        alphabetsRef.child(28+"").setValue(new Letter(28+"", "ي",  "ياء", "ياسمين", "يقطين","يد","يمامة","يـ","ـيـ","ـي"));
    }
    private void addNumbersToDatabase() {

        digitsRef.child(1+"").setValue(new Digit(1+"","١","واحد"));
        digitsRef.child(2+"").setValue(new Digit(2+"","٢","اثنان"));
        digitsRef.child(3+"").setValue(new Digit(3+"","٣","ثلاثة"));
        digitsRef.child(4+"").setValue(new Digit(4+"","٤","أربعة"));
        digitsRef.child(5+"").setValue(new Digit(5+"","٥","خمسة"));

        digitsRef.child(6+"").setValue(new Digit(6+"","٦","ستة"));
        digitsRef.child(7+"").setValue(new Digit(7+"","٧","سبعة"));
        digitsRef.child(8+"").setValue(new Digit(8+"","٨","ثمانية"));
        digitsRef.child(9+"").setValue(new Digit(9+"","٩","تسعة"));
        digitsRef.child(10+"").setValue(new Digit(10+"","١٠","عشرة"));


    }
    private void popUpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.play_previous_stage_first, viewGroup, false);
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
    private void buttonsActivation() {
        int intLevel=level;

        //set passed levels
        for (int i=1; i<intLevel+1;i++){
            findViewById(getResources().getIdentifier("buttonGray" + i, "id",this.getPackageName())).
                    setBackgroundResource( getResources().getIdentifier("button_green" + i, "mipmap",this.getPackageName()));
        }

        //set current level
        intLevel++;
        if(intLevel!=39){
            findViewById(getResources().getIdentifier("buttonGray" + intLevel, "id",this.getPackageName())).
                    setBackgroundResource( getResources().getIdentifier("button_blue" + intLevel, "mipmap",this.getPackageName()));
        }

    }
    private void retrieveChildInfo() {
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot userchildren : userSnapshot.child("children").getChildren()) {
                        Child child = userchildren.getValue(Child.class);

                        if (child.getId().equals(childId)) {
                            desiredChild = child;

                            i.putExtra("parentId", desiredChild.getParentId());
                            i.putExtra("childId", desiredChild.getId());
                            i.putExtra("childLevel", desiredChild.getLevel());

                            level = desiredChild.getLevel();
                            buttonsActivation();

                            PushDownAnim.setPushDownAnimTo(buttonGray1).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","1");
                                    if(child.getLevel()+1>=1){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }

                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray2).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","2");
                                    if(child.getLevel()+1>=2){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray3).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","3");
                                    if(child.getLevel()+1>=3){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray4).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","4");
                                    if(child.getLevel()+1>=4){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray5).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","5");
                                    if(child.getLevel()+1>=5){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray6).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","6");
                                    if(child.getLevel()+1>=6){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray7).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","7");
                                    if(child.getLevel()+1>=7){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray8).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","8");
                                    if(child.getLevel()+1>=8){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray9).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","9");
                                    if(child.getLevel()+1>=9){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray10).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","10");
                                    if(child.getLevel()+1>=10){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray11).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","11");
                                    if(child.getLevel()+1>=11){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray12).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","12");
                                    if(child.getLevel()+1>=12){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray13).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","13");
                                    if(child.getLevel()+1>=13){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray14).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","14");
                                    if(child.getLevel()+1>=14){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray15).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","15");
                                    if(child.getLevel()+1>=15){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray16).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","16");
                                    if(child.getLevel()+1>=16){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray17).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","17");
                                    if(child.getLevel()+1>=17){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray18).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","18");
                                    if(child.getLevel()+1>=18){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray19).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","19");
                                    if(child.getLevel()+1>=19){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray20).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","20");
                                    if(child.getLevel()+1>=20){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray21).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","21");
                                    if(child.getLevel()+1>=21){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray22).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","22");
                                    if(child.getLevel()+1>=22){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray23).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","23");
                                    if(child.getLevel()+1>=23){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray24).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","24");
                                    if(child.getLevel()+1>=24){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray25).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","25");
                                    if(child.getLevel()+1>=25){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray26).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","26");
                                    if(child.getLevel()+1>=26){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray27).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","27");
                                    if(child.getLevel()+1>=27){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray28).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i.putExtra("button","28");
                                    if(child.getLevel()+1>=28){
                                        startActivity(i);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });

                            ///////////////////////////////////////////////////////////////////////////////////////////
                            PushDownAnim.setPushDownAnimTo(buttonGray29).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","1");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                 if(child.getLevel()+1>=29){
                                        startActivity(i2);
                                 }else{
                                     popUpDialog();
                                 }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray30).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","2");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                    if(child.getLevel()+1>=30){
                                        startActivity(i2);
                                   }else {
                                       popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray31).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","3");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                   if(child.getLevel()+1>=31){
                                        startActivity(i2);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray32).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","4");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                    if(child.getLevel()+1>=32){
                                        startActivity(i2);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray33).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","5");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                 //   if(child.getLevel()+1>=33){
                                        startActivity(i2);
                                   // }else {
                                     //   popUpDialog();
                                    //}
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray34).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","6");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                   if(child.getLevel()+1>=34){
                                        startActivity(i2);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray35).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","7");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                    if(child.getLevel()+1>=35){
                                        startActivity(i2);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray36).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","8");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                    if(child.getLevel()+1>=36){
                                        startActivity(i2);
                                    }else {
                                        popUpDialog();
                                }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray37).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","9");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                   if(child.getLevel()+1>=37){
                                        startActivity(i2);
                                   }else {
                                       popUpDialog();
                                    }
                                }
                            });
                            PushDownAnim.setPushDownAnimTo(buttonGray38).setScale(.7f).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i2.putExtra("button","10");
                                    i2.putExtra("childId",childId);
                                    i2.putExtra("parentId",parentId);

                                    if(child.getLevel()+1>=38){
                                        startActivity(i2);
                                    }else {
                                        popUpDialog();
                                    }
                                }
                            });


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MapActivity.this,ChildProfileActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        startActivity(i);
    }
    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
    }
    private void initialization() {
        buttonGray1=findViewById(R.id.buttonGray1);
        buttonGray2=findViewById(R.id.buttonGray2);
        buttonGray3=findViewById(R.id.buttonGray3);
        buttonGray4=findViewById(R.id.buttonGray4);
        buttonGray5=findViewById(R.id.buttonGray5);
        buttonGray6=findViewById(R.id.buttonGray6);
        buttonGray7=findViewById(R.id.buttonGray7);
        buttonGray8=findViewById(R.id.buttonGray8);
        buttonGray9=findViewById(R.id.buttonGray9);
        buttonGray10=findViewById(R.id.buttonGray10);
        buttonGray11=findViewById(R.id.buttonGray11);
        buttonGray12=findViewById(R.id.buttonGray12);
        buttonGray13=findViewById(R.id.buttonGray13);
        buttonGray14=findViewById(R.id.buttonGray14);
        buttonGray15=findViewById(R.id.buttonGray15);
        buttonGray16=findViewById(R.id.buttonGray16);
        buttonGray17=findViewById(R.id.buttonGray17);
        buttonGray18=findViewById(R.id.buttonGray18);
        buttonGray19=findViewById(R.id.buttonGray19);
        buttonGray20=findViewById(R.id.buttonGray20);
        buttonGray21=findViewById(R.id.buttonGray21);
        buttonGray22=findViewById(R.id.buttonGray22);
        buttonGray23=findViewById(R.id.buttonGray23);
        buttonGray24=findViewById(R.id.buttonGray24);
        buttonGray25=findViewById(R.id.buttonGray25);
        buttonGray26=findViewById(R.id.buttonGray26);
        buttonGray27=findViewById(R.id.buttonGray27);
        buttonGray28=findViewById(R.id.buttonGray28);
        buttonGray29=findViewById(R.id.buttonGray29);
        buttonGray30=findViewById(R.id.buttonGray30);
        buttonGray31=findViewById(R.id.buttonGray31);
        buttonGray32=findViewById(R.id.buttonGray32);
        buttonGray33=findViewById(R.id.buttonGray33);
        buttonGray34=findViewById(R.id.buttonGray34);
        buttonGray35=findViewById(R.id.buttonGray35);
        buttonGray36=findViewById(R.id.buttonGray36);
        buttonGray37=findViewById(R.id.buttonGray37);
        buttonGray38=findViewById(R.id.buttonGray38);



        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        alphabetsRef = database.getReference("alphabets");
        digitsRef = database.getReference("digits");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        desiredChild=new Child();
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        i = new Intent(MapActivity.this, StepOneActivity.class);
        i.putExtra("childId",childId);
        i.putExtra("parentId",parentId);
        i2 = new Intent(MapActivity.this, NumStepOneActivity.class);
        scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this,R.anim.scale_down);

    }
}