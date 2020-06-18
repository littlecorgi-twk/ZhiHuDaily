package com.example.a1203.zhihudaily.net;

import com.example.a1203.zhihudaily.bean.ArticleBefore;
import com.example.a1203.zhihudaily.bean.ArticleContent;
import com.example.a1203.zhihudaily.bean.ArticleLatest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author littlecorgi
 */
public interface ArticleService {

    /**
     * 获取今天的文章列表
     *
     * @return 今天的文章列表
     */
    @GET("latest")
    Call<ArticleLatest> getLatestArticles();

    /**
     * 根据日期获取当天文章列表
     *
     * @param date 日期
     * @return 当前文章列表
     */
    @GET("before/{date}")
    Call<ArticleBefore> getBeforeArticles(@Path("date") String date);

    /**
     * 根据id获取全文
     *
     * @param id 文章id
     * @return 文章内容
     */
    @GET("{id}")
    Call<ArticleContent> getArticleDetail(@Path("id") int id);
}
