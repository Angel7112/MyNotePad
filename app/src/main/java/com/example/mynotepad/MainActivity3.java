package com.example.mynotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity3 extends AppCompatActivity {

    private EditText editTextusername, editTextpassword, editTextconfirmpassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //Progress Bar
        progressBar = findViewById(R.
                id.progressBar);

        //Initialize UI elements
        editTextusername = findViewById(R.id.boxusername);
        editTextpassword = findViewById(R.id.boxpassword);
        editTextconfirmpassword = findViewById(R.id.boxconfpassword);

        Button registerbutton = findViewById(R.id.signupbut);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain the entered data
                String textusername = editTextusername.getText().toString();
                String textpassword = editTextpassword.getText().toString();
                String textconfirmpassword = editTextconfirmpassword.getText().toString();

                if (TextUtils.isEmpty(textusername)){
                    Toast.makeText(MainActivity3.this, "Please Enter a Username", Toast.LENGTH_SHORT).show();
                    editTextusername.setError("Username is required");
                    editTextusername.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(textusername).matches()) {
                    Toast.makeText(MainActivity3.this, "Please re-enter your Email", Toast.LENGTH_SHORT).show();
                    editTextusername.setError("Valid email is required");
                    editTextusername.requestFocus();
                }   else if(TextUtils.isEmpty(textpassword)) {
                    Toast.makeText(MainActivity3.this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                    editTextpassword.setError("Password is required");
                    editTextpassword.requestFocus();
                }else if (textpassword.length()<8){
                    Toast.makeText(MainActivity3.this, "Password should be at least 8 characters", Toast.LENGTH_SHORT).show();
                    editTextpassword.setError("Password is too weak");
                    editTextpassword.requestFocus();
                }else if (TextUtils.isEmpty(textconfirmpassword)){
                    Toast.makeText(MainActivity3.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    editTextconfirmpassword.setError("Password confirmation is required");
                    editTextconfirmpassword.requestFocus();
                }else if (!textpassword.equals(textconfirmpassword)){
                    Toast.makeText(MainActivity3.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    editTextconfirmpassword.setError("Password confirmation is required");
                    editTextconfirmpassword.requestFocus();
                    //Clear the enterd passwords
                    editTextpassword.clearComposingText();
                    editTextconfirmpassword.clearComposingText();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textusername, textpassword);
                }


            }
        });
    }

    //Register User using the credentials given
    private void registerUser(String textusername, String textpassword) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textusername, textpassword).addOnCompleteListener(MainActivity3.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    Toast.makeText(MainActivity3.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    //Send Verification Email
                    firebaseUser.sendEmailVerification();

                    //Open User Profile after successful registration
                    //FLAGS will prevent user to go back to register activity after successful registration using the backbutton
                    Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); //to close Register  Activity

                }else {
                    //Check if the failure is due to email already in use
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(MainActivity3.this, "Registration failed: Email is already in use", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity3.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void buttonCLick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

