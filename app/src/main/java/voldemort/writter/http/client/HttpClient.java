package voldemort.writter.http.client;

import android.os.AsyncTask;

import java.util.Map;
import java.util.function.Consumer;

public final class HttpClient {

    private HttpClient() {

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Consumer<HttpResponse> callback) {

            return Get(url, null, callback, null);

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Consumer<HttpResponse> callback,
                                                          Consumer<HttpResponse> errorCallback) {

        return Get(url, null, callback, errorCallback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Map<String, String> headers,
                                                          Consumer<HttpResponse> callback) {

        return Get(url, headers, callback, null);

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Map<String, String> headers,
                                                          Consumer<HttpResponse> callback,
                                                          Consumer<HttpResponse> errorCallback) {

        HttpGetRequest getRequest = new HttpGetRequest(url, headers, callback, errorCallback);
        return getRequest.execute();
    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Consumer<HttpResponse> callback) {

        return Post(url, body, null, callback, null);

    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Consumer<HttpResponse> callback,
                                                           Consumer<HttpResponse> errorCallback) {

        return Post(url, body, null, callback, errorCallback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Map<String, String> headers,
                                                           Consumer<HttpResponse> callback) {

        return Post(url, body, headers, callback, null);

    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Map<String, String> headers,
                                                           Consumer<HttpResponse> callback,
                                                           Consumer<HttpResponse> errorCallback) {

        HttpPostRequest postRequest = new HttpPostRequest(url, body, headers, callback, errorCallback);
        return postRequest.execute();
    }

}
