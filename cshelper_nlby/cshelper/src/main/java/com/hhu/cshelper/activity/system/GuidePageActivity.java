package com.hhu.cshelper.activity.system;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.hhu.cshelper.R;

import java.util.ArrayList;

/** 引导页
 * @name  GuidePageActivity
 * @description  引导页
 * @author  nlby
 * @date  2020/4/29
 */
public class GuidePageActivity extends AppCompatActivity {

    ConvenientBanner cbTest;
    Button btnTest;

    private ArrayList<Integer> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        cbTest = findViewById(R.id.cb_test);
        btnTest = findViewById(R.id.btn_test);
        initView();
        initGuidePage();

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuidePageActivity.this, LoginSignActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView() {
        arrayList = new ArrayList<>();
        arrayList.add(R.mipmap.h1);
        arrayList.add(R.mipmap.h2);
        arrayList.add(R.mipmap.h3);
    }

    private void initGuidePage() {
        cbTest.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                //设置加载哪个布局
                return R.layout.item_guide_page;
            }

        }, arrayList)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(true)
                .setCanLoop(false)
                .setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    }

                    @Override
                    public void onPageSelected(int index) {
                        //总共添加了三张图片，如果index为2表示到了最后一张图片，隐藏下面的指示器，显示跳转到主页的按钮
                        if (index == 2) {
                            btnTest.setVisibility(View.VISIBLE);
                            cbTest.setPointViewVisible(false);
                        } else {
                            btnTest.setVisibility(View.GONE);
                            cbTest.setPointViewVisible(true);

                        }
                    }
                });
    }


    /**
     * 轮播图2 对应的holder
     */
    public class LocalImageHolderView extends Holder<Integer> {
        private ImageView mImageView;

        //构造器
        public LocalImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mImageView = itemView.findViewById(R.id.iv_guide_page);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        @Override
        public void updateUI(Integer data) {
            mImageView.setImageResource(data);
        }
    }
}
