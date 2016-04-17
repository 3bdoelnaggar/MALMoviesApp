package elnaggar.malmoviesapp.network;

import elnaggar.malmoviesapp.Constants;

/**
 * Created by Elnaggar on 19/03/2016.
 */
public class URLBuilder {
    public static String getTrailerLinkForId(String id) {
        //return Uri.parse(Constants.TRAILER_BASE_URL).buildUpon().path("" + id).toString();
        return Constants.TRAILER_BASE_REPLACE_URL.replace("bbb", id);

    }
    public static String getReviewsLinkForId(String id) {
       // return Uri.parse(Constants.REVIEWS_BASE_URL).buildUpon().path("" + id).toString();
        return Constants.REVIEWS_BASE_REPLACE_URL.replace("bbb",id);
    }

}
