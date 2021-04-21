package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";
    EditText email,password;
    Button logInButton;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    TextView errorMsg,forgetPasswordLink,Error;
    private FirebaseAuth mAuth;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initialization();
        logIn();
        forgetPassword();

    }

    private void logIn() {
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()){
                    if(email.getText().toString().equals("")||password.getText().toString().equals("")){
                        errorMsg.setText("* جميع الحقول مطلوبة");
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                    else { mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent i = new Intent(LogInActivity.this,ParentHomePageActivity.class);
                                        i.putExtra("parentId",user.getUid());
                                        startActivity(i);

                                    } else if(!task.isSuccessful()) {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        errorMsg.setText("* البريد الإكتروني و/أو كلمة المرور خاطئة");
                                        errorMsg.setVisibility(View.VISIBLE);
                                        counter--;
                                    }
                                    if (counter == 0)
                                    // Disable button after 3 failed attempts
                                    {   logInButton.setEnabled(false);

                                        errorMsg.setText("تم إيقاف تسجيل الدخول لمدة ٥ دقائق");
                                        errorMsg.setVisibility(View.VISIBLE);

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable()
                                        {   @Override
                                        public void run()
                                        {   logInButton.setEnabled(true);
                                            counter = 3;
                                        }
                                        }, 300000);
                                    }
                                }
                            });}
                }else {
                    Error.setText("* لا يوجد إتصال بالإنترنت");
                    Error.setVisibility(View.VISIBLE);

                }
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
        Error=findViewById(R.id.Error);
        mAuth = FirebaseAuth.getInstance();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
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