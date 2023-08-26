package com.example.foodcorneradmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodcorneradmin.Activity.OrderDetailActivity;
import com.example.foodcorneradmin.Models.NotificationModel;
import com.example.foodcorneradmin.Models.OrderModel;
import com.example.foodcorneradmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.ViewHolder> {

     Context context;
     List<OrderModel> orderList;


    public PendingOrderAdapter(Context context, List<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pendin_recycle_order_item, parent, false);
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

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderId = order.getOrderId();
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Data").child("Order").child(orderId);

                orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Update the delivery status to "Reject"
                            orderRef.child("deliveryStatus").setValue("Reject")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Update UI and save notification
                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Data").child("User").child(order.getUserId()).child("Notification");
                                            String notificationID = db.push().getKey();
                                            NotificationModel notificationModel = new NotificationModel(orderId, "Your Order is Reject", order.getUserId(), notificationID,order.getTimeStamp(),false);
                                            db.child(notificationID).setValue(notificationModel);
                                            Toast.makeText(context, "Order is Reject", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Error marking order as Reject", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "Order not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error retrieving order details", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        // Handle "Accept" button click
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderId = order.getOrderId();
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Data").child("Order").child(orderId);

                orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Update the delivery status to "Completed"
                            orderRef.child("deliveryStatus").setValue("Completed")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Update UI and save notification
                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Data").child("User").child(order.getUserId()).child("Notification");
                                            String notificationID = db.push().getKey();
                                            NotificationModel notificationModel = new NotificationModel(orderId, "Your Order is Completed", order.getUserId(), notificationID,order.getTimeStamp(),false);
                                            db.child(notificationID).setValue(notificationModel);
                                            Toast.makeText(context, "Order marked as Completed", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Error marking order as Completed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "Order not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error retrieving order details", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }



    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView, quantityTextView, totalAmountTextView,TimeStamp;
        TextView acceptButton, rejectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TimeStamp=itemView.findViewById(R.id.timestamp);
            orderIdTextView = itemView.findViewById(R.id.OrderItemId);
            quantityTextView = itemView.findViewById(R.id.OrderItemQuantity);
            totalAmountTextView = itemView.findViewById(R.id.OrderItemTotal);
            acceptButton = itemView.findViewById(R.id.OrderItemAccept);
            rejectButton = itemView.findViewById(R.id.OrderItemDecline);
        }
    }
}
