package voldemort.writter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import voldemort.writter.R;

public class RecyclerViewButtonHolder extends RecyclerView.ViewHolder {

    private Button mActionButton;

    public RecyclerViewButtonHolder(@NonNull View itemView) {
        super(itemView);
        mActionButton = itemView.findViewById(R.id.action_button);
    }

    public void bind(String label, Runnable onClick) {
        mActionButton.setText(label);
        mActionButton.setOnClickListener(view -> onClick.run());
    }

}
