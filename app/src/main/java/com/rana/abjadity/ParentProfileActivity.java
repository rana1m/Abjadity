package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import static com.rana.abjadity.RegisterActivity.isValidPassword;

public class ParentProfileActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    Button editProfile,SaveButton,CancelButton;
    FloatingActionButton backIcon;
    EditText ParentNewName, ParentNewEmail;
    String parentNewName,parentNewEmail, parentId;
    View dialogView;
    TextView Email, Name, ErrorName, ErrorEmail,dataUpdated;
    FirebaseUser currentUser;
    ProgressBar dataUpdateProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            parentId = extras.getString("parentId");
        }

        initialization();
        getUserData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ParentProfileActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.edit_parent_info, viewGroup, false);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                initializationForDialog();


                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchInformation();
                        if(checkName(parentNewName)&&CheckEmail(parentNewEmail)){
                            edit();
                            getUserData();
                            dataUpdated.setVisibility(View.GONE);
                            dataUpdateProgressBar.setVisibility(View.VISIBLE);
                            alertDialog.dismiss();

                        }
                    }

                });

                CancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getUserData();
                        alertDialog.dismiss();
                    }
                });


            }
        });



    }

  private void initialization(){
        Name = (TextView) findViewById(R.id.Pname);
        Email = (TextView) findViewById(R.id.Pemail);
        editProfile = findViewById(R.id.editInfo);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        dataUpdateProgressBar= findViewById(R.id.dataUpdateProgressBar);
        dataUpdated= findViewById(R.id.dataUpdated);
    }

  private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        CancelButton = dialogView.findViewById(R.id.buttonCancle);
        ParentNewName = dialogView.findViewById(R.id.EnterParentName);
        ParentNewEmail = dialogView.findViewById(R.id.EnterParentEmail);
        ErrorName = dialogView.findViewById(R.id.ErrorParentName);
        ErrorEmail = dialogView.findViewById(R.id.ErrorParentEmail);
    }

  private void fetchInformation() {
        parentNewName=ParentNewName.getText().toString();
        parentNewEmail=ParentNewEmail.getText().toString();
    }

  private void getUserData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        myRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    String name = datas.child("name").getValue().toString();
                    String email = currentUser.getEmail();
                    Name.setText(name);
                    Email.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

  private void edit() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        myRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                        userSnapshot.getRef().child("name").setValue(parentNewName);
                        currentUser.updateEmail(parentNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dataUpdateProgressBar.setVisibility(View.GONE);
                                dataUpdated.setVisibility(View.VISIBLE);
                                getUserData();
                            }
                        });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }

  private boolean checkName(String name){


        if (name.equals("")){
            ErrorName.setText("* يرجى إدخال الاسم");
            ErrorName.setVisibility(View.VISIBLE);
            ErrorEmail.setVisibility(View.GONE);
            return false;}

        if(name.length() == 1){
            ErrorName.setText("* يرجى إدخال الاسم بشكل صحيح");
            ErrorName.setVisibility(View.VISIBLE);
            ErrorEmail.setVisibility(View.GONE);
            return false;}

        return true;
    }

  private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

  private boolean CheckEmail(String _email) {

        if (_email.equals("")){
            ErrorEmail.setText("* يرجى إدخال البريد الإلكتروني");
            ErrorEmail.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            return false;}
        if(!isValidEmailId(_email.toString().trim())){
            ErrorEmail.setText("* يرجى كتابة البريد الالكتروني بشكل صحيح");
            ErrorEmail.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            return false;
        }
        if(mAuth.getCurrentUser().getEmail().equals(_email)){
            ErrorEmail.setText("* البريد الإلكتروني موجود مسبقاً");
            ErrorEmail.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
        } else {
            editEmail();
        }

        return true;
    }

  private void editEmail() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");
        myRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        currentUser.updateEmail(parentNewEmail);
                        getUserData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }






}


