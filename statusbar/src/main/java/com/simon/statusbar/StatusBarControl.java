package com.simon.statusbar;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

public class StatusBarControl {

    /**
     * 设置状态栏颜色
     *
     * @param activity    --上下文环境
     * @param statusColor --状态栏颜色(具体的颜色值不是资源id)
     */
    @UiThread
    public static void setStatusColor(@NonNull Activity activity, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP/**系统大于等于5.0*/) {
            StatusBarControlLollipop.setStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT/**系统大于等于4.4 小于5.0*/) {
            StatusBarControlKitKat.setStatusBarColor(activity, statusColor);
        }
    }


    /**
     * 更新状态栏颜色
     *
     * @param activity    --上下文环境
     * @param statusColor --状态栏颜色(具体的颜色值不是资源id)
     */
    @UiThread
    public static void updateStatusColor(@NonNull Activity activity, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP/**系统大于等于5.0*/) {
            StatusBarControlLollipop.updateStatusBarColor(activity, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT/**系统大于等于4.4 小于5.0*/) {
            StatusBarControlKitKat.updateStatusBarColor(activity, statusColor);
        }
    }

    /**
     * 全透明状态栏
     *
     * @param activity --上下文环境
     */
    @UiThread
    public static void transparentStatusBar(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP/**系统大于等于5.0*/) {
            StatusBarControlLollipop.transparentStatusBar(activity, true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT/**系统大于等于4.4 小于5.0*/) {
            StatusBarControlKitKat.transparentStatusBar(activity);
        }

    }

    /**
     * 全透明状态栏设置有阴影，仅仅支持5.0及以上
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @UiThread
    public static void transparentStatusBarWithShadow(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP/**系统大于等于5.0*/) {
            StatusBarControlLollipop.transparentStatusBar(activity, false);
        }
    }
}
