package voldemort.writter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import voldemort.writter.R;
import voldemort.writter.data.model.Story;
import voldemort.writter.fragment.EditStoryFragment;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.http.client.HttpResponse;

public class EditStoryActivity extends AppCompatActivity {

    private Story mStory;
    
    private boolean publishing = false;

    private EditStoryFragment mEditStoryFragment;
    private Button mUpdateButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_navigate_before_black);
        actionbar.setTitle(R.string.activity_create_story_title);

        mUpdateButton = findViewById(R.id.update_story_button);
        mUpdateButton.setOnClickListener((view) -> attemptUpdate());

        mCancelButton = findViewById(R.id.cancel_update_story_button);
        mCancelButton.setOnClickListener((view) -> onBackPressed());

        mEditStoryFragment = (EditStoryFragment) getSupportFragmentManager().findFragmentById(R.id.edit_story_fragment);

        Long storyId = Long.valueOf(getIntent().getStringExtra("id"));
        loadStory(storyId);
    }

    private void loadStory(long id) {
        StoryHttpService.getStory(id, this::onStoryLoaded);
    }

    private void onStoryLoaded(Story story) {
        mStory = story;
        mEditStoryFragment.setTitle(story.getTitle());
        mEditStoryFragment.setText(story.getText());
    }

    private void attemptUpdate() {
        if (publishing) {
            return;
        }

        Story story = mEditStoryFragment.getDataForPublish();
        if (story != null) {
            mStory.setText(story.getText());
            mStory.setTitle(story.getTitle());
            publishing = true;
            StoryHttpService.updateStory(mStory, this::onUpdateSuccess, this::onUpdateError);
        }
    }

    private void onUpdateSuccess(Story story) {
        publishing = false;

        // Display success message.
        String successMessage = getResources().getString(R.string.message_update_success);
        Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_SHORT).show();
    }

    public void onUpdateError(HttpResponse httpResponse) {
        publishing = false;

        if (httpResponse != null) {
            Toast.makeText(getApplicationContext(), httpResponse.getResponseBody(), Toast.LENGTH_SHORT).show();
        }
        else {
            String failureMessage = getResources().getString(R.string.message_update_failed);
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
