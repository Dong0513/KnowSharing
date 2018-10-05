package innovation.competition.com.knowsharing.entity;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.entity
 * 文件名：CommunityData
 * 创建者：
 * 创建时间：2018/8/20 18:14
 * 描述：社区实体类
 */
public class CommunityData {

    //头像
    private String com_avator;
    //用户名
    private String com_author;
    //发送时间
    private String com_uploaddate;
    //文本内容
    private String com_text_content;
    //图片内容
    private String com_image_contentId;
    //点赞数
    private String com_like_numbers;
    //评论数
    private String com_comment_numbers;

    public CommunityData() {
    }

    public CommunityData(String com_avator, String com_author, String com_uploaddate, String com_text_content,
                         String com_image_contentId, String com_like_numbers,
                         String com_comment_numbers) {
        this.com_avator = com_avator;
        this.com_author = com_author;
        this.com_uploaddate = com_uploaddate;
        this.com_text_content = com_text_content;
        this.com_image_contentId = com_image_contentId;
        this.com_like_numbers = com_like_numbers;
        this.com_comment_numbers = com_comment_numbers;
    }

    public String getCom_avator() {
        return com_avator;
    }

    public void setCom_avator(String com_avator) {
        this.com_avator = com_avator;
    }

    public String getCom_author() {
        return com_author;
    }

    public void setCom_author(String com_author) {
        this.com_author = com_author;
    }

    public String getCom_uploaddate() {
        return com_uploaddate;
    }

    public void setCom_uploaddate(String com_uploaddate) {
        this.com_uploaddate = com_uploaddate;
    }

    public String getCom_text_content() {
        return com_text_content;
    }

    public void setCom_text_content(String com_text_content) {
        this.com_text_content = com_text_content;
    }

    public String getCom_image_contentId() {
        return com_image_contentId;
    }

    public void setCom_image_contentId(String com_image_contentId) {
        this.com_image_contentId = com_image_contentId;
    }

    public String getCom_like_numbers() {
        return com_like_numbers;
    }

    public void setCom_like_numbers(String com_like_numbers) {
        this.com_like_numbers = com_like_numbers;
    }

    public String getCom_comment_numbers() {
        return com_comment_numbers;
    }

    public void setCom_comment_numbers(String com_comment_numbers) {
        this.com_comment_numbers = com_comment_numbers;
    }
}
