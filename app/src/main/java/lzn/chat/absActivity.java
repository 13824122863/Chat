package lzn.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lzn.chat.main.item.contactItem.chat.model.ReceiveMsgModel;
import lzn.chat.tools.ConstantManager;
import lzn.chat.tools.Utils;

/**
 * Created by Allen on 2016/5/24.
 */
public abstract class absActivity extends AppCompatActivity {
    protected myApplication mvApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明，经测试在代码里直接声明透明状态栏更有效
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        mvApplication = myApplication.getInstance();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();

    }

    private void registerReceiver() {
        IntentFilter lvIntentFliter = new IntentFilter();
        lvIntentFliter.addAction(ConstantManager.CHAT_RECEIVER);
        registerReceiver(mvChatListenertReceiver, lvIntentFliter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mvChatListenertReceiver);
    }

    BroadcastReceiver mvChatListenertReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent pIntent) {
            if(pIntent.getAction().equals(ConstantManager.CHAT_RECEIVER))
            {
               List<ReceiveMsgModel> lvList = (ArrayList) pIntent.getExtras().getSerializable(ConstantManager.CHAT_RECEIVE_MSG_LIST);
                newMsg(lvList);
            }
        }
    };
    public abstract void newMsg(List<ReceiveMsgModel> pMessage);
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> pMessage) {
            //收到消息,应该先存消息到数据库SQLite！
            Intent lvIntent = new Intent();
            lvIntent.setAction(ConstantManager.CHAT_RECEIVER);
            Bundle lvBundle = new Bundle();
            lvBundle.putSerializable(ConstantManager.CHAT_RECEIVE_MSG_LIST, getSerializable(pMessage));
            lvIntent.putExtras(lvBundle);
            sendBroadcast(lvIntent);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    private Serializable getSerializable(List<EMMessage> pMessage) {
        ArrayList<ReceiveMsgModel> lvList = new ArrayList<>();
        ReceiveMsgModel lvMsgModel;
        for (EMMessage lvMsg:pMessage) {
            lvMsgModel = new ReceiveMsgModel();
            lvMsgModel.setContent(Utils.replaceMsgContent(lvMsg.getBody().toString()));
            lvMsgModel.setFrom(lvMsg.getFrom());
            lvMsgModel.setTo(lvMsg.getTo());
            lvMsgModel.setReceiveTime(Utils.long2String(lvMsg.getMsgTime()));
            lvList.add(lvMsgModel);
        }
        return lvList;
    }


}
