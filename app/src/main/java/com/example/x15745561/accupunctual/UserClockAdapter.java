package com.example.x15745561.accupunctual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Configuration;
import java.util.Locale;


import java.util.List;

public class UserClockAdapter extends RecyclerView.Adapter<UserClockAdapter.UserViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<User> userList;

    //getting the context and product list with constructor
    public UserClockAdapter(Context mCtx, List<User> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.usercard, null);
        return new UserViewHolder(view);


    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        //getting the product of the specified position

        User user = userList.get(position);



        //binding the data with the viewholder view
        holder.textViewUsername.setText(user.getUsername());
        holder.textViewUserClockStatus.setText(user.getUserClockStatus());
        holder.textViewUserClockTime.setText(user.getUserClockTime());
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUsername, textViewUserClockStatus, textViewUserClockTime;

        public UserViewHolder(final View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.username_txtview);
            textViewUserClockStatus = itemView.findViewById(R.id.userClockStatus_txtview);
            textViewUserClockTime = itemView.findViewById(R.id.userClocktime_txtview);
        }
    }
}