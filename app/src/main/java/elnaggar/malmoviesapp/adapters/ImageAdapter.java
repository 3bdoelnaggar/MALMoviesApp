package elnaggar.malmoviesapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import elnaggar.malmoviesapp.Constants;
import elnaggar.malmoviesapp.data.Movie;

/**
 * Created by Elnaggar on 02/12/2015.
 */

public class ImageAdapter extends BaseAdapter {
    ArrayList<Movie>movies;
    Context mContext;


    public ImageAdapter(Context mContext, ArrayList<Movie> movies) {
        this.mContext = mContext;
        this.movies=movies;
    }
    public void setMovies(ArrayList<Movie>movies){
        this.movies=movies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
            imageView.setAdjustViewBounds(true);
           // imageView.setPadding(8, 8, 8, 8);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else {
            imageView = (ImageView)view;
        }
        Picasso.with(mContext).load(Constants.IMAGE_BASE_URL+movies.get(i).getImageThumb()).fit().centerCrop().into(imageView);
        return imageView;
    }


}
