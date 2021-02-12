package com.rana.abjadity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    private TextView Email, Name;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_settings);

        Name = (TextView) findViewById(R.id.Pname);
        Email = (TextView) findViewById(R.id.Pemail);
        getUserData();

      /*  firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();*/

    }

    private void getUserData() {
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
        });
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


    }

}