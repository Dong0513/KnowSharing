package innovation.competition.com.knowsharing.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wildma.pictureselector.PictureSelector;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.entity.MyUserObject;
import innovation.competition.com.knowsharing.utils.ToastUtil;
import innovation.competition.com.knowsharing.utils.UtilTools;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.ui
 * 文件名：EditDataActivity
 * 创建者：
 * 创建时间：2018/7/18 16:35
 * 描述：个人资料修改界面
 */
public class EditDataActivity extends AppCompatActivity {

    private TextView tap_cancle;
    private CircleImageView profile_image;
    private RelativeLayout nickname;
    private TextView nickname_text;
    private LinearLayout introduction;


    private String picturePath;

    MyUserObject myUserObject = new MyUserObject();
    BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
    BmobUser bmobUser = BmobUser.getCurrentUser();
    String objectId = bmobUser.getObjectId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_editdata);
        super.onCreate(savedInstanceState);

        //登录后的数据加载
        downData();
        initView();

        //读头像
        //UtilTools.getImageToShare(this, profile_image);
    }


    private void initView() {
        tap_cancle = findViewById(R.id.tap_cancle);
        profile_image = findViewById(R.id.profile_image);
        nickname = findViewById(R.id.nickname);
        nickname_text = findViewById(R.id.nickname_text);
        introduction = findViewById(R.id.introduction);


        /*Intent editdata =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        String msg=editdata.getStringExtra("editnickname");
        nickname_text.setText(msg);*/

        onClick();
    }

    //数据加载
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
                                            nickname_text.setText(nickname);
                                        }
                                    });
                                }
                            }.start();
                        }else {
                            return;
                        }
                        //头像
                        String head_portrait_bmob = myUser.getAvatar();

                        //简介
                        final String introduction_bmob = myUser.getIntroduction();


                    } else {
                        ToastUtil.showShort(EditDataActivity.this, "同步失败");

                    }
                }
            });
        }else {
            ToastUtil.showShort(EditDataActivity.this,"objectId为空");
        }


    }



    private void onClick() {

        //返回
        tap_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暂时直接返回
                onBackPressed();
            }
        });



        //头像
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * create方法参数一是上下文，在activity中传activity.this，在fragment中传fragment.this。参数二为请求码，用于结果回调onActivityResult中判断
                 * selectPicture方法参数分别为图片的裁剪宽、裁剪高、宽比例、高比例。默认不传则为宽200，高200，宽高比例为1：1。
                 */
                PictureSelector
                        .create(EditDataActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(200, 200, 1, 1);
            }
        });

        //昵称
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = nickname_text.getText().toString().trim();

                Intent intent_nickname = new Intent(EditDataActivity.this, NicknameActivity.class);
                intent_nickname.putExtra("editnickname", nick);
                startActivity(intent_nickname);
                finish();



            }
        });

        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                profile_image.setImageBitmap(bitmap);

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
                    myUserObject.setPic(bmobFile);
                    myUserObject.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            Toast.makeText(EditDataActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        }
                    });


                    /*editdatauser.setHead_portrait(bmobFile.getFileUrl());
                    editdatauser.update(objectId,new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){

                                ToastUtil.showShort(EditDataActivity.this, "更新成功：" + bmobFile.getFileUrl());
                                //保存头像到本地
                                UtilTools.putImageToShare(EditDataActivity.this,profile_image);
                            }else {
                                ToastUtil.showShort(EditDataActivity.this, "更新失败：" + e.getMessage());
                            }
                        }
                    });*/
                } else {
                    ToastUtil.showShort(EditDataActivity.this, "上传文件失败：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }
        });


    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToShare(EditDataActivity.this,profile_image);

    }

}
