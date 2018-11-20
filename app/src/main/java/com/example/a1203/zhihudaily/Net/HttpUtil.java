package com.example.a1203.zhihudaily.Net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.a1203.zhihudaily.Bean.ArticleBefore;
import com.example.a1203.zhihudaily.Bean.ArticleContent;
import com.example.a1203.zhihudaily.Bean.ArticleLatest;
import com.example.a1203.zhihudaily.Listern.OnLoadArticleContentListener;
import com.example.a1203.zhihudaily.Listern.OnLoadBeforeArticleListener;
import com.example.a1203.zhihudaily.Listern.OnLoadLatestArticleListener;
import com.example.a1203.zhihudaily.Utility.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * @author littlecorgi
 * @Date 2018-11-08 22:17
 * @email a1203991686@126.com
 */
public class HttpUtil {
    public static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setConnectTimeout(1000 * 30);
        client.setTimeout(1000 * 30);
    }

    //判断当前网络状态
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //获取最新文章
    public static void getLatestArticleList(final OnLoadLatestArticleListener listener) {
        client.get(Constant.LatestArticleUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ArticleLatest articleLatest = JSON.parseObject(responseString, ArticleLatest.class);
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
        });
    }

    //获取过去文章
    public static void getBeforeArticleList(final String date, final OnLoadBeforeArticleListener listener) {
        client.get(Constant.BeforeArticleUrl + date, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ArticleBefore articleBefore = JSON.parseObject(responseString, ArticleBefore.class);
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
        });
    }

    //获取文章内容
    public static void getArticleContent(int id, final OnLoadArticleContentListener listener) {
        client.get(Constant.ArticleContentUrl + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ArticleContent content = JSON.parseObject(responseString, ArticleContent.class);
                if (content != null && !TextUtils.isEmpty(content.getBody())) {
                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/src.css\" type=\"text/css\">";
                    String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    html = html.replace("<img class=\"content-image\"", "<img height=\"auto\"; width=\"100%\"");
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
        });
    }
}
