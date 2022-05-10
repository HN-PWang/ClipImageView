package com.mr.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ClipImageView clipImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clipImageView = findViewById(R.id.clip_image_view);

        clipImageView.setImage(R.drawable.app_test_image);
    }
}