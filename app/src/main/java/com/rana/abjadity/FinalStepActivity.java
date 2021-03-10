package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinalStepActivity extends AppCompatActivity {

    Button completeButton;
    String childId,parentId,childLevel,button;
    FirebaseDatabase database;
    DatabaseReference accountRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_step);

        initialization();
        updateChildLevel();

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FinalStepActivity.this,MapActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                i.putExtra("childLevel",childLevel);
                i.putExtra("button",button);
                startActivity(i);
            }
        });
    }

    private void updateChildLevel(){
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                        Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){
                            if(Integer.parseInt(button)>child.getLevel()){
                            theChild.getRef().child("level").setValue(Integer.parseInt(button));
                        }}
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

      private void initialization(){
          database = FirebaseDatabase.getInstance();
          accountRef = database.getReference("accounts");
       completeButton=findViewById(R.id.completeButton);
       childId = getIntent().getStringExtra("childId");
       parentId = getIntent().getStringExtra("parentId");
       childLevel = getIntent().getStringExtra("childLevel");
       button = getIntent().getStringExtra("button");
     }
}