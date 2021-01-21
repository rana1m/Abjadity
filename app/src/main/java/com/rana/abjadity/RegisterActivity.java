package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef;
    EditText name,password,passwordConf,email;
    Button register;
    String _name,_password,_passwordConf,_email;
    int id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialization();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInformation();
                addParentToDatabase();
            }
        });

    }

    private void addParentToDatabase() {

        if(CheckForPassword()) {
            id++;
            accountRef.push().setValue(new Parent(id + "", "parent", "0", _name, _email, _password));
        }
    }

    private boolean CheckForPassword() {
        if (_password.equals(_passwordConf))
            return true;
        else return false;
    }

    private void fetchInformation() {
        _name = name.getText().toString();
        _password = password.getText().toString();
        _passwordConf = passwordConf.getText().toString();
        _email = email.getText().toString();
    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        name=findViewById(R.id.EnterName);
        password=findViewById(R.id.EnterPassword);
        passwordConf=findViewById(R.id.EnterPasswordConf);
        email=findViewById(R.id.EnterEmail);
        register=findViewById(R.id.RegisterButton);
    }
}