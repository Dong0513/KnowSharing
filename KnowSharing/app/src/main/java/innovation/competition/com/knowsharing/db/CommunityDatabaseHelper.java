package innovation.competition.com.knowsharing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import innovation.competition.com.knowsharing.utils.ToastUtil;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.db
 * 文件名：CommunityDatabaseHelper
 * 创建者：
 * 创建时间：2018/9/17 17:05
 * 描述：TODO
 */
public class CommunityDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static final String CREATE_COMMUNITY = "create table Community("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "avator text, "
            + "uploaddate text, "
            + "content text, "
            + "likenumber text, "
            + "commentnumber text)";

    public CommunityDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMUNITY);
        ToastUtil.showShort(mContext,"Create successed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Community");
        onCreate(db);
    }
}
