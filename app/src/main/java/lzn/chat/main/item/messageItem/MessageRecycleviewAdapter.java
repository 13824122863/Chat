package lzn.chat.main.item.messageItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lzn.chat.R;
import lzn.chat.main.item.contactItem.chat.model.MsgModel;


/**
 * Created by Allen on 2016/5/30.
 */
public class MessageRecycleviewAdapter extends RecyclerView.Adapter<MessageRecycleviewAdapter.MessageHolder> implements OnClickListener {
    private Context mvContext;
    private List<MsgModel> mvList;

    public MessageRecycleviewAdapter(Context pContext , List<MsgModel> pList)
    {
        mvContext = pContext;
        mvList = pList;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lvView = LayoutInflater.from(mvContext).inflate(R.layout.message_recycleview_item,null);
        lvView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MessageHolder(lvView) ;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.mvContentView.setText(mvList.get(position).getContent());
        holder.mvTimeView.setText(mvList.get(position).getReceiveTime());
        holder.mvNameView.setText(mvList.get(position).getFrom());
        holder.mvRootLayout.setTag(mvList.get(position));
    }
    public void addItem(List<MsgModel> pMessage)
    {
        mvList.addAll(pMessage);
        notifyItemRangeChanged(0, pMessage.size());
    }
    @Override
    public int getItemCount() {
        return mvList.size();
    }

    @Override
    public void onClick(View pView) {
        switch (pView.getId())
        {
            case R.id.rootLayout:
                Toast.makeText(mvContext,"Rootlayout Click!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class MessageHolder extends RecyclerView.ViewHolder
    {
        public ImageView mvIconView;
        public TextView mvNameView;
        public TextView mvTimeView;
        public TextView mvContentView;
        public LinearLayout mvRootLayout;
        public MessageHolder(View pView) {
            super(pView);

            mvIconView = (ImageView) pView.findViewById(R.id.icon);
            mvNameView = (TextView) pView.findViewById(R.id.name_view);
            mvTimeView = (TextView) pView.findViewById(R.id.time_view);
            mvContentView = (TextView) pView.findViewById(R.id.content);
            mvRootLayout = (LinearLayout) pView.findViewById(R.id.rootLayout);
            mvRootLayout.setOnClickListener(MessageRecycleviewAdapter.this);
        }
    }
}
