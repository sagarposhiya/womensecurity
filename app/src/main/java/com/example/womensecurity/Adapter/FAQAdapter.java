package com.example.womensecurity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.womensecurity.R;
import com.example.womensecurity.models.Genre;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class FAQAdapter extends ExpandableRecyclerViewAdapter<FAQAdapter.QuestionViewHolder, FAQAdapter.AnswerViewHolder> {


    public FAQAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public QuestionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public AnswerViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq_ans, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(AnswerViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final String ans = (String) ((Genre) group).getItems().get(childIndex);
        //final String ans = ((Genre) group).getTitle();
        holder.setArtistName(ans);
    }

    @Override
    public void onBindGroupViewHolder(QuestionViewHolder holder, int flatPosition, ExpandableGroup group) {
            holder.setGenreTitle(group);
    }

    public class QuestionViewHolder extends GroupViewHolder {

        private TextView genreTitle;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            genreTitle = itemView.findViewById(R.id.txtFAQ);
        }

        public void setGenreTitle(ExpandableGroup group) {
            genreTitle.setText(group.getTitle());
        }
    }


    public class AnswerViewHolder extends ChildViewHolder {

        private TextView artistName;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.txtFAQ);
        }

        public void setArtistName(String answer) {
            artistName.setText(answer);
        }
    }
}
