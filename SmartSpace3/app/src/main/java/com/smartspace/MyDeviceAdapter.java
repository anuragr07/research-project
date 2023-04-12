package com.smartspace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyDeviceAdapter extends RecyclerView.Adapter<MyDeviceAdapter.MyViewHolder> {
    ArrayList roomNames;
    Context context;
    public MyDeviceAdapter(Context context, ArrayList roomNames) {
        this.context = context;
        this.roomNames = roomNames;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_mydevice, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(roomNames.get(position).toString());
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Intent i = new Intent(context, PickColor.class);
                context.startActivity(i); // invoke the SecondActivity.

                        }
        });
    }


    @Override
    public int getItemCount() {
        return roomNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the item view's
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.txtdevicename);
        }
    }
}