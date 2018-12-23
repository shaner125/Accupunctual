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

public class WorkPlaceAdapter extends RecyclerView.Adapter<WorkPlaceAdapter.WorkplaceViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;
    private String username;

    //we are storing all the products in a list
    private List<Workplace> workplaceList;

    //getting the context and product list with constructor
    public WorkPlaceAdapter(Context mCtx, List<Workplace> workplaceList, String username) {
        this.mCtx = mCtx;
        this.workplaceList = workplaceList;
        this.username = username;
    }

    @Override
    public WorkplaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card, null);
        return new WorkplaceViewHolder(view);


    }

    @Override
    public void onBindViewHolder(WorkplaceViewHolder holder, int position) {
        //getting the product of the specified position

        Workplace workplace = workplaceList.get(position);



        //binding the data with the viewholder views
        holder.textViewWorkplaceName.setText(workplace.getWorkplaceName());
        holder.textViewAddr1.setText(workplace.getWorkplaceAddr1());
        holder.setWorkplaceName(workplace.getWorkplaceName());
    }


    @Override
    public int getItemCount() {
        return workplaceList.size();
    }


    class WorkplaceViewHolder extends RecyclerView.ViewHolder {

        TextView textViewWorkplaceName, textViewAddr1;
        String workplaceName;

        public WorkplaceViewHolder(final View itemView) {
            super(itemView);

            textViewWorkplaceName = itemView.findViewById(R.id.workplace_title_txtview);
            textViewAddr1 = itemView.findViewById(R.id.workplace_addr1_txtview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentBundle = new Intent(itemView.getContext(),Workplaceclock.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("workplacename", workplaceName);
                    bundle.putString("username", username);
                    intentBundle.putExtras(bundle);
                    itemView.getContext().startActivity(intentBundle);
                }
            });

        }

        public void setWorkplaceName(String wname){
            this.workplaceName = wname;
        }
    }
}