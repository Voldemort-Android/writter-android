package voldemort.writter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Objects;

import voldemort.writter.R;
import voldemort.writter.data.model.Story;
import voldemort.writter.data.model.User;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.utils.TokenUtils;

public class ViewStoryActivity extends AppCompatActivity {

    private Story mStory;

    private TextView mTitleView;
    private TextView mAuthor;
    private TextView mTextView;
    private RatingBar mRatingBar;
    private FloatingActionButton mEditStoryFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_story);

        mTitleView = findViewById(R.id.title_of_story);
        mAuthor = findViewById(R.id.author_of_story);
        mTextView = findViewById(R.id.text_of_story);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        mEditStoryFab = findViewById(R.id.edit_story_fab);
        mEditStoryFab.setOnClickListener((view) -> {
            Intent intent = new Intent(this, EditStoryActivity.class);
            intent.putExtra("id", String.valueOf(mStory.getId()));
            startActivity(intent);
        });

        addListenerOnRatingBar();

        Long storyId = Long.valueOf(getIntent().getStringExtra("id"));
        loadStory(storyId);
    }

    public void addListenerOnRatingBar() {

        Intent intent = getIntent();

        if (intent.getStringExtra("rating") != null) {
            int rank = Integer.parseInt(intent.getStringExtra("rating"));
            mRatingBar.setRating(rank);
        }

        mRatingBar = (RatingBar) findViewById(R.id.ratingBar2);
//        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {


                //Meathod to change the rating

            }
        });
    }

    private void loadStory(long id) {
        StoryHttpService.incrementViews(id, () -> {
            StoryHttpService.getStory(id, this::onStoryLoaded);
        });
    }

    private void onStoryLoaded(Story story) {
        mStory = story;
        mTitleView.setText(story.getTitle());
        mTextView.setText(story.getText());

        User author = story.getAuthor();
        mAuthor.setText(author.getUsername() + " - " + author.getFirstName() + " " + author.getLastName());

        // Enable the edit button if its the current user's own story.
        User user = TokenUtils.getCurrentUser();
        if (user != null && Objects.equals(user.getId(), author.getId())) {
            mEditStoryFab.show();
        }
        else {
            mEditStoryFab.hide();
        }
    }

}
