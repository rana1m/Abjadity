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
    Button buttonGray1,buttonGray2,buttonGray3,buttonGray4,buttonGray5,buttonGray6,buttonGray7,buttonGray8,buttonGray9,buttonGray10,buttonGray11,buttonGray12,buttonGray13,buttonGray14,
    buttonGray15,buttonGray16,buttonGray17,buttonGray18,buttonGray19,buttonGray20,buttonGray21,buttonGray22,buttonGray23,buttonGray24,buttonGray25,buttonGray26,buttonGray27,buttonGray28;
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

    }

    private void addLettersToDatabase() {
        id=new Date().getTime()+"";
       // alphabetsRef.push().setValue(new Letter( 1+"", "ألف"));
      //  alphabetsRef.push().setValue(new Letter( 2+"", "باء"));


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


        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        alphabetsRef = database.getReference("alphabets");
        digitsRef = database.getReference("digits");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        desiredChild=new Child();
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        i = new Intent(MapActivity.this, StepOneActivity.class);

    }
}