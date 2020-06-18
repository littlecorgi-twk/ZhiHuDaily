package com.example.a1203.zhihudaily.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

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
    public static boolean isNotNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (connectivityManager != null) {
                    Network nw = connectivityManager.getActiveNetwork();
                    NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
                    if (actNw != null) {
                        if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return false;
                        } else if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            return false;
                        } else {
                            return !actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                NetworkInfo mNetworkInfo = null;
                if (connectivityManager != null) {
                    mNetworkInfo = connectivityManager.getActiveNetworkInfo();
                }
                if (mNetworkInfo != null) {
                    return !mNetworkInfo.isConnected();
                }
            }
        }
        return true;
    }
}
