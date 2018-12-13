package voldemort.writter.data.model;

import java.util.List;
import java.util.Date;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "rating")
public class Rating {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private User user;

    @NonNull
    private Story story;

    @NonNull
    private Double rating;

    @NonNull
    private boolean enabled = true;

    @NonNull
    private Date created;

    @NonNull
    private Date modified;

    public Rating(@NonNull User user, @NonNull Story story, @NonNull Double rating, @NonNull boolean enabled, @NonNull Date created, @NonNull Date modified) {
        this.user = user;
        this.story = story;
        this.rating = rating;
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

    @NonNull
    public Double getRating() {
        return rating;
    }

    public void setRating(@NonNull Double rating) {
        this.rating = rating;
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
