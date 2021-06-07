package com.example.myadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myadmin.R;
import com.example.myadmin.model.SanPham;
import com.example.myadmin.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    Context context;
    List<User> UserList;

    public UserAdapter(Context context, List<User> UserList) {
        this.context = context;
        this.UserList = UserList;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_row_item, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, int position) {

        Glide.with(context).load(UserList.get(position).getAvatar()).into(holder.avatar);
        holder.name.setText("Họ tên: "+UserList.get(position).getName());
        holder.email.setText(String.valueOf("Email: "+UserList.get(position).getEmail()));
        holder.address.setText(String.valueOf("Địa chỉ: "+UserList.get(position).getAddress()));
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }


    public static final class UserViewHolder extends RecyclerView.ViewHolder {


        ImageView avatar;
        TextView address, name,email;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            address = itemView.findViewById(R.id.address);
            email = itemView.findViewById(R.id.email);
            name = itemView.findViewById(R.id.name);
        }
    }
}
