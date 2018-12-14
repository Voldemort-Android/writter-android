package voldemort.writter.http.client;

import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import voldemort.writter.WritterApplication;
import voldemort.writter.utils.TokenUtils;

/**
 * Contains a set of static functions that make asynchronous HTTP calls.
 * The authorization token is automatically added to each request (if available).
 * Only use this class if authentication is required for the request.
 */
public final class AuthHttpClient {

    private AuthHttpClient() {

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Consumer<HttpResponse> callback) {

        return HttpClient.Get(url, addAuthorizationHeader(null), callback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Consumer<HttpResponse> callback,
                                                          Consumer<HttpResponse> errorCallback) {

        return HttpClient.Get(url, addAuthorizationHeader(null), callback, errorCallback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Map<String, String> headers,
                                                          Consumer<HttpResponse> callback) {

        return HttpClient.Get(url, addAuthorizationHeader(headers), callback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Get(String url,
                                                          Map<String, String> headers,
                                                          Consumer<HttpResponse> callback,
                                                          Consumer<HttpResponse> errorCallback) {

        return HttpClient.Get(url, addAuthorizationHeader(headers), callback, errorCallback);
    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Consumer<HttpResponse> callback) {

        return HttpClient.Post(url, body, addAuthorizationHeader(null), callback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Consumer<HttpResponse> callback,
                                                           Consumer<HttpResponse> errorCallback) {

        return HttpClient.Post(url, body, addAuthorizationHeader(null), callback, errorCallback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Map<String, String> headers,
                                                           Consumer<HttpResponse> callback) {

        return HttpClient.Post(url, body, addAuthorizationHeader(headers), callback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Post(String url,
                                                           Object body,
                                                           Map<String, String> headers,
                                                           Consumer<HttpResponse> callback,
                                                           Consumer<HttpResponse> errorCallback) {

        return HttpClient.Post(url, body, addAuthorizationHeader(headers), callback, errorCallback);
    }

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                          Object body,
                                                          Consumer<HttpResponse> callback) {

        return HttpClient.Put(url, body, addAuthorizationHeader(null), callback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                          Object body,
                                                          Consumer<HttpResponse> callback,
                                                          Consumer<HttpResponse> errorCallback) {

        return HttpClient.Put(url, body, addAuthorizationHeader(null), callback, errorCallback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                          Object body,
                                                          Map<String, String> headers,
                                                          Consumer<HttpResponse> callback) {

        return HttpClient.Put(url, body, addAuthorizationHeader(headers), callback);

    }

    public static AsyncTask<Void, Void, HttpResponse> Put(String url,
                                                          Object body,
                                                          Map<String, String> headers,
                                                          Consumer<HttpResponse> callback,
                                                          Consumer<HttpResponse> errorCallback) {

        return HttpClient.Put(url, body, addAuthorizationHeader(headers), callback, errorCallback);
    }

    private static Map<String, String> addAuthorizationHeader(Map<String, String> headers) {
        String token = TokenUtils.getToken();
        if (token != null) {
            if (headers == null) {
                headers = new HashMap<>();
            }
            headers.put("Authorization", token);
        }
        return headers;
    }


}
