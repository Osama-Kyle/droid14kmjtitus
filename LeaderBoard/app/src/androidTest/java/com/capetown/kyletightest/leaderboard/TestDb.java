package com.capetown.kyletightest.leaderboard;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.capetown.kyletightest.leaderboard.data.UserDbHelper;

/**
 * Created by root on 11/9/14.
 */

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(UserDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new UserDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }
}
