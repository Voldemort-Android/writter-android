package voldemort.writter.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import voldemort.writter.R;
import voldemort.writter.activity.ViewStoryActivity;
import voldemort.writter.data.model.Story;

public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy h:mm a");

    private Story story;

    private TextView mTitleView;
    private TextView mInfoView;
    private TextView mViewCountView;

    public StoryHolder(View itemView) {
        super(itemView);
        mTitleView = itemView.findViewById(R.id.title);
        mInfoView = itemView.findViewById(R.id.info);
        mViewCountView = itemView.findViewById(R.id.views);
    }

    public void bind(Story story) {
        this.story = story;

        mTitleView.setText(story.getTitle());

        mViewCountView.setText(story.getViews() + " Views");

        String usernameText =
                story.getAuthor().getUsername() + " • " +
                DATE_FORMAT.format(story.getCreated());
        mInfoView.setText(usernameText);

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