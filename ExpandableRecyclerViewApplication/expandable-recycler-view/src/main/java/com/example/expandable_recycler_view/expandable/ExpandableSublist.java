package com.example.expandable_recycler_view.expandable;

import java.util.List;

/**
 * Created by Nelson Cheung on 03/03/2017.
 */
public class ExpandableSublist<T> {

    String mTitle;
    List<T> mChildList;
    boolean mExpanded;

    public ExpandableSublist(List<T> list, String title) {
        mTitle = title;
        mChildList = list;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public List<T> getChildList() {
        return mChildList;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public Object getChild(int index) {
        if (index == 0)
            return this;
        return getChildList().get(index -1);
    }

    public void setChildList(List<T> mChildList) {
        this.mChildList = mChildList;
    }

    public int getItemCount() {
        if (mChildList != null && isExpanded())
            return mChildList.size() + 1;
        return 1;
    }

    public int getAbsoluteItemCount() {
        if (mChildList != null)
            return mChildList.size() + 1;
        return 1;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean mExpanded) {
        this.mExpanded = mExpanded;
    }
}


