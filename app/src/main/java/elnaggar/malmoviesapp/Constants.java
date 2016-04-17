package elnaggar.malmoviesapp;

/**
 * Created by Elnaggar on 08/03/2016.
 */
public interface Constants {
    String MOVIES_API_KEY="";
    String URL_POPULAR="http://api.themoviedb.org/3/movie/popular"+MOVIES_API_KEY;
    String IMAGE_BASE_URL="http://image.tmdb.org/t/p/w185/";
    String URL_TOP_RATED="http://api.themoviedb.org/3/movie/top_rated"+MOVIES_API_KEY;
    String TRAILER_YOUTUBE_BASE_URL="https://www.youtube.com/watch?v=";
    String TRAILER_BASE_REPLACE_URL ="http://api.themoviedb.org/3/movie/bbb/videos"+MOVIES_API_KEY;
    String REVIEWS_BASE_REPLACE_URL="http://api.themoviedb.org/3/movie/bbb/reviews"+MOVIES_API_KEY;


    int TYPE_MOVIES=1;
    int TYPE_TRAILER=2;
    int TYPE_REVIEWS =3;

}