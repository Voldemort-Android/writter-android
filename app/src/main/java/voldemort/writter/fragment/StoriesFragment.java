package voldemort.writter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import voldemort.writter.R;
import voldemort.writter.activity.StoryAdapter;
import voldemort.writter.data.model.Story;


public class StoriesFragment extends Fragment {

    private final List<Story> mStories = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private StoryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stories, container, false);

        mRecyclerView = view.findViewById(R.id.story_recyclerview);
        mAdapter = new StoryAdapter(view.getContext(), mStories);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    public void onLoadStories(List<Story> stories) {
        mStories.clear();
        mStories.addAll(stories);
        mAdapter.notifyDataSetChanged();
    }


}
