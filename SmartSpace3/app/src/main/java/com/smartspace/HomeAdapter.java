package com.smartspace;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private ArrayList<String> roomNames;
    private Context context;
    private String user_id;
    private String userName;

    public HomeAdapter(Context context, ArrayList<String> roomNames, String user_id,String userName) {
        this.context = context;
        this.roomNames = roomNames;
        this.user_id = user_id;
        this.userName = userName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v); // Return the view holder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Set the room name
        holder.name.setText(roomNames.get(position));
        // Set onClickListener to start new activity and pass the selected room name and user_id
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Room.class);
                intent.putExtra("roomName", roomNames.get(position));
                intent.putExtra("user_id", user_id);
                intent.putExtra("userName", userName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomNames.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            // Get the reference of item view's
            name = itemView.findViewById(R.id.text_roomname);
        }
    }
}
