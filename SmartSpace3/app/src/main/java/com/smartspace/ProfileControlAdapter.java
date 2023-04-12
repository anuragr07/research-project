package com.smartspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProfileControlAdapter extends RecyclerView.Adapter<ProfileControlAdapter.MyViewHolder> {

    private ArrayList<ProfileItem> users;
    private Context context;
    private ArrayList<String> laundry;

    public ProfileControlAdapter(Context context, ArrayList<ProfileItem> users,ArrayList<String> laundry) {
        this.context = context;
        this.users = users;
        this.laundry=laundry;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row2, parent, false);
        // Set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v); // Return the view holder
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String username = users.get(position).getUserName();
        String consumption = users.get(position).getReadings();
        String consumption2 = laundry.get(position);
        double value = Double.parseDouble(consumption);
        double value2 = Double.parseDouble(consumption2);
        double value3 = value+value2;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String newValue = decimalFormat.format(value);
        String newValue2 = decimalFormat.format(value2);
        String newValue3 = decimalFormat.format(value3);
        holder.usernameTextView.setText(username);
        holder.consumptionTextViewK.setText(String.valueOf(newValue)+" KW");
        holder.consumptionTextViewL.setText(String.valueOf(newValue2)+" KW");
        holder.consumptionTextViewT.setText(String.valueOf(newValue3)+" KW");

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setData(ArrayList<ProfileItem> users, ArrayList<String> laundry) {
        this.users = users;
        this.laundry = laundry;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView consumptionTextViewK;
        TextView consumptionTextViewL;
        TextView consumptionTextViewT;

        public MyViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.user1);
            consumptionTextViewK = itemView.findViewById(R.id.ReadingsK);
            consumptionTextViewL=itemView.findViewById(R.id.ReadingsL);
            consumptionTextViewT=itemView.findViewById(R.id.total);
        }
    }
}
