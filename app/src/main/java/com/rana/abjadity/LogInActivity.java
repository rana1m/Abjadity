package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";
    EditText email,password;
    Button logInButton;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        initialization();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUserAndPassword(email.getText().toString(),password.getText().toString());
            }
        });

    }

    private void CheckUserAndPassword(String _email, String _password) {
     accountRef.orderByChild("email").equalTo(_email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    errorMsg.setText("* البريد الاكتروني و/أو كلمة المرور خاطئة");
                    errorMsg.setVisibility(View.VISIBLE);
                }
                List<Parent> parents = new ArrayList<>();

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    if (userSnapshot.child("password").getValue().toString().equals(_password)){
                        Intent i = new Intent(LogInActivity.this,ParentHomePageActivity.class);
                        i.putExtra("parentId",userSnapshot.child("id").getValue().toString());
                        startActivity(i);
                        break;
                    } else {
                        errorMsg.setText("* البريد الاكتروني و/أو كلمة المرور خاطئة");
                        errorMsg.setVisibility(View.VISIBLE);
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
        email=findViewById(R.id.EnterEmail);
        password=findViewById(R.id.EnterPassword);
        logInButton=findViewById(R.id.logInButton);
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        errorMsg=findViewById(R.id.ErrorNameOrPassword);
    }
}