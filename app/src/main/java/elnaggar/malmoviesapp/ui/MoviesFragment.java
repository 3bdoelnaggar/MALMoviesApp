package elnaggar.malmoviesapp.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import elnaggar.malmoviesapp.Constants;
import elnaggar.malmoviesapp.R;
import elnaggar.malmoviesapp.adapters.ImageAdapter;
import elnaggar.malmoviesapp.data.Movie;
import elnaggar.malmoviesapp.data.MovieContract;
import elnaggar.malmoviesapp.network.ConnectionManager;
import elnaggar.malmoviesapp.network.MoviesGrapper;
import elnaggar.malmoviesapp.network.NetworkTaskListner;
import elnaggar.malmoviesapp.network.Parser;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MoviesFragment extends Fragment implements NetworkTaskListner, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 23213;
    private OnFragmentInteractionListener mListener;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private MoviesGrapper moviesGrapper;
    private boolean isFavorite = false;
    private int itemPosition = 0;
    private boolean isFirstTime = true;
    private ArrayList<Movie> movies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (ConnectionManager.isInternetConnected(getContext())) {
            moviesGrapper = new MoviesGrapper(this, Constants.TYPE_MOVIES);
            moviesGrapper.execute(Constants.URL_POPULAR);
        } else
            Toast.makeText(getContext(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.mi_sort_by_most_pupular:
                if (ConnectionManager.isInternetConnected(getContext())) {
                    isFavorite = false;
                    moviesGrapper = new MoviesGrapper(this, Constants.TYPE_MOVIES);
                    moviesGrapper.execute(Constants.URL_POPULAR);
                } else
                    Toast.makeText(getContext(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mi_sort_by_highs_rate:
                if (ConnectionManager.isInternetConnected(getContext())) {
                    isFavorite = false;
                    moviesGrapper = new MoviesGrapper(this, Constants.TYPE_MOVIES);
                    moviesGrapper.execute(Constants.URL_TOP_RATED);
                } else
                    Toast.makeText(getContext(), "Please Check You Internet Connection", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mi_sort_by_favorite:
                isFavorite = true;
//                MoviesDatabaseController moviesDatabaseController = new MoviesDatabaseController(getActivity());
//                ArrayList<Movie> movies = moviesDatabaseController.getAll();
//                Cursor cursor = getContext().getContentResolver().
//                        query(MovieContract.MovieEntry.CONTENT_URI, Projection.MOVIE_COLUMN, null, null, null, null);
//                movies = cursorToMovies(cursor);
//                imageAdapter.setMovies(movies);
                getLoaderManager().initLoader(LOADER_ID, null, this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;
                Movie movie = (Movie) parent.getItemAtPosition(position);
                mListener.onFragmentInteraction(movie, isFavorite);
            }
        });
        imageAdapter = new ImageAdapter(getContext(), new ArrayList<Movie>());
        gridView.setAdapter(imageAdapter);
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("movies");
            imageAdapter.setMovies(movies);
            isFirstTime = savedInstanceState.getBoolean("isFirst");
            itemPosition = savedInstanceState.getInt("position");
        }
        return view;
    }

    private ArrayList<Movie> cursorToMovies(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setImageThumb(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_IMAGE_KEY)));
            movie.setID(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_MOVIE_ID_KEY)));
            movie.setTrailerKeys(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_TRAILER_KEY)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_NAME_KEY)));
            movie.setSynopsis(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_DESCRIPTION_KEY)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_DATE_KEY)));
            movie.setUserRating(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_RATE_KEY)));
            movie.setReviews(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_REVIEWS_KEY)));
            movies.add(movie);
        }
        return movies;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", itemPosition);
        if (movies != null) {
            outState.putParcelableArrayList("movies", movies);
        }
        outState.putBoolean("isFirst", isFirstTime
        );
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTaskFinished(String jsonString, int jsonType) {
        if (jsonString!=null) {
            movies = Parser.parseThisMoviesJSON(jsonString);
            imageAdapter.setMovies(movies);
            if (getContext().getResources().getBoolean(R.bool.isTowPane) && isFirstTime) {
                gridView.performItemClick(null, 1, 0);
                isFirstTime = false;
            }
            gridView.smoothScrollToPosition(itemPosition);
        } else {
            Toast.makeText(getActivity(), "failed to read data from server", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(), MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movies = cursorToMovies(data);
        imageAdapter.setMovies(movies);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Movie movie, boolean isFavorite);
    }
}
