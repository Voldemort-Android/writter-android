package voldemort.writter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import voldemort.writter.R;
import voldemort.writter.data.model.Story;

import java.util.ArrayList;

public class StoryAdapter  extends RecyclerView.Adapter<StoryAdapter.StoryHolder>{
    Context mContext;
    ArrayList<Story> mStories;

    public StoryAdapter(Context context, ArrayList<Story> stories){
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
        TextView title;
        TextView username;

        public StoryHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            username = (TextView) itemView.findViewById(R.id.username);
        }

        void bind(final int listIndex) {
            title.setText(mStories.get(listIndex).getTitle());
            String usernameText = mStories.get(listIndex).getAuthor().getUsername();
            usernameText += " • points: ";
            usernameText += mStories.get(listIndex).getPoints();
            usernameText += " • views: ";
            usernameText += mStories.get(listIndex).getViews();
            username.setText(usernameText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            String urlString = mStories.get(getAdapterPosition()).getUrl();
//            Intent intent = new Intent(mContext, WebActivity.class);
//            intent.putExtra("urlString", urlString);
//            mContext.startActivity(intent);
        }
    }
}
