package innovation.competition.com.knowsharing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import innovation.competition.com.knowsharing.R;

/**
 * 项目名：KnowSharing
 * 包名：innovation.competition.com.knowsharing.fragment
 * 文件名：MenuFragment
 * 创建者：
 * 创建时间：2018/6/14 16:08
 * 描述：TODO
 */
public class MenuFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        return view;
    }
}
