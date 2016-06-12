package lzn.chat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.WindowManager;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lzn.chat.db.DBManager;
import lzn.chat.main.item.contactItem.chat.model.MsgModel;
import lzn.chat.main.item.contactItem.chat.view.ChatActivity;
import lzn.chat.main.view.MainActivity;
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
            if (pIntent.getAction().equals(ConstantManager.CHAT_RECEIVER)) {
                List<MsgModel> lvList = (ArrayList) pIntent.getExtras().getSerializable(ConstantManager.CHAT_RECEIVE_MSG_LIST);
                DBManager lvDBManager = mvApplication.getDBManager();
                lvDBManager.addChatMsg(lvList);
                newMsg(lvList);
            }
        }
    };

    public abstract void newMsg(List<MsgModel> pMessage);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    public EMMessageListener msgListener = new EMMessageListener() {

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
        ArrayList<MsgModel> lvList = new ArrayList<>();
        MsgModel lvMsgModel;
        for (EMMessage lvMsg : pMessage) {
            lvMsgModel = new MsgModel();
            lvMsgModel.setContent(Utils.replaceMsgContent(lvMsg.getBody().toString()));
            lvMsgModel.setFrom(lvMsg.getFrom());
            lvMsgModel.setTo(lvMsg.getTo());
            lvMsgModel.setReceiveTime(Utils.long2String(lvMsg.getMsgTime()));
            lvList.add(lvMsgModel);
        }
        return lvList;
    }

    public void NewMsgNotification(List<MsgModel> pModelList) {
        for (MsgModel lvModel : pModelList) {
            this.NewMsgNotification(lvModel);
        }
    }

    public void NewMsgNotification(MsgModel pMsgModel) {

        NotificationCompat.Builder lvBuilder = new NotificationCompat.Builder(this);
        NotificationManager lvManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        lvBuilder.setContentTitle(pMsgModel.getFrom());
        lvBuilder.setContentText(pMsgModel.getContent());
        lvBuilder.setSmallIcon(R.drawable.account_icon);
        lvBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent lvIntent1 = new Intent(this, ChatActivity.class);
        lvIntent1.putExtra(ChatActivity.CHATTOWHO, pMsgModel.getFrom());
        lvIntent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent lvIntent0 = new Intent(this, MainActivity.class);

        Intent[] lvIntents = new Intent[2];
        lvIntents[0] = lvIntent0;
        lvIntents[1] = lvIntent1;

        PendingIntent resultPendingIntent = PendingIntent.getActivities(this, 0, lvIntents, PendingIntent.FLAG_CANCEL_CURRENT);
        lvBuilder.setContentIntent(resultPendingIntent);
//        lvBuilder.setFullScreenIntent(resultPendingIntent, true);
        lvBuilder.setAutoCancel(true);
        lvBuilder.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        lvManager.notify(1, lvBuilder.build() );

    }

}
