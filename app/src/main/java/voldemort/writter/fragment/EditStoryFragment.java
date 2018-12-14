package voldemort.writter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import voldemort.writter.R;
import voldemort.writter.data.model.Story;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditStoryFragment extends Fragment {

    private EditText mTitleView;
    private EditText mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_story, container, false);

        mTitleView = view.findViewById(R.id.story_title);
        mTextView = view.findViewById(R.id.story_text);

        return view;
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public Story getDataForPublish() {

        String title = mTitleView.getText().toString();
        String text = mTextView.getText().toString();

        if (TextUtils.isEmpty(title)) {
            mTitleView.setError("A title is required");
            mTitleView.requestFocus();
            return null;
        }

        if (TextUtils.isEmpty(text)) {
            mTextView.setError("Story body cannot be empty");
            mTextView.requestFocus();
            return null;
        }

        Story story = new Story();
        story.setTitle(title);
        story.setText(text);
        return story;
    }

}
