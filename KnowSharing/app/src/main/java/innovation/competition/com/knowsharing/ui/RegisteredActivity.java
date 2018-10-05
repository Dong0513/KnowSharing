package innovation.competition.com.knowsharing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import innovation.competition.com.knowsharing.MainActivity;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.base.BaseActivity;
import innovation.competition.com.knowsharing.event.FinishEvent;
import innovation.competition.com.knowsharing.model.BaseModel;
import innovation.competition.com.knowsharing.model.UserModel;
import innovation.competition.com.knowsharing.utils.ToastUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisteredActivity extends BaseActivity{

    private EditText et_name;
    private EditText et_pwd;
    private EditText et_repwd;
    private Button btn_registered;
    private TextView register_login;

    private String url = "";
    String tag = "Registered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();

    }

    protected void initView() {
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        et_repwd = findViewById(R.id.et_repwd);
        btn_registered = findViewById(R.id.btn_registered);
        register_login = findViewById(R.id.register_login);

        onClick();
    }

    private void onClick() {

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class, null, true);
            }
        });


        btn_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel.getInstance().register(et_name.getText().toString().trim(), et_pwd.getText().toString().trim(), et_repwd.getText().toString().trim(), new LogInListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            EventBus.getDefault().post(new FinishEvent());
                            startActivity(MainActivity.class, null, true);
                        } else {
                            if (e.getErrorCode() == BaseModel.CODE_NOT_EQUAL) {
                                et_repwd.setText("");
                            }
                            toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                        }
                    }
                });

                /*//获取到输入框的值  trim()去空格
                final String name = et_name.getText().toString().trim();
                final String pass = et_pwd.getText().toString().trim();
                String re_pass = et_repwd.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(pass) & !TextUtils.isEmpty(re_pass)){
                    if (pass.equals(re_pass)){
                        //sendRequestWithOkHttp(name,pass);
                        //Bmob注册
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BmobUser bmobUser = new BmobUser();
                                bmobUser.setUsername(name);
                                bmobUser.setPassword(pass);
                                bmobUser.signUp(new SaveListener<MyUser>() {
                                    @Override
                                    public void done(MyUser myUser, BmobException e) {
                                        if (e == null){
                                            ToastUtil.showShort(RegisteredActivity.this,"注册成功");
                                            Intent intent = new Intent(RegisteredActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            ToastUtil.showShort(RegisteredActivity.this,"注册失败"+e.toString());
                                        }
                                    }

                                });
                            }
                        }).start();

                    }else {
                        Toast.makeText(RegisteredActivity.this,"两次输入密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisteredActivity.this,"输入框不能为空", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void sendRequestWithOkHttp(final String name, final String pass) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                    //Form表单格式的参数传递
                    RequestBody post = new FormBody.Builder()
                            .add("username", name)
                            .add("userpass", pass)
                            .build();
                    Request request = new Request
                            .Builder()
                            .post(post)//Post请求的参数传递
                            .url(url)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(RegisteredActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            //此方法运行在子线程中，不能在此方法中进行UI操作。
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (response.body() != null){
                                            String responseData = response.body().string();
                                            parseJSONWithJSONObject(responseData);
                                        }else {
                                            Toast.makeText(RegisteredActivity.this,"连接异常",Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void parseJSONWithJSONObject(String responseData) {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            //JSONObject jsonObject1 = jsonObject.getJSONObject(i);
            //int id = jsonObject.getInt("id");
            int success = jsonObject.getInt("success");

            if (success == 1){

                Toast.makeText(RegisteredActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisteredActivity.this,LoginActivity.class);
                startActivity(intent);
                this.finish();

            }else if (success == 0){
                Toast.makeText(RegisteredActivity.this,"用户名已存在",Toast.LENGTH_LONG).show();
            }else {
                ToastUtil.showShort(RegisteredActivity.this,"注册失败，请稍后再试");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
