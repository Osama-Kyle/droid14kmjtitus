package com.capetown.kyletightest.leaderboard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.PushService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by root on 11/5/14.
 */
public class LoginActivity extends Activity {
    final String TAG = "LoginActivity";
    private String mUsername = null;
    private String mUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginBtn = (Button) findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String email = ((EditText)findViewById(R.id.text_email)).getText().toString();
                String password = ((EditText)findViewById(R.id.text_password)).getText().toString();

                fetchUser f = new fetchUser();

                f.execute(email,password);

            }
        });

    }

    public class fetchUser extends AsyncTask<String,Void, Void> {

        private final String LOG_TAG = "LoginActivity.fetchJson";
        private String result = "";

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, result);

            String [] split = result.split(";");

            if (split[0].equals("1")) {
                Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_LONG).show();

            } else if (split[0].equals("2")) {
                Toast.makeText(getApplicationContext(), "No account for that username, click register to register", Toast.LENGTH_LONG).show();

            } else {
                mUserId = split[1];

                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                i.putExtra("user_id", mUserId);
                startActivity(i);
                finish();

            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String responseJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String baseUrl = "http://theleaderboard.eu5.org/Login.php?";
                final String username_param = "username";
                final String password_param = "password";
                Uri builtUri = Uri.parse(baseUrl).buildUpon().appendQueryParameter(username_param,params[0])
                        .appendQueryParameter(password_param,params[1]).build();
                mUsername = params[0];
                URL url = new URL(builtUri.toString());
                Log.d(TAG, builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line);
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                responseJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            result = responseJsonStr.trim();
            return null;
        }

    }
}
