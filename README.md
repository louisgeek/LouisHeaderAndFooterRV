# LouisHeaderAndFooterRV
HeaderAndFooterRecyclerView  带头部header和尾部footer的封装   通用Adapter ViewHolder


     
		
	//	main code
		//...
	 RecyclerView idrv = (RecyclerView) findViewById(R.id.id_rv);
       // RecyclerViewWithEmpty id_rvwe = (RecyclerViewWithEmpty) findViewById(R.id.id_rvwe);
       // id_rvwe.setEmptyView(null);

        idrv.setLayoutManager(new LinearLayoutManager(this));
       /*idrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
         idrv.setLayoutManager(new GridLayoutManager(this,3));
        idrv.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false));
        idrv.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
       idrv.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));*/

       // MyRecyclerViewAdapter myAdapter= new MyRecyclerViewAdapter(getData());

        MyCommRecyclerViewAdapter myCommRecyclerViewAdapter=new MyCommRecyclerViewAdapter(getData(),idrv,R.layout.personal_item);
        myCommRecyclerViewAdapter.setHeaderView(R.layout.personal_header);
        myCommRecyclerViewAdapter.setFooterView(R.layout.personal_footer);
       /* myCommRecyclerViewAdapter.setHeaderView(LayoutInflater.from(this).inflate(R.layout.personal_header,idrv,false));
        myCommRecyclerViewAdapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.personal_footer,idrv,false));*/
        idrv.setHasFixedSize(true);
        idrv.setItemAnimator(new DefaultItemAnimator());
        idrv.setAdapter(myCommRecyclerViewAdapter);
	//	...


	//Adapter

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


![image](https://raw.githubusercontent.com/louisgeek/LouisHeaderAndFooterRV/master/screenshots/pic1.png)
