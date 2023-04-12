package com.smartspace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailRoomAdapter extends RecyclerView.Adapter<DetailRoomAdapter.MyViewHolder> {
    ArrayList roomNames;
    ArrayList deviceBright;
    Context context;
    ArrayList selectedColor;
    ArrayList<Boolean> switchStatus;
    String selectedRoomName,userName;
    ArrayList<String> myList;
    public DetailRoomAdapter(Context context, ArrayList<String> roomNames, ArrayList<Boolean> switchStatus,ArrayList<String> deviceBright,ArrayList selectedColor,String selectedRoomName,String userName,ArrayList<String> myList) {
        this.context = context;
        this.roomNames = roomNames;
        this.switchStatus = switchStatus;
        this.deviceBright=deviceBright;
        this.selectedColor=selectedColor;
        this.selectedRoomName=selectedRoomName;
        this.userName=userName;
        this.myList=myList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_room, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String colorValue1 = (String) selectedColor.get(position);
        Log.i("Saksham", String.valueOf(selectedRoomName));
        Log.i("Saksham","Overall ");
        Log.i("Anu","Hello"+userName);
    if (selectedRoomName.equals("Bedroom-1") || selectedRoomName.equals("Bedroom-2")||selectedRoomName.equals("Bathroom"))
        {
           Log.i("Saksham","Ligh wala ");
            String[] components = colorValue1.split(",");
            int r = Integer.parseInt(components[0]);
            int g = Integer.parseInt(components[1]);
            int b = Integer.parseInt(components[2]);
            int colorInt = Color.rgb(r, g, b);
            ColorStateList colorStateList = ColorStateList.valueOf(colorInt);
            holder.toggleSwitch.setThumbTintList(colorStateList);
            holder.toggleSwitch.setTrackTintList(colorStateList);
            String  brightnessS=String.valueOf(deviceBright.get(position));
            int brightness=Integer.valueOf(brightnessS);
            Log.i("adapcheck", String.valueOf(brightness));
            if (brightness<100)
            {
                holder.sek.setProgress(brightness);
            }
            else if (brightness>100)
            {
                int value=brightness;
                int percentage = (value / 254) * 100;
                holder.sek.setProgress(percentage);
            }
            Log.i("Detail", String.valueOf(selectedColor.get(0)));
//        Log.i("Detail", String.valueOf(selectedColor.get(1)));
            holder.sek.setThumbTintList(colorStateList);
            holder.sek.setProgressTintList(colorStateList);
        }
    else {
        holder.sek.setVisibility(View.GONE);
    }
        holder.name.setText(roomNames.get(position).toString());

        // set the status of the switch based on the switchStatus variable
        holder.toggleSwitch.setChecked(switchStatus.get(position));

        // set the background color of the RelativeLayout
//        mySwitch.setThumbTintList(ColorStateList.valueOf(Color.RED));
//        holder.toggleSwitch.setHighlightColor(Color.parseColor(selectedColor));

// start

        //end

//        ColorStateList colorStateList = ColorStateList.valueOf(colorInt);
//        holder.sek.setThumbTintList(colorStateList);
//        holder.sek.setProgressTintList(colorStateList);
//        holder.sek.setThumbTintList(ColorStateList.valueOf(Color.parseColor(selectedColor)));
//       holder.sek.setProgressTintList(ColorStateList.valueOf(Color.parseColor(selectedColor)));


        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Log.i("Hello",roomNames.get(position).toString());

                if (roomNames.get(position).toString().equals("Shelly Smart Plug-1"))
                {
                    for (String number : myList) {
                        Log.i("Arpit500",number);
                    }
                    Log.i("Hello","smart plug 1");
                    Intent i = new Intent(context, GraphView.class);
                    i.putExtra("roomName",selectedRoomName);
                    i.putExtra("userName",userName);
                    context.startActivity(i); // invoke the SecondActivity.
                } else if (roomNames.get(position).toString().equals("Shelly Smart Plug-2"))
                {
                    Log.i("Hello","smart plug 2");
                    Intent i = new Intent(context, GraphView.class);
                    i.putExtra("roomName",selectedRoomName);
                    i.putExtra("userName",userName);
                    context.startActivity(i); // invoke the SecondActivity.
                }
                else {Intent i = new Intent(context, PickColor.class);
                    context.startActivity(i);} // invoke the SecondActivity.}
//                Intent i = new Intent(context, MyDevice.class);
            }
        });
    }



    @Override
    public int getItemCount() {
        return roomNames.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Switch toggleSwitch;
        TextView rel;
        SeekBar sek;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.text_roomname);
            toggleSwitch = itemView.findViewById(R.id.switch1);
            rel=itemView.findViewById(R.id.text_roomname);
            sek=itemView.findViewById(R.id.seekBar);
        }
    }
}