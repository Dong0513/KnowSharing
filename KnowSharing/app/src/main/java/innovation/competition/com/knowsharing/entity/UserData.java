package innovation.competition.com.knowsharing.entity;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.entity
 * 文件名：UserData
 * 创建者：
 * 创建时间：2018/9/20 10:09
 * 描述：TODO
 */
public class UserData extends BmobObject {

    private String uploaddate;
    private String content;
    private String author;

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
