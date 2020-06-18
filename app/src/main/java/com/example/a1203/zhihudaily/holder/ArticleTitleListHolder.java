package com.example.a1203.zhihudaily.holder;

import android.view.View;
import android.widget.TextView;

import com.example.a1203.zhihudaily.listener.OnArticleItemClickListener;
import com.example.a1203.zhihudaily.R;

/**
 * @author littlecorgi
 * @Date 2018-11-28 21:14
 * @email a1203991686@126.com
 */
public class ArticleTitleListHolder extends ArticleListHolder{

    public TextView articleListItemTitle;

    public ArticleTitleListHolder(View itemView) {
        super(itemView);
        articleListItemTitle = itemView.findViewById(R.id.article_list_item_title);
    }

    @Override
    public void setItemClickListener(OnArticleItemClickListener itemClickListener) {
        super.setItemClickListener(itemClickListener);
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClickListener(getAdapterPosition());
        }
    }
}
