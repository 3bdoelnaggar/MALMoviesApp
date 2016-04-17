package elnaggar.malmoviesapp.network;

/**
 * Created by Elnaggar on 08/03/2016.
 */
public interface NetworkTaskListner {
    void onTaskFinished(String jsonString,int jsonType);
}
