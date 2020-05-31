package com.hhu.cshelper.effect;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/** ViewPager页卡切换效果
 * @name  LoopTransformer
 * @description  ViewPager页卡切换效果
 * @author  nlby
 * @date  2020/4/29
 */
public class LoopTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9f;

    @Override
    public void transformPage(View view, float position) {
        /**
         * 过滤那些 <-1 或 >1 的值，使它区于【-1，1】之间
         */
        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }
        /**
         * 判断是前一页 1 + position ，右滑 pos -> -1 变 0
         * 判断是后一页 1 - position ，左滑 pos -> 1 变 0
         */
        float tempScale = position < 0 ? 1 + position : 1 - position; // [0,1]
        float scaleValue = MIN_SCALE + tempScale * 0.1f; // [0,1]
        view.setScaleX(scaleValue);
        view.setScaleY(scaleValue);
    }
}
