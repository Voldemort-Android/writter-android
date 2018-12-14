package voldemort.writter.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import voldemort.writter.R;
import voldemort.writter.activity.ViewStoryActivity;
import voldemort.writter.data.model.Story;

public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Story story;

    private TextView title;
    private TextView username;
    private TextView views;

    public StoryHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        username = itemView.findViewById(R.id.username);
        views = itemView.findViewById(R.id.views);
    }

    public void bind(Story story) {
        this.story = story;

        title.setText(story.getTitle());
        views.setText(story.getViews() + "");
        String usernameText = story.getAuthor().getUsername();
        usernameText += " • ";
        usernameText += story.getCreated();
        username.setText(usernameText);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ViewStoryActivity.class);
        intent.putExtra("id", String.valueOf(story.getId()));
        context.startActivity(intent);
    }

}