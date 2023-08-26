package com.example.foodcorneradmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodcorneradmin.Activity.DetailActivity;
import com.example.foodcorneradmin.Models.MenuModel;
import com.example.foodcorneradmin.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context context;
    private List<MenuModel> menuList;

    public MenuAdapter(Context context, List<MenuModel> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_view, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuModel menuModel = menuList.get(position);
        holder.DishName.setText(menuModel.getItemName());
        holder.DishPrice.setText(menuModel.getItemPrice());
        holder.DishDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("MenuId",menuModel.getMenuId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(menuModel.getItemImage()).into(holder.DishImage);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView DishName,DishPrice,DishDetail;
        ImageView DishImage;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            DishName = itemView.findViewById(R.id.MenuRecycleViewDishName);
            DishPrice = itemView.findViewById(R.id.MenuRecycleViewDishPrice);
            DishDetail = itemView.findViewById(R.id.MenuRecycleViewDishDetail);
            DishImage = itemView.findViewById(R.id.MenuRecycleViewItemImage);
        }
    }
}
