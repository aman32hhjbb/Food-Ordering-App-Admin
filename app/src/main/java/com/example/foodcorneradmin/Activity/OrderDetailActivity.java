package com.example.foodcorneradmin.Activity;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import com.example.foodcorneradmin.Adapter.OrderDetailItemAdapter;
import com.example.foodcorneradmin.Models.AddToCartModel;
import com.example.foodcorneradmin.Models.OrderModel;
import com.example.foodcorneradmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    TextView OrderIdText,OrderTotalAmountText,OrderQuantity;
    List<AddToCartModel> list;
    RecyclerView recyclerView;
    TextView TimeStamp;
    OrderDetailItemAdapter orderDetailItemAdapter;
    String OrderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_actvity);
        OrderId = getIntent().getStringExtra("OrderId");
        OrderIdText = findViewById(R.id.OrderDetailOrderID);
        OrderQuantity=findViewById(R.id.OrderDetailQuantity);
        OrderIdText.setText(OrderId);
        TimeStamp=findViewById(R.id.timestamp);
        OrderTotalAmountText = findViewById(R.id.OrderDetailPrice);
        recyclerView = findViewById(R.id.OrderDetailRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>(); // Initialize the list before using it
        orderDetailItemAdapter = new OrderDetailItemAdapter(this, list);
        recyclerView.setAdapter(orderDetailItemAdapter);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Data").child("Order").child(OrderId);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderModel orderModel = snapshot.getValue(OrderModel.class);
                OrderTotalAmountText.setText(orderModel.getTotalAmount());
                OrderQuantity.setText(orderModel.getQuantity());
                TimeStamp.setText(orderModel.getTimeStamp());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.child("OrderItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new items
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AddToCartModel addToCartModel = dataSnapshot.getValue(AddToCartModel.class);
                    list.add(addToCartModel);
                }
                Collections.reverse(list);
                orderDetailItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}