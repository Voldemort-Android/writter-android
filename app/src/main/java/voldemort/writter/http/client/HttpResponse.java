package voldemort.writter.http.client;

/**
 * POJO class that stores the response results of a HttpURLConnection.
 */
public class HttpResponse {

    private int status;

    private String responseBody;

    public HttpResponse(int status, String responseBody) {
        this.status = status;
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Status: " + status + "\n" +
                "Response: " + responseBody;
    }
}
