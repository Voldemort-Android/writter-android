package voldemort.writter.utils;
import voldemort.writter.data.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Story> parseStories(String JSONString){
        List<Story> storiesObject = new ArrayList<NewsItem>();
        try {
            JSONObject mainJSONObject = new JSONObject(JSONString);
            JSONArray stories = mainJSONObject.getJSONArray("stories");

            for(int i = 0; i < stories.length(); i++){
                JSONObject story = stories.getJSONObject(i);
                storiesObject.add(new Story(
                        story.getInt("id"),
                        story.getString("title"),
                        story.getString("text"),
                        story.get("author"),
                        story.getInt("points"),
                        story.getInt("views"),
                        story.getBoolean("enabled"),
                        story.get("created"),
                        story.get(("modified").toString())
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsObject;
    }
}