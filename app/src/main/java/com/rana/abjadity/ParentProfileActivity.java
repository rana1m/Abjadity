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
import android.widget.TextView;

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
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("accounts");
    private Button editProfile,SaveButton,CancelButton;
    FloatingActionButton backIcon;
    EditText ParentNewName, ParentNewEmail, ParentNewPassword;
    String parentNewName,parentNewEmail,parentNewPassword, parentId, pass;
    View dialogView;
    private TextView Email, Name, ErrorName, ErrorEmail, ErrorPass;
    String uId;



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
        backIcon = findViewById(R.id.backIcon);
        Name = (TextView) findViewById(R.id.Pname);
        Email = (TextView) findViewById(R.id.Pemail);
        editProfile = findViewById(R.id.editInfo);
    }

    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        CancelButton = dialogView.findViewById(R.id.buttonCancle);
        ParentNewName = dialogView.findViewById(R.id.EnterParentName);
        ParentNewEmail = dialogView.findViewById(R.id.EnterParentEmail);
        ParentNewPassword = dialogView.findViewById(R.id.EnterParentPassword);
        ErrorName = dialogView.findViewById(R.id.ErrorParentName);
        ErrorEmail = dialogView.findViewById(R.id.ErrorParentEmail);
        ErrorPass = dialogView.findViewById(R.id.ErrorParentPassword);
    }

    private void fetchInformation() {
        parentNewName=ParentNewName.getText().toString();
        parentNewEmail=ParentNewEmail.getText().toString();
        parentNewPassword = ParentNewPassword.getText().toString();
    }





   /* private void UserData() {
        DatabaseReference updateData = FirebaseDatabase.getInstance()
                .getReference("accounts").child(parentId);
        Map<String, Object> updates = new HashMap<String,Object>();

        updates.put("name", parentNewName);
        updates.put("Email", parentNewEmail);
        updates.put("password", parentNewPassword);
        updateData.updateChildren(updates);

    }*/
   /* private void setUserData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        myRef.orderByChild("id").equalTo(parentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    datas.getRef().child("name").setValue(parentNewName);
                    //datas.getRef().child("pasword").setValue(parentNewName);
                   // Name.setText(name);
                   // Email.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private void getUserData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        myRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    String name = datas.child("name").getValue().toString();
                    String email = datas.child("email").getValue().toString();
                     pass = datas.child("password").getValue().toString();
                    Name.setText(name);
                    Email.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


// ref.updateChildren()

  private void edit() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");

        myRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    if(checkName(parentNewName)){
                        userSnapshot.getRef().child("name").setValue(parentNewName);
                    }

                    if(isValidPassword(parentNewPassword)){
                        userSnapshot.getRef().child("password").setValue(parentNewPassword);
                    }

                    getUserData();


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
            ErrorPass.setVisibility(View.GONE);
            ErrorEmail.setVisibility(View.GONE);
            return false;}

        if(name.length() == 1){
            ErrorName.setText("* يرجى إدخال الاسم بشكل صحيح");
            ErrorName.setVisibility(View.VISIBLE);
            ErrorPass.setVisibility(View.GONE);
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
            ErrorPass.setVisibility(View.GONE);
            return false;}
        if(!isValidEmailId(_email.toString().trim())){
            ErrorEmail.setText("* يرجى كتابة البريد الالكتروني بشكل صحيح");
            ErrorEmail.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            ErrorPass.setVisibility(View.GONE);
            return false;
        }
        myRef.orderByChild("email").equalTo(_email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    editEmail();
                }
                else{
                    ErrorEmail.setText("* البريد الإلكتروني موجود مسبقاً");
                    ErrorEmail.setVisibility(View.VISIBLE);
                    ErrorName.setVisibility(View.GONE);
                    ErrorPass.setVisibility(View.GONE);
                    return;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
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
                        userSnapshot.getRef().child("email").setValue(parentNewEmail);
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


