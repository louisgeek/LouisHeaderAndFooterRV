package com.louisgeek.headerandfooterrecycleviewlib;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by louisgeek on 2016/9/12.
 */
public class HeaderFooterRecyclerViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViewSparseArray = new SparseArray<>();
    private View mItemView;

    public HeaderFooterRecyclerViewHolder(View itemView) {
        super(itemView);
        mItemView=itemView;
    }

    public <T extends View> T onBindView(int viewId) {
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;

    }
}
