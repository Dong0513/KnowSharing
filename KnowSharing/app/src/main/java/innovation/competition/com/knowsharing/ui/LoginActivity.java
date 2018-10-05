package innovation.competition.com.knowsharing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import innovation.competition.com.knowsharing.MainActivity;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.base.BaseActivity;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.model.UserModel;
import innovation.competition.com.knowsharing.utils.ShareUtils;
import innovation.competition.com.knowsharing.utils.ToastUtil;
import innovation.competition.com.knowsharing.view.CustomDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends BaseActivity {


    //按钮
    private Button btn_registered;
    private EditText et_name;
    private EditText et_pwd;
    private Button btn_login;
    private CheckBox keep_password;
    private CustomDialog dialog;

    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);


    //请求地址
    String url = "";
    String tag = "Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        //CrashReport.testJavaCrash();
    }

    protected void initView() {
        btn_registered = findViewById(R.id.btn_registered);
        et_name = findViewById(R.id.et_username);
        et_pwd = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        keep_password = findViewById(R.id.keep_password);
        dialog = new CustomDialog(this,100,100,R.layout.dialog_loding,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheck);
        if (isCheck){
            //设置密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_pwd.setText(ShareUtils.getString(this,"password",""));
        }

        onClick();

    }



    private void onClick() {

        btn_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisteredActivity.class, null, true);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel.getInstance().login(et_name.getText().toString().trim(), et_pwd.getText().toString().trim(), new LogInListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            //登录成功
                            startActivity(MainActivity.class, null, true);
                        } else {
                            toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                        }
                    }
                });


                        /*final String et_username = et_name.getText().toString().trim();
                        final String et_userpass = et_pwd.getText().toString().trim();

                        if (!TextUtils.isEmpty(et_username)&!TextUtils.isEmpty(et_userpass)){
                            dialog.show();
                            //sendRequestWithOkHttp(et_username,et_userpass);
                            //Bmob登录
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    BmobUser bmobUser = new BmobUser();
                                    bmobUser.setUsername(et_username);
                                    bmobUser.setPassword(et_userpass);
                                    bmobUser.login(new SaveListener<MyUser>() {
                                        @Override
                                        public void done(MyUser myUser, BmobException e) {
                                            if (e == null){
                                                //ToastUtil.showShort(LoginActivity.this,"登录成功");
                                                if (!TextUtils.isEmpty(myUser.getObjectId())) {
                                                    BmobIM.connect(myUser.getObjectId(), new ConnectListener() {
                                                        @Override
                                                        public void done(String uid, BmobException e) {
                                                            if (e == null) {
                                                                //连接成功
                                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                //intent.putExtra("menufragment_id",4);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                //连接失败
                                                                ToastUtil.showShort(LoginActivity.this,"连接服务器失败"+e.getMessage());
                                                            }
                                                        }
                                                    });
                                                }
                                            }else {
                                                ToastUtil.showShort(LoginActivity.this,"登录失败"+e.toString());
                                            }
                                        }

                                    });
                                }
                            }).start();
                            dialog.dismiss();


                        }else {
                            ToastUtil.showShort(LoginActivity.this,"输入框不能为空");
                        }*/

            }
        });

    }




    private void sendRequestWithOkHttp(final String et_username, final String et_userpass) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                    //Post
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                    //Form表单格式的参数传递
                    //创建post表单，获取username和password
                    RequestBody post = new FormBody.Builder()
                            .add("username", et_username)
                            .add("userpass", et_userpass)
                            .build();
                    //开始请求，填入url，和表单
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(post)
                            .build();

                    dialog.dismiss();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();

                        }


                        @Override
                        public void onResponse(Call call, final Response response) {
                            //UI线程运行
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (response.body() != null){
                                        try {
                                            String responseData = response.body().string();
                                            parseJSONWithJSONObject(responseData);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }else {
                                        Toast.makeText(LoginActivity.this,"连接异常",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });

            }
        }).start();
    }


    /**
     * jsonObject解析json数据
     * @param responseData
     */
    private void parseJSONWithJSONObject(String responseData){
        try {
            JSONObject jsonObject = new JSONObject(responseData);

                Integer success = jsonObject.getInt("success");

                if (success == 1){

                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }else if (success == 0){
                    Toast.makeText(LoginActivity.this,"无此用户，请注册",Toast.LENGTH_LONG).show();
                }else {
                    ToastUtil.showShort(LoginActivity.this,"用户名或密码错误");
                }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this,"keeppass",keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()){
            //记住用户名和密码
            ShareUtils.putString(this,"name",et_name.getText().toString().trim());
            ShareUtils.putString(this,"password",et_pwd.getText().toString().trim());
        }else {
            ShareUtils.deleShare(this,"name");
            ShareUtils.deleShare(this,"password");
        }
    }




}
