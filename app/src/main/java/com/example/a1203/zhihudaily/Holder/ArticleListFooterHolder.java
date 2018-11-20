package com.example.a1203.zhihudaily.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a1203.zhihudaily.R;

/**
 * @author littlecorgi
 * @Date 2018-11-09 17:38
 * @email a1203991686@126.com
 */
public class ArticleListFooterHolder extends RecyclerView.ViewHolder {
    public TextView footerTitle;

    public ArticleListFooterHolder(View itemView) {
        super(itemView);
        footerTitle = (TextView) itemView.findViewById(R.id.footerTitle);
    }
}
