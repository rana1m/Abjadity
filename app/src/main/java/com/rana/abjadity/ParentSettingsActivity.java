package com.rana.abjadity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.util.LongSummaryStatistics;
import java.util.Map;

import android.os.Bundle;

public class ParentSettingsActivity extends AppCompatActivity {
    //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("accounts");
    private Button logout,accountInfo, SaveButton, CancelButton;
    FloatingActionButton backIcon;
    private TextView Email, Name;
    View dialogView;
    String uId, parentId;
    Trace ParentSettings= FirebasePerformance.getInstance().newTrace("ParentSettings");;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParentSettings.start();
        setContentView(R.layout.activity_parent_settings);
        ParentSettings.stop();

       initialization();

        /*Name = (TextView) findViewById(R.id.Pname);
        Email = (TextView) findViewById(R.id.Pemail);
        getUserData();*/


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ParentSettingsActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.logout_dialog, viewGroup, false);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                initializationForDialog();


                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        finish();
                    }

                });

                CancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


            }
        });


        accountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parentPro = new Intent(ParentSettingsActivity.this, ParentProfileActivity.class);
                parentPro.putExtra("parentId",parentId);
                startActivity(parentPro);
            }
        });


        goToChildrenAccounts();




      /*  firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();*/

    }

   /* private void getUserData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    String name = datas.child("name").getValue().toString();
                    String email = datas.child("email").getValue().toString();
                    Name.setText(name);
                    Email.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
  /* private void getUserData2()
   {
       FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
       String userid=user.getUid();
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("accounts");
       reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot datas: dataSnapshot.getChildren()){
                   parentInfo parentInfo=dataSnapshot.getValue(com.rana.abjadity.parentInfo.class);

                   if(parentInfo.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                       String name=datas.child("name").getValue().toString();
                       String email=datas.child("email").getValue().toString();
                       Name.setText(name);
                       Email.setText(email);}
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
   }*/



  //  }
  private void initialization() {
      parentId = getIntent().getStringExtra("parentId");
      logout = findViewById(R.id.logouts);
      accountInfo = findViewById(R.id.accountInfo);
  }

  private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        CancelButton = dialogView.findViewById(R.id.buttonCancle);
    }

  private void goToChildrenAccounts(){

        final Button goToChildrenAccounts=findViewById(R.id.myChildrenButton);
        goToChildrenAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent parentPro = new Intent(ParentSettingsActivity.this, ParentHomePageActivity.class);
                parentPro.putExtra("parentId",parentId);
                startActivity(parentPro);
            }
        });
    }
}