package innovation.competition.com.knowsharing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import innovation.competition.com.knowsharing.R;

/**
 * 项目名：KnowSharing
 * 包名：adapter
 * 文件名：IndexAdapter
 * 创建者：唐在栋
 * 创建时间：2018/3/10 14:37
 * 描述：首页Adapter
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.ViewHolder> {


    private List<String> date;

    //构造器   接收数据集
    public IndexAdapter(List<String> date) {
        this.date = date;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_index_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IndexAdapter.ViewHolder holder, int position) {

        //将数据填充到具体的view中
        holder.index_title.setText(date.get(position));
        holder.index_introdiction.setText(date.get(position));
        holder.index_uploadtime.setText(date.get(position));
        holder.index_author.setText(date.get(position));
        holder.index_collect.setImageResource(R.drawable.icon_collect);
        holder.index_collect_numbers.setText(date.get(position));
        holder.index_like.setImageResource(R.drawable.icon_work_like);
        holder.index_like_numbers.setText(date.get(position));


        //如果设置了回调  则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position1 = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position1);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position1 = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, position1);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return date.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView index_title;
        public TextView index_introdiction;
        public TextView index_uploadtime;
        public TextView index_author;
        public ImageView index_collect;
        public TextView index_collect_numbers;
        public ImageView index_like;
        public TextView index_like_numbers;


        public ViewHolder(View itemView) {
            super(itemView);

            index_title = itemView.findViewById(R.id.index_title);
            index_introdiction = itemView.findViewById(R.id.index_introdiction);
            index_uploadtime = itemView.findViewById(R.id.index_uploadtime);
            index_author = itemView.findViewById(R.id.index_author);
            index_collect = itemView.findViewById(R.id.index_collect);
            index_collect_numbers = itemView.findViewById(R.id.index_collect_numbers);
            index_like = itemView.findViewById(R.id.index_like);
            index_like_numbers = itemView.findViewById(R.id.index_like_numbers);

        }
    }
    /**
     * 定义接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private IndexAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(IndexAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
