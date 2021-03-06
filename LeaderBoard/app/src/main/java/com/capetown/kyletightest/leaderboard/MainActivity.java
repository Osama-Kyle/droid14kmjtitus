package com.capetown.kyletightest.leaderboard;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements ActionBar.TabListener {

    final String TAG = "MainActivity";
    public static String mUserId = "0";
    private ShareActionProvider mShareActionProvider;
    public static boolean challenge_request = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserId = getIntent().getStringExtra("user_id");

        Intent i = new Intent(this, ChallengeService.class);

        startService(i);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new RivalFragment()).commit();
        }

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // for each of the sections in the app, add a tab to the action bar.

        actionBar.addTab(actionBar.newTab().setText(R.string.Feeds)
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.Rivals)
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.Leaderboard)
                .setTabListener(this));

        actionBar.selectTab(actionBar.getTabAt(1));

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
        } else if (id == R.id.menu_item_share) {
            Intent i = new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT,"LeaderBoard");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "Download the brand new Leaderboard app and compete with your friends: www.virus.com/breakyourphone");
            startActivity(Intent.createChooser(i,"Share"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {


        Log.d(TAG,"Tab selected is " + tab.getText());

       if (tab.getText().equals(getString(R.string.Rivals))) {

            setContentView(R.layout.activity_main);
            getFragmentManager().beginTransaction().add(R.id.container, new RivalFragment()).commit();

       } else if (tab.getText().equals(getString(R.string.Feeds))) {
           setContentView(R.layout.feeds);

       }  else if (tab.getText().equals(getString(R.string.Leaderboard))) {

           setContentView(R.layout.table_leaderboard);

           TableLayout tableLeaderboard = (TableLayout) findViewById(R.id.table_leaderboard);

           TableRow tr_head = new TableRow(this);
           tr_head.setBackgroundColor(Color.GRAY);
           tr_head.setLayoutParams(new TableRow.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));

           TextView label_position = new TextView(this);
           label_position.setText("Pos");
           label_position.setTextColor(Color.WHITE);
           label_position.setPadding(5, 5, 5, 5);
           tr_head.addView(label_position);

           TextView label_player = new TextView(this);
           label_player.setText("Player");
           label_player.setTextColor(Color.WHITE);
           label_player.setPadding(5, 5, 5, 5);
           tr_head.addView(label_player);// add the column to the table row here

           TextView label_R = new TextView(this);
           label_R.setText("R"); // set the text for the header
           label_R.setTextColor(Color.WHITE); // set the color
           label_R.setPadding(5, 5, 5, 5); // set the padding (if required)
           tr_head.addView(label_R); // add the column to the table row here

           TextView label_W = new TextView(this);
           label_W.setText("W"); // set the text for the header
           label_W.setTextColor(Color.WHITE); // set the color
           label_W.setPadding(5, 5, 5, 5); // set the padding (if required)
           tr_head.addView(label_W); // add the column to the table row here

           TextView label_D = new TextView(this);
           label_D.setText("D"); // set the text for the header
           label_D.setTextColor(Color.WHITE); // set the color
           label_D.setPadding(5, 5, 5, 5); // set the padding (if required)
           tr_head.addView(label_D); // add the column to the table row here

           TextView label_L = new TextView(this);
           label_L.setText("L"); // set the text for the header
           label_L.setTextColor(Color.WHITE); // set the color
           label_L.setPadding(5, 5, 5, 5); // set the padding (if required)
           tr_head.addView(label_L); // add the column to the table row here


           tableLeaderboard.addView(tr_head, new TableLayout.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));

           int count = 1;

           TableRow tr = new TableRow(this);
           if(count%2!=0) tr.setBackgroundColor(Color.WHITE);
           tr.setId(200+count);
           tr.setLayoutParams(new TableRow.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
           // Create a TextView to add date
           TextView labelPos = new TextView(this);
           labelPos.setId(200+count);
           labelPos.setText("1");
           labelPos.setPadding(5, 5, 5, 5);
           labelPos.setTextColor(Color.BLACK);
           tr.addView(labelPos);

           TextView labelPlayer = new TextView(this);
           labelPlayer.setId(200+count);
           labelPlayer.setText("Osama");
           labelPlayer.setPadding(5, 5, 5, 5);
           labelPlayer.setTextColor(Color.BLACK);
           tr.addView(labelPlayer);

           TextView labelR = new TextView(this);
           labelR.setId(200+count);
           labelR.setText("2");
           labelR.setPadding(5, 5, 5, 5);
           labelR.setTextColor(Color.BLACK);
           tr.addView(labelR);

           TextView labelW = new TextView(this);
           labelW.setId(200+count);
           labelW.setText("2");
           labelW.setPadding(5, 5, 5, 5);
           labelW.setTextColor(Color.BLACK);
           tr.addView(labelW);

           TextView labelD = new TextView(this);
           labelD.setId(200+count);
           labelD.setText("0");
           labelD.setPadding(5, 5, 5, 5);
           labelD.setTextColor(Color.BLACK);
           tr.addView(labelD);

           TextView labelL = new TextView(this);
           labelL.setId(200+count);
           labelL.setText("0");
           labelL.setPadding(5, 5, 5, 5);
           labelL.setTextColor(Color.BLACK);
           tr.addView(labelL);

           tableLeaderboard.addView(tr, new TableLayout.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));


           count++;
           TableRow tr2 = new TableRow(this);
           if(count%2!=0) tr2.setBackgroundColor(Color.WHITE);
           tr2.setId(100+count);
           tr2.setLayoutParams(new TableRow.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
           // Create a TextView to add date
           TextView labelPos2 = new TextView(this);
           labelPos2.setId(200+count);
           labelPos2.setText("2");
           labelPos2.setPadding(5, 5, 5, 5);
           labelPos2.setTextColor(Color.BLACK);
           tr2.addView(labelPos2);

           TextView labelPlayer2 = new TextView(this);
           labelPlayer2.setId(200+count);
           labelPlayer2.setText("Likes");
           labelPlayer2.setPadding(5, 5, 5, 5);
           labelPlayer2.setTextColor(Color.BLACK);
           tr2.addView(labelPlayer2);

           TextView labelR2 = new TextView(this);
           labelR2.setId(200+count);
           labelR2.setText("1");
           labelR2.setPadding(5, 5, 5, 5);
           labelR2.setTextColor(Color.BLACK);
           tr2.addView(labelR2);

           TextView labelW2 = new TextView(this);
           labelW2.setId(200+count);
           labelW2.setText("0");
           labelW2.setPadding(5, 5, 5, 5);
           labelW2.setTextColor(Color.BLACK);
           tr2.addView(labelW2);

           TextView labelD2 = new TextView(this);
           labelD2.setId(200+count);
           labelD2.setText("0");
           labelD2.setPadding(5, 5, 5, 5);
           labelD2.setTextColor(Color.BLACK);
           tr2.addView(labelD2);

           TextView labelL2 = new TextView(this);
           labelL2.setId(200+count);
           labelL2.setText("1");
           labelL2.setPadding(5, 5, 5, 5);
           labelL2.setTextColor(Color.BLACK);
           tr2.addView(labelL2);

           tableLeaderboard.addView(tr2, new TableLayout.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));

           count++;

           TableRow tr3 = new TableRow(this);
           if(count%2!=0) tr3.setBackgroundColor(Color.WHITE);
           tr3.setId(100+count);
           tr3.setLayoutParams(new TableRow.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
           // Create a TextView to add date
           TextView labelPos3 = new TextView(this);
           labelPos3.setId(200+count);
           labelPos3.setText("3");
           labelPos3.setPadding(5, 5, 5, 5);
           labelPos3.setTextColor(Color.BLACK);
           tr3.addView(labelPos3);

           TextView labelPlayer3 = new TextView(this);
           labelPlayer3.setId(200+count);
           labelPlayer3.setText("idiotP83");
           labelPlayer3.setPadding(5, 5, 5, 5);
           labelPlayer3.setTextColor(Color.BLACK);
           tr3.addView(labelPlayer3);

           TextView labelR3 = new TextView(this);
           labelR3.setId(200+count);
           labelR3.setText("1");
           labelR3.setPadding(5, 5, 5, 5);
           labelR3.setTextColor(Color.BLACK);
           tr3.addView(labelR3);

           TextView labelW3 = new TextView(this);
           labelW3.setId(200+count);
           labelW3.setText("0");
           labelW3.setPadding(5, 5, 5, 5);
           labelW3.setTextColor(Color.BLACK);
           tr3.addView(labelW3);

           TextView labelD3 = new TextView(this);
           labelD3.setId(200+count);
           labelD3.setText("0");
           labelD3.setPadding(5, 5, 5, 5);
           labelD3.setTextColor(Color.BLACK);
           tr3.addView(labelD3);

           TextView labelL3 = new TextView(this);
           labelL3.setId(200+count);
           labelL3.setText("1");
           labelL3.setPadding(5, 5, 5, 5);
           labelL3.setTextColor(Color.BLACK);
           tr3.addView(labelL3);

           tableLeaderboard.addView(tr3, new TableLayout.LayoutParams(
                   TableRow.LayoutParams.FILL_PARENT,
                   TableRow.LayoutParams.WRAP_CONTENT));


       } else {
           System.err.print("Error: Unknown tab selected");
       }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */

}
