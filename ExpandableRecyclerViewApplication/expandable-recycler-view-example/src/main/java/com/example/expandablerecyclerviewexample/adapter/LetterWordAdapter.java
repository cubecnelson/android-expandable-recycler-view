package com.example.expandablerecyclerviewexample.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expandable_recycler_view.expandable.ExpandableList;
import com.example.expandable_recycler_view.expandable.ExpandableRecyclerViewAdapter;
import com.example.expandablerecyclerviewexample.R;
import com.example.expandablerecyclerviewexample.items.Letter;
import com.example.expandablerecyclerviewexample.items.Word;

/**
 * Created by Nelson.Cheung on 8/7/2017.
 */

public class LetterWordAdapter extends ExpandableRecyclerViewAdapter<LetterWordAdapter.LetterViewHolder, LetterWordAdapter.WordViewHolder> {

    public LetterWordAdapter(Context context, ExpandableList list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_word_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateSublistViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_letter_sublist, parent, false);
        return new LetterViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(WordViewHolder holder, int position) {
        if (mDataList.get(position) instanceof Word) {
            Word item = (Word) mDataList.get(position);
            Log.d(LetterWordAdapter.class.getSimpleName(), item.toString());
            holder.tvWord.setText(item.getWord());
        }
    }

    @Override
    public void onBindSublistViewHolder(LetterViewHolder holder, int position) {
        Letter item = (Letter) mDataList.getSublistByPosition(position);
        Log.d(LetterWordAdapter.class.getSimpleName(), item.toString());
        holder.tvLetter.setText(item.getTitle());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onCollapseGroup(View view) {

    }

    @Override
    protected void onExpandGroup(View view) {

    }

    public class LetterViewHolder extends ExpandableRecyclerViewAdapter.SublistViewHolder {

        TextView tvLetter;

        public LetterViewHolder(View itemView) {
            super(itemView);
            tvLetter = (TextView) itemView.findViewById(R.id.textview_letter);
        }

    }

    public class WordViewHolder extends ExpandableRecyclerViewAdapter.ItemViewHolder {

        TextView tvWord;

        public WordViewHolder(View itemView) {
            super(itemView);
            tvWord = (TextView) itemView.findViewById(R.id.textview_word);
        }
    }
}
