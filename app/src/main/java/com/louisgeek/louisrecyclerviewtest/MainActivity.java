package com.louisgeek.louisrecyclerviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }


    public List<String> getData(){
        List<String> dataList=new ArrayList<>();
        for (int i = 0; i <10; i++) {
            dataList.add("str"+i);
        }
        return  dataList;
    }


}
