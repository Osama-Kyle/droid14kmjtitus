package com.capetown.kyletightest.leaderboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Spinner;

/**
 * Created by root on 11/10/14.
 */
public class ChallengeDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.input_dialog, null))
                // Add action buttons
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Spinner spinner = (Spinner) ChallengeDialog.this.getDialog().findViewById(R.id.game_spinner);

                        int sel = spinner.getSelectedItemPosition();

                       RivalFragment.challengeRequest(sel+1);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChallengeDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
