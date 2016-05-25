package lzn.chat.main.item.contactItem.chat;

import android.content.Context;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2016/5/25.
 */
public class ChatPresenterImpl implements absChatPresenter {
    private IChatView mvIChatView;
    private Context mvContext;

    public ChatPresenterImpl(Context pContext, IChatView pIChatView) {
        mvContext = pContext;
        mvIChatView = pIChatView;
    }

    @Override
    public void sendMsg(String pMsg, String pToWho) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(pMsg, pToWho);
        message.setMessageStatusCallback(MsgCallBackListener);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    @Override
    public List<ChatUserModel> getMsgHistory(String pToWho) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(pToWho);
        List<ChatUserModel> lvList = new ArrayList<ChatUserModel>();
        ChatUserModel lvUserModel;
        if (conversation != null) {
            //获取此会话的所有消息
            List<EMMessage> messages = conversation.getAllMessages();
            //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
            //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
            // List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
            for (EMMessage lvMessage : messages) {
                lvUserModel = new ChatUserModel();
                lvUserModel.setMsgCotent(lvMessage.getBody().toString());
                lvUserModel.setTime((String.valueOf(lvMessage.getMsgTime())));
                lvList.add(lvUserModel);
            }
        }
        return lvList;
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
