package lzn.chat.main.item.contactItem.chat.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lzn.chat.R;
import lzn.chat.absActivity;
import lzn.chat.main.item.contactItem.RecycleViewDivider;
import lzn.chat.main.item.contactItem.chat.model.MsgModel;
import lzn.chat.main.item.contactItem.chat.presenter.ChatPresenterImpl;
import lzn.chat.main.item.contactItem.chat.presenter.absChatPresenter;

/**
 * Created by Allen on 2016/5/25.
 */
public class ChatActivity extends absActivity implements IChatView, OnClickListener {

    public final static String CHATTOWHO = "ChatToWho";

    private RecyclerView mvRecyclerView;
    private EditText mvInputMsgEditView;
    private Button mvBtnSendMsg;

    private absChatPresenter mvChatPresenter;
    private String mvChatToWho;

    private List<MsgModel> mvList;
    private chatAdapter mvChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        initView();
        mvChatPresenter = new ChatPresenterImpl(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String lvStirng = getIntent().getStringExtra(CHATTOWHO);
        if (!lvStirng.equals("") && lvStirng != null) {
            mvChatToWho = lvStirng;
        }
        initMsgHistory();
        initToolBar();
    }

    @Override
    public void newMsg(List<MsgModel> pMessage) {
        for (MsgModel lvModel: pMessage) {
            if(lvModel.getFrom().equals(mvChatToWho))
            {
                //当前聊天对象发送的消息，直接更新界面
                if (mvChatAdapter == null)
                {
                    List<MsgModel> lvList = new ArrayList<>();
                    lvList.add(lvModel);
                    setAdadpter(lvList);
                }else {
                    mvChatAdapter.addItem(lvModel);
                    mvRecyclerView.smoothScrollToPosition(mvChatAdapter.getItemCount());
                }
            }else {
                //新消息不是当前好友发送，通知栏推送
                NewMsgNotification(lvModel);
            }
        }
    }

    private void initToolBar() {
        Toolbar lvToobar = (Toolbar) this.findViewById(R.id.toolbar);
        lvToobar.setTitle("");
        TextView lvTitlteView = (TextView) this.findViewById(R.id.toolBar_title);
        lvTitlteView.setText(mvChatToWho);
        setSupportActionBar(lvToobar);
    }

    private void initMsgHistory() {
        mvList = mvChatPresenter.getMsgHistory(mvChatToWho);
        if (mvList.size() != 0) {
           setAdadpter(mvList);
        }
    }

    private void initView() {
        mvRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        mvRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);

        mvRecyclerView.setLayoutManager(layoutManager);
        mvInputMsgEditView = (EditText) this.findViewById(R.id.input_message);
        mvBtnSendMsg = (Button) this.findViewById(R.id.bentSendMessage);
        mvBtnSendMsg.setOnClickListener(this);
    }
    @Override
    public void UpdateMsg(MsgModel pMsgModel) {
        if (mvChatAdapter == null)
        {
            List<MsgModel> lvList = new ArrayList<>();
            lvList.add(pMsgModel);
            setAdadpter(lvList);

        } else {
            mvChatAdapter.addItem(pMsgModel);
            mvRecyclerView.smoothScrollToPosition(mvChatAdapter.getItemCount());
        }
    }
    public void setAdadpter(List<MsgModel> pList)
    {
        mvChatAdapter = new chatAdapter(this,pList);
        mvRecyclerView.setAdapter(mvChatAdapter);
        mvRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mvRecyclerView.smoothScrollToPosition(mvChatAdapter.getItemCount());
    }

    @Override
    public void sendMsgSuccessful() {
        Toast.makeText(this, "发送消息成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMsgError(int i, String s) {
        Toast.makeText(this, "发送消息失败！   Error:" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendMsgProgress(int i, String s) {

    }

    @Override
    public void onClick(View pView) {
        switch (pView.getId()) {
            case R.id.bentSendMessage:
                String lvMsgContent = mvInputMsgEditView.getText().toString();
                if (!((ChatPresenterImpl) mvChatPresenter).checkIsEmpty(lvMsgContent)) {
                    mvChatPresenter.sendMsg(lvMsgContent, mvChatToWho);
                    mvInputMsgEditView.setText("");
                }
                break;
        }
    }

}
