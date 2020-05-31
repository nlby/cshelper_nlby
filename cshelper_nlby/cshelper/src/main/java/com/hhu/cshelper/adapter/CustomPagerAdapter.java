package com.hhu.cshelper.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/** ViewPager定制的适配器类
 * @name  CustomPagerAdapter
 * @description  ViewPager定制的适配器类
 * @author  nlby
 * @date  2020/4/29
 */
public class CustomPagerAdapter extends PagerAdapter {

    private List<View> viewList;
    private List<String> titleList;
    private View currentView;

    public CustomPagerAdapter(List<View> viewList, List<String> titleList) {
        this.viewList = viewList;
        this.titleList = titleList;
    }

    /*
      返回的是页卡的数量
     */
    @Override
    public int getCount() {
        return viewList.size();
    }

    /*
      判断页卡是否来自对象
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /*
      实例化一个页卡
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }


    /*
      给每个视图添加标题
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }


    /*
      设置当前项
     */
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (View)object;
    }

    /*
      获取当前项 上下这两个方法是正确获取ViewPager当前页卡的关键
     */
    public View getPrimaryItem() {
        return currentView;
    }

    /*
     获取当前项在ViewPager中的位置 和以下的destroyItem方法是正确删除页卡的关键
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /*
      销毁一个页卡
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


}
