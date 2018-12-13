package voldemort.writter.http.client;

import android.os.AsyncTask;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Contains a set of static functions that make asynchronous HTTP calls.
 */
public final class HttpClient {

    // We probably won't need most of the overloads in here, but I included them just in case.

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

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                           Object body,
                                                           Consumer<HttpResponse> callback) {

        return Put(url, body, null, callback, null);

    }

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                           Object body,
                                                           Consumer<HttpResponse> callback,
                                                           Consumer<HttpResponse> errorCallback) {

        return Put(url, body, null, callback, errorCallback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                           Object body,
                                                           Map<String, String> headers,
                                                           Consumer<HttpResponse> callback) {

        return Put(url, body, headers, callback, null);

    }

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                           Object body,
                                                           Map<String, String> headers,
                                                           Consumer<HttpResponse> callback,
                                                           Consumer<HttpResponse> errorCallback) {

        HttpPutRequest putRequest = new HttpPutRequest(url, body, headers, callback, errorCallback);
        return putRequest.execute();
    }

}
