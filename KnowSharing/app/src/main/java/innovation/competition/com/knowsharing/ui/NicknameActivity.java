package innovation.competition.com.knowsharing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.utils.ToastUtil;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.ui
 * 文件名：NicknameActivity
 * 创建者：
 * 创建时间：2018/9/1 16:21
 * 描述：修改昵称页
 */
public class NicknameActivity extends AppCompatActivity {

    private TextView tap_cancle;
    private TextView tap_save;
    private EditText nickname_edittext;

    BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
    final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    String objectId = myUser.getObjectId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);

        downData();

        initView();
    }

    private void downData() {


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
                                            nickname_edittext.setText(nickname);
                                        }
                                    });
                                }
                            }.start();
                        }else {
                            return;
                        }
                    } else {
                        ToastUtil.showShort(NicknameActivity.this, "同步失败");

                    }
                }
            });
        }else {
            ToastUtil.showShort(NicknameActivity.this,"objectId为空");
        }
    }

    private void initView() {
        tap_cancle = findViewById(R.id.tap_cancle);
        tap_save = findViewById(R.id.tap_save);
        nickname_edittext = findViewById(R.id.nickname_edittext);

        onClick();
    }

    private void onClick() {

        tap_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tap_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload();
                finish();
            }
        });
    }

    //上传数据
    private void upload() {


        myUser.setNickname(nickname_edittext.getText().toString().trim());
        myUser.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ToastUtil.showShort(NicknameActivity.this, "同步成功");
                }else {
                    ToastUtil.showShort(NicknameActivity.this, "同步失败");
                }
            }
        });
    }
}
