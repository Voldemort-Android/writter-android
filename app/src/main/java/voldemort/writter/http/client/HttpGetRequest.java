package voldemort.writter.http.client;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;

public class HttpGetRequest extends AsyncTask<Void, Void, HttpResponse> {

    private final int requestTimeout = 4000;

    private final ObjectMapper mapper = new ObjectMapper();

    private String url;

    private Map<String, String> headers;

    private Consumer<HttpResponse> callback;

    private Consumer<HttpResponse> errorCallback;

    protected HttpGetRequest(String url,
                          Map<String, String> headers,
                          Consumer<HttpResponse> callback,
                          Consumer<HttpResponse> errorCallback) {

        this.url = url;
        this.headers = headers;
        this.callback = callback;
        this.errorCallback = errorCallback;
    }


    @Override
    protected HttpResponse doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;

        try {

            // Create a HTTP connection object from the URL.
            urlConnection = (HttpURLConnection) new URL(url).openConnection();

            // Add headers if needed.
            if (headers != null) {
                HttpClientUtils.setHeaders(urlConnection, headers);
            }

            urlConnection.setConnectTimeout(requestTimeout);

            // Make the request.
            urlConnection.connect();

            Log.d("HELLO", "Response Code: " + urlConnection.getResponseCode());

            // Try to get response body from URL connection.
            String responseBody = HttpClientUtils.getResponseBody(urlConnection);

            return new HttpResponse(urlConnection.getResponseCode(), responseBody);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(HttpResponse httpResponse) {

        if (httpResponse == null || httpResponse.getStatus() >= 400) {
            if (errorCallback != null) {
                errorCallback.accept(httpResponse);
            }
        }
        else {
            if (callback != null) {
                callback.accept(httpResponse);
            }
        }

    }

}
