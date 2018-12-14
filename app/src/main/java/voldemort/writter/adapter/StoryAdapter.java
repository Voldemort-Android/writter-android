package voldemort.writter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import voldemort.writter.data.model.Story;

public class StoryAdapter extends RecyclerView.Adapter{

    Context mContext;
    List<Story> mStories;

    StoryAdapter(Context context, List<Story> stories) {
        this.mContext = context;
        this.mStories = stories;
    }

    @Override
    public StoryAdapter.StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

//        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
//        StoryHolder viewHolder = new StoryHolder(view);
//        return viewHolder;
        return null;
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
        TextView name;
        TextView url;

        public StoryHolder(View itemView) {
            super(itemView);
//            name = (TextView) itemView.findViewById(R.id.name);
//            url = (TextView) itemView.findViewById(R.id.url);
        }

        void bind(final int listIndex) {
//            name.setText(mStories.get(listIndex).getName());
//            url.setText(mStories.get(listIndex).getUrl());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            String urlString = mRepos.get(getAdapterPosition()).getUrl();
//            Intent intent = new Intent(mContext, WebActivity.class);
//            intent.putExtra("urlString", urlString);
//            mContext.startActivity(intent);
        }
    }


}
