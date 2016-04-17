package elnaggar.malmoviesapp.data;

/**
 * Created by Elnaggar on 12/04/2016.
 */
public interface Projection {
    String[] MOVIE_COLUMN = new String[]{
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.CUL_NAME_KEY,
            MovieContract.MovieEntry.CUL_MOVIE_ID_KEY,
            MovieContract.MovieEntry.CUL_DESCRIPTION_KEY,
            MovieContract.MovieEntry.CUL_DATE_KEY,
            MovieContract.MovieEntry.CUL_IMAGE_KEY,
            MovieContract.MovieEntry.CUL_RATE_KEY,
            MovieContract.MovieEntry.CUL_REVIEWS_KEY,
            MovieContract.MovieEntry.CUL_TRAILER_KEY
    };

    int COL_MOVIE_ID = 0;
    int COL_MOVIE_NAME=1;
    int COL_MOVIE_DESC = 2;
    int COL_MOVIE_DATE = 3;
    int COL_MOVIE_IMAGE = 4;
    int COL_MOVIE_RATE = 5;
    int COL_MOVIE_REVIEWS = 6;
    int COL_MOVIE_TRAILERS = 7;


}
