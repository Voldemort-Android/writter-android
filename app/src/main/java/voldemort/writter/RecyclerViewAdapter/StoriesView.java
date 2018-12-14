package voldemort.writter.RecyclerViewAdapter;
import voldemort.writter.data.model.Story;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class StoriesView extends RecyclerView.Adapter<StoriesView.StoriesHolder>{

    public List<Story> mStories;

    public class StoriesHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView text;

        public StoriesHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        void bind(final int listIndex) {
            title.setText("Title:\t"+mStories.get(listIndex).getTitle());
            author.setText("Description:\t"+mStories.get(listIndex).getAuthor());
            text.setText("Date:\t"+mStories.get(listIndex).getText());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String url = mArticles.get(listIndex).getUrl();
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    mContext.startActivity(intent);
//                }
//            });
        }
    }

}
