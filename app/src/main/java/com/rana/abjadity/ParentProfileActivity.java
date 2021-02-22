package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentProfileActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("accounts");
    private Button editProfile,SaveButton,CancelButton;
    FloatingActionButton backIcon;
    EditText ParentNewName, ParentNewEmail, ParentNewPassword;
    String parentNewName,parentNewEmail;
    View dialogView;
    private TextView Email, Name;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        initialization();
        getUserData();
        goToChildrenAccounts();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ParentProfileActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.ghada, viewGroup, false);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                initializationForDialog();


                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // fetchInformation();
                        // UpdateChildInfo();
                        alertDialog.dismiss();
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

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parentBack = new Intent(getApplicationContext(), ParentHomePageActivity.class);
                startActivity(parentBack);
            }
        });


    }

    private void initialization(){
        backIcon = findViewById(R.id.backIcon);
        Name = (TextView) findViewById(R.id.Pname);
        Email = (TextView) findViewById(R.id.Pemail);
        editProfile = findViewById(R.id.editInfo);
    }

    private void fetchInformation() {
        parentNewName=ParentNewName.getText().toString();
        parentNewEmail=ParentNewEmail.getText().toString();
    }

    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        CancelButton = dialogView.findViewById(R.id.buttonCancle);
        ParentNewName = dialogView.findViewById(R.id.EnterParentName);
        ParentNewEmail = dialogView.findViewById(R.id.EnterParentEmail);
        ParentNewPassword = dialogView.findViewById(R.id.EnterParentPassword);
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
    }


    private void goToChildrenAccounts(){

        final Button goToChildrenAccounts=findViewById(R.id.myChildrenButton);
        goToChildrenAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ParentHomePageActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

}