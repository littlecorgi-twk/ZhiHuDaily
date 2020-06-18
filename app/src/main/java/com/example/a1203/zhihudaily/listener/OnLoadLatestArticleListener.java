package com.example.a1203.zhihudaily.listener;

import com.example.a1203.zhihudaily.bean.ArticleLatest;

/**
 * @author littlecorgi
 * @Date 2018-11-07 19:51
 * @email a1203991686@126.com
 */
public interface OnLoadLatestArticleListener {

    void   onSuccess(ArticleLatest articleLatest);

    void onFailure();
}
