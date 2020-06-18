package com.example.a1203.zhihudaily.listener;

import com.example.a1203.zhihudaily.bean.TopStories;

import java.util.List;

/**
 * @author littlecorgi
 * @Date 2018-11-04 15:49
 * @email a1203991686@126.com
 */
public interface OnLoadTopArticleListener {

    void onSuccess(List<TopStories> topStoriesList);

    void onFailure();
}
