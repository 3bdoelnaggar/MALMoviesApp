package elnaggar.malmoviesapp.adapters;

        import android.content.Context;
        import android.database.Cursor;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CursorAdapter;
        import android.widget.GridView;
        import android.widget.ImageView;

        import com.squareup.picasso.Picasso;

        import elnaggar.malmoviesapp.Constants;
        import elnaggar.malmoviesapp.data.MovieContract;

/**
 * Created by Elnaggar on 12/04/2016.
 */
public class MoviesCursorAdapter extends CursorAdapter {
    private final Context mContext;

    public MoviesCursorAdapter(Context context, Cursor c,int flag) {
        super(context, c,flag);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ImageView imageView;
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        imageView.setAdjustViewBounds(true);
        // imageView.setPadding(8, 8, 8, 8);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view;
        String image = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.CUL_IMAGE_KEY));
        Picasso.with(mContext).load(Constants.IMAGE_BASE_URL + image).fit().centerCrop().into(imageView);
    }
}
