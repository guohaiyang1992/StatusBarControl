package com.simon.statusbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * description: 支持android 5.0 沉浸式
 * author: Simon
 * created at 2017/7/21 下午7:12
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarControlLollipop {


    /**
     * android 5.0可以直接设置颜色了，和之前的4.4有所区别
     */
    public static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //下面两步操作是为了状态栏完全透明（默认的有阴影）,上面的属性会导致状态栏透明（有阴影）而且布局会往上移动，所以此处要clearFlags
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(statusColor);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //设置setFitsSystemWindows false
            ViewCompat.setFitsSystemWindows(mChildView, false);
            //刷新配置
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * 透明状态栏=》不设置颜色，透明色
     */
    public static void transparentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //隐藏背景的阴影
        if (hideStatusBarBackground) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * android 5.0的可以直接设置
     *
     * @param activity
     * @param statusBarColor
     */
    public static void updateStatusBarColor(Activity activity, int statusBarColor) {
        setStatusBarColor(activity, statusBarColor);
    }
}
