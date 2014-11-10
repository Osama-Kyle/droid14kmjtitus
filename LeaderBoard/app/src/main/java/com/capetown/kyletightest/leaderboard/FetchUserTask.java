package com.capetown.kyletightest.leaderboard;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.capetown.kyletightest.leaderboard.data.UserContract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.capetown.kyletightest.leaderboard.data.UserContract.UserEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.ChallengesEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.FeedsEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.GamesEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.LeaderboardEntry;

/**
 * Created by root on 11/9/14.
 */
public class FetchUserTask extends AsyncTask <String, Void, Void>{

    private final String LOG_TAG = "FetchUserTask";
    private String result = "";

    private ArrayAdapter<String> mUserAdapter;
    private final Context mContext;

    public FetchUserTask(Context context,ArrayAdapter<String> forecastAdapter) {
        mContext = context;
        mUserAdapter = forecastAdapter;
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
            final String baseUrl = "http://theleaderboard.eu5.org/GetRivals.php?";
            final String id_param = "id";
            Uri builtUri = Uri.parse(baseUrl).buildUpon().appendQueryParameter(id_param, params[0]).build();
            URL url = new URL(builtUri.toString());

            Log.d(LOG_TAG, builtUri.toString());


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

        Log.d(LOG_TAG, result);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if (!result.isEmpty()) {
            mUserAdapter.clear();
            String [] split = result.split(";");
            for(String dayForecastStr : split) {
                mUserAdapter.add(dayForecastStr);
            }
        }
    }




}
