package com.smartspace;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetProfileByUserIdTask extends AsyncTask<String, Void, String> {
    private static final String API_URL = "http://174.6.73.177:3000/";

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            String id = params[0];
            URL url = new URL(API_URL + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Add headers or authentication token if required

            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the API response
    }
}

