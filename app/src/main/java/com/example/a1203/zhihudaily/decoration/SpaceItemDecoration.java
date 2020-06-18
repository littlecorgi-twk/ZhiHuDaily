package com.example.a1203.zhihudaily.decoration;

import android.graphics.Rect;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

/**
 * @author littlecorgi
 * @Date 2018-11-10 20:42
 * @email a1203991686@126.com
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = space;
        }
    }
}
