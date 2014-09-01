package com.example.kyle.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("test", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test", "onSTart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "ondesdtroy");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        if (id == R.id.action_map) {
            openPreferredLocationMap();
        }

        return super.onOptionsItemSelected(item);
    }



    public void openPreferredLocationMap() {

        Intent mapIntent = new Intent(Intent.ACTION_VIEW);

        String location = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()).getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        Log.d("Test", location);
        Uri loc = Uri.parse("geo:0,0?q=" + location);

        Log.d("Test", loc.toString());

        mapIntent.setData(loc);

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Cant dala bra", Toast.LENGTH_LONG).show();
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */


}
