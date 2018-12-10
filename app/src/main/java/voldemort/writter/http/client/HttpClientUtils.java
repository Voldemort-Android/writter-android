package voldemort.writter.http.client;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public final class HttpClientUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private HttpClientUtils() {

    }

    public static void setHeaders(@NonNull HttpURLConnection urlConnection, @NonNull Map<String, String> headers) {

        for (Entry<String, String> header : headers.entrySet()) {

            // This will overwrite the header value if it already exists.
            urlConnection.setRequestProperty(header.getKey(), header.getValue());
        }

    }

    public static String getResponseBody(@NonNull HttpURLConnection urlConnection) throws IOException {

        // Try to get the input stream from the HTTP connection.
        InputStream inputStream;
        if (urlConnection.getResponseCode() < 400) {
            inputStream = urlConnection.getInputStream();
        }
        else {
            inputStream = urlConnection.getErrorStream();
        }

        if (inputStream != null) {

            // Try to extract response body from the input stream.
            try (Scanner scanner = new Scanner(inputStream)) {
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                }

            }

        }

        return null;
    }

    public static <T> T parseResponseBody(String json, Class<T> targetType) {
        try {
            return MAPPER.readValue(json, targetType);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
