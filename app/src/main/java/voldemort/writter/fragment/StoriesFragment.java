package voldemort.writter.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

    private ProgressBar mProgressView;
    private View mResultsContainer;
    private TextView mNoStoriesMessage;
    private RecyclerView mRecyclerView;
    private StoryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stories, container, false);

        mProgressView = view.findViewById(R.id.progress_spinner);
        mResultsContainer = view.findViewById(R.id.story_results_container);
        mNoStoriesMessage = view.findViewById(R.id.message_no_stories);

        mRecyclerView = view.findViewById(R.id.story_recyclerview);
        mAdapter = new StoryAdapter(view.getContext(), mStories);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    public void onLoadStories(List<Story> stories) {
        mStories.clear();
        if (stories.isEmpty()) {
            mNoStoriesMessage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else {
            mNoStoriesMessage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mStories.addAll(stories);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setNoResultsMessage(String message) {
        mNoStoriesMessage.setText(message);
    }

    public void showPreviousButton(Runnable callback) {
        mAdapter.showPreviousButton(callback);
    }

    public void hidePreviousButton() {
        mAdapter.hidePreviousButton();
    }

    public void showNextButton(Runnable callback) {
        mAdapter.showNextButton(callback);
    }

    public void hideNextButton() {
        mAdapter.hideNextButton();
    }

    /**
     * Shows the progress UI and hides the login form.
     * Auto-generated by Android Studio.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mResultsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
            mResultsContainer.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mResultsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mResultsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }


}
