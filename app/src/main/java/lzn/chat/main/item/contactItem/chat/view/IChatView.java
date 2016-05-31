package lzn.chat.main.item.contactItem.chat.view;

import lzn.chat.main.item.contactItem.chat.model.MsgModel;

/**
 * Created by Allen on 2016/5/25.
 */
public interface IChatView  {
    void UpdateMsg(MsgModel pMsgModel);
    void sendMsgSuccessful();
    void sendMsgError(int i , String s);
    void sendMsgProgress(int i ,String s);
}
