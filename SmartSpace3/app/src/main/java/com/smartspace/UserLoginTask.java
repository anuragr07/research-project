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

public class UserLoginTask extends AsyncTask<String, Void, String> {

    private OnTaskCompleted listener;

    public UserLoginTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String password = params[1];

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String responseJsonString = null;

        try {
            URL url = new URL("http://174.6.73.177:3000/user/login/" + username + "/" + password);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Read the response from the server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                responseJsonString = stringBuilder.toString();
            } else {
                responseJsonString = null;
            }
        } catch (IOException e) {
            Log.e("UserLoginTask", "Error in making request " + e.getMessage());
            responseJsonString = null;
        } finally {
            // Clean up resources
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("UserLoginTask", "Error closing stream " + e.getMessage());
                }
            }
        }

        return responseJsonString;
    }

    @Override
    protected void onPostExecute(String responseJsonString) {
        super.onPostExecute(responseJsonString);

        if (responseJsonString != null) {
            try {
                JSONObject responseObject = new JSONObject(responseJsonString);

                boolean auth = responseObject.getBoolean("auth");
                if (auth) {
                    String user_id = responseObject.getString("user_id");
                    String email = responseObject.getString("email");
                    listener.onTaskCompleted(auth, user_id, email);
                } else {
                    listener.onTaskCompleted(auth, "", "");
                }
            } catch (JSONException e) {
                Log.e("UserLoginTask", "Error parsing response " + e.getMessage());
                listener.onTaskCompleted(false, "", "");
            }
        } else {
            listener.onTaskCompleted(false, "", "");
        }
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(boolean auth, String user_id, String email);
    }
}
