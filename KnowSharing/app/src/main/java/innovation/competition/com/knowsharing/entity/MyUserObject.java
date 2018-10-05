package innovation.competition.com.knowsharing.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.entity
 * 文件名：MyUserObject
 * 创建者：
 * 创建时间：2018/9/7 15:26
 * 描述：TODO
 */
public class MyUserObject extends BmobObject {

    private BmobFile pic;

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }
}
