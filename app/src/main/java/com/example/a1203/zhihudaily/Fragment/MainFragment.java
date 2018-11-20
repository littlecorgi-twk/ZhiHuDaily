package com.example.a1203.zhihudaily.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1203.zhihudaily.Adapter.ArticleListAdapter;
import com.example.a1203.zhihudaily.Bean.ArticleBefore;
import com.example.a1203.zhihudaily.Bean.ArticleLatest;
import com.example.a1203.zhihudaily.Bean.TopStories;
import com.example.a1203.zhihudaily.Listern.OnLoadBeforeArticleListener;
import com.example.a1203.zhihudaily.Listern.OnLoadLatestArticleListener;
import com.example.a1203.zhihudaily.Listern.OnSlideToTheBottomListener;
import com.example.a1203.zhihudaily.Net.HttpUtil;
import com.example.a1203.zhihudaily.R;
import com.example.a1203.zhihudaily.SpaceItemDecoration;

import java.util.List;

/**
 * @author littlecorgi
 * @Date 2018-11-07 19:46
 * @email a1203991686@126.com
 */
public class MainFragment extends BaseFragment{

    // 文章列表
    private RecyclerView recyclerView;

    private FloatingActionButton floatingActionButton;

    private ArticleListAdapter adapter;

    private OnLoadLatestArticleListener latestListener;

    private OnLoadBeforeArticleListener beforeListener;

    private boolean flag;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_article_list, container, false);
        recyclerView = view.findViewById(R.id.articleList);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    protected void initData() {
        adapter = new ArticleListAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        // 加载最新文章事件监听
        latestListener = new OnLoadLatestArticleListener() {
            @Override
            public void onSuccess(ArticleLatest articleLatest) {
                adapter.setData(articleLatest);
                getRootActivity().setDate(articleLatest.getDate());
                List<TopStories> topStoriesList = articleLatest.getTop_stories();
                if (adapter.loadTopArticleListener != null){
                    adapter.loadTopArticleListener.onSuccess(topStoriesList);
                }
                stopRefresh();
                if (!flag){
                    flag = true;
                } else {
                    hint(recyclerView, "已经是最新的文章啦", Color.parseColor("#0099CC"));
                }
                // 价值啊最新文章成功后再在后台加载下一页
                getBeforeArticleList();
            }

            @Override
            public void onFailure() {
                if (mActivity != null){
                    hint(recyclerView, "好奇怪，文章加载不出来", Color.parseColor("#0099CC"));
                }
                stopRefresh();
            }
        };
        // 加载过去文章事件监听
        beforeListener = new OnLoadBeforeArticleListener() {
            @Override
            public void onSuccess(ArticleBefore articleBefore) {
                adapter.addData(articleBefore.getStories());
                adapter.notifyDataSetChanged();
                getRootActivity().setDate(articleBefore.getDate());
            }

            @Override
            public void onFailure() {
                if (mActivity != null){
                    hint(recyclerView, "好奇怪，文章加载不出来", Color.parseColor("#0099CC"));
                }
            }
        };

        // 滑到底部事件监听
        OnSlideToTheBottomListener slideToTheBottomListener = new OnSlideToTheBottomListener() {
            @Override
            public void onSlideToTheBottom() {
                getBeforeArticleList();
            }
        };
        adapter.setSlideToTheBottomListener(slideToTheBottomListener);
        getLatestArticleList();
    }

    public void getLatestArticleList(){
        if (!HttpUtil.isNetworkConnected(mActivity)){
            hint(recyclerView, "似乎没有连接网络?", Color.parseColor("#0099CC"));
            stopRefresh();
        }
        HttpUtil.getLatestArticleList(latestListener);
    }

    public void getBeforeArticleList(){
        if (!HttpUtil.isNetworkConnected(mActivity)){
            hint(recyclerView, "似乎没有连接网络?", Color.parseColor("#0099CC"));
        }
        HttpUtil.getBeforeArticleList(getRootActivity().getDate(), beforeListener);
    }

    public void stopRefresh(){
        if (getRootActivity() != null){
            getRootActivity().setRefresh(false);
        }
    }
}
