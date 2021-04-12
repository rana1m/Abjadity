package com.rana.abjadity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class catchingGame extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUESR_CODE =0;
    private GoogleMap mMap;
    String childId,parentId;
    FirebaseDatabase database;
    DatabaseReference accountRef;
    String character ="0";
    int score;
    Window window ;
    View dialogView;
    Button SaveButton;
    TextView scores;
    MediaPlayer mediaPlayer,mediaPlayerInstructions;
    ImageView letter1,letter2,letter3,letter4,letter5;
    int count =1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catching_game);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initialization();
        GetLocation();
        try {
            playInstructions();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
//                catchingGame.this, R.raw.map));
        LatLng myLocation = new LatLng(LocationListener.location.getLatitude(), LocationListener.location.getLongitude());

        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                        Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){
                            character= child.getCharacter();
                            if(character.equals("1")){
                                mMap.addMarker(new MarkerOptions()
                                        .position(myLocation)
                                        .title("me")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.c1))
                                );
                            }else if(character.equals("2")){
                                mMap.addMarker(new MarkerOptions()
                                        .position(myLocation)
                                        .title("me")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.c2))
                                );
                            }else if(character.equals("3")){
                                mMap.addMarker(new MarkerOptions()
                                        .position(myLocation)
                                        .title("me")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.c3))
                                );
                            }else if(character.equals("4")){
                                mMap.addMarker(new MarkerOptions()
                                        .position(myLocation)
                                        .title("me")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.c4))
                                );
                            }else if(character.equals("5")){
                                mMap.addMarker(new MarkerOptions()
                                        .position(myLocation)
                                        .title("me")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.c5))
                                );
                            }
