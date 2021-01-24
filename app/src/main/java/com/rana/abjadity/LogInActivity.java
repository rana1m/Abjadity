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
    EditText name,password;
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
                CheckUserAndPassword(name.getText().toString(),password.getText().toString());
            }
        });

    }

    private void CheckUserAndPassword(String _nmae, String _password) {
     accountRef.orderByChild("name").equalTo(_nmae).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Parent> parents = new ArrayList<>();

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    if (userSnapshot.child("password").getValue().toString().equals(_password)){
                        Intent i = new Intent(LogInActivity.this,ParentHomePageActivity.class);
                        i.putExtra("parentId",userSnapshot.child("id").getValue().toString());
                        startActivity(i);
                        return;

                    } else {
                        errorMsg.setText("* الاسم و/أو كلمة المرور خاطئة");
                        errorMsg.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

//        if(dataSnapshot)
        errorMsg.setText("* الاسم و/أو كلمة المرور خاطئة");
        errorMsg.setVisibility(View.VISIBLE);

    }

    private void initialization() {
        name=findViewById(R.id.EnterName);
        password=findViewById(R.id.EnterPassword);
        logInButton=findViewById(R.id.logInButton);
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        errorMsg=findViewById(R.id.ErrorNameOrPassword);
    }
}