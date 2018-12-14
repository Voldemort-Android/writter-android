package voldemort.writter.http;

import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import voldemort.writter.WritterApplication;
import voldemort.writter.data.model.Story;
import voldemort.writter.http.client.AuthHttpClient;
import voldemort.writter.http.client.HttpResponse;

public final class StoryHttpService {

    private static final String STORY_ENDPOINT = HttpEndpoints.WRITTER_SERVER_API + "/story";

    private volatile static ObjectMapper mapper;

    private StoryHttpService() {

    }

    public static void getStory(long storyId, Consumer<Story> callback) {
        getStory(storyId, callback, null);
    }

    public static void getStory(long storyId, Consumer<Story> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT + "/" + storyId,
                (res) -> {
                    try {
                        Story story = getMapper().readValue(res.getResponseBody(), Story.class);
                        callback.accept(story);
                    }
                    catch (IOException e) {
                        onJsonParseError();
                    }
                },
                errorCallback
        );
    }

    public static void getPaginatedStories(int page, int limit, Consumer<List<Story>> callback) {
        getPaginatedStories(page, limit, callback, null);
    }

    public static void getPaginatedStories(int page, int limit, Consumer<List<Story>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                HttpEndpoints.WRITTER_SERVER_API + "/story/page/" + page + "/limit/" + limit,
                (res) -> {
                    try {
                        List<Story> stories = getMapper().readValue(res.getResponseBody(), new TypeReference<List<Story>>(){});
                        callback.accept(stories);
                    }
                    catch (IOException e) {
                        onJsonParseError();
                    }
                },
                errorCallback
        );
    }

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = WritterApplication.getMapper();
        }
        return mapper;
    }

    private static void onJsonParseError() {
        Toast.makeText(WritterApplication.getAppContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
    }


}
