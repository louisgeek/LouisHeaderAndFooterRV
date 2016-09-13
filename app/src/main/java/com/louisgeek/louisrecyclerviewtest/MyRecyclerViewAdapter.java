package com.louisgeek.louisrecyclerviewtest;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/9/11.
 */
@Deprecated
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int list_Item_LayoutID=R.layout.list_item;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;
    private static final String TAG = "MyRecyclerViewAdapter";
    private List<String> mDatas = new ArrayList<>();

    public MyRecyclerViewAdapter(List<String> dataList) {
        mDatas=dataList;
    }
    private View mHeaderView;
    private View mFooterView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }
    public View getFooterView() {
        return mFooterView;
    }


    public void refreshDatas(List<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.i(TAG, "onAttachedToRecyclerView: ");
        //为GridLayoutManager 合并头布局的跨度
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                /**
                 * 抽象方法  返回当前index位置的item所占用的跨度的数量
                 * ##单元格合并  就是相当于占据了设定列spanCount的数量
                 * ##不合并     就是相当于占据了原来1个跨度
                 *
                 * @param position
                 * @return
                 */
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == TYPE_HEADER||getItemViewType(position) ==TYPE_FOOTER) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
            //
            list_Item_LayoutID=gridLayoutManager.getOrientation()==GridLayoutManager.HORIZONTAL
                    ?R.layout.list_item_4_horizontal :list_Item_LayoutID;
            //
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager staggeredGridLayoutManager= (StaggeredGridLayoutManager) layoutManager;
            list_Item_LayoutID=staggeredGridLayoutManager.getOrientation()==StaggeredGridLayoutManager.HORIZONTAL
                        ?R.layout.list_item_4_horizontal :list_Item_LayoutID;

        }else if (layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager= (LinearLayoutManager) layoutManager;
            list_Item_LayoutID=linearLayoutManager.getOrientation()==LinearLayoutManager.HORIZONTAL
                    ?R.layout.list_item_4_horizontal :list_Item_LayoutID;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView != null&&mFooterView!=null) {
            if(position == 0) {
                return TYPE_HEADER;
            }
            if (position==getItemCount()-1){
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }else  if (mHeaderView != null){
            if(position == 0) {
                return TYPE_HEADER;
            }
            return TYPE_NORMAL;
        }else  if (mFooterView!=null){
            if (position==getItemCount()-1){
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyRecyclerViewViewHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER) {
            return new MyRecyclerViewViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(list_Item_LayoutID, parent, false);
        return new MyRecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.i(TAG, "onBindViewHolder: ");
        //
        //is StaggeredGridLayoutManager  config  header FullSpan
            ViewGroup.LayoutParams vg_lp = viewHolder.itemView.getLayoutParams();
            if (vg_lp!=null&&vg_lp instanceof StaggeredGridLayoutManager.LayoutParams){
                StaggeredGridLayoutManager.LayoutParams sglm_lp= (StaggeredGridLayoutManager.LayoutParams) vg_lp;
                //### sglm_lp.setFullSpan(viewHolder.getLayoutPosition()==0);
                if(getItemViewType(position) == TYPE_HEADER){
                    sglm_lp.setFullSpan(position==0);
                }
                if (getItemViewType(position) ==TYPE_FOOTER){
                    sglm_lp.setFullSpan(position==getItemCount()-1);
                }

                //
                if (position!=0){
                if (list_Item_LayoutID==R.layout.list_item){
                    sglm_lp.height=position%3==0?200:400;
                }else if (list_Item_LayoutID==R.layout.list_item_4_horizontal){
                    sglm_lp.width=position%3==0?150:300;
                }}
            }


        int itemType =getItemViewType(position);
        if(itemType == TYPE_HEADER||itemType==TYPE_FOOTER) {
            return;
        }

       /*### int position = viewHolder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;*/
        int realPos=position;
        if (mHeaderView!=null){
            realPos=position - 1;
        }
        final int pos = realPos;
        final String data = mDatas.get(pos);
        if(viewHolder instanceof MyRecyclerViewViewHolder) {
            ((MyRecyclerViewViewHolder) viewHolder).id_tv.setText(data);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(pos, data);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView!=null&&mFooterView!=null){
            return mDatas.size()+1+1;
        }else  if (mHeaderView != null){
            return mDatas.size() + 1;
        }else  if (mFooterView != null){
            return  mDatas.size()+1;
        }
        return  mDatas.size();
    }

    class MyRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView id_tv;

        public MyRecyclerViewViewHolder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView||itemView==mFooterView) {
                return;
            }
            id_tv = (TextView) itemView.findViewById(R.id.id_tv);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, String data);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    //

    /**
     * 利用反射来获取View未显示前的高度
     *
     * @param v
     * @return
     */
    private int getTargetHeight(View v) {
        try {
            Method m = v.getClass().getDeclaredMethod("onMeasure", int.class,
                    int.class);
            m.setAccessible(true);
            m.invoke(v, View.MeasureSpec.makeMeasureSpec(
                    ((View) v.getParent()).getMeasuredWidth(),
                    View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED));
        } catch (Exception e) {

        }
        return v.getMeasuredHeight();
    }
}