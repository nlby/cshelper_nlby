package com.hhu.cshelper.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hhu.cshelper.R;


import com.hhu.cshelper.config.mynote;

import com.hhu.cshelper.database.NoteDatabaseHelper;
import com.hhu.cshelper.entity.Note;
import com.hhu.cshelper.utils.ImageUtils;
import com.hhu.cshelper.utils.ScreenUtils;
import com.hhu.cshelper.utils.TimeUtil;
import com.hhu.cshelper.utils.UriHelper;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pub.devrel.easypermissions.EasyPermissions;



public class NoteDetailActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    protected final int RC_READ_EXTERNAL_STORAGE = 10001;
    private static final int PICTURE = 0;
    private static final int FREEHAND = 1;
    private static final int RECORD = 2;
    private static final int TAKE_PHOTO = 3;

    private EditText ettitle;
    private EditText etcontent;
    private Note mnote;
    private androidx.appcompat.widget.Toolbar mtoolbar;
    private int position; //这个note的id
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_note_detail);


//        mtoolbar = findViewById(R.id.sub_toolbar);
//        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(NoteDetailActivity.this , "aaa" ,Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder dialog = new AlertDialog.Builder(NoteDetailActivity.this);
//                dialog.setMessage(R.string.save_query);
//                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        saveNote();
//                        finish();
//                    }
//                });
//
//                dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
//                    }
//                });
//                dialog.show();
//            }
        //       });
