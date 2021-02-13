package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ParentSettingsActivity extends AppCompatActivity {
    private static final String TAG = "ParentSettingsActivity";
    Button logout;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_settings);

        initialization();


        // logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialogBilder = new android.app.AlertDialog.Builder(ParentSettingsActivity.this);
                alertDialogBilder.setTitle("تسجيل الخروج");
                alertDialogBilder.setMessage("هل أنت متأكد من تسجيل الخروج؟")
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // close the dialog
                            }
                        })
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {

                                FirebaseAuth.getInstance().signOut();
                                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(logout);
                                finish();
                            }
                        });


                android.app.AlertDialog alertDialog = alertDialogBilder.create();
                alertDialog.show();

            }
        });
    }



    private void initialization() {
        logout = findViewById(R.id.logouts);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");

    }
}