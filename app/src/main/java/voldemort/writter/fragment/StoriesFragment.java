package voldemort.writter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import org.w3c.dom.Text;

import voldemort.writter.R;

public class StoriesFragment extends Fragment {

    private TextView asdf;
    private ScrollView display;
    private RatingBar ratingBar;
    public int count;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stories, container, false);

//        asdf = view.findViewById(R.id.test_text);
//        asdf.setText("WTF 123");
        asdf = view.findViewById(R.id.test_text);
        asdf.setText("WTF 123");
        count = 0;
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        addListenerOnRatingBar();

        return view;
    }

    public void setText(String text) {

        asdf.setText(text);

    }
    public String getText() {
        return asdf.getText().toString();
    }


    public void addListenerOnRatingBar() {

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                Log.d("yas", ""+count++ );

            }
        });
    }

}
