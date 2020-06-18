package com.example.a1203.zhihudaily.listener;

import com.example.a1203.zhihudaily.bean.ArticleContent;

/**
 * @author littlecorgi
 * @Date 2018-11-09 17:27
 * @email a1203991686@126.com
 */
public interface OnLoadArticleContentListener {

    void onSuccess(ArticleContent content);

    void onFailure();
}
