package com.cursoandroid.pucfrotasdeescolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(Home.this, MainActivity.class));
            finish();
        }, 1000);
    }
}
