package com.example.a1203.zhihudaily.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author littlecorgi
 * @Date 2018-11-08 22:17
 * @email a1203991686@126.com
 */
public class HttpUtil {

    /**
     * 判断当前网络状态
     *
     * @param context ${Context}
     * @return 有网络返回true
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
