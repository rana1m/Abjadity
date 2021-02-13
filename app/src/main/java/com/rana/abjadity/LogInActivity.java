package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
    TextView errorMsg,forgetPasswordLink;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        initialization();
        forgetPassword();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUserAndPassword(email.getText().toString(),password.getText().toString());
            }
        });

    }

    private void CheckUserAndPassword(String _email, String _password) {
     accountRef.orderByChild("name").equalTo(_email).addListenerForSingleValueEvent(new ValueEventListener() {
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
        forgetPasswordLink=findViewById(R.id.forgetpassword);
        fAuth=FirebaseAuth.getInstance();

    }
    private void forgetPassword(){

        forgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail=new EditText(view.getContext());
                AlertDialog.Builder forgetPasswordDialog=new AlertDialog.Builder(view.getContext());
                forgetPasswordDialog.setTitle("تغيير كلمة المرور؟");
                forgetPasswordDialog.setMessage("أدخل بريدك الألكتروني لكي يرسل اليك رابط تغيير كلمة المرور  ");
                forgetPasswordDialog.setView(resetMail);


                forgetPasswordDialog.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LogInActivity.this,"تم آرسال رابط تغيير كلمة المرور الى بريدك الألكتروني",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogInActivity.this,"حصل خطأ! لم يتم أرسال الرابط الى بريدك الألكتروني",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                forgetPasswordDialog.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                forgetPasswordDialog.create().show();
            }
        });

    }

}