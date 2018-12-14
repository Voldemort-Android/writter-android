package voldemort.writter.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import voldemort.writter.data.model.Story;
import voldemort.writter.data.model.User;

public class JsonUtils {
    public static ArrayList<Story> parseStories(String JSONString){
        ArrayList<Story> storyList = new ArrayList<>();
        try {
            JSONArray jObject = new JSONArray(JSONString);
            JSONArray items = jObject;

            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                int id = item.getInt("id");
                String title = item.getString("title");
                int points = item.getInt("points");
                int views = item.getInt("views");

                JSONObject authorObj = item.getJSONObject("author");
                int authorId = authorObj.getInt("id");
                String authorFirstName = authorObj.getString("firstName");
                String authorLastName = authorObj.getString("lastName");
                String authorEmail = authorObj.getString("email");
                String authorUsername = authorObj.getString("username");


                User author = new User();
                author.setId(authorId);
                author.setFirstName(authorFirstName);
                author.setLastName(authorLastName);
                author.setEmail(authorEmail);
                author.setUsername(authorUsername);


                Story story = new Story();
                story.setId(id);
                story.setTitle(title);
                story.setPoints(points);
                story.setViews(views);
                story.setAuthor(author);

                storyList.add(story);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return storyList;
    }
}
