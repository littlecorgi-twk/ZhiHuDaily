package com.example.a1203.zhihudaily.listener;

/**
 * @author littlecorgi
 * @Date 2018-11-04 15:43
 * @email a1203991686@126.com
 */
public interface OnArticleItemClickListener {

    /**
     * 文章点击回调
     * @param position 被点击文章在RecyclerView中的位置
     */
    void onItemClickListener(int position);

}
