package elnaggar.malmoviesapp.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import elnaggar.malmoviesapp.data.Movie;
import elnaggar.malmoviesapp.data.Review;

/**
 * Created by Elnaggar on 08/03/2016.
 */
public class Parser {
    public static ArrayList<Movie> parseThisMoviesJSON(String jsonString) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                Movie movie = new Movie();
                JSONObject jsonObject = results.getJSONObject(i);
                movie.setID(jsonObject.getString("id"));
                movie.setImageThumb(jsonObject.getString("poster_path"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setSynopsis(jsonObject.getString("overview"));
                movie.setUserRating(jsonObject.getDouble("vote_average"));
//                movie.setTrailerKeys(getTrailer(movie.getID()));
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static String getTrailer(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            String trilerURI = jsonArray.getJSONObject(0).getString("key");
            return trilerURI;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static ArrayList<String> getTrailerAsArray(String jsonString) {
        try {
            ArrayList<String> trailers=new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++) {
                String trilerURI = jsonArray.getJSONObject(i).getString("key");
                trailers.add(trilerURI);

            }
            return trailers;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Review> getReviews(String jsonString) {
        try {
            ArrayList<Review> reviews = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                Review review = new Review();
                JSONObject jsonReview = jsonArray.getJSONObject(i);
                review.setAuthor(jsonReview.getString("author"));
                review.setContent(jsonReview.getString("content"));
                reviews.add(review);
            }
            return reviews;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
