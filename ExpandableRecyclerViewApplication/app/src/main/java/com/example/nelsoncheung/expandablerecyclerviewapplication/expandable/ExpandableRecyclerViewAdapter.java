package com.example.nelsoncheung.expandablerecyclerviewapplication.expandable;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.nelsoncheung.expandablerecyclerviewapplication.recyclerview.RecyclerViewItemClickListener;

/**
 * Created by Nelson Cheung on 02/24/2017.
 */

public abstract class ExpandableRecyclerViewAdapter<GVH extends ExpandableRecyclerViewAdapter.GroupViewHolder, CVH extends ExpandableRecyclerViewAdapter.ChildViewHolder>
        extends RecyclerView.Adapter {

    private static final String TAG = ExpandableRecyclerViewAdapter.class.getSimpleName();

    protected ExpandableList mDataList;

    protected Context mContext;

    private int lastPosition = -1;

    private RecyclerView mRecyclerView;

    private RecyclerViewItemClickListener.OnItemClickListener mOnGroupItemClickListener;
    
    private RecyclerViewItemClickListener.OnItemClickListener mOnChildItemClickListener;

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

    public ExpandableRecyclerViewAdapter(Context context, ExpandableList groups) {
        mDataList = groups;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return onCreateGroupViewHolder(parent, viewType);
            case 1:
                return onCreateChildViewHolder(parent, viewType);
        }
        return null;
    }

    protected abstract ViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType);

    protected abstract ViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType);


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
                    ExpandableSublist expandableItem = mDataList.getSublistByPosition(position);
                    mDataList.setExpandOrCollapse(expandableItem);
                    if (mDataList.getSublistByPosition(position).isExpanded()) {
                        notifyItemRangeInserted(mDataList.getSublistPosition(expandableItem) + 1, expandableItem.getAbsoluteItemCount() - 1);
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position + 1, (view.getHeight() + view.getPaddingTop() / 2));
                    } else
                        notifyItemRangeRemoved(mDataList.getSublistPosition(expandableItem) + 1, expandableItem.getAbsoluteItemCount() - 1);

                    if (expandableItem.isExpanded()) {
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

    protected abstract void onCollapseGroup(View view);

    protected abstract void onExpandGroup(View view);


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                onBindGroupViewHolder((GVH) holder, position);
                break;
            case 1:
                onBindChildViewHolder((CVH) holder, position);
                break;
        }
        setSlideInAnimation(holder.itemView);
    }

    protected abstract void onBindChildViewHolder(CVH holder, int position);

    protected abstract void onBindGroupViewHolder(GVH holder, int position);

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
        return (mDataList.get(position) instanceof ExpandableSublist) ? 0 : 1;
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder {
        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }


    public  class ChildViewHolder extends RecyclerView.ViewHolder {
        public ChildViewHolder(View itemView) {
            super(itemView);
        }
    }



}