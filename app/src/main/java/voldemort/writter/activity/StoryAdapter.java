package voldemort.writter.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
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
            title = (TextView) itemView.findViewById(R.id.name);
            username = (TextView) itemView.findViewById(R.id.url);
        }

        void bind(final int listIndex) {
            title.setText(mStories.get(listIndex).getTitle());
            username.setText(mStories.get(listIndex).getAuthor().getUsername());
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
