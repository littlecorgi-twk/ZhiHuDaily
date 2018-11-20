package com.example.a1203.zhihudaily.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.a1203.zhihudaily.R;

import java.util.Random;

public class StartActivity extends AppCompatActivity {

    private ImageView startImage;

    private int[] images = {R.drawable.start0, R.drawable.start1, R.drawable.start2, R.drawable.start3, R.drawable.start4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        initImage();
    }

    private void initImage(){
        startImage = findViewById(R.id.startImage);
        Random random = new Random();
        int index = random.nextInt(images.length);
        startImage.setImageResource(images[index]);
        // 进行缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        // 动画播放完后保持形状
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });
        startImage.startAnimation(scaleAnimation);
    }

    /**
     * 只显示一次启动页（ App 没被 kill 的情况下）
     */
    @Override
    public void onBackPressed() {
        // Intent intent = new Intent(Intent.ACTION_MAIN);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.addCategory(Intent.CATEGORY_HOME);
        // startActivity(intent);
    }

    private void startActivity() {
        /**完成后调整页面*/
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
