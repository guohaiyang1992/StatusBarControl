package com.simon.statusbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * description: 兼容 android 4.4 的沉浸式状态栏工具类
 * author: Simon
 * created at 2017/7/21 下午6:36
 * 思路：首先我们知道activity内有decorview,内部包含titleview(状态栏等)和contentView(你设置的布局)。
 * 我们要做的就是首先设置flag（状态栏透明），然后在decorview上添加一个自己的临时statusbarview,并设置颜色，
 * 然后设置contentview的marginTop属性，
 * 从而达到颜色一致
 * decorview 继承framelayout
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class StatusBarControlKitKat {

    private static final String TAG_TEMP_STATUS_BAR_VIEW = "temp_status_bar_view";
    private static final String TAG_MARGIN_ADDED = "margin_top_add";

    /**
     * 获取状态栏的高度 反射 px
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /**
     * 设置临时的占位view,并设置颜色，其高度等同于statusbar的高度
     */
    private static View addTempStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
        //获取decorview
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        //创建临时view,高度为statusbar的高度，并设置颜色和tag
        View mStatusBarView = new View(activity);

        //设置临时statusBarView的高度等同于状态栏高度
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        layoutParams.gravity = Gravity.TOP;
        mStatusBarView.setLayoutParams(layoutParams);
        mStatusBarView.setBackgroundColor(statusBarColor);
        mStatusBarView.setTag(TAG_TEMP_STATUS_BAR_VIEW);

        //添加到decorview
        mDecorView.addView(mStatusBarView);
        return mStatusBarView;
    }

    /**
     * 可以用于更新statusbar的颜色，内部会判断如果没有则会添加如果有了则会直接修改
     */
    public static void updateStatusBarColor(Activity activity, int statusBarColor) {

        View tempBarView = getTempBarView(activity);
        if (tempBarView != null) {
            tempBarView.setBackgroundColor(statusBarColor);
        } else {
            addTempStatusBarView(activity, statusBarColor, getStatusBarHeight(activity));
        }

    }

    /**
     * 移除之前添加的临时bar =>设置图片全屏可以用到
     */
    private static void removeTempStatusBarViewIfExist(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View fakeView = mDecorView.findViewWithTag(TAG_TEMP_STATUS_BAR_VIEW);
        if (fakeView != null) {
            mDecorView.removeView(fakeView);
        }
    }

    private static View getTempBarView(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();
        View fakeView = mDecorView.findViewWithTag(TAG_TEMP_STATUS_BAR_VIEW);
        return fakeView;
    }

    /**
     * 增加marginTop
     */
    private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (!TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(TAG_MARGIN_ADDED);
        }
    }

    /**
     * 移除marginTop
     */
    private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(null);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity    --activity
     * @param statusColor --颜色
     */
    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        //自身设置的布局
        View mContentChild = mContentView.getChildAt(0);
        int statusBarHeight = getStatusBarHeight(activity);

        //防止重复添加
        removeTempStatusBarViewIfExist(activity);
        addTempStatusBarView(activity, statusColor, statusBarHeight);

        addMarginTopToContentChild(mContentChild, statusBarHeight);

        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }
    }

    /**
     * 仅仅是透明状态栏==>用于设置图片的时候不适合设置其他内容的时候
     * 移除之前的所有设置
     */
    public static void transparentStatusBar(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);

        removeTempStatusBarViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));

        if (mContentChild != null) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }
    }


}