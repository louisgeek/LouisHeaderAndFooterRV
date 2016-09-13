package com.louisgeek.louisrecyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.louisgeek.headerandfooterrecycleviewlib.HeaderFooterRecyclerAdapter;
import com.louisgeek.headerandfooterrecycleviewlib.HeaderFooterRecyclerViewHolder;

import java.util.List;

/**
 * Created by louisgeek on 2016/9/12.
 */
public class MyCommRecyclerViewAdapter extends HeaderFooterRecyclerAdapter<String>{

    public MyCommRecyclerViewAdapter(List<String> dataList, RecyclerView recyclerView, int itemLayoutId) {
        super(dataList, recyclerView,itemLayoutId);
    }

    @Override
    public void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, String data) {

        if(viewHolder instanceof HeaderFooterRecyclerViewHolder) {
            HeaderFooterRecyclerViewHolder headerFooterRecyclerViewHolder= (HeaderFooterRecyclerViewHolder) viewHolder;
           TextView id_tv=headerFooterRecyclerViewHolder.onBindView(R.id.id_tv);
            id_tv.setText(data);
        }
    }


}
