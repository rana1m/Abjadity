package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ChildProfileActivity extends AppCompatActivity {
    private static final String TAG = "ChildProfileActivity";

    String childId,parentId;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    Child desiredChild;
    int childPosition;
    EditText ChildNewName,ChildNewAge;
    String childNewName,childNewAge;
    TextView childName,childAge,childLevel,childScore;
    MaterialSpinner spinner;
    Button changeImg,editInfo,addAlarm,SaveButton,CancelButton,GoToChildAccount,deleteChildAccount;
    View dialogView;
    ImageView profileImg;
    Bundle bundle;

    Uri imageURI;

    final int REQUSET_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);


        initialization();
        retrieveChildInfo();


        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChildProfileActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.edit_info_child_dialog, viewGroup, false);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                initializationForDialog();


                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchInformation();
                        UpdateChildInfo();
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

        GoToChildAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildProfileActivity.this,MapActivity.class);
                startActivity(i);

            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////////
        changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChildProfileActivity.this,CharacterActivity.class);
                startActivity(i);
            }
        });
        if(bundle!= null){
            UpdateProfileImg();
            retrieveChildInfo();}

        deleteChildAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChild();

                Log.e(TAG,childPosition+"");
//                ParentHomePageActivity.recyclerView.getAdapter().notifyItemRemoved(childPosition+1);
                ParentHomePageActivity.childsAdapter.removeItem(childPosition);
//                ParentHomePageActivity.recyclerView.invalidate();
//                ParentHomePageActivity.recyclerView.getAdapter().notifyDataSetChanged();


                finish();

            }
        });
    }

    private void deleteChild() {

        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                        Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){

                                theChild.getRef().removeValue();
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
    private void UpdateChildInfo() {
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                        Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){

                            if(!childNewName.equals("")){
                            theChild.getRef().child("name").setValue(childNewName);
                            retrieveChildInfo();}

                            if(!childNewAge.equals("")){
                                theChild.getRef().child("age").setValue(childNewAge);
                                retrieveChildInfo();}

                        Log.e(TAG,child.getName()+"---"+childNewName);

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
    private void fetchInformation() {
        childNewName=ChildNewName.getText().toString();
        childNewAge=ChildNewAge.getText().toString();
    }
    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        CancelButton = dialogView.findViewById(R.id.buttonCancle);
        ChildNewName = dialogView.findViewById(R.id.EnterChildName);
        ChildNewAge = dialogView.findViewById(R.id.EnterChildAge);
    }
    private void retrieveChildInfo() {
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
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
                            childLevel.setText(desiredChild.getLevel());
                            childScore.setText(desiredChild.getScore());
                            GoToChildAccount.setText("الذهاب إلى حساب "+desiredChild.getName().toLowerCase());
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
//        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //loop through accounts to find the parent with that id
//                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//
//                    //loop through parent children to add them to adapter ArrayList
//                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
//                        Child child = theChild.getValue(Child.class);
//                        if(child.getId().equals(childId)){
//
//                            if(bundle!= null){
//                                int res_image = bundle.getInt("char");
//                                if(res_image != 0){
//                                   profileImg.setImageResource(res_image);
//                                   theChild.getRef().child("character").setValue(profileImg);
//                                   retrieveChildInfo();
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });
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
        spinner=(MaterialSpinner)findViewById(R.id.spinnerr);
        spinner.setSelectedIndex(0);
        spinner.setItems("1","2","3","4","5","6","7","8","9","10","11","12");
        changeImg=findViewById(R.id.changeImg);
        editInfo=findViewById(R.id.editInfo);
        addAlarm=findViewById(R.id.addAlarm);
        GoToChildAccount=findViewById(R.id.GoToChildPage);
        deleteChildAccount=findViewById(R.id.DeleteChildPage);
        profileImg = findViewById(R.id.profileImg);
        bundle = getIntent().getExtras();



    }
}