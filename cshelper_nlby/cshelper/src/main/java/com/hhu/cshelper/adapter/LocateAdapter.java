package com.hhu.cshelper.adapter;


/** 将data数据和位置的view绑定
 * @name  LocateAdapter
 * @description 将data数据和位置的view绑定
 * @author  dyh
 * @date  2020/4/29
 */
public interface LocateAdapter <T>{


    void bindDatatoView(T data , int position);
}