package com.rana.abjadity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.unity3d.player.UnityPlayerActivity;

public class CustomActivity extends UnityPlayerActivity {

    @Override
    public void onBackPressed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CustomActivity.this,
                        "on Back Pressed", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        super.onBackPressed();
    }
}