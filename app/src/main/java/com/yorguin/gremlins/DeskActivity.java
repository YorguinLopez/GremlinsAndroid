package com.yorguin.gremlins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DeskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
    }

    public void salirClick(View v) {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}