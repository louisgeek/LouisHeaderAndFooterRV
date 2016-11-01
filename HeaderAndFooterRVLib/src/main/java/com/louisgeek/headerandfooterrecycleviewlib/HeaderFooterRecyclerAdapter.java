package com.louisgeek.headerandfooterrecycleviewlib;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by louisgeek on 2016/9/12.
 */
public abstract class HeaderFooterRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int mList_Item_LayoutID=R.layout.list_item;
    private int mList_Item_LayoutID_4_horizontal=R.layout.list_item_4_horizontal;
    private int mHeader_LayoutID=R.layout.list_header_layout;
    private int mHeader_LayoutID_4_horizontal=R.layout.list_header_layout_4_horizontal;
    private int mFooter_LayoutID=R.layout.list_footer_layout;
    private int mFooter_LayoutID_4_horizontal=R.layout.list_footer_layout_4_horizontal;

    private int  mNowItemLayoutID;
    private int mNowHeaderLayoutID;
    private int mNowFooterLayoutID;


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NORMAL = 2;
    private static final String TAG = "HeaderRecyclerAdapter";
    protected List<T> mDataList;
    private Context mContext;

    public HeaderFooterRecyclerAdapter(List<T> dataList,RecyclerView recyclerView, int mItemLayoutId) {
        mDataList = dataList;
        mContext=recyclerView.getContext();
        mRecyclerView=recyclerView;

        if (mItemLayoutId==0){
            return;
        }
        mList_Item_LayoutID=mItemLayoutId;
        mList_Item_LayoutID_4_horizontal=mItemLayoutId;
    }

    RecyclerView mRecyclerView;

    private View mHeaderView;
    private View mFooterView;

    public void setHeaderView(int headerViewLayoutID) {
        dealHeaderFooterLayoutType();
        headerViewLayoutID=headerViewLayoutID==0?mNowHeaderLayoutID:headerViewLayoutID;
        /**注意  root view 为recycleview*/
        View headerView = LayoutInflater.from(mContext).inflate(headerViewLayoutID,mRecyclerView,false);
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }
    public void setFooterView(int footerViewLayoutID) {
        dealHeaderFooterLayoutType();
        footerViewLayoutID=footerViewLayoutID==0?mNowFooterLayoutID:footerViewLayoutID;
        View footerView = LayoutInflater.from(mContext).inflate(footerViewLayoutID,mRecyclerView,false);
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public View getFooterView() {
        return mFooterView;
    }


    public void refreshDataList(List<T> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }
    public void appendDataList(List<T> dataList) {
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

/*    public void setItemViewLayoutID(int itemViewLayoutID) {
        if (itemViewLayoutID==0){
            return;
        }
        mList_Item_LayoutID=itemViewLayoutID;
    }
    public void setItemViewLayoutID4Horizontal(int itemViewLayoutID4Horizontal) {
        if (itemViewLayoutID4Horizontal==0){
            return;
        }
        mList_Item_LayoutID_4_horizontal=itemViewLayoutID4Horizontal;
    }*/

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
       // Log.i(TAG, "onAttachedToRecyclerView: ");

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
                    return (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
            //
            mNowItemLayoutID  = gridLayoutManager.getOrientation() == GridLayoutManager.HORIZONTAL
                    ? mList_Item_LayoutID_4_horizontal : mList_Item_LayoutID;
            //
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            mNowItemLayoutID = staggeredGridLayoutManager.getOrientation() == StaggeredGridLayoutManager.HORIZONTAL
                    ? mList_Item_LayoutID_4_horizontal : mList_Item_LayoutID;

        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            mNowItemLayoutID = linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL
                    ?mList_Item_LayoutID_4_horizontal : mList_Item_LayoutID;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && mFooterView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        } else if (mHeaderView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            }
            return TYPE_NORMAL;
        } else if (mFooterView != null) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HeaderFooterRecyclerViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new HeaderFooterRecyclerViewHolder(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(mNowItemLayoutID, parent, false);
        return new HeaderFooterRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
     //   Log.i(TAG, "onBindViewHolder: ");
        //
        //is StaggeredGridLayoutManager  config  header FullSpan
        ViewGroup.LayoutParams vg_lp = viewHolder.itemView.getLayoutParams();
        if (vg_lp != null && vg_lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglm_lp = (StaggeredGridLayoutManager.LayoutParams) vg_lp;
            //### sglm_lp.setFullSpan(viewHolder.getLayoutPosition()==0);
            if (getItemViewType(position) == TYPE_HEADER) {
                sglm_lp.setFullSpan(position == 0);
            }
            if (getItemViewType(position) == TYPE_FOOTER) {
                sglm_lp.setFullSpan(position == getItemCount() - 1);
            }

            //
         /*   if (position != 0) {
                if (list_Item_LayoutID == R.layout.list_item) {
                    sglm_lp.height = position % 3 == 0 ? 200 : 400;
                } else if (list_Item_LayoutID == R.layout.list_item_4_horizontal) {
                    sglm_lp.width = position % 3 == 0 ? 150 : 300;
                }
            }*/
        }


        int itemType = getItemViewType(position);
        if (itemType == TYPE_HEADER || itemType == TYPE_FOOTER) {
            return;
        }

       /*### int position = viewHolder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;*/
        int realPos = position;
        if (mHeaderView != null) {
            realPos = position - 1;
        }
       final int pos = realPos;

        final T data = mDataList.get(pos);
        onBindViewHolderInner(viewHolder, pos, data);

       /* if(viewHolder instanceof MyRecyclerViewViewHolder) {
            ((MyRecyclerViewViewHolder) viewHolder).id_tv.setText(data);
           */
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(v,pos, data);
                    }
                }
            });

    }

    public  void dealHeaderFooterLayoutType(){
        /**单独为HORIZONTAL 设置左侧的布局 右侧布局*/
        RecyclerView.LayoutManager layoutManager=mRecyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager staggeredGridLayoutManager= (StaggeredGridLayoutManager) layoutManager;
            mNowHeaderLayoutID=staggeredGridLayoutManager.getOrientation()==StaggeredGridLayoutManager.HORIZONTAL
                    ?mHeader_LayoutID_4_horizontal :mHeader_LayoutID;
            mNowFooterLayoutID=staggeredGridLayoutManager.getOrientation()==StaggeredGridLayoutManager.HORIZONTAL
                    ?mFooter_LayoutID_4_horizontal :mFooter_LayoutID;
        }else if (layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager= (GridLayoutManager) layoutManager;
            mNowHeaderLayoutID=gridLayoutManager.getOrientation()==GridLayoutManager.HORIZONTAL
                    ?mHeader_LayoutID_4_horizontal :mHeader_LayoutID;
            mNowFooterLayoutID=gridLayoutManager.getOrientation()==GridLayoutManager.HORIZONTAL
                    ?mFooter_LayoutID_4_horizontal :mFooter_LayoutID;
        }else if (layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager= (LinearLayoutManager) layoutManager;
            mNowHeaderLayoutID=linearLayoutManager.getOrientation()==LinearLayoutManager.HORIZONTAL
                    ?mHeader_LayoutID_4_horizontal :mHeader_LayoutID;
            mNowFooterLayoutID=linearLayoutManager.getOrientation()==LinearLayoutManager.HORIZONTAL
                    ?mFooter_LayoutID_4_horizontal :mFooter_LayoutID;
        }
    }

    public abstract void onBindViewHolderInner(RecyclerView.ViewHolder viewHolder, int realPosition, T data);

    @Override
    public int getItemCount() {
        if (mHeaderView != null && mFooterView != null) {
            return mDataList.size() + 1 + 1;
        } else if (mHeaderView != null) {
            return mDataList.size() + 1;
        } else if (mFooterView != null) {
            return mDataList.size() + 1;
        }
        return mDataList.size();
    }

/*    class MyRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView id_tv;

        public MyRecyclerViewViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView || itemView == mFooterView) {
                return;
            }
          //  id_tv = (TextView) itemView.findViewById(R.id.id_tv);
        }
    }*/

    public  interface OnItemClickListener {
        void onItemClick(View itemView,int position, Object data);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
