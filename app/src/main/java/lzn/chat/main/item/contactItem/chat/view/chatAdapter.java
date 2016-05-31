package lzn.chat.main.item.contactItem.chat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lzn.chat.R;
import lzn.chat.main.item.contactItem.chat.model.MsgModel;
import lzn.chat.myApplication;

/**
 * Created by Allen on 2016/5/25.
 */
public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ChatHolder> {
    public static  enum ITEM_TYPE
    {
        RECEIVE,
        SEND
    }
    private List<MsgModel> mvList = new ArrayList<MsgModel>();
    private Context mvContext;

    public chatAdapter(Context pContext ,List<MsgModel> pList)
    {
        mvContext = pContext;
        mvList = pList;
    }
    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lvView ;
        if(viewType == ITEM_TYPE.RECEIVE.ordinal())
        {
            lvView = LayoutInflater.from(mvContext).inflate(R.layout.chat_layout_left_item,null);
        }else
        {
            lvView = LayoutInflater.from(mvContext).inflate(R.layout.chat_layout_right_item,null);
        }
        lvView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ChatHolder(lvView);
    }

    @Override
    public int getItemViewType(int position) {
        boolean lvResult = false;
        if(mvList.size() != 0)
        {
            lvResult = mvList.get(position).getFrom().equals(myApplication.getInstance().getAccountName());
        }
        return lvResult ? ITEM_TYPE.SEND.ordinal() : ITEM_TYPE.RECEIVE.ordinal();
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        if(mvList.get(position) != null)
        {
            holder.mvMsgContent.setText(mvList.get(position).getContent());
            holder.mvTimeView.setText(mvList.get(position).getReceiveTime());
        }

    }
    public void addItem(MsgModel pMsgModel)
    {
        mvList.add(pMsgModel);
        notifyItemInserted(mvList.size() + 1);
    }
    @Override
    public int getItemCount() {
        return mvList.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder
    {
        TextView mvMsgContent;
        ImageView mvIconView;
        TextView mvTimeView;
        public ChatHolder(View pView) {
            super(pView);

            mvMsgContent = (TextView) pView.findViewById(R.id.msgView);
            mvIconView = (ImageView) pView.findViewById(R.id.user_icon);
            mvTimeView = (TextView) pView.findViewById(R.id.time);
        }
    }
}
