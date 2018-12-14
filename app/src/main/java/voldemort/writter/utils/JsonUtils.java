package voldemort.writter.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import voldemort.writter.data.model.Story;

public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<Stroy> parseNews(String jsonString) {
        ArrayList<Story> result = new ArrayList<>();
        try {
            JsonNode jsonNode = mapper.readTree(jsonString);
            JsonNode articles = jsonNode.get("text");
            if (articles != null && articles.isArray()) {
                for (JsonNode article : articles) {
                    Story storyItem = mapper.treeToValue(article, Story.class);
                    result.add(storyItem);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
