package voldemort.writter.data.model;

import java.util.List;
import java.util.Date;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "comment")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String text;

    @NonNull
    private User user;

    @NonNull
    private Story story;

    @ColumnInfo(name = "nested_comments")
    private List<Comment> nestedComments;

    @NonNull
    private int points = 0;

    @NonNull
    private boolean enabled = true;

    @NonNull
    private Date created;

    @NonNull
    private Date modified;

    public Comment(@NonNull String text, @NonNull User user, @NonNull Story story, List<Comment> nestedComments, @NonNull int points, @NonNull boolean enabled, @NonNull Date created, @NonNull Date modified) {
        this.text = text;
        this.user = user;
        this.story = story;
        this.nestedComments = nestedComments;
        this.points = points;
        this.enabled = enabled;
        this.created = created;
        this.modified = modified;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    @NonNull
    public Story getStory() {
        return story;
    }

    public void setStory(@NonNull Story story) {
        this.story = story;
    }

    public List<Comment> getNestedComments() {
        return nestedComments;
    }

    public void setNestedComments(List<Comment> nestedComments) {
        this.nestedComments = nestedComments;
    }

    @NonNull
    public int getPoints() {
        return points;
    }

    public void setPoints(@NonNull int points) {
        this.points = points;
    }

    @NonNull
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(@NonNull boolean enabled) {
        this.enabled = enabled;
    }

    @NonNull
    public Date getCreated() {
        return created;
    }

    public void setCreated(@NonNull Date created) {
        this.created = created;
    }

    @NonNull
    public Date getModified() {
        return modified;
    }

    public void setModified(@NonNull Date modified) {
        this.modified = modified;
    }
}
