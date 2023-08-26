package com.example.foodcorneradmin.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodcorneradmin.Models.MenuModel;
import com.example.foodcorneradmin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
 String menuId;
 TextView DishName,DishPrice,DishDescription;
 ImageView DishImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        menuId=getIntent().getStringExtra("MenuId");
      DishName=findViewById(R.id.detailActivityDishName);
      DishPrice=findViewById(R.id.detailActivityDishPrice);
      DishDescription=findViewById(R.id.detailActivityDishDescription);
      DishImage=findViewById(R.id.detailActivityDishImage);
        FirebaseDatabase.getInstance().getReference().child("Data").child("MenuItems").child(menuId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MenuModel menuModel=snapshot.getValue(MenuModel.class);
                DishName.setText(menuModel.getItemName());
                DishPrice.setText(menuModel.getItemPrice());
                DishDescription.setText(menuModel.getItemDescription());
                Glide.with(getApplicationContext()).load(menuModel.getItemImage()).into(DishImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}