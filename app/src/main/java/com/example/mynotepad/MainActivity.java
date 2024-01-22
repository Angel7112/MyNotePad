package com.example.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button loginbutton;
    private Button signupbutton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        //Check if the user is already signed in
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser !=null){
            //the user is already signed in, redirect to the main activity
            Intent intent = new Intent(this, MainActivity4.class);
            startActivity(intent);
            finish();
        }

        loginbutton = findViewById(R.id.loginbut);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        signupbutton = findViewById(R.id.signupbut);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });

    }
}