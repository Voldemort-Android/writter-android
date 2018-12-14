package voldemort.writter.activity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import voldemort.writter.R;
import voldemort.writter.data.model.Story;
import voldemort.writter.http.HttpEndpoints;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.http.client.HttpClient;
import voldemort.writter.http.client.HttpResponse;
import voldemort.writter.utils.TokenUtils;

public class CreateStoryActivity extends AppCompatActivity{
    private AsyncTask<Void, Void, HttpResponse> mAuthTask = null;

    private EditText mTitleView;
    private EditText mTextView;
    private Button mPublishButton;
    private View mProgressView;
    private View mCreateStoryFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTitle(R.string.create_story_welcome);
        setContentView(R.layout.create_story);

        // Set up the login form.
        mTitleView = findViewById(R.id.new_story_title);

        mTextView = findViewById(R.id.new_story_text);
//        mTextView.setOnEditorActionListener((textView, id, keyEvent) -> {
//            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                attemptPublish();
//                return true;
//            }
//            return false;
//        });

        mPublishButton = findViewById(R.id.create_story_button);
        mPublishButton.setOnClickListener((view) -> attemptPublish());

        mCreateStoryFormView = findViewById(R.id.create_story_form);
        mProgressView = findViewById(R.id.progress_spinner);
    }

    private void attemptPublish() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mTitleView.setError(null);
        mTextView.setError(null);

        // Store values at the time of the login attempt.
        String title = mTitleView.getText().toString();
        String text = mTextView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid title, if the user entered one.
        if (TextUtils.isEmpty(title)) {
            mTitleView.setError("Not Valid");
            focusView = mTitleView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(text)) {
            mTextView.setError(getString(R.string.activity_login_error_field_required));
            focusView = mTextView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don'HttpPostRequest attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);


            Story newStory = new Story();
            newStory.setTitle(title);
            newStory.setText(text);

//            Map<String, String> credentials = new HashMap<>();
//            credentials.put("title", title);
//            credentials.put("text", text);

//            Log.d("HELLO", "Attempting publish: " + credentials);

//            mAuthTask = HttpClient.Post(
//                    HttpEndpoints.WRITTER_SERVER_API + "/story",
//                    newStory,
//                    this::onPublishSuccess,
//                    this::onPublishError
//            );

            StoryHttpService.createStory(newStory, this::onPublishSuccess,
                    this::onPublishError );

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
        Log.d("title", story.getTitle());
        Log.d("text", story.getText());
        mAuthTask = null;
        Intent intent = new Intent(CreateStoryActivity.this, MainActivity.class);
        // Kill activity stack so that the user cannot go back using the back button.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onPublishError(HttpResponse httpResponse) {
        Log.d("CHECKTHIS", httpResponse.getResponseBody());
        mAuthTask = null;
        showProgress(false);

        if (httpResponse != null) {
            if (httpResponse.getStatus() == 401) {
                mTextView.setError(httpResponse.getResponseBody());
                mTextView.requestFocus();
            }
            else {
                Toast.makeText(getApplicationContext(), httpResponse.getResponseBody(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "An unknown error has occurred", Toast.LENGTH_SHORT).show();
        }

    }

}
