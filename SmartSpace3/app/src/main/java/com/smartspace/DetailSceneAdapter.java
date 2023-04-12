package com.smartspace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailSceneAdapter extends RecyclerView.Adapter<DetailSceneAdapter.MyViewHolder> {

    private ArrayList<ColorItem> colorList;
    private Context context;
    private int selectedPosition = -1;
    public DetailSceneAdapter(Context context, ArrayList<ColorItem> colorList) {
        this.context = context;
        this.colorList = colorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_myscene, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ColorItem currentItem = colorList.get(position);
        holder.colorName.setText(currentItem.getColorName());
        holder.colorImage.setImageResource(currentItem.getImageResourceId());

        // set item background color based on selected position
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_item_background));
        } else {
            holder.itemView.setBackgroundResource(R.drawable.back_scene);
        }
        // set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);


                // start DetailPage activity
//                Intent intent = new Intent(context, PickColor.class);
//                intent.putExtra("colorName", currentItem.getColorName());
//                intent.putExtra("colorImage", currentItem.getImageResourceId());
//                context.startActivity(intent);
                Log.i("Hello",currentItem.getColorName());
                Log.i("Hello", String.valueOf(currentItem.getImageResourceId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView colorImage;
        TextView colorName;

        public MyViewHolder(View itemView) {
            super(itemView);
            colorImage = itemView.findViewById(R.id.color_image);
            colorName = itemView.findViewById(R.id.color_name);
        }
    }
}
