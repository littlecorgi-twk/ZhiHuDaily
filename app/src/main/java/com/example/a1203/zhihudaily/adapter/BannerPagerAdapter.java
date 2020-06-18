package com.example.a1203.zhihudaily.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a1203.zhihudaily.listener.OnBannerClickListener;

import java.util.List;

/**
 * @author littlecorgi
 * @Date 2018-11-08 20:40
 * @email a1203991686@126.com
 */
public class BannerPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViews;

    private OnBannerClickListener onBannerClickListener;

    public BannerPagerAdapter(List<ImageView> imageViews){
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView1 = imageViews.get(position);
        container.addView(imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerClickListener != null){
                    onBannerClickListener.onClick(position);
                }
            }
        });
        return imageView1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((View) object));
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }
}
