package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;

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

import java.util.Date;

public class MapActivity extends AppCompatActivity {

    private static final String TAG = "MapActicity";
    FirebaseDatabase database;
    DatabaseReference accountRef,alphabetsRef,digitsRef;
    String childId,parentId;
    Child desiredChild;
    String level,id;
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


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.back:
                        break;
                    case R.id.profileActivity:
                        break;
                }

                return true;
            }
        });

        buttonGray1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","1");
                startActivity(i);
            }
        });
        buttonGray2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","2");
                startActivity(i);
            }
        });
        buttonGray3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","3");
                startActivity(i);
            }
        });
        buttonGray4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","4");
                startActivity(i);
            }
        });
        buttonGray5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","5");
                startActivity(i);
            }
        });
        buttonGray6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","6");
                startActivity(i);
            }
        });
        buttonGray7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","7");
                startActivity(i);
            }
        });
        buttonGray8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","8");
                startActivity(i);
            }
        });
        buttonGray9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","9");
                startActivity(i);
            }
        });
        buttonGray10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","10");
                startActivity(i);
            }
        });
        buttonGray11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","11");
                startActivity(i);
            }
        });
        buttonGray12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","12");
                startActivity(i);
            }
        });
        buttonGray13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","13");
                startActivity(i);
            }
        });
        buttonGray14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","14");
                startActivity(i);
            }
        });
        buttonGray15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","15");
                startActivity(i);
            }
        });
        buttonGray16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","16");
                startActivity(i);
            }
        });
        buttonGray17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","17");
                startActivity(i);
            }
        });
        buttonGray18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","18");
                startActivity(i);
            }
        });
        buttonGray19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","19");
                startActivity(i);
            }
        });
        buttonGray20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","20");
                startActivity(i);
            }
        });
        buttonGray21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","21");
                startActivity(i);
            }
        });
        buttonGray22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","22");
                startActivity(i);
            }
        });
        buttonGray23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","23");
                startActivity(i);
            }
        });
        buttonGray24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","24");
                startActivity(i);
            }
        });
        buttonGray25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","25");
                startActivity(i);
            }
        });
        buttonGray26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","26");
                startActivity(i);
            }
        });
        buttonGray27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","27");
                startActivity(i);
            }
        });
        buttonGray28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("button","28");
                startActivity(i);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////
        buttonGray29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","1");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","2");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","3");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","4");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","5");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","6");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","7");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","8");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","9");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });
        buttonGray38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.putExtra("button","10");
                i2.putExtra("childId",childId);
                i2.putExtra("parentId",parentId);

                startActivity(i2);
            }
        });

    }

    private void addLettersToDatabase() {
        alphabetsRef.child(1+"").setValue(new Letter(1+"", "أ",  "الف", "إبرة", "أرنب","أناناس","أسد", "https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/a1.png?alt=media&token=74440768-6f29-48a6-8628-ea279091c602","https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/a2.png?alt=media&token=728ecb2a-b05d-4c42-9019-ec205c68ac30","https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/a3.png?alt=media&token=b37cf0a8-babe-4338-9565-277bdba78946","https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/a4.png?alt=media&token=aaccad27-9ad9-4741-af40-a037f5f91756","https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/lettersChant%2F1.mp4?alt=media&token=0b22c6d3-d25a-4bdd-8563-e52b1317a14f","https://h.top4top.io/m_1871mcaee1.m4a","https://h.top4top.io/m_1871mcaee1.m4a","https://h.top4top.io/m_1871mcaee1.m4a","https://h.top4top.io/m_1871mcaee1.m4a"));
        alphabetsRef.child(2+"").setValue(new Letter(2+"", "ب",  "باء", "بطة", "برتقال","بطيخ","بحر", "https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/b2.png?alt=media&token=76080664-b34d-49d5-962d-3b0a93540026","https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/b1.png?alt=media&token=d87113dd-83fc-4df1-bdf0-7c41780e1f73","https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/b3.png?alt=media&token=2c54c0bf-61fe-4bf2-a311-0364c2c482a7","https://firebasestorage.googleapis.com/v0/b/abjadity-507d7.appspot.com/o/b4.png?alt=media&token=c52a1bab-5a93-4502-82cd-f60507e4feb6", "https://g.top4top.io/m_1876got4b1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","https://h.top4top.io/m_1871mcaee1.m4a","https://h.top4top.io/m_1871mcaee1.m4a","https://h.top4top.io/m_1871mcaee1.m4a"));
        alphabetsRef.child(3+"").setValue(new Letter(3+"", "ت",  "تاء", "تمساح", "تاج","تفاح","توت", "https://k.top4top.io/p_1880bplz71.png","https://g.top4top.io/p_1880b9fld1.png","https://i.top4top.io/p_1880j5id11.png","https://b.top4top.io/p_1880o2ihn1.png", "https://c.top4top.io/m_1876kjntw1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
//
        alphabetsRef.child(4+"").setValue(new Letter(4+"", "ث",  "ثاء", "ثعلب", "ثلج","ثلاجة","ثمار",  "https://d.top4top.io/p_1880rx3pm2.png","https://h.top4top.io/p_1880pirgk1.png","https://e.top4top.io/p_18807gc2f3.png","https://c.top4top.io/p_1880wbyox1.png","https://e.top4top.io/m_1876pq5nm2.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(5+"").setValue(new Letter(5+"", "ج",  "جيم", "جرس", "جوارب","جمل","جزر",  "https://j.top4top.io/p_1880qycn81.png","https://a.top4top.io/p_1880yojrp4.png","https://l.top4top.io/p_1880vha023.png","https://k.top4top.io/p_18808o9qd2.png","https://g.top4top.io/m_1876zjdvz3.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(6+"").setValue(new Letter(6+"", "ح",  "حاء", "حائط", "حذاء","حجر","حطب",  "https://h.top4top.io/p_18809a64a1.png","https://d.top4top.io/p_1880ekq061.png","https://f.top4top.io/p_1880jsvp21.png","https://a.top4top.io/p_1880svcnr1.png","https://h.top4top.io/m_1877btm2l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(7+"").setValue(new Letter(7+"", "خ",  "خاء", "خيمة", "خضار","خاتم","خيل",  "https://g.top4top.io/p_1880f4t0g3.png","https://h.top4top.io/p_188071r4t4.png","https://f.top4top.io/p_1880zr13j2.png","https://e.top4top.io/p_1880v2hu01.png","https://i.top4top.io/m_187702dsy2.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(8+"").setValue(new Letter(8+"", "د",  "دال", "دب", "دراجة","دلفين","دجاجة",  "https://e.top4top.io/p_1880ah3421.png","https://f.top4top.io/p_18806p8c32.png","https://a.top4top.io/p_18807svpk9.png","https://b.top4top.io/p_1880xi17810.png","https://j.top4top.io/m_18777xqvm3.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));

        alphabetsRef.child(9+"").setValue(new Letter(9+"", "ذ",  "ذال", "ذهب", "ذرة","ذئب","ذبابة",  "https://l.top4top.io/p_1880jk30f8.png","https://k.top4top.io/p_1880cctul7.png","https://j.top4top.io/p_1880rrp7d6.png","https://i.top4top.io/p_1880jqxbr5.png","https://k.top4top.io/m_1877p7esq4.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(10+"").setValue(new Letter(10+"", "ر",  "راء", "ريشة", "رغيف","رمان","رأس",  "https://j.top4top.io/p_18805556b6.png","https://i.top4top.io/p_1880v6zy55.png","https://h.top4top.io/p_1880k7dp24.png","https://g.top4top.io/p_1880dlvun3.png","https://a.top4top.io/m_18774y8pl1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(11+"").setValue(new Letter(11+"", "ز",  "زاء", "زورق", "زرافة","زهرة","زيت",  "https://c.top4top.io/p_1880b6esx10.png","https://b.top4top.io/p_1880qplwm9.png","https://a.top4top.io/p_18801d7dz8.png","https://k.top4top.io/p_1880zaz7m7.png","https://b.top4top.io/m_1877k12zp2.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(12+"").setValue(new Letter(12+"", "س",  "سين", "سفينة", "سيارة","سياج","سمك", "","","","", "https://c.top4top.io/m_1877bj04j3.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(13+"").setValue(new Letter(13+"", "ش",  "شين", "شرطي", "شجرة","شمعة","شمس",  "","","","","https://e.top4top.io/m_1877tlwyo4.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));

        alphabetsRef.child(14+"").setValue(new Letter(14+"", "ص",  "صاد", "صاروخ", "صنبور","صورة","صندوق",  "","","","","https://c.top4top.io/m_1877jlq7y1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(15+"").setValue(new Letter(15+"", "ض",  "ضاد", "ضماد", "ضوء","ضفدع","ضابط",  "","","","","https://d.top4top.io/m_1877ixrvs2.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(16+"").setValue(new Letter(16+"", "ط",  "طاء", "طفل", "طاووس","طاولة","طريق", "","","","", "https://e.top4top.io/m_1877cg6yb3.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(17+"").setValue(new Letter(17+"", "ظ",  "'ظاء'", "ظبي", "ظرف","ظفر","ظهر",  "","","","","https://f.top4top.io/m_1877zz07o4.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));

        alphabetsRef.child(18+"").setValue(new Letter(18+"", "ع",  "عين", "عين", "علبة","عسل","عنب", "","","","", "https://g.top4top.io/m_18776vu2p5.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));









        alphabetsRef.child(19+"").setValue(new Letter(19+"", "غ",  "غين", "غواص", "غيوم","غرس","غابة",  "","","","","https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(20+"").setValue(new Letter(20+"", "ف",  "فاء", "فضاء", "فواكة","فأر","فأس",  "","","","","https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(21+"").setValue(new Letter(21+"", "ق",  "قاف", "قلم", "قبعة","قميص","قمر",  "","","","","https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(22+"").setValue(new Letter(22+"", "ك",  "كاف", "كاميرا", "كرسي","كعكة","كمثرى", "","","","", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));

        alphabetsRef.child(23+"").setValue(new Letter(23+"", "ل",  "لام", "لوز", "لهب","لعبة","ليمون", "","","","", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(24+"").setValue(new Letter(24+"", "م",  "ميم", "ماء", "مظلة","ملعقة","مقص", "","","","", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(25+"").setValue(new Letter(25+"", "ن",  "نون", "نافذة", "نهر","نمر","نورس",  "","","","","https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(26+"").setValue(new Letter(26+"", "ه",  "هاء", "هرة", "هدية","هلال","هرم",  "","","","","https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(27+"").setValue(new Letter(27+"", "و",  "واو", "وعاء", "ورقة","وزة","وردة",  "","","","","https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
        alphabetsRef.child(28+"").setValue(new Letter(28+"", "ي",  "ياء", "بطة", "بيت","بئر","بستان", "","","","", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a","","",""));
    }

    private void buttonsActivation() {
        int intLevel=Integer.parseInt(level);

        //set passed levels
        for (int i=1; i<intLevel+1;i++){
            findViewById(getResources().getIdentifier("buttonGray" + i, "id",this.getPackageName())).
                    setBackgroundResource( getResources().getIdentifier("button_green" + i, "mipmap",this.getPackageName()));
        }

        //set current level
        intLevel++;
        if(intLevel!=29){
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

                            level = new String(desiredChild.getLevel());
                            buttonsActivation();
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
        i2 = new Intent(MapActivity.this, StepOneNumActivity.class);

    }
}