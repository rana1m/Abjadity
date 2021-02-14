package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        setContentView(R.layout.activity_map);

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
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);

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
                startActivity(i);
            }
        });
        buttonGray5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        buttonGray14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapActivity.this, StepOneActivity.class);
                startActivity(i);
            }
        });
        buttonGray28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        id=new Date().getTime()+"";
        alphabetsRef.child(1+"").setValue(new Letter(1+"", "أ",  "ألف", "أرنب", "إنسان","إناء","ألوان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
        alphabetsRef.child(2+"").setValue(new Letter(2+"", "ب",  "باء", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
        alphabetsRef.child(3+"").setValue(new Letter(3+"", "ت",  "تاء", "تمساح", "تاج","تفاح","توت", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//
//        alphabetsRef.child(4+"").setValue(new Letter(4+"", "ث",  "ثوب", "ثعلب", "ثعبان","إناء","ألوان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(5+"").setValue(new Letter(5+"", "ج",  "جيم", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(6+"").setValue(new Letter(6+"", "ح",  "حاء", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(7+"").setValue(new Letter(7+"", "خ",  "خاء", "أرنب", "إنسان","إناء","ألوان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(8+"").setValue(new Letter(8+"", "د",  "دال", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//
//        alphabetsRef.child(9+"").setValue(new Letter(9+"", "ذ",  "ذال", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(10+"").setValue(new Letter(10+"", "ر",  "راء", "أرنب", "إنسان","إناء","ألوان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(11+"").setValue(new Letter(11+"", "ز",  "زاء", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(12+"").setValue(new Letter(12+"", "س",  "سين", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(13+"").setValue(new Letter(13+"", "ش",  "شين", "أرنب", "إنسان","إناء","ألوان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//
//        alphabetsRef.child(14+"").setValue(new Letter(14+"", "ص",  "صاد", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(15+"").setValue(new Letter(15+"", "ض",  "ضاد", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(16+"").setValue(new Letter(16+"", "ط",  "طاء", "أرنب", "إنسان","إناء","ألوان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(17+"").setValue(new Letter(17+"", "ظ",  "'ظاء'", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//
//        alphabetsRef.child(18+"").setValue(new Letter(18+"", "ع",  "عين", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(19+"").setValue(new Letter(19+"", "غ",  "غين", "أرنب", "إنسان","إناء","ألوان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(20+"").setValue(new Letter(20+"", "ف",  "فاء", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(21+"").setValue(new Letter(21+"", "ق",  "قاف", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(22+"").setValue(new Letter(22+"", "ك",  "كاف", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//
//        alphabetsRef.child(23+"").setValue(new Letter(23+"", "ل",  "لام", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(24+"").setValue(new Letter(24+"", "م",  "ميم", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(25+"").setValue(new Letter(25+"", "ن",  "نون", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(26+"").setValue(new Letter(26+"", "ه",  "هاء", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(27+"").setValue(new Letter(27+"", "و",  "واو", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));
//        alphabetsRef.child(28+"").setValue(new Letter(28+"", "ي",  "ياء", "بطة", "بيت","بئر","بستان", "https://o.top4top.io/m_18659gd3l1.mp4","https://h.top4top.io/m_1871mcaee1.m4a"));


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
        i2 = new Intent(MapActivity.this, StepOneNumActivity.class);

    }
}