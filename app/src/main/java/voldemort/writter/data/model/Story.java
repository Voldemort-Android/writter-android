package voldemort.writter.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private User author;

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

    public Story() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
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
    public User getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull User author) {
        this.author = author;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return id == story.id &&
                points == story.points &&
                views == story.views &&
                enabled == story.enabled &&
                Objects.equals(title, story.title) &&
                Objects.equals(text, story.text) &&
                Objects.equals(author, story.author) &&
                Objects.equals(created, story.created) &&
                Objects.equals(modified, story.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, author, points, views, enabled, created, modified);
    }
}
