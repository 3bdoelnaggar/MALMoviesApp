package elnaggar.malmoviesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

import elnaggar.malmoviesapp.R;
import elnaggar.malmoviesapp.adapters.ImageAdapter;
import elnaggar.malmoviesapp.data.Movie;
import elnaggar.malmoviesapp.network.MoviesGrapper;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnFragmentInteractionListener {

    private GridView gridView;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ImageAdapter adapter;
    private MoviesGrapper moviesGrapper;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTwoPane = findViewById(R.id.movie_detail_container) != null ? true : false;
        if(isTwoPane) {
        }
    }




    @Override
    public void onFragmentInteraction(Movie movie,boolean isFavorite) {

        if (isTwoPane) {
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, DetailFragment.newInstance(movie, isFavorite)).commit();
        } else {
            Intent intent = new Intent(this, MovieActivity.class);
            intent.putExtra("isFav",isFavorite);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }

    }
}
