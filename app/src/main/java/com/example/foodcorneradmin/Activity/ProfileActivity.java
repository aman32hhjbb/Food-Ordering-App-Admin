package com.example.foodcorneradmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.foodcorneradmin.Models.UserModel;
import com.example.foodcorneradmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    EditText Name, Restaurant, Phone, Email;
    AppCompatButton SaveInfoButton;
    boolean isEditing = false;
    UserModel userModel;
    String UserId;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserId=getIntent().getStringExtra("UserId");
        db = FirebaseDatabase.getInstance().getReference().child("Data").child("Employee").child(UserId).child("PersonalData");
        Name = findViewById(R.id.profileFragmentName);
        Restaurant = findViewById(R.id.profileFragmentAddress);
        Phone = findViewById(R.id.profileFragmentPhone);
        Email = findViewById(R.id.profileFragmentEmail);
        SaveInfoButton = findViewById(R.id.profileFragmentSaveButton);
        showInfo();
        SaveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditing) {
                    userModel = new UserModel();
                    userModel.setName(Name.getText().toString());
                    userModel.setRestaurant(Restaurant.getText().toString());
                    userModel.setMobileNo(Phone.getText().toString());
                    userModel.setEmail(Email.getText().toString());
                    db.setValue(userModel);

                    toggleEditing();
                } else {
                    toggleEditing();
                }
            }
        });
    }

    private void showInfo() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel = snapshot.getValue(UserModel.class);
                if (userModel != null) {
                    finalShow();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
            }
        });
    }

    private void finalShow() {
        Name.setText(userModel.getName());
        Email.setText(userModel.getEmail());
        if (userModel.getRestaurant() != null) {
            Restaurant.setText(userModel.getRestaurant());
        }
        if (userModel.getMobileNo() != null) {
            Phone.setText(userModel.getMobileNo());
        }
    }

    private void toggleEditing() {
        if (isEditing) {
            // Disable editing
            disable(Name);
            disable(Restaurant);
            disable(Phone);
            disable(Email);
            SaveInfoButton.setText("Edit Information");
        } else {
            // Enable editing
            editable(Name);
            editable(Restaurant);
            editable(Phone);
            editable(Email);
            SaveInfoButton.setText("Save Information");
        }
        isEditing = !isEditing;
    }

    private void editable(EditText editText) {
        editText.setEnabled(true);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    private void disable(EditText editText) {
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }
}
