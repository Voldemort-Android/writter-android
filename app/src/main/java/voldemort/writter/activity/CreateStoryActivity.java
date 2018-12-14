package voldemort.writter.activity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import voldemort.writter.R;
import voldemort.writter.data.model.Story;
import voldemort.writter.fragment.EditStoryFragment;
import voldemort.writter.fragment.StoriesFragment;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.http.client.HttpResponse;

public class CreateStoryActivity extends AppCompatActivity{

    private boolean publishing = false;

    private EditStoryFragment mEditStoryFragment;
    private Button mPublishButton;
    private View mProgressView;
    private View mCreateStoryFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_navigate_before_black);
        actionbar.setTitle(R.string.activity_my_stories_title);

        mPublishButton = findViewById(R.id.create_story_button);
        mPublishButton.setOnClickListener((view) -> attemptPublish());

        mCreateStoryFormView = findViewById(R.id.create_story_form);
        mProgressView = findViewById(R.id.progress_spinner);

        mEditStoryFragment = (EditStoryFragment) getSupportFragmentManager().findFragmentById(R.id.edit_story_fragment);
    }

    private void attemptPublish() {
        if (publishing) {
            return;
        }

        Story newStory = mEditStoryFragment.getDataForPublish();
        if (newStory != null) {
            publishing = true;
            StoryHttpService.createStory(newStory, this::onPublishSuccess, this::onPublishError);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mCreateStoryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mCreateStoryFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCreateStoryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCreateStoryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }

    public void onPublishSuccess(Story story) {
        publishing = false;

        // Display success message.
        String successMessage = getResources().getString(R.string.message_publish_success);

        Intent intent = new Intent(CreateStoryActivity.this, MyStoriesActivity.class);

        // Kill activity stack so that the user cannot go back using the back button.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_SHORT);
    }

    public void onPublishError(HttpResponse httpResponse) {
        publishing = false;
        //showProgress(false);d

        if (httpResponse != null) {
            Toast.makeText(getApplicationContext(), httpResponse.getResponseBody(), Toast.LENGTH_SHORT).show();
        }
        else {
            String failureMessage = getResources().getString(R.string.message_publish_failed);
            Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_SHORT).show();
        }

    }

}
