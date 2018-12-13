package voldemort.writter.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "tag")
public class Tag {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String name;

    @NonNull
    private boolean enabled = true;

    @NonNull
    private Date created;

    @NonNull
    private Date modified;

    public Tag(@NonNull String name, @NonNull boolean enabled, @NonNull Date created, @NonNull Date modified) {
        this.name = name;
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
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
