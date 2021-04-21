package com.rana.abjadity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class alarmPop extends AppCompatActivity implements View.OnClickListener {

    private int notificationId = 1;
    Bundle bundle;
    String childName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_popup);
        bundle = getIntent().getExtras();
        childName = getIntent().getStringExtra("childName");

        DisplayMetrics DM=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DM);

        int width=DM.widthPixels;
        int Height=DM.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(Height*.6));

        findViewById(R.id.setBtn).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // EditText editText = findViewById(R.id.editText);
        TimePicker timePicker = findViewById(R.id.timePicker);

        // Intent
        Intent intent = new Intent(alarmPop.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("childName", childName);
        // intent.putExtra("message", editText.getText().toString());


        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                alarmPop.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // AlarmManager
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        switch (view.getId()) {
            case R.id.setBtn:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Create time.
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                // Set Alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                Toast.makeText(this, "تم ضبط المنبة بنجاح", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.cancelBtn:
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "تم الغاءالمنبة", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}
