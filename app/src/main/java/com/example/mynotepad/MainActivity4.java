package com.example.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Calendar;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // Get the current Firebase user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Get the current date
        Calendar calendar = Calendar.getInstance();
        CharSequence todayDate = DateFormat.format("EEEE, MMMM d", calendar);

        // Set "Today, Month Day" text with the current date
        TextView todayDateTextView = findViewById(R.id.day);
        todayDateTextView.setText(todayDate);

        // Get the TextView for "LOG OUT"
        TextView logoutTextView = findViewById(R.id.logout);

        // Call the method to underline the TextView
        underlineText(logoutTextView);

        // Set OnClickListener for "LOG OUT"
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logout action here (sign out from Firebase)
                FirebaseAuth.getInstance().signOut();

                // Navigate back to MainActivity or perform any other action
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to MainActivity with the back button
            }
        });
    }
        // Method to underline Logout TextView
        private void underlineText (TextView textView){
            SpannableString content = new SpannableString(textView.getText());
            content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(content);
        }
    }
