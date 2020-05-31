package com.hhu.cshelper.ui.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hhu.cshelper.R;

/**
 * @name  BaseActivity
 * @description 所有activity的基类，备用
 * @author  dyh
 * @date  2020/4/29
 */
public class BaseActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}


