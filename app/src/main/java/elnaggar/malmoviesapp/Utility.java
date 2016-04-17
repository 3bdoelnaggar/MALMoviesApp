package elnaggar.malmoviesapp;

import java.util.ArrayList;
import java.util.Arrays;

import elnaggar.malmoviesapp.data.Review;

/**
 * Created by Elnaggar on 30/03/2016.
 */
public class Utility {
    public static String convertArrayToString(ArrayList<Review> reviews) {
        String reviewsAsString = "";
        for (Review review : reviews) {
            reviewsAsString += "---" + review.getAuthor() + "---" + "\n" + review.getContent() + "\n\n";
        }
        return reviewsAsString;
    }

    public static ArrayList<String> StringToArray(String string) {
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(string.split("#")));
        return strings;
    }

    public static String ArrayToString(ArrayList<String> strings) {
        String fullString = "";
        for (String s : strings) {
            fullString += s + "#";
        }
        return fullString;
    }
}
