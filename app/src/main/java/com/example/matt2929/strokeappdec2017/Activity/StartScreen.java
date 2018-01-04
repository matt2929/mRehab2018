package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.matt2929.strokeappdec2017.R;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Button login = (Button) findViewById(R.id.startLogin);
        Button demo = (Button) findViewById(R.id.startDemo);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            Intent intent = new Intent(getApplicationContext(), HistoryMain.class);
	            startActivity(intent);
            }
        });
    }
}
