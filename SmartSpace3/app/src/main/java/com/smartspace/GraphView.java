package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class GraphView extends AppCompatActivity {
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList<Entry> lineEntries = new ArrayList<>();
    ArrayList<String> myList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph_view);
        String roomName = getIntent().getStringExtra("roomName");
        String userName = getIntent().getStringExtra("userName");
        Log.i("Anu","Hello"+userName);
        TextView textViewPower = findViewById(R.id.power);
        TextView textViewCurrent = findViewById(R.id.current);
        TextView textViewRoomName = findViewById(R.id.text_roomname);
        TextView textViewTotal = findViewById(R.id.totalEnergy);
        ImageButton imageButton = findViewById(R.id.btn_power);
        lineChart = findViewById(R.id.chart);
        textViewRoomName.setText(roomName);
        Description desc = new Description();
        desc.setText("Energy Consumption History");
        desc.setTextSize(20f);
        lineChart.setDescription(desc);

        lineEntries = new ArrayList<>(); // Initialize the lineEntries array list

        lineDataSet = new LineDataSet(lineEntries, "Energy Consumption");
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(18f);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
            @Override
            public String getFormattedValue(float value) {
                long millis = (long) value * 1000L;
                return mFormat.format(new Date(millis));
            }

        });
        String deviceName;
        if (roomName.equals("Kitchen"))
        {
             deviceName="Shelly Smart Plug-1";
        }
        else { deviceName="Shelly Smart Plug-2";}
        ShellyDataFetcher shellyDataFetcher = new ShellyDataFetcher("1005", deviceName) {
            String deviceName, status,power, current,totalEnergy, tC, tF, ipAddress;
            @Override
            protected void onPostExecute(ArrayList<String[]> shellyData) {
                for (String[] values : shellyData) {
                    deviceName = values[0];
                    status = values[1];
                    power = values[2];
                    current = values[3];
                    totalEnergy = values[4];
                    tC = values[5];
                    tF = values[6];
                    ipAddress = values[7];
                    Log.i("Harmohit2",status+" "+ deviceName + ", " + current + ", " + totalEnergy + ", " + ipAddress);
                }
                textViewPower.setText(power+" W");
                textViewCurrent.setText(current+" A");
                textViewTotal.setText(totalEnergy+ " J");

                if (status.equals("On")) {

                    // Get the existing drawable background
                    Drawable backgroundDrawable = getResources().getDrawable(R.drawable.back_settings);

// Create a new GradientDrawable for the border
                    GradientDrawable borderDrawable = new GradientDrawable();
                    borderDrawable.setShape(GradientDrawable.RECTANGLE);
                    int lightBlueColor = Color.parseColor("#FF0000");
                    borderDrawable.setStroke(8, lightBlueColor);
                    borderDrawable.setCornerRadius(2);

// Create a new LayerDrawable to combine the border and background drawables
                    Drawable[] layers = new Drawable[2];
                    layers[0] = borderDrawable;
                    layers[1] = backgroundDrawable;
                    LayerDrawable layerDrawable = new LayerDrawable(layers);

// Set the LayerDrawable as the background for the ImageButton
                    imageButton.setBackground(layerDrawable);

//                    GradientDrawable shape = new GradientDrawable();
//                    shape.setShape(GradientDrawable.RECTANGLE);
//                    shape.setStroke(4, Color.BLUE);
//                    shape.setCornerRadius(8);
//                    imageButton.setBackground(shape);
                    ImageButton imageButton = findViewById(R.id.btn_power);
                    imageButton.setImageResource(R.drawable.baseline_power_settings_new_25);

                }
                else
                {
                    imageButton.setImageResource(R.drawable.baseline_power_settings_new_24);
                }


            }
        };
        shellyDataFetcher.execute();

        GraphStatFetcher myGraphStatFetcher = new GraphStatFetcher(userName, roomName) {
            @Override
            protected void onPostExecute(ArrayList<ArrayList<String>> statList) {
                // Handle the API response in your new activity
                if (statList.isEmpty()) {
                    return; // return early if the list is empty
                }
                myList.clear(); // Clear the list before adding new values
                for (ArrayList<String> statItem : statList) {
                    String username = statItem.get(0);
                    String consumption = statItem.get(1);
                    Log.i("Arpit100", "Username: " + username + ", Consumption: " + consumption);
                    // do something with username and consumption

                    myList.add(consumption); // Add new values to the list
                }
                for (String element : myList) {
                    Log.i("Arpit200", String.valueOf(myList));
                }
                getEntries(); // call the getEntries() method to update the line chart

                lineDataSet = new LineDataSet(lineEntries, "Energy Consumption");
                lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                lineDataSet.setValueTextColor(Color.WHITE);
                lineDataSet.setValueTextSize(18f);
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new ValueFormatter() {
                    private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
                    @Override
                    public String getFormattedValue(float value) {
                        long millis = (long) value * 1000L;
                        return mFormat.format(new Date(millis));
                    }
                });
                lineChart.invalidate(); // invalidate the chart to redraw it with the updated data
            }
        };
        myGraphStatFetcher.execute();
    }

    private void getEntries() {
        if (myList.isEmpty()) {
            Log.i("Arpit","Empty");
            return; // return early if the list is empty
        }
        lineEntries.clear(); // Clear the line entries before adding new ones
        for (int k=0; k < myList.size(); k++) {
            Log.i("Arpit",myList.get(k));
            lineEntries.add(new Entry(k+1, Float.parseFloat(myList.get(k))));
        }
    }
}
