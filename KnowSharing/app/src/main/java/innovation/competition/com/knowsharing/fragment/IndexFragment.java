package innovation.competition.com.knowsharing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.adapter.IndexAdapter;
import innovation.competition.com.knowsharing.ui.CommentActivity;

/**
 * 项目名：KnowSharing
 * 包名：fragment
 * 文件名：IndexFragment
 * 创建者：
 * 创建时间：2018/3/11 22:50
 * 描述：TODO
 */

public class IndexFragment extends Fragment {

    private RecyclerView index_recyclerView;
    private IndexAdapter indexAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index,container,false);

        initDate();
        initView(view);
        return view;
    }



    private void initView(View view) {

        index_recyclerView = view.findViewById(R.id.index_recyclerView);
        index_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        layoutManager = new LinearLayoutManager(getContext());
        index_recyclerView.setLayoutManager(layoutManager);
        indexAdapter = new IndexAdapter(list);
        index_recyclerView.setAdapter(indexAdapter);
        indexAdapter.setOnItemClickListener(new IndexAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getContext(),"onItemClick",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), CommentActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"onItemLongClick",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initDate() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("测试数据");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
