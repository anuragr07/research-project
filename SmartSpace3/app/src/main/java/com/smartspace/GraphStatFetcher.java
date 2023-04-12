package com.smartspace;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GraphStatFetcher extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {
    private static final String API_URL = "http://174.6.73.177:3000";

    private String userName;
    private String roomName;

    public GraphStatFetcher(String userName,String roomName) {
        this.userName = userName;
        this.roomName=roomName;
    }

    @Override
    protected ArrayList<ArrayList<String>> doInBackground(Void... params) {
        ArrayList<ArrayList<String>> statList = new ArrayList<>();
        Log.i("Anurag100", "Username: " + userName );
        try {
            URL url = new URL(API_URL +"/"+ roomName+"/" + "latest/" + userName);
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
            JSONArray logs = new JSONArray(response);
            for (int i = 0; i < logs.length(); i++) {
                JSONObject log = logs.getJSONObject(i);
                String username = log.getString("username");
                double consumption = log.getDouble("consumption");
                ArrayList<String> profileItem = new ArrayList<>();
                profileItem.add(username);
                profileItem.add(String.valueOf(consumption*1000));
                statList.add(profileItem);
                Log.i("Anurag100", "fetched data" );
            }

        } catch (IOException | JSONException e) {
            Log.i("Anurag200", "error"+e );

            e.printStackTrace();
            Log.d("Hello", String.valueOf(e));
            Log.i("Anurag100", "error"+e );
        }

        return statList;
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> statList) {
        // Handle the API response, e.g. update the UI with the stat list
        for (ArrayList<String> statItem : statList) {
            String username = statItem.get(0);
            String consumption = statItem.get(1);
            Log.i("Anurag100", "Username: " + username + ", Consumption: " + consumption);
        }
    }
}
