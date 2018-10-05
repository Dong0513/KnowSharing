package innovation.competition.com.knowsharing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.entity.CommunityData;
import innovation.competition.com.knowsharing.entity.MyUser;

/**
 * 项目名：KnowSharing
 * 包名：adapter
 * 文件名：CommunityAdapter
 * 创建者：
 * 创建时间：2018/3/10 14:37
 * 描述：社区列表Adapter
 */

public class CommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<CommunityData> mList;
    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);


    //自定义监听事件
    public abstract interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public CommunityAdapter(Context mContext, List<CommunityData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_community_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        //设置点击监听
        //view.setOnClickListener();
        //view.setOnLongClickListener();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = null;
        //数据与item绑定
        Glide.with(mContext).load(myUser.getAvatar()).into(viewHolder.com_avator);
        viewHolder.com_author.setText(mList.get(position).getCom_author());
        viewHolder.com_text_content.setText(mList.get(position).getCom_text_content());
        viewHolder.com_uploaddate.setText(mList.get(position).getCom_uploaddate());
        viewHolder.com_like_numbers.setText(mList.get(position).getCom_like_numbers());
        viewHolder.com_comment_numbers.setText(mList.get(position).getCom_comment_numbers());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //点击事件回调
    public void onClick(View v){
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v);
        }
    }
    public boolean onItemClick(View v){
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView com_avator;
        TextView com_author;
        TextView com_text_content;
        TextView com_uploaddate;
        TextView com_like_numbers;
        TextView com_comment_numbers;

        public MyViewHolder(View itemView) {
            super(itemView);
            com_avator = itemView.findViewById(R.id.community_avator);
            com_author = itemView.findViewById(R.id.community_author);
            com_text_content = itemView.findViewById(R.id.community_content);
            com_uploaddate = itemView.findViewById(R.id.community_uploadtime);
            com_like_numbers = itemView.findViewById(R.id.community_like_numbers);
            com_comment_numbers = itemView.findViewById(R.id.community_comment_numbers);

        }
    }

    /*private Context mContext;
    private LayoutInflater inflater;
    private List<CommunityData> mList;
    private CommunityData data;

    private MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

    public CommunityAdapter(Context mContext, List<CommunityData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_community_item,null);
            viewHolder.com_avator = convertView.findViewById(R.id.community_avator);
            viewHolder.com_author = convertView.findViewById(R.id.community_author);
            viewHolder.com_text_content = convertView.findViewById(R.id.community_content);
            viewHolder.com_uploaddate = convertView.findViewById(R.id.community_uploadtime);
            viewHolder.com_like_numbers = convertView.findViewById(R.id.community_like_numbers);
            viewHolder.com_comment_numbers = convertView.findViewById(R.id.com_comment_numbers);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        Glide.with(mContext).load(myUser.getAvatar()).into(viewHolder.com_avator);
        viewHolder.com_author.setText(data.getCom_author());
        viewHolder.com_text_content.setText(data.getCom_text_content());
        viewHolder.com_uploaddate.setText(data.getCom_uploaddate());
        viewHolder.com_like_numbers.setText(data.getCom_like_numbers());
        viewHolder.com_comment_numbers.setText(data.getCom_comment_numbers());

        return convertView;
    }

    class ViewHolder {
        private CircleImageView com_avator;
        private TextView com_author;
        private TextView com_text_content;
        private TextView com_uploaddate;
        private TextView com_like_numbers;
        private TextView com_comment_numbers;


    }*/
}
