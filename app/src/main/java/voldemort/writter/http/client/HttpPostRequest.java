package voldemort.writter.http.client;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;

public class HttpPostRequest extends AsyncTask<Void, Void, HttpResponse> {

    private final int requestTimeout = 4000;

    private final ObjectMapper mapper = new ObjectMapper();

    private String url;

    private Map<String, String> headers;

    private Object body;

    private Consumer<HttpResponse> callback;

    private Consumer<HttpResponse> errorCallback;

    protected HttpPostRequest(String url,
                              Object body,
                              Map<String, String> headers,
                              Consumer<HttpResponse> callback,
                              Consumer<HttpResponse> errorCallback) {

        this.url = url;
        this.body = body;
        this.headers = headers;
        this.callback = callback;
        this.errorCallback = errorCallback;
    }

    @Override
    protected HttpResponse doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;

        try {

            // Create a HTTP connection object from the URL.
            urlConnection = (HttpURLConnection) new URL(url).openConnection();

            // Add headers if needed.
            if (headers != null) {
                HttpClientUtils.setHeaders(urlConnection, headers);
            }

            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(requestTimeout);

            // Add a request body for the POST request.
            // This will also automatically open the connection.
            if (body != null) {
                addRequestBody(urlConnection, body);
            }

            // If there is no body, then manually open the connection.
            else {
                urlConnection.connect();
            }

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

    /**
     * Adds request body data to the HTTP URL connection for POST requests.
     * @param urlConnection
     * @param data
     */
    private void addRequestBody(@NonNull HttpURLConnection urlConnection, Object data) throws IOException {
        if (data == null) {
            return;
        }

        String requestBody;
        if (data instanceof String) {
            urlConnection.setRequestProperty("Content-Type", "text/plain");
            requestBody = (String) data;
        }
        else {
            urlConnection.setRequestProperty("Content-Type", "application/json");
            try {
                requestBody = mapper.writeValueAsString(data);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
                requestBody = "{}"; // Bad way to handle this
            }
        }

        OutputStream outputStream = urlConnection.getOutputStream();
        outputStream.write(requestBody.getBytes());
    }

}
