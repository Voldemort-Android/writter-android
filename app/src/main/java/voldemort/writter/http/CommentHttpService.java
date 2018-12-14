package voldemort.writter.http;

import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import voldemort.writter.WritterApplication;
import voldemort.writter.data.model.Comment;
import voldemort.writter.http.client.AuthHttpClient;
import voldemort.writter.http.client.HttpResponse;

public final class CommentHttpService {

    private static final String COMMENT_ENDPOINT = HttpEndpoints.WRITTER_SERVER_API + "/comment";

    private volatile static ObjectMapper mapper;

    private CommentHttpService() {

    }

    public static void addComment(Comment comment, Consumer<Comment> callback) {
        addComment(comment, callback, null);
    }

    public static void addComment(Comment comment, Consumer<Comment> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Put(
                COMMENT_ENDPOINT,
                comment,
                (res) -> handleCommentResponse(res, callback),
                errorCallback
        );
    }

    public static void getComment(Long commentId, Consumer<Comment> callback) {
        getComment(commentId, callback, null);
    }

    public static void getComment(Long commentId, Consumer<Comment> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                COMMENT_ENDPOINT + "/" + commentId,
                (res) -> handleCommentResponse(res, callback),
                errorCallback
        );
    }

    public static void getCommentsByStory(Long storyId, Consumer<List<Comment>> callback) {
        getCommentsByStory(storyId, callback, null);
    }

    public static void getCommentsByStory(Long storyId, Consumer<List<Comment>> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Get(
                COMMENT_ENDPOINT + "/story/" + storyId,
                (res) -> handleCommentsResponse(res, callback),
                errorCallback
        );
    }

    public static void updateComment(Comment comment, Consumer<Comment> callback) {
        updateComment(comment, callback, null);
    }

    public static void updateComment(Comment comment, Consumer<Comment> callback, Consumer<HttpResponse> errorCallback) {
        AuthHttpClient.Post(
                COMMENT_ENDPOINT,
                comment,
                (res) -> handleCommentResponse(res, callback),
                errorCallback
        );
    }

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = WritterApplication.getMapper();
        }
        return mapper;
    }

    private static void handleCommentResponse(HttpResponse res, Consumer<Comment> callback) {
        try {
            Comment comment = getMapper().readValue(res.getResponseBody(), Comment.class);
            callback.accept(comment);
        }
        catch (IOException e) {
            onJsonParseError();
        }
    }

    private static void handleCommentsResponse(HttpResponse res, Consumer<List<Comment>> callback) {
        try {
            List<Comment> comments = getMapper().readValue(res.getResponseBody(), new TypeReference<List<Comment>>(){});
            callback.accept(comments);
        }
        catch (IOException e) {
            onJsonParseError();
        }
    }

    private static void onJsonParseError() {
        Toast.makeText(WritterApplication.getAppContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
    }

}
