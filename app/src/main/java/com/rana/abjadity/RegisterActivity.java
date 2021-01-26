package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    FirebaseDatabase database;
    DatabaseReference accountRef;
    EditText name,password,passwordConf,email;
    Button register;
    String _name,_password,_passwordConf,_email,id;
    TextView ErrorName,ErrorPass,ErrorEmail,ErrorPassConf;


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

        if(CheckForfileds()) {
            id=new Date().getTime()+"";
            accountRef.push().setValue(new Parent( id + "", "parent", "0", _name, _email, _password));
            Intent i = new Intent(RegisterActivity.this,ParentHomePageActivity.class);
            i.putExtra("parentId",id);
            startActivity(i);
        }

    }

    private boolean CheckForfileds() {
        if (_name.equals("")){
        ErrorName.setText("* يرجى إدخال الاسم");
        ErrorName.setVisibility(View.VISIBLE);
            ErrorEmail.setVisibility(View.GONE);
            ErrorPass.setVisibility(View.GONE);
            ErrorPassConf.setVisibility(View.GONE);
        return false;}

        if(_name.length() == 1){
            ErrorName.setText("* يرجى إدخال الاسم بشكل صحيح");
            ErrorName.setVisibility(View.VISIBLE);
            ErrorEmail.setVisibility(View.GONE);
            ErrorPass.setVisibility(View.GONE);
            ErrorPassConf.setVisibility(View.GONE);
            return false;}

        if (_email.equals("")){
            ErrorEmail.setText("* يرجى إدخال البريد الإلكتروني");
            ErrorEmail.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            ErrorPass.setVisibility(View.GONE);
            ErrorPassConf.setVisibility(View.GONE);
            return false;}

        if (_password.equals("")){
            ErrorPass.setText("* يرجى إدخال كلمة المرور");
            ErrorPass.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            ErrorEmail.setVisibility(View.GONE);
            ErrorPassConf.setVisibility(View.GONE);
            return false;}

        if (_passwordConf.equals("")){
            ErrorPassConf.setText("* يرجى تأكيد كلمة المرور");
            ErrorPassConf.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            ErrorEmail.setVisibility(View.GONE);
            ErrorPass.setVisibility(View.GONE);
            return false;}
        //

        if(_password.toString().length()<8 &&!isValidPassword(_password.toString())){
            ErrorPass.setText("* يرجى إدخال على الأقل حرف ورقم ورمز ولا يقل عن ٨ خانة");
            ErrorPass.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            ErrorEmail.setVisibility(View.GONE);
            ErrorPassConf.setVisibility(View.GONE);
            return false; }


        if(!isValidEmailId(_email.toString().trim())){
            ErrorEmail.setText("* يرجى كتابة البريد الالكتروني بشكل صحيح");
            ErrorEmail.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            ErrorPass.setVisibility(View.GONE);
            ErrorPassConf.setVisibility(View.GONE);
            return false;
        }

        //
        if (!_password.equals(_passwordConf)){
            ErrorPassConf.setText("* كلمة المرور غير متطابقة");
        ErrorPassConf.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            ErrorEmail.setVisibility(View.GONE);
            ErrorPass.setVisibility(View.GONE);
            return false;}

        ErrorName.setVisibility(View.GONE);
        ErrorEmail.setVisibility(View.GONE);
        ErrorPass.setVisibility(View.GONE);
        ErrorPassConf.setVisibility(View.GONE);

        return true;
    }
    //
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    //
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
        ErrorName=findViewById(R.id.ErrorName);
        ErrorEmail=findViewById(R.id.ErrorEmail);
        ErrorPass=findViewById(R.id.ErrorPass);
        ErrorPassConf=findViewById(R.id.ErrorPassConf);

    }
}