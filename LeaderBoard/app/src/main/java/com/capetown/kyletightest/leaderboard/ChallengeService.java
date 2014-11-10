package com.capetown.kyletightest.leaderboard;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.capetown.kyletightest.leaderboard.data.UserContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by root on 11/10/14.
 */
public class ChallengeService extends Service {

    boolean challenge_request = false;
    private String TAG = ChallengeService.class.getSimpleName();


    public ChallengeService() {}
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        // Query the database and show alarm if it applies
        final String id = MainActivity.mUserId;

        if (challenge_request) {
            stopSelf();
        }

        Thread t = new Thread() {
            public void run() {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                // Will contain the raw JSON response as a string.
                String responseJsonStr = null;



                try {
                    // Construct the URL for the OpenWeatherMap query
                    // Possible parameters are avaiable at OWM's forecast API page, at
                    // http://openweathermap.org/API#forecast
                    final String baseUrl = "http://theleaderboard.eu5.org/ChallengeRequest.php?";
                    final String id1_param = "id";
                    Uri builtUri = Uri.parse(baseUrl).buildUpon().appendQueryParameter(id1_param, id).build();

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
                        return;
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
                        return;
                    }
                    responseJsonStr = buffer.toString();
                } catch (IOException e) {
                    Log.e(TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attemping
                    // to parse it.
                    return;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(TAG, "Error closing stream", e);
                        }
                    }
                }

                if (!responseJsonStr.isEmpty()) {

                    responseJsonStr = responseJsonStr.trim();

                    String[] split = responseJsonStr.split(",");

                    final String user = split[0];
                    final String game = split[1];

                    Log.d(TAG, user + " " + game);

                    // create alert dialog

                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), user + " challenges you to a game of " + game, Toast.LENGTH_LONG).show();
                        }
                    });


                }


            }
        };

        t.start();


        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarm.set(
                    alarm.RTC_WAKEUP,
                    System.currentTimeMillis() + (7000),
                    PendingIntent.getService(this, 0, new Intent(this, ChallengeService.class), 0)
            );
    }
}
