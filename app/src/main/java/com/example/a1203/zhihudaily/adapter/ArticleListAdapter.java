package com.example.a1203.zhihudaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a1203.zhihudaily.R;
import com.example.a1203.zhihudaily.activity.ArticleContentActivity;
import com.example.a1203.zhihudaily.bean.ArticleLatest;
import com.example.a1203.zhihudaily.bean.Stories;
import com.example.a1203.zhihudaily.bean.TopStories;
import com.example.a1203.zhihudaily.holder.ArticleListFooterHolder;
import com.example.a1203.zhihudaily.holder.ArticleListHolder;
import com.example.a1203.zhihudaily.holder.ArticleListTopHolder;
import com.example.a1203.zhihudaily.holder.ArticleTitleListHolder;
import com.example.a1203.zhihudaily.listener.OnArticleItemClickListener;
import com.example.a1203.zhihudaily.listener.OnLoadTopArticleListener;
import com.example.a1203.zhihudaily.listener.OnSlideToTheBottomListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author littlecorgi
 * @Date 2018-11-04 15:03
 * @email a1203991686@126.com
 */
public class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_TOP = 0;
    private final int TYPE_ARTICLE = 1;
    private final int TYPE_FOOTER = 2;
    private final int TYPE_ARTICLE_TITLE = 3;

    private List<Stories> storiesList;

    private LayoutInflater inflater;

    private Context context;

    public OnLoadTopArticleListener loadTopArticleListener;

    private OnSlideToTheBottomListener slideListener;

    private OnArticleItemClickListener clickListener;

    private ArticleListTopHolder articleListTopHolder;

    public ArticleListAdapter(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(context);
        storiesList = new ArrayList<>();
        //文章列表点击事件监听
        clickListener = new OnArticleItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                int id = storiesList.get(position - 1).getId();
                Intent intent = new Intent(context, ArticleContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        };
        //加载banner文章事件监听
        loadTopArticleListener = new OnLoadTopArticleListener() {
            @Override
            public void onSuccess(List<TopStories> topStoriesList) {
                if (articleListTopHolder != null) {
                    articleListTopHolder.banner.update(topStoriesList);
                    articleListTopHolder.banner.startPlay();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure() {

            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        if (position == 1) {
            return TYPE_ARTICLE_TITLE;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        int currentDate = storiesList.get(position).getDate();
        int prevIndex = position - 1;
        boolean isDifferent = storiesList.get(prevIndex).getDate() != currentDate;
        return isDifferent ? TYPE_ARTICLE_TITLE : TYPE_ARTICLE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ARTICLE) {
            view = inflater.inflate(R.layout.article_list_item, parent, false);
            return new ArticleListHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = inflater.inflate(R.layout.footer, parent, false);
            return new ArticleListFooterHolder(view);
        } else if (viewType == TYPE_ARTICLE_TITLE) {
            view = inflater.inflate(R.layout.article_list_item_title, parent, false);
            return new ArticleTitleListHolder(view);
        }
        view = inflater.inflate(R.layout.banner_layout, parent, false);
        return new ArticleListTopHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ARTICLE:
                ArticleListHolder articleListHolder = ((ArticleListHolder) holder);
                Glide.with(context).load(storiesList.get(position - 1).getImages().get(0)).into(articleListHolder.articleImage);
                articleListHolder.articleTitle.setText(storiesList.get(position - 1).getTitle());
                articleListHolder.setItemClickListener(clickListener);
                break;
            case TYPE_FOOTER:
                if (slideListener != null && storiesList != null && storiesList.size() > 0) {
                    slideListener.onSlideToTheBottom();
                }
                break;
            case TYPE_TOP:
                articleListTopHolder = (ArticleListTopHolder) holder;
                break;
            case TYPE_ARTICLE_TITLE:
                ArticleTitleListHolder articleTitleListHolder = ((ArticleTitleListHolder) holder);
                Glide.with(context).load(storiesList.get(position - 1).getImages().get(0)).into(articleTitleListHolder.articleImage);
                articleTitleListHolder.articleTitle.setText(storiesList.get(position - 1).getTitle());
                articleTitleListHolder.articleListItemTitle.setText(friendlyDate(storiesList.get(position).getDate()));
                articleTitleListHolder.setItemClickListener(clickListener);
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return storiesList.size() + 1;
    }

    /**
     * 当刷新文章列表时使用
     */
    public void setData(ArticleLatest articleLatest) {
        storiesList.clear();
        storiesList.addAll(articleLatest.getStories());
    }

    /**
     * 当加载下一页文章内容时使用
     */
    public void addData(List<Stories> storiesList) {
        this.storiesList.addAll(storiesList);
    }

    public void setSlideToTheBottomListener(OnSlideToTheBottomListener slideListener) {
        this.slideListener = slideListener;
    }

    /**
     * 日期友好转换
     *
     * @param date 文章日期
     * @return 友好的日期显示方式
     */
    public static String friendlyDate(int date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        String start = sdf.format(cal.getTime());
        int nowDate = Integer.parseInt(start);

        if (date == nowDate) {
            return "今日";
        } else if (nowDate - date == 1) {
            return "昨日";
        } else if (nowDate - date == 2) {
            return "前日";
        } else {
            Date date1 = null;
            try {
                date1 = sdf.parse(date + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            if (date1 != null) {
                calendar.setTime(date1);
                return new SimpleDateFormat("MM月dd日 E", Locale.getDefault()).format(date1);
            } else {
                return "";
            }
        }
    }
}
