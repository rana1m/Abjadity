package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;


public class ChildProfileActivity2 extends AppCompatActivity {
    private static final String TAG = "ChildProfileActivity2";

    String childId,parentId;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    Child desiredChild;
    int childPosition;
    TextView childName,childAge,childLevel,childScore;
    Button changeImg;
    ImageView profileImg;
    Bundle bundle;
    StorageReference storageReference ;
    FirebaseUser curretUser;
    Intent i;
    BottomNavigationView bottomNavigationView;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile2);


        initialization();
        retrieveChildInfo();


        changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildProfileActivity2.this,CharacterActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                startActivity(i);
                if(bundle!= null){
                    UpdateProfileImg();}
            }
        });
//            retrieveChildInfo();}



        Button ChildLogOut=findViewById(R.id.ChildLogOut);
        ChildLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildProfileActivity2.this,ParentHomePageActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                startActivity(i);
            }
        });
    }



    private void retrieveChildInfo() {
        accountRef.orderByChild("id").equalTo(parentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot userchildren: userSnapshot.child("children").getChildren()) {
                        Child child = userchildren.getValue(Child.class);

                        if(child.getId().equals(childId)){
                            desiredChild=child;
                            childName.setText(desiredChild.getName());
                            childAge.setText(desiredChild.getAge());
                            childLevel.setText(desiredChild.getLevel().toString());
                            childScore.setText(desiredChild.getScore().toString());

                            storageReference.child("characters/char"+child.getCharacter()+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(getApplicationContext()).load(uri).into(profileImg);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }

    private void UpdateProfileImg(){
        if(bundle!= null){
            int res_image = bundle.getInt("char");
            if(res_image != 0){
                profileImg.setImageResource(res_image); }}
    }


    private void initialization() {
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        childPosition = getIntent().getIntExtra("childPosition",-1);
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        childName=findViewById(R.id.childName);
        childAge=findViewById(R.id.childAge);
        childLevel=findViewById(R.id.childLevel);
        childScore=findViewById(R.id.childScore);
        changeImg=findViewById(R.id.changeImg);
        profileImg = findViewById(R.id.profileImg);
        bundle = getIntent().getExtras();
        storageReference = FirebaseStorage.getInstance().getReference();
        curretUser= FirebaseAuth.getInstance().getCurrentUser();




    }


}