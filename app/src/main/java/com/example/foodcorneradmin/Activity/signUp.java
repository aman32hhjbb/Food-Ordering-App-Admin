package com.example.foodcorneradmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.foodcorneradmin.Models.UserModel;
import com.example.foodcorneradmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference firebaseDatabase;
    private EditText Email,Password,Name,Restaurant;
    private TextView SignupBack;
    private AppCompatButton CreateAccount;
    private UserModel userModel;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("UserId",currentUser.getUid());
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        signup();
        login();

    }

    private void login() {
        SignupBack =findViewById(R.id.signupBack);
        SignupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
            }
        });
    }

    private void signup() {
        Name=findViewById(R.id.signupName);
        Email=findViewById(R.id.signupEmail);
        Password=findViewById(R.id.signupPassword);
        Restaurant=findViewById(R.id.signupRestaurant);
        CreateAccount=findViewById(R.id.signupCreateAccountButton);

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=Name.getText().toString();
                String userEmail=Email.getText().toString();
                String userPassword=Password.getText().toString();
                String userRestaurant=Restaurant.getText().toString();
                userModel=new UserModel(userName,userEmail,userRestaurant);
                if(userEmail.isEmpty()||userName.isEmpty()||userPassword.isEmpty()){
                    Toast.makeText(signUp.this, "Fill Details", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(signUp.this, "Your Account is Created", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        Toast.makeText(signUp.this, "Try After Some Time", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                private void updateUI(FirebaseUser user) {
                                    firebaseDatabase=FirebaseDatabase.getInstance().getReference();
                                    firebaseDatabase.child("Data").child("Employee").child(user.getUid()).child("PersonalData").setValue(userModel);
                                    Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("UserId",user.getUid());
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }
        });
    }

}