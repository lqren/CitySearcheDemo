package com.project.citysearchedemo.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.project.citysearchedemo.Data;
import com.project.citysearchedemo.R;
import com.project.citysearchedemo.adapter.MainAdapter;
import com.project.citysearchedemo.bean.CitiesBean;
import com.project.citysearchedemo.bean.Entity;
import com.project.citysearchedemo.view.SideLetterBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private SideLetterBar mSideLetterBar;
    private TextView mTvHint;
    private TextView tvFlowIndex;
    private RecyclerView mRv;
    private MainAdapter mCitiesAdapter;
    private View vFlow;
    private CitiesBean mCitiesBean;
    private LinearLayoutManager layoutManager;
    private List<Entity> mList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
        initFlowIndex();
    }

    private void initData() {
        Gson gson = new Gson();
        mCitiesBean = gson.fromJson(Data.citiesJson, CitiesBean.class);
        List<CitiesBean.DatasBean> datas = mCitiesBean.getDatas();
        //重新组装数据
        for (int i = 0; i < 2; i++) {

            Entity currrentCity = null;
            if(i == 0){
                currrentCity = new Entity("", "当前城市");
                currrentCity.setIndex(true);
            }else{
                currrentCity = new Entity("成都","当前城市");
                currrentCity.setIndex(false);
            }
            mList.add(currrentCity);
        }

        for (int i = 0; i < datas.size(); i++) {
            List<CitiesBean.DatasBean.AddressListBean> addressList = datas.get(i).getAddressList();
            Entity indexEntity = new Entity("", datas.get(i).getAlifName());
            indexEntity.setIndex(true);
            mList.add(indexEntity);
            for (int j = 0; j < addressList.size(); j++) {
                Entity entity = new Entity(addressList.get(j).getName(), datas.get(i).getAlifName());
                entity.setIndex(false);
                mList.add(entity);
            }
        }
    }

    private void initEvent() {
        mSideLetterBar.setOnTouchListeber(new SideLetterBar.OnTouchListeber() {
            @Override
            public void onTouch(String letter) {
                mTvHint.setVisibility(View.VISIBLE);
                mTvHint.setText(letter);
                for (int i = 0; i < mList.size(); i++) {
                    String firstWord = mList.get(i).getFirstWord();
                    if (letter.equals(firstWord)) {
                        // 滚动列表到指定的位置
                        layoutManager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }

            @Override
            public void dismiss() {
                mTvHint.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initView() {
        mSideLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mRv = (RecyclerView) findViewById(R.id.search_result_ry);
        layoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(layoutManager);

        mCitiesAdapter = new MainAdapter(this, mList);
        mRv.setAdapter(mCitiesAdapter);
        mTvHint = (TextView)findViewById(R.id.tv_hint);
    }

    /**
     * 初始化顶部悬浮标签
     */
    private void initFlowIndex() {
        vFlow = findViewById(R.id.ll_index);
        tvFlowIndex = (TextView) findViewById(R.id.tv_index);
        mRv.addOnScrollListener(new mScrollListener());
        //设置首项的索引字母
        if (mList.size() > 0) {
            tvFlowIndex.setText(mList.get(0).getFirstWord());
            vFlow.setVisibility(View.VISIBLE);
        }
    }

    class mScrollListener extends RecyclerView.OnScrollListener {

        private int mFlowHeight;
        private int mCurrentPosition = -1;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            mFlowHeight = vFlow.getMeasuredHeight();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            View view = layoutManager.findViewByPosition(firstVisibleItemPosition + 1);

            if (view != null) {
                if (view.getTop() <= mFlowHeight && isItem(firstVisibleItemPosition + 1)) {
                    vFlow.setY(view.getTop() - mFlowHeight);
                } else {
                    vFlow.setY(0);
                }
            }

            if (mCurrentPosition != firstVisibleItemPosition) {
                mCurrentPosition = firstVisibleItemPosition;
                tvFlowIndex.setText(mList.get(mCurrentPosition).getFirstWord());
            }
        }

        /**
         * @param position 对应项的下标
         * @return 是否为标签项
         */
        private boolean isItem(int position) {
            return mCitiesAdapter.getItemViewType(position) == MainAdapter.VIEW_INDEX;
        }
    }
}
