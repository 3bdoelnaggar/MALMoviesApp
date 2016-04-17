package elnaggar.malmoviesapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Elnaggar on 12/04/2016.
 */
public class MovieContract {
    public static final String AUTHORITY = "elnaggar.malmoviesapp";
    public static final Uri CONTENT_BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "movie";



    public static final class MovieEntry implements BaseColumns {

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + AUTHORITY + "/" + MovieContract.PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                "/" + AUTHORITY + "/" + MovieContract.PATH_MOVIE;
        public static final Uri CONTENT_URI = MovieContract.CONTENT_BASE_URI.buildUpon()
                .appendPath(MovieContract.PATH_MOVIE).build();

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static final String TABLE_NAME = "movies";
        public static final String CUL_NAME_KEY = "name";
        public static final String CUL_DESCRIPTION_KEY = "description";
        public static final String CUL_RATE_KEY = "rating";
        public static final String CUL_DATE_KEY = "date";
        public static final String CUL_REVIEWS_KEY = "reviews";
        public static final String CUL_IMAGE_KEY = "image";
        public static final String CUL_MOVIE_ID_KEY = "_movieId";
        public static final String CUL_TRAILER_KEY = "trailer";
    }
}
