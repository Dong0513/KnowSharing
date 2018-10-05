package innovation.competition.com.knowsharing.entity;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.entity
 * 文件名：Comment
 * 创建者：
 * 创建时间：2018/9/20 14:58
 * 描述：TODO 评论
 */
public class Comment extends BmobObject {

    private String content;//评论内容

    private MyUser user;//评论的用户，Pointer类型，一对一关系

    private Post post; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个微博

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
