package innovation.competition.com.knowsharing.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cn.bmob.newim.bean.BmobIMLocationMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import innovation.competition.com.knowsharing.R;
import innovation.competition.com.knowsharing.adapter.base.BaseViewHolder;
import innovation.competition.com.knowsharing.base.ImageLoaderFactory;

/**
 * 接收到的位置类型
 */
public class ReceiveLocationHolder extends BaseViewHolder {

  protected ImageView iv_avatar;
  protected TextView tv_time;
  protected TextView tv_location;

  public ReceiveLocationHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
    super(context, root, R.layout.item_chat_received_location,onRecyclerViewListener);
    iv_avatar = root.findViewById(R.id.iv_avatar);
    tv_time = root.findViewById(R.id.tv_time);
    tv_location = root.findViewById(R.id.tv_location);
  }

  @Override
  public void bindData(Object o) {
    BmobIMMessage msg = (BmobIMMessage)o;
    //用户信息的获取必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
    final BmobIMUserInfo info = msg.getBmobIMUserInfo();
    //加载头像
    ImageLoaderFactory.getLoader().loadAvator(iv_avatar,info != null ? info.getAvatar() : null, R.mipmap.head);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    String time = dateFormat.format(msg.getCreateTime());
    tv_time.setText(time);
    //
    final BmobIMLocationMessage message = BmobIMLocationMessage.buildFromDB(msg);
    tv_location.setText(message.getAddress());
    //
    tv_location.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toast("经度：" + message.getLongitude() + ",维度：" + message.getLatitude());
        if(onRecyclerViewListener!=null){
          onRecyclerViewListener.onItemClick(getAdapterPosition());
        }
      }
    });
    tv_location.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        if (onRecyclerViewListener != null) {
          onRecyclerViewListener.onItemLongClick(getAdapterPosition());
        }
        return true;
      }
    });

    iv_avatar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toast("点击"+info.getName()+"头像");
      }
    });
  }

  public void showTime(boolean isShow) {
    tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
  }
}