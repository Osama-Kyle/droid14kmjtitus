package com.capetown.kyletightest.leaderboard.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 11/8/14.
 */
public class UserContract {

    public static final String CONTENT_AUTHORITY = "com.capetown.kyletightest.leaderboard";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "users";
    public static final String PATH_GAME = "games";
    public static final String PATH_CHALLENGE = "challenges";
    public static final String PATH_FEED = "feeds";
    public static final String PATH_LEADERBOARD = "leaderboard";

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "Users";

        public static final String COLUMN_USER_ID = "id";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_USERNAME = "username";

        public static final String COLUMN_DATE_JOINED = "date_joined";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class GamesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAME).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_GAME;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_GAME;

        public static final String TABLE_NAME = "Games";

        public static final String COLUMN_GAME_ID = "id";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_TYPE = "type";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class ChallengesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHALLENGE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CHALLENGE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CHALLENGE;

        public static final String TABLE_NAME = "Challenges";

        public static final String COLUMN_USER1_KEY = "user1_id";

        public static final String COLUMN_USER2_KEY = "user2_id";

        public static final String COLUMN_GAME_KEY = "game_id";

        public static final String COLUMN_ACCEPTED = "accepted";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class FeedsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FEED).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FEED;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FEED;

        public static final String TABLE_NAME = "Feeds";

        public static final String COLUMN_USER1_KEY = "user1_id";

        public static final String COLUMN_USER2_KEY = "user2_id";

        public static final String COLUMN_FEED = "event";

        public static final String COLUMN_FEED_TIME = "event_time";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class LeaderboardEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LEADERBOARD).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LEADERBOARD;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LEADERBOARD;

        public static final String TABLE_NAME = "Leaderboard";

        public static final String COLUMN_USER_KEY = "user_id";

        public static final String COLUMN_RIVALS = "rivals";

        public static final String COLUMN_WINS = "wins";

        public static final String COLUMN_LOSSES = "losses";

        public static final String COLUMN_POSITION = "position";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }




}

