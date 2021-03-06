package lzn.chat.main.item.contactItem.chat.presenter;

import android.content.Context;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import lzn.chat.db.DBManager;
import lzn.chat.main.item.contactItem.chat.model.MsgModel;
import lzn.chat.main.item.contactItem.chat.view.ChatActivity;
import lzn.chat.main.item.contactItem.chat.view.IChatView;
import lzn.chat.myApplication;
import lzn.chat.tools.Utils;

/**
 * Created by Allen on 2016/5/25.
 */
public class ChatPresenterImpl implements absChatPresenter {
    private IChatView mvIChatView;
    private Context mvContext;
    private DBManager mvDBManager;

    public ChatPresenterImpl(Context pContext, IChatView pIChatView) {
        mvContext = pContext;
        mvIChatView = pIChatView;
        mvDBManager =  new DBManager(mvContext);
    }

    @Override
    public void sendMsg(String pMsg, String pToWho) {
        MsgModel lvModel = packageModel(pMsg,pToWho);
        mvIChatView.UpdateMsg(lvModel);
        //先把要发送的消息存到Sqlite
        mvDBManager.addChatMsg(lvModel);
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(pMsg, pToWho);
        message.setMessageStatusCallback(MsgCallBackListener);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    private MsgModel packageModel(String pMsg, String pToWho) {
        MsgModel lvMsgModel = new MsgModel();
        lvMsgModel.setReceiveTime(Utils.getCurrentDateString());
        lvMsgModel.setContent(pMsg);
        lvMsgModel.setTo(pToWho);
        lvMsgModel.setFrom(myApplication.getInstance().getAccountName());
        return lvMsgModel;
    }

    @Override
    public List<MsgModel> getMsgHistory(String pToWho) {

        return mvDBManager.getMsgHistoryFromDb(pToWho);

//        List<MsgModel> lvList = new ArrayList<MsgModel>();
//        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(pToWho);
//        MsgModel lvUserModel;
//        if (conversation != null) {
//            //获取此会话的所有消息
//            List<EMMessage> messages = conversation.getAllMessages();
//            //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
//            //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//            // List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
//            for (EMMessage lvMessage : messages) {
//                lvUserModel = new MsgModel();
//
//                lvUserModel.setContent(Utils.replaceMsgContent(lvMessage.getBody().toString()));
//                lvUserModel.setReceiveTime(Utils.long2String(lvMessage.getMsgTime()));
//                lvUserModel.setTo(lvMessage.getTo());
//                lvUserModel.setFrom(lvMessage.getFrom());
//                lvList.add(lvUserModel);
//            }
//        }

    }


    EMCallBack MsgCallBackListener = new EMCallBack() {
        @Override
        public void onSuccess() {
            ((ChatActivity) mvContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mvIChatView.sendMsgSuccessful();
                }
            });

        }

        @Override
        public void onError(int i, String s) {
            mvIChatView.sendMsgError(i, s);
        }

        @Override
        public void onProgress(int i, String s) {
            mvIChatView.sendMsgProgress(i, s);
        }
    };

    public boolean checkIsEmpty(String pContent) {
        boolean lvResult = true;

        if (!pContent.equals("") && pContent != null) {
            lvResult = false;
        }
        return lvResult;

    }
}
