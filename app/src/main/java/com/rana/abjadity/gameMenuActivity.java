package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class gameMenuActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Intent i;
    String childId,parentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        initialization();

        onBackPressed();


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
                        i = new Intent(gameMenuActivity.this,gameMenuActivity.class);
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
    private void initialization() {
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
    }
}