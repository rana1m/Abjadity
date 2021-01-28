package com.rana.abjadity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.ArrayList;
import java.util.Date;

public class ParentHomePageActivity extends AppCompatActivity {

    private static final String TAG = "ParentHomePageActivity";

    EditText ChildName,ChildAge;
    Button AddButton,CancelButton;
    View dialogView;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    String childName,childAge,parentId,id;
    int childPosition;
    ArrayList<Child> children;
    static RecyclerView recyclerView;
    static ChildsAdapter childsAdapter;
    TextView ErrorName,ErrorAge;
    SpaceNavigationView spaceNavigationView;


    @Override
    protected void onResume() {
        super.onResume();
        spaceNavigationView.changeCurrentItem(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_page);

        initialization();
        retrieveChildrenFromDB();
        spaceNavigationView(savedInstanceState);

    }

    private void spaceNavigationView(Bundle savedInstanceState) {

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.home_icon));
        spaceNavigationView.addSpaceItem(new SpaceItem("Settings", R.drawable.settings_icon));
        spaceNavigationView.showIconOnly();


        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ParentHomePageActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_child_dialog, viewGroup, false);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();//ll
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
                });            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if(itemName.equals("Home")){
//                    Intent i = new Intent(ParentHomePageActivity.this,ParentHomePageActivity.class);
//                    startActivity(i);

                }
                if(itemName.equals("Settings")){
                    Intent i = new Intent(ParentHomePageActivity.this,ParentSettingsActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    public void retrieveChildrenFromDB() {
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
        id=new Date().getTime()+"";
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    if(CheckForfileds()) {

                        Child newChild =new Child(parentId,id,childAge, "0",childName,
                                "0","0", "0");
                        userSnapshot.getRef().child("children").push()
                                .setValue(newChild);

                        childsAdapter.addItem(newChild);
                        childsAdapter.notifyDataSetChanged();

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
        int i = Integer.parseInt(childAge);
        if (i < 3 || i > 6){
            ErrorAge.setText("* العمر المسموح من ٣ إلى ٦");
            ErrorAge.setVisibility(View.VISIBLE);
            ErrorName.setVisibility(View.GONE);
            return false;}

        ErrorName.setVisibility(View.GONE);
        ErrorAge.setVisibility(View.GONE);

        return true;
    }

    private void fetchInformation() {
        childName=ChildName.getText().toString();
        childAge=ChildAge.getText().toString();
    }

    private void initialization() {
        parentId = getIntent().getStringExtra("parentId");
        childPosition = getIntent().getIntExtra("childPosition",-1);
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