package elnaggar.malmoviesapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Elnaggar on 30/03/2016.
 */
public class MoviesDatabaseController {
    private final Context context;
    private SQLiteDatabase sqlDatabase;

    public MoviesDatabaseController(Context context) {
        this.context = context;
    }

    public SQLiteDatabase open() {
        this.sqlDatabase = new DataBaseHelper(context).getWritableDatabase();
        return sqlDatabase;
    }

    public long insert(Movie movie) {
        open();
       String id=movie.getID();
        Cursor cursor= sqlDatabase.query(DataBaseHelper.TABLE_NAME
                , new String[]{DataBaseHelper.CUL_MOVIE_ID_KEY},DataBaseHelper.CUL_MOVIE_ID_KEY + "=?"
                , new String[]{id}, null, null, null);
        if(cursor.moveToFirst()){
            close();
            return -1;

        }else {
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.CUL_NAME_KEY, movie.getTitle());
            values.put(DataBaseHelper.CUL_IMAGE_KEY, movie.getImageThumb());
            values.put(DataBaseHelper.CUL_MOVIE_ID_KEY, movie.getID());
            values.put(DataBaseHelper.CUL_DESCRIPTION_KEY, movie.getSynopsis());
            values.put(DataBaseHelper.CUL_DATE_KEY, movie.getReleaseDate());
            values.put(DataBaseHelper.CUL_RATE_KEY, movie.getUserRating());
            values.put(DataBaseHelper.CUL_TRAILER_KEY, movie.getTrailerKeys());
            values.put(DataBaseHelper.CUL_REVIEWS_KEY, movie.getReviews());
            long returnId=sqlDatabase.insert(DataBaseHelper.TABLE_NAME, null, values);
            close();
            return returnId;
        }


    }
    public void delete(String id) {
        open();
        sqlDatabase.delete(DataBaseHelper.TABLE_NAME,DataBaseHelper.CUL_MOVIE_ID_KEY+"=?",new String[]{id});
        close();
    }

    public ArrayList<Movie> getAll() {
        open();
        String[] PROJECTION = { DataBaseHelper.CUL_NAME_KEY,
                DataBaseHelper.CUL_MOVIE_ID_KEY,DataBaseHelper.CUL_IMAGE_KEY,
                DataBaseHelper.CUL_TRAILER_KEY
                , DataBaseHelper.CUL_DESCRIPTION_KEY, DataBaseHelper.CUL_DATE_KEY, DataBaseHelper.CUL_REVIEWS_KEY
                , DataBaseHelper.CUL_RATE_KEY};
        Cursor cursor = sqlDatabase.query(DataBaseHelper.TABLE_NAME, PROJECTION, null, null, null, null, null);
        ArrayList<Movie> movies = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setImageThumb(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CUL_IMAGE_KEY)));
            movie.setID(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CUL_MOVIE_ID_KEY)));
            movie.setTrailerKeys(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CUL_TRAILER_KEY)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CUL_NAME_KEY)));
            movie.setSynopsis(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CUL_DESCRIPTION_KEY)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CUL_DATE_KEY)));
            movie.setUserRating(cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.CUL_RATE_KEY)));
            movie.setReviews(cursor.getString(cursor.getColumnIndex(DataBaseHelper.CUL_REVIEWS_KEY)));

            movies.add(movie);

        }
        close();
        return movies;
    }

    public void close() {
        sqlDatabase.close();
        sqlDatabase=null;
    }

    public boolean isFavoriateMovie(String movieId) {
        open();
        Cursor cursor= sqlDatabase.query(DataBaseHelper.TABLE_NAME
                , new String[]{DataBaseHelper.CUL_MOVIE_ID_KEY},DataBaseHelper.CUL_MOVIE_ID_KEY + "=?"
                , new String[]{movieId}, null, null, null);
        if(cursor.moveToFirst()){
            close();
            return true;

        }else {
            close();
            return false;
        }
    }

    private class DataBaseHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "moviesDB";
        public static final String TABLE_NAME = "movies";
        public static final String CUL_NAME_KEY = "name";
        public static final String CUL_DESCRIPTION_KEY = "description";
        public static final String CUL_RATE_KEY = "rating";
        public static final String CUL_DATE_KEY = "date";
        public static final String CUL_REVIEWS_KEY = "reviews";
        public static final String CUL_IMAGE_KEY = "image";
        public static final int DATABASE_VERSION = 1;
        private static final String _ID = "_id";
        private static final String CUL_MOVIE_ID_KEY = "_movieId";
        public static final String CUL_TRAILER_KEY = "trailer";

        public DataBaseHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CUL_MOVIE_ID_KEY + " TEXT NOT NULL, " +
                    CUL_NAME_KEY + " TEXT NOT NULL, " +
                    CUL_DESCRIPTION_KEY + " TEXT NOT NULL, " +
                    CUL_IMAGE_KEY + " TEXT NOT NULL, " +
                    CUL_DATE_KEY + " TEXT NOT NULL, " +
                    CUL_TRAILER_KEY + " TEXT, " +
                    CUL_RATE_KEY + " REAL NOT NULL, " +
                    CUL_REVIEWS_KEY + " TEXT);";
            db.execSQL(SQL_CREATE_MOVIES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        }
    }
}
