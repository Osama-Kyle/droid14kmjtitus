package com.capetown.kyletightest.leaderboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.capetown.kyletightest.leaderboard.data.UserContract.UserEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.ChallengesEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.FeedsEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.GamesEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.LeaderboardEntry;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RivalFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CHALLENGE_LOADER = 0;
    private static int CHALLENGE_ID;

    private static Context mContext;


    private static final String[] CHALLENGE_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            ChallengesEntry.TABLE_NAME + "." + ChallengesEntry._ID,
            ChallengesEntry.COLUMN_USER1_KEY,
            ChallengesEntry.COLUMN_USER2_KEY,
            ChallengesEntry.COLUMN_GAME_KEY,
            ChallengesEntry.COLUMN_ACCEPTED
    };

    public static final int COL_RIVAL_NAME = 0;
    public static final int COL_RIVAL_AVATAR = 1;
    public static final int COL_RIVAL_ICON = 2;

    private ArrayAdapter<String> mRivalAdapter;
    private static String mUserId;

    public RivalFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }



        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {


            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mRivalAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_item_rival, // The name of the layout ID.
                            R.id.list_item_rival_name, // The ID of the textview to populate.
                            new ArrayList<String>());


            ListView listView = (ListView) rootView.findViewById(R.id.listview_rivals);
            listView.setAdapter(mRivalAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    ChallengeDialog cd = new ChallengeDialog();
                    setChallengeId(position+2);

                    cd.show(getFragmentManager(), "ChallengeDialogFragment");
                }
            });


            return rootView;
        }

    public static void challengeRequest(int selected) {
        Intent i = new Intent(mContext,SendChallengeService.class);

        i.putExtra("id1", mUserId);
        i.putExtra("id2", "" +CHALLENGE_ID);
        i.putExtra("game", ""+selected);
        mContext.startService(i);


    }

    private void setChallengeId(int pos) {
        CHALLENGE_ID = pos;
    }

    private void updateRivals() {
        new FetchUserTask(getActivity(), mRivalAdapter).execute(mUserId);
    }

    @Override
    public void onStart() {
        mUserId = MainActivity.mUserId;
        super.onStart();
        updateRivals();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(CHALLENGE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        // Sort order:  Ascending, by date.
        String sortOrder = ChallengesEntry._ID + " ASC";

        Uri challengeEntryUri = ChallengesEntry.CONTENT_URI;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                challengeEntryUri,
                CHALLENGE_COLUMNS,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }




    protected void showInputDialog(LayoutInflater inflater, ViewGroup container) {

        // get prompts.xml view
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity().getBaseContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View promptView = inflater.inflate(R.layout.input_dialog, container, false);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity().getApplicationContext());
        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
}

