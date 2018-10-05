package innovation.competition.com.knowsharing.entity;

import cn.bmob.v3.BmobUser;
import innovation.competition.com.knowsharing.db.NewFriend;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.entity
 * 文件名：MyUser
 * 创建者：
 * 创建时间：2018/8/29 18:15
 * 描述：TODO
 */
public class MyUser extends BmobUser {

    //昵称
    private String nickname;
    //头像
    private String avatar;
    //个人简介
    private String introduction;

    public MyUser() {
    }
    public MyUser(NewFriend friend){
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
