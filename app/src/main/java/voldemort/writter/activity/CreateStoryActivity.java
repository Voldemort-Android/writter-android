package voldemort.writter.activity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_navigate_before_black);
        actionbar.setTitle(R.string.activity_create_story_title);

        mPublishButton = findViewById(R.id.create_story_button);
        mPublishButton.setOnClickListener((view) -> attemptPublish());

        mEditStoryFragment = (EditStoryFragment) getSupportFragmentManager().findFragmentById(R.id.edit_story_fragment);
        mEditStoryFragment.setTitle("Romeo and Juliet");
        mEditStoryFragment.setText("Two household both ...");
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

    public void onPublishSuccess(Story story) {
        publishing = false;

        // Display success message.
        String successMessage = getResources().getString(R.string.message_publish_success);

        Intent intent = new Intent(this, MyStoriesActivity.class);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.discard_changes_dialog_title))
                .setMessage(getResources().getString(R.string.discard_changes_dialog_message))
                .setPositiveButton(
                        getResources().getString(R.string.discard_changes_dialog_ok),
                        (dialog, id) -> {
                            super.onBackPressed();
                        }
                )
                .setNegativeButton(
                        getResources().getString(R.string.discard_changes_dialog_no),
                        (dialog, id) -> {
                            // Do nothing.
                        }
                )
                .create()
                .show();

    }

}
