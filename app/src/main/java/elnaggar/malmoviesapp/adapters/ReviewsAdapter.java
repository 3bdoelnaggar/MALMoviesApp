package elnaggar.malmoviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import elnaggar.malmoviesapp.data.Review;

/**
 * Created by Elnaggar on 27/03/2016.
 */
public class ReviewsAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Review> reviews = new ArrayList<>();

    public ReviewsAdapter(ArrayList<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;

    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = reviews.get(position);
        View view = LayoutInflater.from(context).inflate(android.R.layout.two_line_list_item, parent, false);
        TextView authorTextView = (TextView) view.findViewById(android.R.id.text1);
        TextView contentTextView = (TextView) view.findViewById(android.R.id.text2);
        authorTextView.setText(review.getAuthor());
        contentTextView.setText(review.getContent());
        return view;
    }
}
