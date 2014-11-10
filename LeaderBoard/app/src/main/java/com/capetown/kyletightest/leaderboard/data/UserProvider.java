package com.capetown.kyletightest.leaderboard.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by root on 11/9/14.
 */
public class UserProvider extends ContentProvider {
    private static final int USER = 100;
    private static final int USER_WITH_ID = 101;
    private static final int GAME = 200;
    private static final int GAME_WITH_ID = 201;
    private static final int CHALLENGE = 300;
    private static final int CHALLENGE_WITH_ID = 301;
    private static final int FEED = 400;
    private static final int FEED_WITH_ID = 401;
    private static final int LEADERBOARD = 500;
    private static final int LEADERBOARD_WITH_ID = 501;

    private UserDbHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UserContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, UserContract.PATH_USER, USER);
        matcher.addURI(authority, UserContract.PATH_USER + "/*", USER_WITH_ID);
        matcher.addURI(authority,UserContract.PATH_GAME, GAME);
        matcher.addURI(authority,UserContract.PATH_GAME + "/*", GAME_WITH_ID);
        matcher.addURI(authority,UserContract.PATH_CHALLENGE, CHALLENGE);
        matcher.addURI(authority,UserContract.PATH_CHALLENGE + "/*", CHALLENGE_WITH_ID);
        matcher.addURI(authority,UserContract.PATH_FEED, FEED);
        matcher.addURI(authority,UserContract.PATH_FEED + "/*", FEED_WITH_ID);
        matcher.addURI(authority,UserContract.PATH_LEADERBOARD,LEADERBOARD);
        matcher.addURI(authority,UserContract.PATH_LEADERBOARD + "/*",LEADERBOARD_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new UserDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case USER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case USER_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.UserEntry.TABLE_NAME,
                        projection,
                        UserContract.UserEntry.COLUMN_USER_ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;

            }

            case GAME: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.GamesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case GAME_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.GamesEntry.TABLE_NAME,
                        projection,
                        UserContract.GamesEntry.COLUMN_GAME_ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;

            }

            case FEED: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.FeedsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FEED_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.FeedsEntry.TABLE_NAME,
                        projection,
                        UserContract.FeedsEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;

            }

            case CHALLENGE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.ChallengesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case CHALLENGE_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.ChallengesEntry.TABLE_NAME,
                        projection,
                        UserContract.ChallengesEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;

            }

            case LEADERBOARD: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case LEADERBOARD_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UserContract.LeaderboardEntry.TABLE_NAME,
                        projection,
                        UserContract.LeaderboardEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;

            }




            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case USER:
                return UserContract.UserEntry.CONTENT_TYPE;
            case USER_WITH_ID:
                return UserContract.UserEntry.CONTENT_ITEM_TYPE;

            case GAME:
                return UserContract.GamesEntry.CONTENT_TYPE;
            case GAME_WITH_ID:
                return UserContract.GamesEntry.CONTENT_ITEM_TYPE;

            case CHALLENGE:
                return UserContract.ChallengesEntry.CONTENT_TYPE;
            case CHALLENGE_WITH_ID:
                return UserContract.ChallengesEntry.CONTENT_ITEM_TYPE;

            case FEED:
                return UserContract.FeedsEntry.CONTENT_TYPE;
            case FEED_WITH_ID:
                return UserContract.FeedsEntry.CONTENT_ITEM_TYPE;

            case LEADERBOARD:
                return UserContract.LeaderboardEntry.CONTENT_TYPE;
            case LEADERBOARD_WITH_ID:
                return UserContract.LeaderboardEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case USER: {
                long _id = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = UserContract.UserEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case GAME: {
                long _id = db.insert(UserContract.GamesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = UserContract.GamesEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            }
            case CHALLENGE: {
                long _id = db.insert(UserContract.ChallengesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = UserContract.ChallengesEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FEED: {
                long _id = db.insert(UserContract.FeedsEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = UserContract.FeedsEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LEADERBOARD: {
                long _id = db.insert(UserContract.LeaderboardEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = UserContract.LeaderboardEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;




    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {

            case USER: {
                rowsDeleted = db.delete(
                        UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case GAME: {
                rowsDeleted = db.delete(
                        UserContract.GamesEntry.TABLE_NAME, selection, selectionArgs);
                break;

            }
            case CHALLENGE: {
                rowsDeleted = db.delete(
                        UserContract.ChallengesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case FEED: {
                rowsDeleted = db.delete(
                        UserContract.FeedsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case LEADERBOARD: {
                rowsDeleted = db.delete(
                        UserContract.LeaderboardEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {

            case USER: {
                rowsUpdated = db.update(
                        UserContract.UserEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }
            case GAME: {
                rowsUpdated = db.update(
                        UserContract.GamesEntry.TABLE_NAME,values, selection, selectionArgs);
                break;

            }
            case CHALLENGE: {
                rowsUpdated = db.update(
                        UserContract.ChallengesEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }
            case FEED: {
                rowsUpdated = db.update(
                        UserContract.FeedsEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }
            case LEADERBOARD: {
                rowsUpdated = db.update(
                        UserContract.LeaderboardEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
