package lzn.chat.main.item.contactItem.chat.presenter;

import java.util.List;

import lzn.chat.main.item.contactItem.chat.model.MsgModel;

/**
 * Created by Allen on 2016/5/25.
 */
public interface absChatPresenter {
    /**
     * @param pMsg 发送消息的内容;
     * @param pToWho  发送给谁；
     * **/
    void sendMsg(String pMsg , String pToWho);

    List<MsgModel> getMsgHistory(String pToWho);


}
