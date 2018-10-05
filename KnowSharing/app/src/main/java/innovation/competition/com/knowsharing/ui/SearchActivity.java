package innovation.competition.com.knowsharing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import innovation.competition.com.knowsharing.R;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.ui
 * 文件名：SearchActivity
 * 创建者：
 * 创建时间：2018/8/24 18:43
 * 描述：TODO
 */
public class SearchActivity extends AppCompatActivity {

    private EditText search_user;
    private Button search_user_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        onClick();
    }

    private void initView() {

        search_user = findViewById(R.id.search_user);
        search_user_button = findViewById(R.id.search_user_button);

    }

    private void onClick() {

        search_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_user_content = search_user.getText().toString().trim();



            }
        });

    }






}
