package elnaggar.malmoviesapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import elnaggar.malmoviesapp.Constants;
import elnaggar.malmoviesapp.R;
import elnaggar.malmoviesapp.Utility;
import elnaggar.malmoviesapp.data.Movie;
import elnaggar.malmoviesapp.data.MovieContract;
import elnaggar.malmoviesapp.data.MoviesDatabaseController;
import elnaggar.malmoviesapp.network.MoviesGrapper;
import elnaggar.malmoviesapp.network.NetworkTaskListner;
import elnaggar.malmoviesapp.network.Parser;
import elnaggar.malmoviesapp.network.URLBuilder;


public class DetailFragment extends Fragment implements NetworkTaskListner {
    public final String TAG = this.getClass().getSimpleName();
    private TextView title, date, description;
    private ImageView thump;
    private RatingBar ratingBar;
    private ListView reviewListView;
    private Movie movie;
    private TextView reviews;
    private CheckBox addToFavorite;
    private boolean isFavorite;
    private MoviesDatabaseController moviesDatabaseController;
    private boolean fovoraiteMovie;
    private ArrayList<String> trailers;
    private ProgressBar loadingView;
    private ShareActionProvider mShareActionProvider;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Movie movie, boolean isFavorite) {
        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        args.putBoolean("isFav", isFavorite);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(createShareTrailerIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private Intent createShareTrailerIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        if (movie.getTrailerKeys() != null) {
            shareIntent.setType("text/plain");

            shareIntent.putExtra(Intent.EXTRA_TEXT, Constants.TRAILER_YOUTUBE_BASE_URL + Utility.StringToArray(movie.getTrailerKeys()).get(0));
        }
        return shareIntent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            movie = getArguments().getParcelable("movie");
            isFavorite = getArguments().getBoolean("isFav");
        }
        moviesDatabaseController = new MoviesDatabaseController(getActivity());

//        if (moviesDatabaseController.isFavoriateMovie(movie.getID())) {
//            fovoraiteMovie = true;
//        }
        Cursor cursor = getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI
                , new String[]{MovieContract.MovieEntry.CUL_MOVIE_ID_KEY}, MovieContract.MovieEntry
                        .CUL_MOVIE_ID_KEY + "=?", new String[]{movie.getID()}, null);
        if (cursor.moveToFirst()) {
            fovoraiteMovie = true;
        }
        if (!isFavorite) {
            String trailerUrl = URLBuilder.getTrailerLinkForId(movie.getID());
            MoviesGrapper moviesGrapper = new MoviesGrapper(this, Constants.TYPE_TRAILER);
            moviesGrapper.execute(trailerUrl);
            String reviewsUrl = URLBuilder.getReviewsLinkForId(movie.getID());
            MoviesGrapper reviewsGrapper = new MoviesGrapper(this, Constants.TYPE_REVIEWS);
            reviewsGrapper.execute(reviewsUrl);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        reviews = (TextView) view.findViewById(R.id.reviews_texview);
        reviews.setText(movie.getReviews());
        title = (TextView) view.findViewById(R.id.tv_movie_title);
        date = (TextView) view.findViewById(R.id.tv_movie_date);
        loadingView = (ProgressBar) view.findViewById(R.id.marker_progress);
        description = (TextView) view.findViewById(R.id.tv_description);
        thump = (ImageView) view.findViewById(R.id.iv_thump);
        ratingBar = (RatingBar) view.findViewById(R.id.rb_rate);
        Button showTrailer = (Button) view.findViewById(R.id.trailer_button);
        addToFavorite = (CheckBox) view.findViewById(R.id.add_to_favorite_checkbox);
        addToFavorite.setVisibility(View.INVISIBLE);
        if (fovoraiteMovie) {
            addToFavorite.setChecked(true);

        }
        addToFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    moviesDatabaseController.insert(movie);
                    getContext().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieToContentValues(movie));
                } else {
                    getContext().getContentResolver().delete(MovieContract.MovieEntry
                            .CONTENT_URI, MovieContract.MovieEntry.CUL_MOVIE_ID_KEY + "=?", new String[]{movie.getID()});
//                    moviesDatabaseController.delete(movie.getID());
                }
            }
        });

        showTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrailersDialog();

            }
        });
        title.setText(movie.getTitle());
        date.setText(movie.getReleaseDate());
        description.setText(movie.getSynopsis());
        Picasso.with(getActivity()).load(Constants.IMAGE_BASE_URL + movie.getImageThumb()).fit().into(thump);
        ratingBar.setRating((float) (movie.getUserRating() / 2));
        if (isFavorite) {
            loadingFinish();
        }
        return view;
    }

    private void showTrailersDialog() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Trailers");
        builder.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1
                , buildDialogTitles()), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String trailerFullURL = Constants.TRAILER_YOUTUBE_BASE_URL + Utility.StringToArray(movie.getTrailerKeys()).get(which);
                Log.d(TAG, "trailer full URL" + trailerFullURL);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerFullURL));
                startActivity(intent);
            }
        });
        dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onTaskFinished(String jsonString, int jsonType) {
        if (jsonString != null) {

            if (jsonType == Constants.TYPE_TRAILER) {
                movie.setTrailerKeys(Parser.getTrailer(jsonString));
                trailers = Parser.getTrailerAsArray(jsonString);
                movie.setTrailerKeys(Utility.ArrayToString(trailers));

            }
            if (jsonType == Constants.TYPE_REVIEWS) {
                movie.setReviews(Utility.convertArrayToString(Parser.getReviews(jsonString)));
                reviews.setText(movie.getReviews());
                loadingFinish();


            }
        } else {
            Toast.makeText(getActivity(), "failed to read data from server", Toast.LENGTH_SHORT).show();
        }
    }

    public String[] buildDialogTitles() {
        String[] strings = new String[Utility.StringToArray(movie.getTrailerKeys()).size()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = "Trailer " + (i + 1);
        }
        return strings;
    }

    void loadingFinish() {
        loadingView.setVisibility(View.INVISIBLE);
        addToFavorite.setVisibility(View.VISIBLE);
    }

    public ContentValues movieToContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.CUL_NAME_KEY, movie.getTitle());
        values.put(MovieContract.MovieEntry.CUL_IMAGE_KEY, movie.getImageThumb());
        values.put(MovieContract.MovieEntry.CUL_MOVIE_ID_KEY, movie.getID());
        values.put(MovieContract.MovieEntry.CUL_DESCRIPTION_KEY, movie.getSynopsis());
        values.put(MovieContract.MovieEntry.CUL_DATE_KEY, movie.getReleaseDate());
        values.put(MovieContract.MovieEntry.CUL_RATE_KEY, movie.getUserRating());
        values.put(MovieContract.MovieEntry.CUL_TRAILER_KEY, movie.getTrailerKeys());
        values.put(MovieContract.MovieEntry.CUL_REVIEWS_KEY, movie.getReviews());
        return values;
    }

}
