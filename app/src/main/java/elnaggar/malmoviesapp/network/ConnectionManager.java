package elnaggar.malmoviesapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {

	private static final String REQUEST_METHOD_POST = "POST";
	private static final String REQUEST_METHOD_GET = "GET";

	private static final int READ_TIMEOUT = 10000;
	private static final int CONNECT_TIMEOUT = 10000;
	
	
	public static boolean isInternetConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null)
			return true;
		return false;
	}

	public static HttpURLConnection openConnection(String urlAddress)
			throws IOException {
		URL url = new URL(urlAddress);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setDoInput(true);
		//httpURLConnection.setDoOutput(true);
		httpURLConnection.setReadTimeout(READ_TIMEOUT);
		httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
		httpURLConnection.setRequestMethod(REQUEST_METHOD_GET);
		httpURLConnection
				.setRequestProperty("Content-Type", "application/json");
		return httpURLConnection;
	}

	public static String getResponse(HttpURLConnection httpURLConnection)
			throws IOException {

		InputStream is = httpURLConnection.getInputStream();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	public static void writeRequestParams(HttpURLConnection httpURLConnection,
			String params) throws IOException {
		OutputStream os = httpURLConnection.getOutputStream();
		os.write(params.getBytes());
		os.close();
	}

	

}
