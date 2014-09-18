package com.capetown.kyletightest.leaderboard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ArrayList<String> rivals = new ArrayList<String>();

            rivals.add("John 'John-D' Doe");
            rivals.add("Jane 'G.I.Jane' Doe");
            rivals.add("Kyle 'Osama' Titus");
            rivals.add("Lyle 'The Hammer' Jones");
            rivals.add("Michael 'MIKE' Williams");
            rivals.add("Steve 'Scumbag' Stevens");
            rivals.add("Teunis 'KakMaaker' Van Louw");

            ArrayAdapter <String> mRivalsAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_item_rival, // The name of the layout ID.
                            R.id.list_item_rival_name, // The ID of the textview to populate.
                            rivals);

            ListView listView = (ListView) rootView.findViewById(R.id.listview_rivals);
            listView.setAdapter(mRivalsAdapter);

            return rootView;
        }
    }
}
