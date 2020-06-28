package com.example.a1203.zhihudaily.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.a1203.zhihudaily.R;
import com.example.a1203.zhihudaily.fragment.MainFragment;
import com.example.a1203.zhihudaily.net.HttpUtil;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.snackbar.Snackbar;

/**
 * @author littlecorgi
 * @email a1203991686@126.com
 * @Date 2018-10-30
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private SwipeRefreshLayout refreshLayout;

    /**
     * 文章的发布日期
     */
    private String date;

    /**
     * 用来实现再按一次退出程序的效果
     */
    private boolean isExit;

    private int currentId;

    public boolean isHomepage;

    public static String BASE_URL = "https://news-at.zhihu.com/api/4/news/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
        if (sp.contains("zhihu_article_url")) {
            BASE_URL = sp.getString("zhihu_article_url", "https://news-at.zhihu.com/api/4/news/");
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("zhihu_article_url", "https://news-at.zhihu.com/api/4/news/");
            editor.apply();
        }

        // 状态栏变色
        int color = Color.rgb(0, 162, 237);
        StatusBarCompat.setStatusBarColor(this, color);

        initView();
        currentId = -1;
        // 添加Fragment
        getTransition().add(R.id.title_frameLayout, new MainFragment(), "Fragment" + currentId).commit();
        isHomepage = true;
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText("享受阅读的快乐");

        drawerLayout = findViewById(R.id.drawerLayout);
        refreshLayout = findViewById(R.id.refreshLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(() -> {
            if (isHomepage) {
                MainFragment mainFragment = ((MainFragment) getFragmentByTag("Fragment" + currentId));
                mainFragment.getLatestArticleList();
            }
        });
    }

    /**
     * 获取主页
     */
    public void getHomepage() {
        MainFragment mainFragment = (MainFragment) getFragmentByTag("Fragment" + "-1");
        FragmentTransaction transition = getTransition();
        if (mainFragment == null) {
            transition.add(R.id.title_frameLayout, new MainFragment(), "Fragment" + "-1").commit();
        } else {
            transition.show(mainFragment).commit();
        }
        currentId = -1;
        isHomepage = true;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("享受阅读的乐趣");
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawerLayout();
            return;
        }
        if (!isHomepage) {
            getHomepage();
            return;
        }
        if (isExit) {
            refreshLayout.setRefreshing(false);
            super.onBackPressed();
        } else {
            hint();
        }
    }

    private void hint() {
        Snackbar snackbar = Snackbar.make(refreshLayout, "再按一次退出", Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor("#0099CC"));
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                isExit = false;
            }

            @Override
            public void onShown(Snackbar snackbar) {
                isExit = true;
            }
        }).show();
    }

    private FragmentTransaction getTransition() {
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        return transition;
    }

    public Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public int getCurrentId() {
        return currentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRefresh(boolean flag) {
        refreshLayout.setRefreshing(flag);
    }

    public void closeDrawerLayout() {
        this.drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void joke(View view) {
        Snackbar snackbar = Snackbar.make(refreshLayout, "其实并没有该功能，只是为了占个地方。。。", Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.parseColor("#0099CC"));
        snackbar.show();
    }
}
