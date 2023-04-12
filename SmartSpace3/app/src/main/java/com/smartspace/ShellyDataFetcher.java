package com.smartspace;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ShellyDataFetcher extends AsyncTask<Void, Void, ArrayList<String[]>> {
    private static final String API_URL = "http://174.6.73.177:3000/";
    private String userId;
    private String deviceName;

    public ShellyDataFetcher(String userId, String deviceName) {
        this.userId = userId;
        this.deviceName = deviceName;
    }

    @Override
    protected ArrayList<String[]> doInBackground(Void... params) {
        ArrayList<String[]> shellyData = new ArrayList<>();
        try {
            URL url = new URL(API_URL + "shelly/" + "status" + "/" + deviceName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.d("Hello", String.valueOf(url));
            // Add headers or authentication token if required

            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            String response = sb.toString();

            // Parse the response JSON object
            JSONObject jsonObject = new JSONObject(response);

            // Get the device name, status, and IP address
            String deviceName = jsonObject.getString("device_name");
            String status = jsonObject.getString("status");
            String ipAddress = jsonObject.getString("ip");

            // Get the settings object and its properties
            JSONObject settings = jsonObject.getJSONObject("settings");
            double power = settings.getDouble("power");
            double current = settings.getDouble("current");
            double totalEnergy = settings.getDouble("total_energy");

            // Get the temperature object and its properties
            JSONObject temp = settings.getJSONObject("temp");
            double tC = temp.getDouble("tC");
            double tF = temp.getDouble("tF");

            // Add all the fetched values to a string array
            String[] shellyValues = {deviceName, status, String.valueOf(power), String.valueOf(current), String.valueOf(totalEnergy), String.valueOf(tC), String.valueOf(tF), ipAddress};
            shellyData.add(shellyValues);
            Log.i("Harmohit","got the data");


        } catch (IOException | JSONException e) {
            Log.e("ShellyDataFetcher", "Error fetching data: " + e.getMessage());
        }

        return shellyData;
    }
}
