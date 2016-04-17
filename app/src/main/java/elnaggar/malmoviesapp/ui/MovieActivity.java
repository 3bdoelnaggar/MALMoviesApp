package elnaggar.malmoviesapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import elnaggar.malmoviesapp.data.Movie;
import elnaggar.malmoviesapp.R;

public class MovieActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        boolean isFavorite=getIntent().getBooleanExtra("isFav",false);
        Movie movie=getIntent().getParcelableExtra("movie");
        getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, DetailFragment.newInstance(movie, isFavorite)).commit();

    }



}

