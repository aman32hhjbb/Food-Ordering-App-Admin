package com.example.foodcorneradmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodcorneradmin.Adapter.CompleteOrderAdapter;
import com.example.foodcorneradmin.Adapter.PendingOrderAdapter;
import com.example.foodcorneradmin.Models.OrderModel;
import com.example.foodcorneradmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CompletedOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_order);

        List<OrderModel> list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.CompletedActivityRecycleView);
        CompleteOrderAdapter pendingOrderAdapter = new CompleteOrderAdapter(getApplicationContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(pendingOrderAdapter);

        FirebaseDatabase.getInstance().getReference().child("Data").child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderModel orderModel = dataSnapshot.getValue(OrderModel.class);
                    if (Objects.equals(orderModel.getDeliveryStatus(), "Completed")) {
                        list.add(orderModel);
                    }
                }
                Collections.reverse(list);
                pendingOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });
    }
}
