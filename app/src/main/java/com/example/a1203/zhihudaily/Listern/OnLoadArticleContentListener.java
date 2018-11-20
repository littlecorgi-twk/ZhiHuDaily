package com.example.a1203.zhihudaily.Listern;

import com.example.a1203.zhihudaily.Bean.ArticleContent;

/**
 * @author littlecorgi
 * @Date 2018-11-09 17:27
 * @email a1203991686@126.com
 */
public interface OnLoadArticleContentListener {

    void onSuccess(ArticleContent content);

    void onFailure();
}
