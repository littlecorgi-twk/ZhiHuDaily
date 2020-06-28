package com.example.a1203.zhihudaily.net;

import android.text.TextUtils;
import android.util.Log;

import com.example.a1203.zhihudaily.activity.MainActivity;
import com.example.a1203.zhihudaily.bean.ArticleBefore;
import com.example.a1203.zhihudaily.bean.ArticleContent;
import com.example.a1203.zhihudaily.bean.ArticleLatest;
import com.example.a1203.zhihudaily.listener.OnLoadArticleContentListener;
import com.example.a1203.zhihudaily.listener.OnLoadBeforeArticleListener;
import com.example.a1203.zhihudaily.listener.OnLoadLatestArticleListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author littlecorgi
 */
public class RetrofitUtil {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    private static ArticleService service = retrofit.create(ArticleService.class);


    /**
     * 获取最新文章
     *
     * @param listener 加载监听器
     */
    public static void getLatestArticleList(final OnLoadLatestArticleListener listener) {
        Call<ArticleLatest> call = service.getLatestArticles();
        call.enqueue(new Callback<ArticleLatest>() {
            @Override
            public void onResponse(Call<ArticleLatest> call, Response<ArticleLatest> response) {
                Log.d("23424", "onResponse: " + response.body().toString());
                ArticleLatest articleLatest = response.body();

                if (articleLatest != null && articleLatest.getStories() != null && articleLatest.getStories().size() > 0
                        && articleLatest.getTop_stories() != null && articleLatest.getTop_stories().size() > 0) {
                    if (listener != null) {
                        listener.onSuccess(articleLatest);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleLatest> call, Throwable t) {
                Log.d("23424", "onFailure: ");
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }

    /**
     * 获取过去的文章
     *
     * @param date     传入需要的时间
     * @param listener 加载监听器
     */
    public static void getBeforeArticleList(final String date, final OnLoadBeforeArticleListener listener) {
        Call<ArticleBefore> call = service.getBeforeArticles(date);
        call.enqueue(new Callback<ArticleBefore>() {
            @Override
            public void onResponse(Call<ArticleBefore> call, Response<ArticleBefore> response) {
                ArticleBefore articleBefore = response.body();
                if (articleBefore != null && articleBefore.getStories() != null && articleBefore.getStories().size() > 0) {
                    if (listener != null) {
                        listener.onSuccess(articleBefore);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleBefore> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }

    /**
     * 获取文章内容
     *
     * @param id       文章id
     * @param listener 加载监听器
     */
    public static void getArticleContent(int id, final OnLoadArticleContentListener listener) {
        Call<ArticleContent> call = service.getArticleDetail(id);
        call.enqueue(new Callback<ArticleContent>() {
            @Override
            public void onResponse(Call<ArticleContent> call, Response<ArticleContent> response) {
                ArticleContent content = response.body();
                if (content != null && !TextUtils.isEmpty(content.getBody())) {
                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/src.css\" type=\"text/css\">";
                    String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    html = html.replace("<img class=\"content-image\"", "<img height=\"auto\"; width=\"100%\"");
                    html = html.replace("http:", "https:");
                    content.setBody(html);
                    if (listener != null) {
                        listener.onSuccess(content);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleContent> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }
}
