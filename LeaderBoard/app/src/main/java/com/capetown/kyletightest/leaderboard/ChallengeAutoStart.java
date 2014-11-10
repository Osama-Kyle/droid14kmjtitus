package com.capetown.kyletightest.leaderboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by root on 11/10/14.
 */

public class ChallengeAutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ChallengeService.class));
    }
}

