package com.example.foodcorneradmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodcorneradmin.Models.OrderModel;
import com.example.foodcorneradmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    TextView Pending,Completed,Earning;
    String UserId;
    int pending=0,completed=0,earning=0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserId=getIntent().getStringExtra("UserId");
        setContentView(R.layout.activity_home);
        Pending=findViewById(R.id.MainPendingTextCount);
        Completed=findViewById(R.id.MainCompletedTextCount);
        Earning=findViewById(R.id.MainEarningTextCount);
        getData();
        callBack();
    }

    private void getData() {
        FirebaseDatabase.getInstance().getReference().child("Data").child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pending = 0;
                completed = 0;
                earning = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderModel orderModel = dataSnapshot.getValue(OrderModel.class);
                    if (orderModel.getDeliveryStatus().equals("Completed")) {
                        completed++;
                    }
                    if (orderModel.getDeliveryStatus().equals("Pending")) {
                        pending++;
                    }
                    earning += Integer.parseInt(orderModel.getTotalAmount());
                }

                // Update the TextView values after processing the data
                Pending.setText(Integer.toString(pending));
                Completed.setText(Integer.toString(completed));
                Earning.setText(Integer.toString(earning));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error here
            }
        });
    }


    private void callBack() {
        findViewById(R.id.MainAddMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(),AddMenuActivity.class));
            }
        });
        findViewById(R.id.MainAllMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(), AllMenuActivity.class));
            }
        });
        findViewById(R.id.MainProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
           intent.putExtra("UserId",UserId);
           startActivity(intent);
            }
        });
        findViewById(R.id.MainPending).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(),PendingOrderActivity.class));
            }
        });
        findViewById(R.id.MainHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), CompletedOrderActivity.class));
            }
        });
        findViewById(R.id.MainLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
            }
        });
    }


}