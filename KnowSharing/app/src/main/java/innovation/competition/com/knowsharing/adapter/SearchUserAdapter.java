package innovation.competition.com.knowsharing.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import innovation.competition.com.knowsharing.adapter.base.BaseViewHolder;
import innovation.competition.com.knowsharing.entity.MyUser;

/**
 */

public class SearchUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MyUser> users = new ArrayList<>();

    public SearchUserAdapter() {
    }

    public void setDatas(List<MyUser> list) {
        users.clear();
        if (null != list) {
            users.addAll(list);
        }
    }

    /**获取用户
     * @param position
     * @return
     */
    public MyUser getItem(int position){
        return users.get(position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchUserHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bindData(users.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}