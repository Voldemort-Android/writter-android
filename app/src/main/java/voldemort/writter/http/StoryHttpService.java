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

    public static void createStory(Story story, Consumer<Story> callback) {
        createStory(story, callback, null);
    }

    public static void createStory(Story story, Consumer<Story> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Put(
                STORY_ENDPOINT,
                story,
                (res) -> handleStoryResponse(res, callback),
                errorCallback
        );
    }

    public static void getStory(long storyId, Consumer<Story> callback) {
        getStory(storyId, callback, null);
    }

    public static void getStory(long storyId, Consumer<Story> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT + "/" + storyId,
                (res) -> handleStoryResponse(res, callback),
                errorCallback
        );
    }

    public static void getStoriesForUser(Consumer<List<Story>> callback) {
        getStoriesForUser(callback, null);
    }

    public static void getStoriesForUser(Consumer<List<Story>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT + "/user",
                (res) -> handleStoriesResponse(res, callback),
                errorCallback
        );
    }

    public static void getStoriesByUser(Long userId, Consumer<List<Story>> callback) {
        getStoriesByUser(userId, callback, null);
    }

    public static void getStoriesByUser(Long userId, Consumer<List<Story>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT + "/user/" + userId,
                (res) -> handleStoriesResponse(res, callback),
                errorCallback
        );
    }

    public static void getRecommendedStories(Consumer<List<Story>> callback) {
        getRecommendedStories(callback, null);
    }

    public static void getRecommendedStories(Consumer<List<Story>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT + "/recommended",
                (res) -> handleStoriesResponse(res, callback),
                errorCallback
        );
    }

    public static void getRecommendedStories(Long userId, Consumer<List<Story>> callback) {
        getRecommendedStories(userId, callback, null);
    }

    public static void getRecommendedStories(Long userId, Consumer<List<Story>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT + "/recommended/" + userId,
                (res) -> handleStoriesResponse(res, callback),
                errorCallback
        );
    }

    public static void getAllStories(Consumer<List<Story>> callback) {
        getAllStories( callback, null);
    }

    public static void getAllStories(Consumer<List<Story>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT,
                (res) -> handleStoriesResponse(res, callback),
                errorCallback
        );
    }

    public static void getPaginatedStories(int page, int limit, Consumer<List<Story>> callback) {
        getPaginatedStories(page, limit, callback, null);
    }

    public static void getPaginatedStories(int page, int limit, Consumer<List<Story>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                STORY_ENDPOINT + "/page/" + page + "/limit/" + limit,
                (res) -> handleStoriesResponse(res, callback),
                errorCallback
        );
    }

    public static void updateStory(Story story, Consumer<Story> callback) {
        updateStory(story, callback, null);
    }

    public static void updateStory(Story story, Consumer<Story> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Post(
                STORY_ENDPOINT,
                story,
                (res) -> handleStoryResponse(res, callback),
                errorCallback
        );
    }

//    public static void incrementViews(Long storyId, Consumer callback, Consumer<HttpResponse> errorCallback) {
//        AuthHttpClient.Post(
//                STORY_ENDPOINT + "/update-views/" + storyId,
//                (res)
//        )
//    }

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = WritterApplication.getMapper();
        }
        return mapper;
    }

    private static void handleStoryResponse(HttpResponse res, Consumer<Story> callback) {
        try {
            Story story = getMapper().readValue(res.getResponseBody(), Story.class);
            callback.accept(story);
        }
        catch (IOException e) {
            onJsonParseError();
        }
    }

    private static void handleStoriesResponse(HttpResponse res, Consumer<List<Story>> callback) {
        try {
            List<Story> stories = getMapper().readValue(res.getResponseBody(), new TypeReference<List<Story>>(){});
            callback.accept(stories);
        }
        catch (IOException e) {
            onJsonParseError();
        }
    }

    private static void onJsonParseError() {
        Toast.makeText(WritterApplication.getAppContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
    }


}
