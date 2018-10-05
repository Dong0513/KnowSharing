package innovation.competition.com.knowsharing.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import innovation.competition.com.knowsharing.MainActivity;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.base.BaseActivity;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.model.UserModel;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.ui
 * 文件名：SplashActivity
 * 创建者：
 * 创建时间：2018/3/13 10:32
 * 描述：闪屏页
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler =new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = UserModel.getInstance().getCurrentUser();
                if (user == null) {
                    startActivity(LoginActivity.class,null,true);
                }else{
                    startActivity(MainActivity.class,null,true);
                }
            }
        },1000);

    }


/*
    *//**
     * 1、延时2000ms
     * 2、判断程序是否第一次运行
     * 3、自定义字体
     * 4、Activity全屏主题**//*
    private TextView tv_splash;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }
    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);

        tv_splash = findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this,tv_splash);
    }

    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            //是第一次运行
            return true;
        }else {
            return false;
        }

    }
    //禁止返回键

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }*/
}
