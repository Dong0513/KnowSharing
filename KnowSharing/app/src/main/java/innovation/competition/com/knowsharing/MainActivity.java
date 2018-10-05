package innovation.competition.com.knowsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import innovation.competition.com.knowsharing.base.BaseActivity;
import innovation.competition.com.knowsharing.db.NewFriendManager;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.event.RefreshEvent;
import innovation.competition.com.knowsharing.fragment.CommunityFragment;
import innovation.competition.com.knowsharing.fragment.ConversationFragment;
import innovation.competition.com.knowsharing.fragment.FriendFragment;
import innovation.competition.com.knowsharing.fragment.IndexFragment;
import innovation.competition.com.knowsharing.fragment.NotificationFragment;
import innovation.competition.com.knowsharing.fragment.UserFragment;
import innovation.competition.com.knowsharing.ui.LoginActivity;
import innovation.competition.com.knowsharing.ui.PostSwipeActivity;
import innovation.competition.com.knowsharing.utils.IMMLeaks;
import innovation.competition.com.knowsharing.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    private LinearLayout rg_tab_bar;
    private RelativeLayout rb_index;
    private RelativeLayout rb_community;
    //private RelativeLayout rb_notifition;
    private RelativeLayout rb_friends;
    private RelativeLayout rb_user;
    private ImageView fl_post;
    private ImageView iv_contact_tips;

    private IndexFragment indexFragment;
    private CommunityFragment communityFragment;
    private NotificationFragment notificationFragment;
    private FriendFragment friendFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
        onClick();
        //判断用户是否已登录
        final MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser != null){
            if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()){
                BmobIM.connect(myUser.getObjectId(), new ConnectListener() {
                    @Override
                    public void done(String uid, BmobException e) {
                        if (e == null) {
                            //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                            //TODO 会话：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                            BmobIM.getInstance().
                                    updateUserInfo(new BmobIMUserInfo(myUser.getObjectId(),
                                            myUser.getUsername(), myUser.getAvatar()));
                            EventBus.getDefault().post(new RefreshEvent());
                        } else {
                            ToastUtil.showShort(MainActivity.this,e.getMessage());
                        }
                    }
                });
                //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
                BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                    @Override
                    public void onChange(ConnectionStatus status) {
                        ToastUtil.showShort(MainActivity.this,status.getMsg());
                        Logger.i(BmobIM.getInstance().getCurrentStatus().getMsg());
                    }
                });
                ToastUtil.showShort(MainActivity.this,"连接成功");
            }else {
                ToastUtil.showShort(MainActivity.this,"IM服务器已连接");
            }
        }else {
            //未登录
            ToastUtil.showShort(MainActivity.this,"未登录，请登录");
            startActivity(LoginActivity.class,null,true);
        }
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());



        //获取Intent中的bundle对象
        //Bundle bundle = this.getIntent().getExtras();
        //获取bundle中的数据，注意类型和key
        //String username = bundle.getString("username");
        //Toast.makeText(MainActivity.this,"欢迎"+username,Toast.LENGTH_LONG).show();
    }

    private void ifLoad() {



        /*if (myUser == null){
            //未登录
            ToastUtil.showShort(MainActivity.this,"未登录，请登录");
        }else {
            //已登录
            ToastUtil.showShort(MainActivity.this,"已登录,欢迎回来："+myUser.getUsername());

        }*/

    }

    private void findView() {
        rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rb_index = findViewById(R.id.rb_index);
        rb_community = findViewById(R.id.rb_community);
        //rb_notifition = findViewById(R.id.rb_notifition);
        rb_friends = findViewById(R.id.rb_friends);
        rb_user = findViewById(R.id.rb_user);
        fl_post = findViewById(R.id.fl_post);
        iv_contact_tips = findViewById(R.id.iv_contact_tips);

    }

    protected void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new IndexFragment();
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
        //获取第一个单选按钮  并设置为选中状态
        //rb_index.setChecked(true);


        int id = getIntent().getIntExtra("menufragment_id",0);
        if (id == 4){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new UserFragment())
                    .commit();
        }else if (id == 2){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ConversationFragment())
                    .commit();
        }else if (id == 1){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CommunityFragment())
                    .commit();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new IndexFragment())
                    .commit();
        }
    }

    public void onClick() {

        rb_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new IndexFragment(), null)
                        .commit();
            }
        });

        rb_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CommunityFragment(), null)
                        .commit();
            }
        });

        fl_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostSwipeActivity.class);
                startActivity(intent);
            }
        });

        rb_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ConversationFragment(), null)
                        .commit();
            }
        });

        rb_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new UserFragment(), null)
                        .commit();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次进来应用都检查会话和好友请求的情况
        checkRedPoint();
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
    }

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.3、通知有在线消息接收
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.4、通知有离线消息接收
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.5、通知有自定义消息接收
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        checkRedPoint();
    }

    /**
     *
     */
    private void checkRedPoint() {
        //TODO 会话：4.4、获取全部会话的未读消息数量
        int count = (int) BmobIM.getInstance().getAllUnReadCount();
        if (count > 0) {
            //iv_conversation_tips.setVisibility(View.VISIBLE);
        } else {
            //iv_conversation_tips.setVisibility(View.GONE);
        }
        //TODO 好友管理：是否有好友添加的请求
        if (NewFriendManager.getInstance(this).hasNewFriendInvitation()) {
            iv_contact_tips.setVisibility(View.VISIBLE);
        } else {
            iv_contact_tips.setVisibility(View.GONE);
        }
    }


}

