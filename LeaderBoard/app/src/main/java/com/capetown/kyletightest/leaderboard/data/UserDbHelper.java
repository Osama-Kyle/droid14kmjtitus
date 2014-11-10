package com.capetown.kyletightest.leaderboard.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.capetown.kyletightest.leaderboard.data.UserContract.UserEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.ChallengesEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.FeedsEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.GamesEntry;
import com.capetown.kyletightest.leaderboard.data.UserContract.LeaderboardEntry;

/**
 * Created by root on 11/8/14.
 */
public class UserDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_GAMES_TABLE = "CREATE TABLE " + GamesEntry.TABLE_NAME + " (" +
                GamesEntry.COLUMN_GAME_ID + " INTEGER PRIMARY KEY," +
                GamesEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                GamesEntry.COLUMN_TYPE + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                UserEntry.COLUMN_SURNAME + " TEXT NOT NULL, " +
                UserEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
                UserEntry.COLUMN_DATE_JOINED + " TEXT NOT NULL);";

        final String SQL_CREATE_CHALLENGES_TABLE = "CREATE TABLE " + ChallengesEntry.TABLE_NAME + " (" +
                ChallengesEntry._ID + " INTEGER PRIMARY KEY," +

                ChallengesEntry.COLUMN_USER1_KEY + " INTEGER NOT NULL, " +
                ChallengesEntry.COLUMN_USER2_KEY + " INTEGER NOT NULL, " +
                ChallengesEntry.COLUMN_GAME_KEY  + " INTEGER NOT NULL, " +
                ChallengesEntry.COLUMN_ACCEPTED + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" + ChallengesEntry.COLUMN_USER1_KEY + ") REFERENCES " +
                UserEntry.TABLE_NAME  + "(" + UserEntry.COLUMN_USER_ID + "), " +

                " FOREIGN KEY (" + ChallengesEntry.COLUMN_USER2_KEY + ") REFERENCES " +
                UserEntry.TABLE_NAME  + "(" + UserEntry.COLUMN_USER_ID + "), " +

                " FOREIGN KEY (" + ChallengesEntry.COLUMN_GAME_KEY + ") REFERENCES " +
                GamesEntry.TABLE_NAME + "(" + GamesEntry.COLUMN_GAME_ID + "));";



        final String SQL_CREATE_FEEDS_TABLE = "CREATE TABLE " + FeedsEntry.TABLE_NAME + " (" +
                FeedsEntry._ID + " INTEGER PRIMARY KEY," +

                FeedsEntry.COLUMN_USER1_KEY + " INTEGER NOT NULL, " +
                FeedsEntry.COLUMN_USER2_KEY + " INTEGER NOT NULL, " +
                FeedsEntry.COLUMN_FEED + " TEXT NOT NULL, " +
                FeedsEntry.COLUMN_FEED_TIME + " TEXT NOT NULL, " +

                " FOREIGN KEY (" + FeedsEntry.COLUMN_USER1_KEY + ") REFERENCES " +
                UserEntry.TABLE_NAME  + "(" + UserEntry.COLUMN_USER_ID + "), " +

                " FOREIGN KEY (" + FeedsEntry.COLUMN_USER2_KEY + ") REFERENCES " +
                UserEntry.TABLE_NAME  + "(" + UserEntry.COLUMN_USER_ID + "));";



        final String SQL_CREATE_LEADERBOARD_TABLE = "CREATE TABLE " + LeaderboardEntry.TABLE_NAME + " (" +
                LeaderboardEntry._ID + " INTEGER PRIMARY KEY," +

                LeaderboardEntry.COLUMN_USER_KEY + " INTEGER NOT NULL, " +
                LeaderboardEntry.COLUMN_RIVALS + " INTEGER NOT NULL, " +
                LeaderboardEntry.COLUMN_WINS + " INTEGER NOT NULL, " +
                LeaderboardEntry.COLUMN_LOSSES + " INTEGER NOT NULL, " +
                LeaderboardEntry.COLUMN_POSITION + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" + LeaderboardEntry.COLUMN_USER_KEY + ") REFERENCES " +
                UserEntry.TABLE_NAME  + "(" + UserEntry.COLUMN_USER_ID + "));";



        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GAMES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CHALLENGES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FEEDS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LEADERBOARD_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GamesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChallengesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FeedsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LeaderboardEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
