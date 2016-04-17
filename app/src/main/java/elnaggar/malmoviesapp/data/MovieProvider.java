package elnaggar.malmoviesapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Elnaggar on 12/04/2016.
 */
public class MovieProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = buildUriMatcher();

    static final int MOVIE = 100;
    private static final int MOVIE_WITH_ID = 101;

    private static MovieDBHelper dbHelper = null;
    static private SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

    static {
        sqLiteQueryBuilder.setTables(MovieContract.MovieEntry.TABLE_NAME);
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.AUTHORITY;
        uriMatcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(authority, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MovieDBHelper(getContext());
        return true;
    }

    public Cursor getMoviesCursor(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return sqLiteQueryBuilder.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                retCursor = getMoviesCursor(projection, selection, selectionArgs, sortOrder);
                break;
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long returnID = sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        Uri returnUri = null;
        if (returnID > 0) {
            returnUri = MovieContract.MovieEntry.buildMovieUri(returnID);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        sqLiteDatabase.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int num = 0;
        num = sqLiteDatabase.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int num = 0;
        num = sqLiteDatabase.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
        if (num > 0) {
            return num;
        }
        return num;
    }
}
