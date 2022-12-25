package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            GameView gameView = (GameView) findViewById(R.id.view);

            Button restartButton = (Button) findViewById(R.id.restart);
            Button findButton = (Button) findViewById(R.id.find);

            restartButton.setOnClickListener(view -> gameView.restart());
            findButton.setOnClickListener(view -> gameView.findPath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}