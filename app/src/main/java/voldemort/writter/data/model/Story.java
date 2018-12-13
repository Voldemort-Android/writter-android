package voldemort.writter.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

@Entity(tableName = "story")
public class Story {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String title;

    @NonNull
    private String text;

    @NonNull
    private List<User> authors;

    @NonNull
    private List<Tag> tags;

    @NonNull
    private int points = 0;

    @NonNull
    private int views = 0;

    @NonNull
    private boolean enabled = true;

    @NonNull
    private Date created;

    @NonNull
    private Date modified;

    public Story(@NonNull String title, @NonNull String text, @NonNull List<User> authors, @NonNull List<Tag> tags, @NonNull int points, @NonNull int views, @NonNull boolean enabled, @NonNull Date created, @NonNull Date modified) {
        this.title = title;
        this.text = text;
        this.authors = authors;
        this.tags = tags;
        this.points = points;
        this.views = views;
        this.enabled = enabled;
        this.created = created;
        this.modified = modified;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public List<User> getAuthors() {
        return authors;
    }

    public void setAuthors(@NonNull List<User> authors) {
        this.authors = authors;
    }

    @NonNull
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(@NonNull List<Tag> tags) {
        this.tags = tags;
    }

    @NonNull
    public int getPoints() {
        return points;
    }

    public void setPoints(@NonNull int points) {
        this.points = points;
    }

    @NonNull
    public int getViews() {
        return views;
    }

    public void setViews(@NonNull int views) {
        this.views = views;
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
