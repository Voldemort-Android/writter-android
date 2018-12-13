package voldemort.writter.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import voldemort.writter.WritterApplication;
import voldemort.writter.data.model.Story;
import voldemort.writter.http.client.AuthHttpClient;

public final class StoryHttpService {

    private volatile static ObjectMapper mapper;

    private StoryHttpService() {
        mapper = WritterApplication.getMapper();
    }

    public static void getStory(long storyId, Consumer<Story> callback) {
        AuthHttpClient.Get(
                HttpEndpoints.WRITTER_SERVER_API + "/" + storyId,
                (res) -> {
                    try {
                        Story story = mapper.readValue(res.getResponseBody(), Story.class);
                        callback.accept(story);
                    }
                    catch (IOException e) {
                        // Call error callback;
                    }
                }
        );
    }

    public static void getPaginatedStories(int page, int limit, Consumer<List<Story>> callback) {
        AuthHttpClient.Get(
                HttpEndpoints.WRITTER_SERVER_API + "/story/page/" + page + "/limit/" + limit,
                (res) -> {
                    //List<Story> stories =
                }
        );
    }


}
