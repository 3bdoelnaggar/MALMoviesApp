package elnaggar.malmoviesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static elnaggar.malmoviesapp.data.MovieContract.MovieEntry.*;
/**
 * Created by Elnaggar on 12/04/2016.
 */
public class MovieDBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "moviesDBProvider";
    public static final int DATABASE_VERSION = 1;
    public MovieDBHelper( Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
