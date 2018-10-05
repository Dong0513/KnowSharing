package innovation.competition.com.knowsharing.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.entity
 * 文件名：Post
 * 创建者：
 * 创建时间：2018/9/20 14:52
 * 描述：TODO 发帖
 */
public class Post extends BmobObject {

    private String title;//帖子标题

    private String content;// 帖子内容

    private String uploaddate;// 帖子的发表时间

    private MyUser author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户

    private BmobFile image;//帖子图片

    private BmobRelation likes;//多对多关系：用于存储喜欢该帖子的所有用户

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }
}
