package com.osg.oneto25;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("1-50");
    }

    public void click25(View view) {
        Intent intent=new Intent(this, OneTo25Activity.class);
        startActivity(intent);
        finish();
    }

    public void click50(View view) {
        Intent intent=new Intent(this, OneTo50Activity.class);
        startActivity(intent);
        finish();
    }


}