package innovation.competition.com.knowsharing.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;

import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.base.BaseActivity;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.utils.ToastUtil;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.ui
 * 文件名：UserInfoActivity
 * 创建者：
 * 创建时间：2018/9/3 18:16
 * 描述：TODO 个人资料页
 */
public class UserInfoActivity extends BaseActivity {

    private TextView tab_cancle;
    private CircleImageView iv_avator;
    private TextView iv_nickname_text;


    BmobIMUserInfo info;
    BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    String objectId = myUser.getObjectId();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
        //用户
        downData();
        onClick();
    }

    private void downData() {
        //第一次登录 从服务器获取数据
        if (objectId != null){
            //ToastUtil.showShort(EditDataActivity.this,"objectId："+objectId);
            bmobQuery.getObject(objectId, new QueryListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if (e == null) {
                        //昵称
                        final String nickname = myUser.getNickname();
                        if (nickname != null){
                            new Thread(){
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            iv_nickname_text.setText(nickname);
                                        }
                                    });
                                }
                            }.start();
                        }else {
                            return;
                        }
                        Glide.with(UserInfoActivity.this).load(myUser.getAvatar()).into(iv_avator);
                        //头像
                        String head_portrait_bmob = myUser.getAvatar();

                        //简介
                        final String introduction_bmob = myUser.getIntroduction();


                    } else {
                        ToastUtil.showShort(UserInfoActivity.this, "同步失败");

                    }
                }
            });
        }else {
            ToastUtil.showShort(UserInfoActivity.this,"objectId为空");
        }
    }


    protected void initView() {
        tab_cancle = findViewById(R.id.tab_cancle);
        iv_avator = findViewById(R.id.iv_avator);
        iv_nickname_text = findViewById(R.id.iv_nickname_text);

    }

    private void onClick() {

        tab_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暂时直接返回
                onBackPressed();
            }
        });

        iv_avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * create方法参数一是上下文，在activity中传activity.this，在fragment中传fragment.this。参数二为请求码，用于结果回调onActivityResult中判断
                 * selectPicture方法参数分别为图片的裁剪宽、裁剪高、宽比例、高比例。默认不传则为宽200，高200，宽高比例为1：1。
                 */
                PictureSelector
                        .create(UserInfoActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(200, 200, 1, 1);
            }
        });

        iv_nickname_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = iv_nickname_text.getText().toString().trim();
                Intent intent_nickname = new Intent(UserInfoActivity.this, NicknameActivity.class);
                intent_nickname.putExtra("editnickname", nick);
                startActivity(intent_nickname);
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                iv_avator.setImageBitmap(bitmap);

                upload(picturePath);

            }
        }
    }

    private void upload(String picturePath) {
        //上传头像资料
        final BmobFile bmobFile = new BmobFile(new File(picturePath));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //将文件路径设置到User表中
                    myUser.setAvatar(bmobFile.getFileUrl());
                    myUser.update(objectId,new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                ToastUtil.showShort(UserInfoActivity.this, "更新成功：" + bmobFile.getFileUrl());

                            }else {
                                ToastUtil.showShort(UserInfoActivity.this, "更新失败：" + e.getMessage());
                            }
                        }
                    });
                } else {
                    ToastUtil.showShort(UserInfoActivity.this, "上传文件失败：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }
        });


    }
}