//        mtoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case  android.R.id.home: Toast.makeText(NoteDetailActivity.this , "aaa" , Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });





        init();
    }


    /**
     * @name  init
     * @description 点击一个note item 把信息填充到note_detail中
     * @param
     * @return
     * @date  2020/4/29
     */
    private void init() {

        ettitle = findViewById(R.id.et_title);
        etcontent = findViewById(R.id.et_content);


        Intent intent = getIntent();
        if (intent != null) {
            mnote = intent.getParcelableExtra("note_item");
            position =  intent.getIntExtra("pos",0);
            id = intent.getIntExtra("id" , 0);
            if (mnote != null) {
                ettitle.setText(mnote.getTitle());
                //etcontent.setText(mnote.getContent());
                showContent();
            }
        }

    }

    private void showContent() {
        String input = mnote.getContent();
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);


        SpannableString spannable = new SpannableString(input);
        while(m.find()){

            String s = m.group();
            int start = m.start();
            int end = m.end();

            String path = s.replaceAll("\\<img src=\"|\"\\/>","").trim();


            int width = ScreenUtils.getScreenWidth(NoteDetailActivity.this);
            int height = ScreenUtils.getScreenHeight(NoteDetailActivity.this);

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                bitmap = ImageUtils.zoomImage(bitmap,(width-32)*0.8,bitmap.getHeight()/(bitmap.getWidth()/((width-32)*0.8)));
                ImageSpan imageSpan = new ImageSpan(this, bitmap);
                spannable.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        etcontent.setText(spannable);

    }


    /**
     * @name  onCreateOptionsMenu
     * @description 把toolbar上的几个item填充进来(删除、保存、其他)
     * @param
     * @return
     * @date  2020/4/29
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.note_detail, menu);
        return true;
    }



    /**
     * @name  onOptionsItemSelected
     * @description 设置toolbar删除、保存、其他功能的选择事件
     * @param
     * @return
     * @date  2020/4/29
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:{
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.save_query);
                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveNote();
                        finish();
                    }
                });

                dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.show();
                break;
            }


            case R.id.note_detail_save:{
                Toast.makeText(NoteDetailActivity.this , R.string.save_suc , Toast.LENGTH_SHORT).show();
                saveNote();
                finish();
                break;
            }

            case R.id.note_detail_delete:{
                Toast.makeText(NoteDetailActivity.this , R.string.delete_suc , Toast.LENGTH_SHORT).show();
                deleteNote();

                finish();
                break;
            }

            case R.id.note_detail_album:{
                doAlbum();
                break;
            }
//
//            case R.id.note_detail_photo:{
//                doPhoto();
//                break;
//            }
//
//            case R.id.note_detail_record:{
//                doRecord();
//                break;
//            }


        }
        return super.onOptionsItemSelected(item);


    }

    private void doRecord() {

    }

    private void doPhoto() {

    }

    private void doAlbum() {

        if (EasyPermissions.hasPermissions(NoteDetailActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            openAlbum();

        } else {
            EasyPermissions.requestPermissions(NoteDetailActivity.this,
                    getString(R.string.need_read_storage_permission),
                    RC_READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }

    /**
     * @name  openAlbum
     * @description 打开相册，还未实现完全
     * @param
     * @return
     * @date  2020/4/29
     */
    private void openAlbum() {
        Intent getAlbum = new Intent(Intent.ACTION_PICK);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum,PICTURE);
    }

    private void deleteNote() {

        NoteDatabaseHelper.getInstance().delete(id);

    }

    private void saveNote() {
        String noteTitle = ettitle.getText().toString();
        String noteContent = etcontent.getText().toString();
        String date = TimeUtil.getDataTime("yyyy/MM/dd HH:mm");
        Note note = new Note(noteTitle, noteContent, date, "", System.currentTimeMillis());  // nlby: 空构造方法已经把日期给初始化了 这个日期一直会变
        if (mnote != null) { // 来自已经存在的Note
            note.setId(mnote.getId());
            note.setTimestamp(mnote.getTimestamp());
        } else {
            note.setTimestamp(System.currentTimeMillis());
//            System.out.println("创建" + "----------" +TimeUtil.getDataTime("yyyy/MM/dd HH:mm"));
        }
        NoteDatabaseHelper.getInstance().save(note);



    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK) {
//
//            switch (requestCode) {
//                case PICTURE: {
//                    Uri uri = data.getData();
//                    etcontent.insertImage(UriHelper.getAbsolutePathByUri(mynote.getAppContext(), uri));
//
//                break;
//            }
//
//
//            }
//        }
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == RC_READ_EXTERNAL_STORAGE) {
            openAlbum();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm = null;

        ContentResolver resolver = getContentResolver();
        if (requestCode == PICTURE) {
            try {
                // 获得图片的uri
                assert data != null;
                Uri originalUri = data.getData();
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                String[] proj = {MediaStore.Images.Media.DATA};

                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                String path = cursor.getString(column_index);

                insertImg(path);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(NoteDetailActivity.this, R.string.photo_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertImg(String path) {
        String tagPath = "<img src=\""+path+"\"/>";
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(bitmap != null){
            SpannableString ss = getBitmapMime(path, tagPath);
            insertPhotoToEditText(ss);
            etcontent.append("\n");

        }else{
            //Log.d("YYPT_Insert", "tagPath: "+tagPath);
            Toast.makeText(NoteDetailActivity.this , R.string.photo_permission,Toast.LENGTH_LONG).show();
        }
    }

    private void insertPhotoToEditText(SpannableString ss) {
        Editable et = etcontent.getText();
        int start = etcontent.getSelectionStart();
        et.insert(start,ss);
        etcontent.setText(et);
        etcontent.setSelection(start+ss.length());
        etcontent.setFocusableInTouchMode(true);
        etcontent.setFocusable(true);
    }

    private SpannableString getBitmapMime(String path, String tagPath) {

        SpannableString ss = new SpannableString(tagPath);

        int width = ScreenUtils.getScreenWidth(NoteDetailActivity.this);
        int height = ScreenUtils.getScreenHeight(NoteDetailActivity.this);

        //Log.d("YYPT_IMG_SCREEN", "高度:"+height+",宽度:"+width);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        //Log.d("YYPT_IMG_IMG", "高度:"+bitmap.getHeight()+",宽度:"+bitmap.getWidth());
        bitmap = ImageUtils.zoomImage(bitmap,(width-32)*0.8,bitmap.getHeight()/(bitmap.getWidth()/((width-32)*0.8)));


        //Log.d("YYPT_IMG_COMPRESS", "高度："+bitmap.getHeight()+",宽度:"+bitmap.getWidth());


        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        ss.setSpan(imageSpan, 0, tagPath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

}

