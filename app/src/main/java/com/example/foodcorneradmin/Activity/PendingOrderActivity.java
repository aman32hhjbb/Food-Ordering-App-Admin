package com.example.foodcorneradmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

public class PendingOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order);
        List<OrderModel> list=new ArrayList<>();
        RecyclerView recyclerView=findViewById(R.id.PendingActivityRecycleView);
        PendingOrderAdapter pendingOrderAdapter=new PendingOrderAdapter(getApplicationContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(pendingOrderAdapter);
        FirebaseDatabase.getInstance().getReference().child("Data").child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    OrderModel orderModel=dataSnapshot.getValue(OrderModel.class);
                    if(Objects.equals(orderModel.getDeliveryStatus(), "Pending")){
                        list.add(dataSnapshot.getValue(OrderModel.class));

                    }
                }
                Collections.reverse(list);
                pendingOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}