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
import voldemort.writter.holder.RecyclerViewButtonHolder;
import voldemort.writter.holder.StoryHolder;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Story> mStories;

    private Context mContext;

    private Runnable previousButtonCallback;

    private Runnable nextButtonCallback;

    public StoryAdapter(Context context, List<Story> stories){
        mContext = context;
        mStories = stories;
    }

    public void showPreviousButton(Runnable callback) {
        boolean notify = previousButtonCallback == null;
        previousButtonCallback = callback;
        if (notify) {
            notifyItemInserted(0);
        }
    }

    public void hidePreviousButton() {
        boolean notify = previousButtonCallback != null;
        previousButtonCallback = null;
        if (notify) {
            notifyItemRemoved(0);
        }
    }

    public void showNextButton(Runnable callback) {
        boolean notify = nextButtonCallback == null;
        nextButtonCallback = callback;
        if (notify) {
            notifyItemInserted(mStories.size() + (previousButtonCallback != null ? 1 : 0));
        }
    }

    public void hideNextButton() {
        boolean notify = nextButtonCallback != null;
        nextButtonCallback = null;
        if (notify) {
            notifyItemRemoved(mStories.size() + (previousButtonCallback != null ? 1 : 0));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int listIndex = position - (previousButtonCallback != null ? 1 : 0);
        if (listIndex < 0 || listIndex >= mStories.size()) {
            return R.layout.recycler_view_button;
        }
        return R.layout.story_item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        if (viewType == R.layout.story_item) {
            View view = inflater.inflate(R.layout.story_item, parent, shouldAttachToParentImmediately);
            return new StoryHolder(view);
        }
        else {
            View view = inflater.inflate(R.layout.recycler_view_button, parent, shouldAttachToParentImmediately);
            return new RecyclerViewButtonHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int listIndex = position - (previousButtonCallback != null ? 1 : 0);
        if (holder instanceof StoryHolder) {
            ((StoryHolder) holder).bind(mStories.get(listIndex));
        }
        else if (holder instanceof RecyclerViewButtonHolder) {
            if (listIndex < 0) {
                String label = mContext.getResources().getString(R.string.action_load_previous_stories);
                ((RecyclerViewButtonHolder) holder).bind(label, previousButtonCallback);
            }
            else {
                String label = mContext.getResources().getString(R.string.action_load_next_stories);
                ((RecyclerViewButtonHolder) holder).bind(label, nextButtonCallback);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStories.size() +
                (previousButtonCallback != null ? 1 : 0) +
                (nextButtonCallback != null ? 1 : 0);
    }


}
