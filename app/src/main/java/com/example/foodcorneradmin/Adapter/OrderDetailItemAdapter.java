package com.example.foodcorneradmin.Adapter;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodcorneradmin.Models.AddToCartModel;
import com.example.foodcorneradmin.Models.MenuModel;
import com.example.foodcorneradmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OrderDetailItemAdapter extends RecyclerView.Adapter<OrderDetailItemAdapter.ViewHolder> {
    private Context context;
    private List<AddToCartModel> orderMenuList;

    public OrderDetailItemAdapter(Context context, List<AddToCartModel> orderMenuList) {
        this.context = context;
        this.orderMenuList = orderMenuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_ordermenu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddToCartModel orderMenuItem = orderMenuList.get(position);
        Log.d(TAG, "onBindViewHolder: "+orderMenuItem.getMenuId());
        FirebaseDatabase.getInstance().getReference().child("Data").child("MenuItems").child(orderMenuItem.getMenuId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MenuModel model=snapshot.getValue(MenuModel.class);
                if(model!=null){
                    holder.dishNameTextView.setText(model.getItemName());
                    Glide.with(context).load(model.getItemImage()).into(holder.dishImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.dishQuantityTextView.setText(orderMenuItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderMenuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishNameTextView, dishQuantityTextView;
        ImageView dishImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage=itemView.findViewById(R.id.OrderDetailRecycleDishImage);
            dishNameTextView = itemView.findViewById(R.id.OrderDetailRecycleDishName); // Replace with your actual IDs
            dishQuantityTextView = itemView.findViewById(R.id.OrderDetailRecycleDishQuantity); // Replace with your actual IDs
        }
    }
}
