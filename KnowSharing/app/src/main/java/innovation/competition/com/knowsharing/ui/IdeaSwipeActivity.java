package innovation.competition.com.knowsharing.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import innovation.competition.com.knowsharing.MainActivity;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.adapter.CommunityAdapter;
import innovation.competition.com.knowsharing.db.CommunityDatabaseHelper;
import innovation.competition.com.knowsharing.entity.CommunityData;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.entity.Post;
import innovation.competition.com.knowsharing.entity.UserData;
import innovation.competition.com.knowsharing.utils.GetDate;
import innovation.competition.com.knowsharing.utils.KeyBoardUtil;
import innovation.competition.com.knowsharing.utils.ToastUtil;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.activity
 * 文件名：IdeaSwipeActivity
 * 创建者：
 * 创建时间：2018/8/10 21:10
 * 描述：发文字界面。
 */
public class IdeaSwipeActivity extends AppCompatActivity {

    private Context mContext;
    private TextView mCancalButton;
    private TextView mSendButton;
    private EditText idea_content;
    private ImageView upload_file;
    private ImageView upload_picture;
    private TextView fileList;
    private RecyclerView pictureList;


    CommunityAdapter communityAdapter;
    List<CommunityData> communityDataList;
    CommunityDatabaseHelper dbHelper;

    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    UserData userData = new UserData();
    Post post = new Post();



    String url = "http://119.29.4.201/knowsharing/ideaswipe.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_idea_layout);
        mContext = this;
        initView();
        dbHelper = new CommunityDatabaseHelper(this,"Community.db",null,1);
    }


    private void initView() {
        mCancalButton = findViewById(R.id.idea_cancal);
        mSendButton = findViewById(R.id.idea_send);
        idea_content = findViewById(R.id.idea_content);
        upload_file = findViewById(R.id.upload_file);
        upload_picture = findViewById(R.id.upload_picture);
        fileList = findViewById(R.id.fileList);
        pictureList = findViewById(R.id.pictureList);

        onClick();

    }

    /**
     * 设置监听事件
     */
    private void onClick() {
        mCancalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.closeKeybord(idea_content, mContext);
                Intent intent = new Intent(IdeaSwipeActivity.this, MainActivity.class);
                intent.putExtra("menufragment_id",1);
                startActivity(intent);
                finish();
            }
        });


        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果发的没有图片，且也没有文本内容，识别为空
                if ((idea_content.getText().toString().isEmpty() || idea_content.getText().toString().length() == 0)) {
                    ToastUtil.showShort(mContext, "发送的内容不能为空");
                    return;
                }else {
                    try {
                        final String uploaddate = GetDate.getDate().toString();
                        final String author = myUser.getNickname();
                        final String avator = myUser.getAvatar();
                        final String idea_content_text = idea_content.getText().toString();

                        //上传到云数据库Post表
                        post.setContent(idea_content_text);
                        post.setAuthor(myUser);
                        post.setUploaddate(uploaddate);
                        post.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null){
                                    ToastUtil.showShort(IdeaSwipeActivity.this,"发表成功");
                                    Intent intent = new Intent(IdeaSwipeActivity.this, MainActivity.class);
                                    intent.putExtra("menufragment_id",2);
                                    startActivity(intent);
                                    // 存储到本地
                                    // CommunityFragment 获取到云端数据并加载，用户发帖后先上传服务器，成功后存储到本地 并设置到页面上  ListView、
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("author",author);
                                    values.put("avator",avator);
                                    values.put("uploaddate", uploaddate);
                                    values.put("content", idea_content_text);
                                    //点赞数和评论数
                                    db.insert("Community", null, values);
                                    values.clear();
                                    finish();
                                    //Toast.makeText(IdeaSwipeActivity.this,"发表成功",Toast.LENGTH_SHORT).show();


                                }else {
                                    ToastUtil.showShort(IdeaSwipeActivity.this,"发表失败："+e.getErrorCode());
                                }
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(IdeaSwipeActivity.this,"发表失败，请重试",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }





                }

                KeyBoardUtil.closeKeybord(idea_content, mContext);
                finish();
            }
        });



        upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        upload_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureChooser();
            }
        });



    }




    private void showFileChooser() {

        /*Intent intent4 = new Intent(this, NormalFilePickActivity.class);
        intent4.putExtra(Constant.MAX_NUMBER, 9);
        intent4.putExtra(IS_NEED_FOLDER_LIST, true);
        intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"});
        startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);*/


    }

    private void showPictureChooser() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /*ArrayList<NormalFile> file_list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);

            StringBuilder builder = new StringBuilder();
            for (NormalFile file : file_list) {
                String path = file.getPath();
                builder.append(path + "\n");
            }
            fileList.setText(builder.toString());*/


        }





    }

}
