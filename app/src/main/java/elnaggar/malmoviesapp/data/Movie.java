package elnaggar.malmoviesapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Elnaggar on 08/03/2016.
 */
public class Movie implements Parcelable {
    private String ID;
    private String imageThumb;
    private String title;
    private String synopsis;
    private double userRating;
    private String releaseDate;
    private String trailerKeys;



    public Movie(){

    }


//    private ArrayList<Review> reviewsURL = new ArrayList<>();
    private String reviews;

    protected Movie(Parcel in) {
        ID = in.readString();
        imageThumb = in.readString();
        title = in.readString();
        synopsis = in.readString();
        userRating = in.readDouble();
        releaseDate = in.readString();
        trailerKeys = in.readString();
        reviews = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getTrailerKeys() {
        return trailerKeys;
    }

    public void setTrailerKeys(String trailerKeys) {
        this.trailerKeys = trailerKeys;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviewsURL) {
        this.reviews = reviewsURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(imageThumb);
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
        dest.writeString(trailerKeys);
        dest.writeString(reviews);
    }
}
