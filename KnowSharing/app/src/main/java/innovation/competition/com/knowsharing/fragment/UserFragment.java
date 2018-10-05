package innovation.competition.com.knowsharing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.base.ParentWithNaviFragment;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.model.UserModel;
import innovation.competition.com.knowsharing.ui.LoginActivity;
import innovation.competition.com.knowsharing.ui.RegisteredActivity;
import innovation.competition.com.knowsharing.ui.UserInfoActivity;
import innovation.competition.com.knowsharing.utils.ToastUtil;

/**
 * 项目名：KnowSharing
 * 包名：fragment
 * 文件名：UserFragment
 * 创建者：
 * 创建时间：2018/3/11 22:55
 * 描述：用户个人中心
 */

public class UserFragment extends ParentWithNaviFragment {
    private CircleImageView profile_image;
    private CircleImageView head_image;
    private Button edit_user;
    private TextView my_friends;
    private TextView guanzhu;

    private Button btn_login;
    private Button btn_register;
    private Button btn_logout;

    //是否第一次加载
    private boolean isFirstLoading = true;

    BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
    //判断用户是否已登录
    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);

        findView(view);


        if (myUser != null){
            //已登录 获取头像数据
            String objectId = myUser.getObjectId();
            downloadData(objectId);
        }

        return view;
    }




    //登录后的数据加载
    private void downloadData(String objectId) {

        //先从缓存获取
        //bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        Glide.with(UserFragment.this).load(myUser.getAvatar()).into(profile_image);
        //1、从服务器查询之前上传的图片地址
        bmobQuery.getObject(objectId, new QueryListener<MyUser>() {
            @Override
            public void done(MyUser myUserFile, BmobException e) {
                if (e == null){
                    //获取数据表中的头像地址
                    final String headportrait_url = myUserFile.getAvatar();
                    //ToastUtil.showShort(getActivity(),"头像地址: "+headportrait_url);
                    if (headportrait_url != null){
                        //2、根据地址在线加载图片并设置到头像上并缓存


                    }else {
                        ToastUtil.showShort(getActivity(),"请编辑个人资料");
                    }
                }else {
                    ToastUtil.showShort(getActivity(), "上传文件失败：" + e.getMessage());
                }
            }
        });

    }

    private void findView(View view) {
        profile_image = view.findViewById(R.id.head_image);
        edit_user = view.findViewById(R.id.edit_user);
        my_friends = view.findViewById(R.id.my_friends);
        guanzhu = view.findViewById(R.id.fragment_user_guanzhu);
        btn_login = view.findViewById(R.id.btn_login);
        btn_register = view.findViewById(R.id.btn_registered);
        btn_logout = view.findViewById(R.id.btn_logout);
        onClick();
    }

    private void onClick() {

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser == null){
                    ToastUtil.showShort(getActivity(),"未登录，请登录后再操作");
                }else {

                }
            }
        });

        edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser == null){
                    ToastUtil.showShort(getActivity(),"未登录，请登录后再操作");
                }else {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                    /*Intent intent = new Intent(getActivity(), EditDataActivity.class);
                    startActivity(intent);*/
                }
            }
        });

        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser == null){
                    ToastUtil.showShort(getActivity(),"未登录，请登录后再操作");
                }else {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new ContactFragment();
                    fragmentTransaction.add(R.id.fragment_container,fragment);
                    fragmentTransaction.commit();


                    //ToastUtil.showShort(getActivity(),"正在开发中……");
                    //Intent intent = new Intent(getActivity(), ContactActivity.class);
                    //startActivity(intent);

                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisteredActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myUser != null) {
                    UserModel.getInstance().logout();
                    profile_image.setImageResource(R.drawable.add_pic);
                    getActivity().finish();
                    startActivity(LoginActivity.class,null);
                    ToastUtil.showShort(getActivity(), "已退出");

                }else {
                    ToastUtil.showShort(getActivity(),"未登录，请登录后再操作");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected String title() {
        return null;
    }


    /*@Override
    public void onResume() {
        super.onResume();
        UtilTools.getImageToShare(getActivity(),profile_image);
    }*/

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            //在最前端显示
            ToastUtil.showShort(getActivity(),"onHiddenChanged方法已调用");
            UtilTools.getImageToShare(getActivity(),profile_image);
        }else {


        }
    }*/
}
