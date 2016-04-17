package elnaggar.malmoviesapp.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Elnaggar on 08/03/2016.
 */
public class MoviesGrapper extends AsyncTask<String,Void,String> {
    private final int type;
    private  NetworkTaskListner callBackListner=null;

    public MoviesGrapper(NetworkTaskListner callBackListner,int type){
        this.type=type;
        this.callBackListner=callBackListner;
    }
    public  final String TAG=this.getClass().getSimpleName();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            HttpURLConnection urlConnection= ConnectionManager.openConnection(params[0]);
            String response=ConnectionManager.getResponse(urlConnection);
            if(response!=null){
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,e.toString()+"/n"+e.getMessage());

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
       // Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
        callBackListner.onTaskFinished(s,type);

    }
}
