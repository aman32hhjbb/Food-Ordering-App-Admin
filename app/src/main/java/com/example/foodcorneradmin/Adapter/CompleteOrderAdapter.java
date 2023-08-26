package com.example.foodcorneradmin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodcorneradmin.Activity.OrderDetailActivity;
import com.example.foodcorneradmin.Models.OrderModel;
import com.example.foodcorneradmin.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderModel> orderList;

    public CompleteOrderAdapter(Context context, List<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.completed_recycle_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.orderIdTextView.setText(order.getOrderId());
        holder.quantityTextView.setText(order.getQuantity());
        holder.totalAmountTextView.setText(order.getTotalAmount());
        holder.TimeStamp.setText(order.getTimeStamp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderDetailActivity.class);
                intent.putExtra("OrderId",order.getOrderId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, quantityTextView, totalAmountTextView,TimeStamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TimeStamp=itemView.findViewById(R.id.timestamp);
            orderIdTextView = itemView.findViewById(R.id.OrderItemId);
            quantityTextView = itemView.findViewById(R.id.OrderItemQuantity);
            totalAmountTextView = itemView.findViewById(R.id.OrderItemTotal);
        }
    }
}
