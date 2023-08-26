package com.example.foodcorneradmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.foodcorneradmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delayed navigation to the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the signUp activity
                startActivity(new Intent(getApplicationContext(), signUp.class));
                finish(); // Close the current activity
            }
        }, 2000); // Delay for 2000 milliseconds (2 seconds)
    }


}
