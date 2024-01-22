package com.example.mynotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText EditTextboxusername;
    private EditText EditTextboxpassword;
    private View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Progress Bar
        progressBar = findViewById(R.id.progressBar);

        // Initialize UI elements
        EditTextboxusername = findViewById(R.id.boxusername);
        EditTextboxpassword = findViewById(R.id.boxpassword);
        progressBar = findViewById(R.id.progressBar);
    }

    // Called when the "Login" button is clicked
    public void loginUser(View view) {
        String email = EditTextboxusername.getText().toString().trim();
        String password = EditTextboxpassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Enter Both Email and Password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sign in the user with email and password
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // Log in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(MainActivity2.this, "Login successful", Toast.LENGTH_SHORT).show();

                            // Open the main activity
                            Intent userProfileIntent = new Intent(MainActivity2.this, MainActivity4.class);
                            startActivity(userProfileIntent);
                            finish();
                        } else {
                            // If Log in fails, display a message to the user.
                            Log.w("Authentication", "signInWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(MainActivity2.this, "Authentication failed: Incorrect Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void buttonCLick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
