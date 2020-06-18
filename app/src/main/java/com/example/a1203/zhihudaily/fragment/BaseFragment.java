package com.example.a1203.zhihudaily.fragment;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1203.zhihudaily.activity.MainActivity;

/**
 * @author littlecorgi
 * @Date 2018-10-30 21:51
 * @email a1203991686@126.com
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

    /**
     * 加载View。放在onCreateView中
     * @param inflater 布局
     * @param container 上层ViewGroup
     * @param savedInstanceState 数据，一般为null
     * @return 返回加载到的View，并交给onCreateView
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 加载数据。放在onActivityCreated中
     */
    protected abstract void initData();

    protected void hint(View view, String content, int color){
        Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(color);
        snackbar.show();
    }

    public MainActivity getRootActivity(){
        return ((MainActivity) mActivity);
    }
}
