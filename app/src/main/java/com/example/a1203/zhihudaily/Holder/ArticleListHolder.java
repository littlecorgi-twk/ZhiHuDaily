package com.example.a1203.zhihudaily.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1203.zhihudaily.Listern.OnArticleItemClickListener;
import com.example.a1203.zhihudaily.R;

/**
 * @author littlecorgi
 * @Date 2018-11-09 17:37
 * @email a1203991686@126.com
 */
public class ArticleListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView articleTitle;

    public ImageView articleImage;

    public OnArticleItemClickListener itemClickListener;

    public ArticleListHolder(View itemView) {
        super(itemView);
        articleTitle = itemView.findViewById(R.id.article_title);
        articleImage = itemView.findViewById(R.id.article_image);
        articleTitle.setOnClickListener(this);
        articleImage.setOnClickListener(this);
    }

    public void setItemClickListener(OnArticleItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClickListener(getAdapterPosition());
        }
    }
}
