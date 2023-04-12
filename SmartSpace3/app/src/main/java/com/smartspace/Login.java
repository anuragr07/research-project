package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Button loginBtn;
    private EditText userNameEdt, passwordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.btn_login);
        userNameEdt = findViewById(R.id.et_username);
        passwordEdt = findViewById(R.id.et_password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the entered username and password
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // call the UserLoginTask to login the user
                new UserLoginTask(new UserLoginTask.OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(boolean auth, String user_id, String email) {
                        if (auth) {
                            String message = "User ID: " + user_id + ", Username: " + email;
                            String userId= user_id;

                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();

                            // Start the new activity here
                            Intent intent = new Intent(Login.this, HomeScreen.class);
                            intent.putExtra("user_id", userId);
                            intent.putExtra("userName",email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute(userName, password);
            }
        });
    }
}




//                try {
//                    // Open the file from the assets folder
//                    InputStream inputStream = assetManager.open("Users.json");
//
//                    // Read the contents of the file into a byte array
//                    byte[] buffer = new byte[inputStream.available()];
//                    inputStream.read(buffer);
//
//                    // Convert the byte array to a string
//                    String jsonData = new String(buffer, "UTF-8");
//
//                    // Parse the JSON data
//                    JSONArray jsonArray = new JSONArray(jsonData);
//
//                    // Loop through the array to check if the entered username and password exists and matches the ones in the JSON file
//                    boolean isUserFound = false;
//                    String userId = "";
//                    String username = "";
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                        // Get values from JSON object
//                        String currentUsername = jsonObject.getString("username");
//                        String currentPassword = jsonObject.getString("password");
//                        String currentUserId = jsonObject.getString("user_id");
//
//                        if (userName.equals(currentUsername) && password.equals(currentPassword)) {
//                            // if the entered username and password match the ones in the JSON file, set the flag to true and store the user ID and username
//                            isUserFound = true;
//                            userId = currentUserId;
//                            username = currentUsername;
//                            break;
//                        }
//                    }
//
//                    // check if the entered text is empty or not
//                    if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
//                        Toast.makeText(Login.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
//                    }
//                    // if the entered username and password match the ones in the JSON file, start the next activity and pass the user ID and username
//                    else if (isUserFound) {
//                        Intent intent = new Intent(Login.this, HomeScreen.class);
//                        intent.putExtra("user_id", userId);
//                        intent.putExtra("username", username);
//                        startActivity(intent);
//                        finish();
//                    }
//                    // if the entered username and password don't match the ones in the JSON file, show an error message
//                    else {
//                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.i("Name:", "failed to get data " + e);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.i("Name:", "failed to get data2 " + e);
//                }


                // using api
              //  174.6.73.177:3000/user/login/AnuragRawal/1234



//            }
//        });
//    }
//}

