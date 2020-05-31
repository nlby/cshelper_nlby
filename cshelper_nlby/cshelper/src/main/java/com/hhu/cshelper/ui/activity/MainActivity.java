package com.hhu.cshelper.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.hhu.cshelper.R;
import com.hhu.cshelper.database.NoteDatabaseHelper;
import com.hhu.cshelper.ui.fragment.AllNoteFragment;
import com.hhu.cshelper.ui.fragment.BaseNoteListFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;





public class MainActivity extends AppCompatActivity  implements BaseNoteListFragment.OnFragmentInteractionListener {

    protected final int RC_ALL_PERM = 10000;
    protected final int RC_READ_EXTERNAL_STORAGE = 10001;
    protected final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE};

    private androidx.appcompat.widget.Toolbar mtoolbar;
    private ImageView miv;

    private Fragment mfragment;
    private Button mbutton,testbutton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_main);


        set_toolbar_back();
        init();
        requestPermissions();


        //点击新建笔记，转到noteactivity界面

        mbutton = findViewById(R.id.new_note);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , NoteDetailActivity.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this , "aaa" , Toast.LENGTH_SHORT).show();
            }
        });




//        testbutton = findViewById(R.id.test);
//        testbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this , TestActivity.class);
//                startActivity(intent);
//            }
//        });



    }

    private void init() {
        fragment_init();
    }

    /**
     * @name  fragment_init
     * @description 把note item 组成的笔记块以fragment形式填充
     * @param
     * @return
     * @date  2020/4/29
     */
    private void fragment_init() {

        mfragment = new AllNoteFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container1 , mfragment).commitAllowingStateLoss();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    /**
     * @name  set_toolbar_back
     * @description 设置main_activity的toolbar的返回键
     * @param
     * @return
     * @date  2020/4/29
     */
    public void set_toolbar_back(){
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mtoolbar.setNavigationIcon();

        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this , R.string.notwork , Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

    /**
     * @name  onCreateOptionsMenu
     * @description 填充基本框架
     * @param
     * @return
     * @date  2020/4/29
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * @name   requestPermissions
     * @description 权限获取
     * @param
     * @return
     * @date  2020/4/29
     */
    @AfterPermissionGranted(RC_ALL_PERM)
    public void requestPermissions() {
        if (!EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_tip),
                    10000, PERMISSIONS);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
