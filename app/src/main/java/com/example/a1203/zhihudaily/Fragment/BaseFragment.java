package com.example.a1203.zhihudaily.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1203.zhihudaily.Activity.MainActivity;

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

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected void initData(){

    }

    protected void hint(View view, String content, int color){
        Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(color);
        snackbar.show();
    }

    public MainActivity getRootActivity(){
        return ((MainActivity) mActivity);
    }
}