//                            level.setText(child.getLevel().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,30));
    }

    void GetLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUESR_CODE);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3, locationListener);

        MyThread myThread = new MyThread();
        myThread.start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            GetLocation();
        }else {
            Toast.makeText(this, "الموقع غير مفعل", Toast.LENGTH_SHORT).show();
        }
    }

    Location oldLocation;
    class MyThread extends Thread{
      public MyThread() {
            oldLocation=new Location("zero");
            oldLocation.getLatitude();
            oldLocation.getLongitude();
      }
      int counter=0;
      @Override
      public void run() {
        while (true){
           if(oldLocation.distanceTo(LocationListener.location)==0){ continue;}
                oldLocation=LocationListener.location;
            try {
            runOnUiThread(new Runnable() {
                 @Override
                 public void run() {

                   accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       //loop through accounts to find the parent with that id
                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                       Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){
                             character= child.getCharacter();

                               mMap.clear();
                               // Add a marker in my location and move the camera
                            LatLng myLocation = new LatLng(LocationListener.location.getLatitude(), LocationListener.location.getLongitude());
                            if(character.equals("1")){
                                mMap.addMarker(new MarkerOptions()
                                                .position(myLocation)
                                                .title("me")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.c1))
                                );
                            }else if(character.equals("2")){
                                mMap.addMarker(new MarkerOptions()
                                                .position(myLocation)
                                                .title("me")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.c2))
                                );
                            }else if(character.equals("3")){
                                mMap.addMarker(new MarkerOptions().position(myLocation)
                                                .title("me")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.c3))
                                );
                            }else if(character.equals("4")){
                                mMap.addMarker(new MarkerOptions()
                                                .position(myLocation)
                                                .title("me")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.c4))
                                );
                            }else if(character.equals("5")){
                                mMap.addMarker(new MarkerOptions()
                                                .position(myLocation)
                                                .title("me")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.c5))
                                );
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,30));
                                                if(counter==0){
                                                    if(child.getLevel()==1){
                                                        loadLettersLevelOne();
                                                    }
                                                    if(child.getLevel()==2){
                                                        loadLettersLevelTow();
                                                    } if(child.getLevel()==3){
                                                        loadLettersLevelThree();
                                                    } if(child.getLevel()==4){
                                                        loadLettersLevelFour();
                                                    } else {
                                                        loadLetters();
                                                    }
                                                    counter++;
                                                }
                                                //load letters
                                                int size=0;
                                                if (child.getLevel()>27){
                                                    size=letterObjectArrayList.size();
                                                }else if(child.getLevel()==1||child.getLevel()==2||child.getLevel()==3||child.getLevel()==4){
                                                    size=6;
                                                }else{
                                                    size=child.getLevel();
                                                }

                            for(int i=0;i<size;i++){
                                LetterObject letterObject=letterObjectArrayList.get(i);
                                if(letterObject.isCatch==false) {
                                    LatLng letter = new LatLng(letterObject.location.getLatitude(), letterObject.location.getLongitude());
                                    mMap.addMarker(new MarkerOptions()
                                            .position(letter)
                                            .title("letter")
                                            .icon(BitmapDescriptorFactory.fromResource(letterObject.Image))
                                    );
//
                                    if(letterObject.location.distanceTo(LocationListener.location)<2){
                                        letterObject.isCatch=true;
                                        letterObjectArrayList.set(i,letterObject);
                                        if(count == 1){
                                            letter1.setImageResource(letterObject.Image);
                                            count++;
                                        }else  if(count == 2){
                                            letter2.setImageResource(letterObject.Image);
                                            count++;
                                        }else  if(count == 3){
                                            letter3.setImageResource(letterObject.Image);
                                            count++;
                                        }
                                        else  if(count == 4){
                                            letter4.setImageResource(letterObject.Image);
                                            count++;
                                        }else  if(count == 5){
                                            letter5.setImageResource(letterObject.Image);
                                            winningFunction();
                                        }

                                        try {
                                            playVoice();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                            }
                            counter++;

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
            });
            Thread.sleep(100);
            }catch (Exception e){
                }
            }
        }
    }

    ArrayList<LetterObject> letterObjectArrayList= new ArrayList<>();

    void loadLettersLevelOne(){
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),20)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
    }

    void loadLettersLevelTow(){
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l2,"",4,getRandomLocation(LocationListener.getLocation(),20)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l2,"",4,getRandomLocation(LocationListener.getLocation(),20)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l2,"",4,getRandomLocation(LocationListener.getLocation(),20)));
    }

    void loadLettersLevelThree(){
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l2,"",4,getRandomLocation(LocationListener.getLocation(),20)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l3,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),20)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l2,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l3,"",4,getRandomLocation(LocationListener.getLocation(),30)));
    }

    void loadLettersLevelFour(){
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l2,"",4,getRandomLocation(LocationListener.getLocation(),20)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l3,"",4,getRandomLocation(LocationListener.getLocation(),20)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l4,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l3,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l4,"",4,getRandomLocation(LocationListener.getLocation(),30)));
    }

    void loadLetters(){
        letterObjectArrayList.add(new LetterObject(R.drawable.l1,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l2,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l3,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l4,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l5,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l6,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l7,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l8,"",4,getRandomLocation(LocationListener.getLocation(),30)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l9,"",4,getRandomLocation(LocationListener.getLocation(),60)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l10,"",4,getRandomLocation(LocationListener.getLocation(),60)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l11,"",4,getRandomLocation(LocationListener.getLocation(),60)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l12,"",4,getRandomLocation(LocationListener.getLocation(),60)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l13,"",4,getRandomLocation(LocationListener.getLocation(),60)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l14,"",4,getRandomLocation(LocationListener.getLocation(),70)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l15,"",4,getRandomLocation(LocationListener.getLocation(),70)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l16,"",4,getRandomLocation(LocationListener.getLocation(),70)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l17,"",4,getRandomLocation(LocationListener.getLocation(),70)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l18,"",4,getRandomLocation(LocationListener.getLocation(),70)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l19,"",4,getRandomLocation(LocationListener.getLocation(),60)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l20,"",4,getRandomLocation(LocationListener.getLocation(),60)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l21,"",4,getRandomLocation(LocationListener.getLocation(),40)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l22,"",4,getRandomLocation(LocationListener.getLocation(),40)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l23,"",4,getRandomLocation(LocationListener.getLocation(),40)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l24,"",4,getRandomLocation(LocationListener.getLocation(),40)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l25,"",4,getRandomLocation(LocationListener.getLocation(),40)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l26,"",4,getRandomLocation(LocationListener.getLocation(),40)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l27,"",4,getRandomLocation(LocationListener.getLocation(),40)));
        letterObjectArrayList.add(new LetterObject(R.drawable.l28,"",4,getRandomLocation(LocationListener.getLocation(),40)));
    }
    public LatLng getRandomLocation(LatLng point, int radius) {

        List<LatLng> randomPoints = new ArrayList<>();
        List<Float> randomDistances = new ArrayList<>();
        Location myLocation = new Location("");
        myLocation.setLatitude(point.latitude);
        myLocation.setLongitude(point.longitude);

        //This is to generate 10 random points
        for(int i = 0; i<10; i++) {
            double x0 = point.latitude;
            double y0 = point.longitude;

            Random random = new Random();

            // Convert radius from meters to degrees
            double radiusInDegrees = radius / 111000f;

            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Adjust the x-coordinate for the shrinking of the east-west distances
            double new_x = x / Math.cos(y0);

            double foundLatitude = new_x + x0;
            double foundLongitude = y + y0;
            LatLng randomLatLng = new LatLng(foundLatitude, foundLongitude);
            randomPoints.add(randomLatLng);
            Location l1 = new Location("");
            l1.setLatitude(randomLatLng.latitude);
            l1.setLongitude(randomLatLng.longitude);
            randomDistances.add(l1.distanceTo(myLocation));
        }
        //Get nearest point to the centre
        int indexOfNearestPointToCentre =
                randomDistances.indexOf(Collections.min(randomDistances));
        return randomPoints.get(indexOfNearestPointToCentre);
    }

    private void initialization() {
        database = FirebaseDatabase.getInstance();
        accountRef = database.getReference("accounts");
        childId = getIntent().getStringExtra("childId");
        parentId = getIntent().getStringExtra("parentId");
        score=0;
        window = this.getWindow();
        letter1=findViewById(R.id.letter1);
        letter2=findViewById(R.id.letter2);
        letter3=findViewById(R.id.letter3);
        letter4=findViewById(R.id.letter4);
        letter5=findViewById(R.id.letter5);

    }

    private void initializationForDialog() {
        SaveButton = dialogView.findViewById(R.id.buttonOk);
        scores= dialogView.findViewById(R.id.score);
    }
    public void winningFunction() {

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        AlertDialog.Builder builder = new AlertDialog.Builder(catchingGame.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        dialogView = LayoutInflater.from(this).inflate(R.layout.next_step_dialog, viewGroup, false);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        initializationForDialog();
        scores.setText("ربحت ١٥ نقطة");

        //update scores
        updateScores();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(catchingGame.this,gameMenuActivity.class);
                i.putExtra("childId",childId);
                i.putExtra("parentId",parentId);
                startActivity(i);
                finish();
                alertDialog.dismiss();
            }
            public boolean onTouchEvent(MotionEvent event)
            {

                if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    alertDialog.dismiss();
                }
                return false;
            }
        });
    }

    private void updateScores() {
        score=15;
        accountRef.orderByChild("id").equalTo(parentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loop through accounts to find the parent with that id
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {

                    //loop through parent children to add them to adapter ArrayList
                    for (DataSnapshot theChild: userSnapshot.child("children").getChildren()) {
                        Child child = theChild.getValue(Child.class);
                        if(child.getId().equals(childId)){
                            score += child.getScore();
                            theChild.getRef().child("score").setValue(score);
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

    private void playVoice() throws IOException {
        mediaPlayer=new MediaPlayer();
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.good_job;
        Uri uri =Uri.parse(path);
        mediaPlayer.setDataSource(this,uri);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayerInstructions.stop();
                mp.start();
            }
        });
    }
    private void playInstructions() throws IOException {
        mediaPlayerInstructions=new MediaPlayer();
        String path = "android.resource://"+getPackageName()+"/"+ R.raw.good_job;
        Uri uri =Uri.parse(path);
        mediaPlayerInstructions.setDataSource(this,uri);
        mediaPlayerInstructions.prepareAsync();
        mediaPlayerInstructions.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }
}
