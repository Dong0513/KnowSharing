package innovation.competition.com.knowsharing.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import de.hdodenhof.circleimageview.CircleImageView;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.adapter.CommunityAdapter;
import innovation.competition.com.knowsharing.db.CommunityDatabaseHelper;
import innovation.competition.com.knowsharing.entity.CommunityData;
import innovation.competition.com.knowsharing.entity.MyUser;
import innovation.competition.com.knowsharing.entity.Post;
import innovation.competition.com.knowsharing.utils.ToastUtil;

/**
 * 项目名：KnowSharing
 * 包名：fragment
 * 文件名：CommunityFragment
 * 创建者：
 * 创建时间：2018/3/11 22:55
 * 描述：社区
 */

public class CommunityFragment extends Fragment {

    //@BindView(R.id.community_avator)
    CircleImageView community_avator;
    //@BindView(R.id.community_author)
    TextView community_author;
    //@BindView(R.id.community_content)
    TextView community_content;
    //@BindView(R.id.community_uploadtime)
    TextView community_uploadtime;
    //@BindView(R.id.community_comment)
    TextView community_comment;
    //@BindView(R.id.community_comment_numbers)
    TextView community_comment_numbers;
    //@BindView(R.id.community_like)
    ImageView community_like;
    //@BindView(R.id.community_like_numbers)
    TextView community_like_numbers;
    //@BindView(R.id.mListView)
    RecyclerView mRecyclerView;

    List<CommunityData> mList;
    CommunityAdapter mAdapter;
    Context mContext;
    CommunityDatabaseHelper dbHelper;
    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    BmobQuery<Post> query = new BmobQuery<>();
    CommunityData data = new CommunityData();


    String bql ="select * from Post";//查询所有的游戏得分记录



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community,container,false);
        community_avator = view.findViewById(R.id.community_avator);
        community_author = view.findViewById(R.id.community_author);
        community_content = view.findViewById(R.id.community_content);
        community_uploadtime = view.findViewById(R.id.community_uploadtime);
        community_comment = view.findViewById(R.id.community_comment);
        community_comment_numbers = view.findViewById(R.id.community_comment_numbers);
        community_like = view.findViewById(R.id.community_like);
        community_like_numbers = view.findViewById(R.id.community_like_numbers);
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        //ButterKnife.bind(this, view);
        dbHelper = new CommunityDatabaseHelper(getActivity(),"Community.db",null,1);
        getCommunityDataList();
        initView();
        return view;
    }

    private void initView() {
        //点击事件
        /*mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }


    private void getCommunityDataList() {
        /*//查询Community表的所有数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Community",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                //遍历cursor对象，取出数据
                String com_author = cursor.getString(cursor.getColumnIndex("author"));
                String com_avator = cursor.getString(cursor.getColumnIndex("avator"));
                String com_uploaddate = cursor.getString(cursor.getColumnIndex("uploaddate"));
                String com_text_content = cursor.getString(cursor.getColumnIndex("content"));
                CommunityData data = new CommunityData();
                //Glide.with(mContext).load(com_avator).into(community_avator);
                data.setCom_author(com_author);
                data.setCom_text_content(com_text_content);
                data.setCom_uploaddate(com_uploaddate);
                mList.add(data);
            }while (cursor.moveToNext());
        }
        cursor.close();
        mAdapter.notifyDataSetChanged();*/

        //查询Post表中的所有数据
        new BmobQuery<Post>().doSQLQuery(bql, new SQLQueryListener<Post>() {
            @Override
            public void done(BmobQueryResult<Post> bmobQueryResult, BmobException e) {
                if (e == null){

                    List<Post> list = bmobQueryResult.getResults();
                    if (list != null && list.size()>0){
                        for (int i = 0; i<=list.size(); i++){


                            ToastUtil.showShort(getActivity(),"list数据值："+list.toString());
                        }
                        ToastUtil.showShort(getActivity(),"查询成功："+list);
                    }else {
                        ToastUtil.showShort(getActivity(),"查询为空");
                    }
                }else {
                    ToastUtil.showShort(getActivity(),"连接异常："+e.getMessage());
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });

        /*//查询当前用户的数据
        query.addWhereEqualTo("author",myUser.getObjectId());
        query.setLimit(50);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null){
                    ToastUtil.showShort(getActivity(),"查询成功：共"+list.size()+"条数据。");
                }else {

                }
            }
        });*/

    }


}
