package voldemort.writter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import voldemort.writter.R;
import voldemort.writter.data.model.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryAdapter  extends RecyclerView.Adapter<StoryAdapter.StoryHolder>{

    private Context mContext;

    private final List<Story> mStories;

    public StoryAdapter(Context context, List<Story> stories){
        this.mContext = context;
        this.mStories = stories;
    }

    @Override
    public StoryAdapter.StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.story_item, parent, shouldAttachToParentImmediately);
        StoryHolder viewHolder = new StoryHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StoryAdapter.StoryHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView username;
        private TextView views;

        public StoryHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            username = itemView.findViewById(R.id.username);
            views = itemView.findViewById(R.id.views);
        }

        private void bind(final int listIndex) {
            Story story = mStories.get(listIndex);
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
            Intent intent = new Intent(mContext, StoryActivity.class);
            intent.putExtra("Title", mStories.get(getAdapterPosition()).getTitle());
            intent.putExtra("Author", mStories.get(getAdapterPosition()).getAuthor().getFirstName());
            intent.putExtra("Id", mStories.get(getAdapterPosition()).getId() + "");
            Log.d("check title", intent.getStringExtra("Title"));
            Log.d("check author", intent.getStringExtra("Author"));
            Log.d("check id", intent.getStringExtra("Id"));

            mContext.startActivity(intent);
        }
    }
}
