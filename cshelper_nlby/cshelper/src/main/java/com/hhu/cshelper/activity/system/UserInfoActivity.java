package com.hhu.cshelper.activity.system;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hhu.cshelper.R;
import com.hhu.cshelper.common.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/** 用户信息页
 * @name  UserInfoActivity
 * @description  用户信息页
 * @author  nlby
 * @date  2020/4/29
 */
public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    //图片路径
    protected static Uri tempUri = null;

    private Context context;
    private SharedPreferences sharedPreferences;

    private ImageView iconIv;
    private TextView nameTv;
    private TextView mailTv;
    private TextView phoneTv;

    @SuppressWarnings("all")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        context = this;
        sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String mail = sharedPreferences.getString("mail", "");
        String phone = sharedPreferences.getString("phone", "");
        nameTv = findViewById(R.id.user_name);
        nameTv.setText(username);
        phoneTv = findViewById(R.id.user_phone);
        phoneTv.setText(phone);
        mailTv = findViewById(R.id.user_mail);
        mailTv.setText(mail);


        String userId = sharedPreferences.getString("userId", "");
        iconIv = findViewById(R.id.user_avator);
        final ImageView imageView = iconIv;
        new AsyncTask<String, Void, String>() {
            Bitmap bitmap;
            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    InputStream is = url.openStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                imageView.setImageBitmap(bitmap);
            }
        }.execute(Constants.URL_USER_AVATAR + userId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_avator:
                startActivityForResult(
                        new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                        CHOOSE_PICTURE);
//                showIconDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 显示选择头像来源对话框
     */
    public void showIconDialog() {
        new MaterialDialog.Builder(context)
                .title("选择图片来源")
                .titleGravity(GravityEnum.CENTER)
                .items(new String[]{"相册", "相机"})
                .positiveText("确定")
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            case CHOOSE_PICTURE: // 选择本地照片
                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                UserInfoActivity.this.startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                break;
                            case TAKE_PICTURE: // 拍照
                                UserInfoActivity.this.takePicture();
                                break;
                        }
                        dialog.dismiss();
                        return false;
                    }
                }).show();
    }

    /**
     * 拍照
     */
    private void takePicture() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(UserInfoActivity.this,
                    "com.copasso.cocobill.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            iconIv.setImageBitmap(photo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        setImageToView(data);
    }
}
