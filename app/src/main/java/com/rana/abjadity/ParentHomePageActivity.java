package com.rana.abjadity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentHomePageActivity extends AppCompatActivity {

    private static final String TAG = "ParentHomePageActivity";

    FloatingActionButton AddChildFloatButton;
    EditText ChildName,ChildAge;
    Button AddButton,CancelButton;
    View dialogView;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    String childName,childAge,parentId;
    ArrayList<Child> children;
    RecyclerView recyclerView;
    ChildsAdapter childsAdapter;
    TextView ErrorName,ErrorAge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_page);
        AddChildFloatButton=findViewById(R.id.AddChild);
        initialization();

        //retrieveChildrenFromDB
        retrieveChildrenFromDB();

        //Add child
        AddChildFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ParentHomePageActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_child_dialog, viewGroup, false);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                initializationForDialog();


                AddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchInformation();
                        addChildToParent();
                        if(CheckForfileds()){
                        alertDialog.dismiss();}
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
    }

    private void retrieveChildrenFromDB() {
        children.clear();
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot userchildren: userSnapshot.child("children").getChildren()) {
                        Child child = userchildren.getValue(Child.class);
                        children.add(child);
                        childsAdapter.notifyDataSetChanged();
//                        childsAdapter.notifyItemRangeChanged(0, children.size());
//                        recyclerView.invalidate();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        recyclerView.setAdapter(childsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializationForDialog() {
        AddButton = dialogView.findViewById(R.id.buttonOk);
        CancelButton = dialogView.findViewById(R.id.buttonCancle);
        ChildName = dialogView.findViewById(R.id.EnterChildName);
        ChildAge = dialogView.findViewById(R.id.EnterChildAge);
        ErrorName=dialogView.findViewById(R.id.ErrorChildName);
        ErrorAge=dialogView.findViewById(R.id.ErrorChildAge);
    }

    private void addChildToParent() {
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    if(CheckForfileds()) {
                        Log.e(TAG, String.valueOf(userSnapshot.getRef().child("children").push().setValue(new Child(childName, childAge, "0"))));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private boolean CheckForfileds() {
        if (childName.equals("")){
            ErrorName.setText("* يرجى إدخال الاسم");
            ErrorName.setVisibility(View.VISIBLE);
            ErrorAge.setVisibility(View.GONE);
            return false;}

        if (childAge.equals("")){
            ErrorAge.setText("* يرجى إدخال العمر");
            ErrorAge.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            return false;}

        if(!onlyDigits(childAge,childAge.length())){
            ErrorAge.setText("* يرجى إدخال رقم فقط");
            ErrorAge.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            return false;
        }

        ErrorName.setVisibility(View.GONE);
        ErrorAge.setVisibility(View.GONE);

        return true;
    }

    private void fetchInformation() {
        childName=ChildName.getText().toString();
        childAge=ChildAge.getText().toString();
        children.add(new Child(childName,childAge,"0"));


    }

    private void initialization() {
        parentId = getIntent().getStringExtra("parentId");
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        recyclerView=findViewById(R.id.ListView);
        children=new ArrayList<>();
        childsAdapter=new ChildsAdapter(this,children);

    }

    private boolean onlyDigits(String str, int n) {

        for (int i = 0; i < n; i++) {

            if (str.charAt(i) >= '0'
                    && str.charAt(i) <= '9') {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}