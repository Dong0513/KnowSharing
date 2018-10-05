package innovation.competition.com.knowsharing.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import innovation.competition.com.knowsharing.R;

import innovation.competition.com.knowsharing.utils.ToastUtil;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.ui
 * 文件名：PostSwipeActivity
 * 创建者：
 * 创建时间：2018/8/10 18:20
 * 描述：底部菜单栏中间加号的页面
 */
public class PostSwipeActivity extends AppCompatActivity {



    //文字
    private ImageView composeIdea;
    //照片/视频
    private ImageView composePhoto;
    //头条文章
    private ImageView composeHeadlines;
    //签到
    private ImageView composeLbs;
    //点评
    private ImageView composeReview;
    //更多
    private ImageView composeMore;
    //关闭
    private ImageView composeClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postswipe);
        composeIdea = findViewById(R.id.compose_idea);
        composePhoto = findViewById(R.id.compose_photo);
        composeHeadlines = findViewById(R.id.compose_headlines);
        composeLbs = findViewById(R.id.compose_lbs);
        composeReview = findViewById(R.id.compose_review);
        composeMore = findViewById(R.id.compose_more);
        composeClose = findViewById(R.id.compose_close);





        composeIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostSwipeActivity.this, IdeaSwipeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        composePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(PostSwipeActivity.this, "正在开发中...");
            }
        });
        composeHeadlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(PostSwipeActivity.this, "正在开发中...");
            }
        });
        composeLbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(PostSwipeActivity.this, "正在开发中...");
            }
        });
        composeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(PostSwipeActivity.this, "正在开发中...");
            }
        });
        composeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(PostSwipeActivity.this, "正在开发中...");
            }
        });
        composeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}
