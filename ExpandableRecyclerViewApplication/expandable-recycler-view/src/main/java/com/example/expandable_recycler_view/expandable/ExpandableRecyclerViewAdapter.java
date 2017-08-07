package com.example.expandable_recycler_view.expandable;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.expandable_recycler_view.recyclerview.RecyclerViewItemClickListener;


/**
 * Created by Nelson Cheung on 02/24/2017.
 */

public abstract class ExpandableRecyclerViewAdapter<GVH extends ExpandableRecyclerViewAdapter.SublistViewHolder, CVH extends ExpandableRecyclerViewAdapter.ItemViewHolder>
        extends RecyclerView.Adapter {

    private static final String TAG = ExpandableRecyclerViewAdapter.class.getSimpleName();
    private static final int SUBLIST = 0;
    private static final int ITEM = 1;

    protected ExpandableList mDataList;

    protected Context mContext;

    private int lastPosition = -1;

    private RecyclerView mRecyclerView;

    private RecyclerViewItemClickListener.OnItemClickListener mOnGroupItemClickListener;

    private RecyclerViewItemClickListener.OnItemClickListener mOnChildItemClickListener;

    public ExpandableRecyclerViewAdapter(Context context, ExpandableList list) {
        mDataList = list;
        mContext = context;
    }


    protected abstract RecyclerView.ViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract RecyclerView.ViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindItemViewHolder(CVH holder, int position);

    protected abstract void onBindSublistViewHolder(GVH holder, int position);

    protected abstract void onCollapseGroup(View view);

    protected abstract void onExpandGroup(View view);

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(mContext, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (mDataList.get(position) instanceof ExpandableSublist) {
                    if (getOnGroupItemClickListener() != null)
                        getOnGroupItemClickListener().onItemClick(view, position);
                    ExpandableSublist expandableSublist = mDataList.getSublistByPosition(position);
                    mDataList.setExpandOrCollapse(expandableSublist);
                    if (mDataList.getSublistByPosition(position).isExpanded()) {
                        notifyItemRangeInserted(mDataList.getSublistPosition(expandableSublist) + 1, expandableSublist.getAbsoluteItemCount() - 1);
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position + 1, (view.getHeight() + view.getPaddingTop() / 2));
                    } else
                        notifyItemRangeRemoved(mDataList.getSublistPosition(expandableSublist) + 1, expandableSublist.getAbsoluteItemCount() - 1);

                    if (expandableSublist.isExpanded()) {
                        onExpandGroup(view);
                    } else {
                        onCollapseGroup(view);
                    }

                } else {
                    if (getOnChildItemClickListener() != null) {
                        getOnChildItemClickListener().onItemClick(view, position);
                    }
                }
            }


        }));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SUBLIST:
                return onCreateGroupViewHolder(parent, viewType);
            case ITEM:
                return onCreateChildViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case SUBLIST:
                onBindSublistViewHolder((GVH) holder, position);
                break;
            case ITEM:
                onBindItemViewHolder((CVH) holder, position);
                break;
        }
        setSlideInAnimation(holder.itemView);
    }

    public RecyclerViewItemClickListener.OnItemClickListener getOnGroupItemClickListener() {
        return mOnGroupItemClickListener;
    }

    public void setOnGroupItemClickListener(RecyclerViewItemClickListener.OnItemClickListener mOnGroupItemClickListener) {
        this.mOnGroupItemClickListener = mOnGroupItemClickListener;
    }

    public RecyclerViewItemClickListener.OnItemClickListener getOnChildItemClickListener() {
        return mOnChildItemClickListener;
    }

    public void setOnChildItemClickListener(RecyclerViewItemClickListener.OnItemClickListener mOnChildItemClickListener) {
        this.mOnChildItemClickListener = mOnChildItemClickListener;
    }

    private void setSlideInAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (mDataList.get(position) instanceof ExpandableSublist) ? SUBLIST : ITEM;
    }


    public class SublistViewHolder extends RecyclerView.ViewHolder {
        public SublistViewHolder(View itemView) {
            super(itemView);
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }


}