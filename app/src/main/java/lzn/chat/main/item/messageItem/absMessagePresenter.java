package lzn.chat.main.item.messageItem;

import java.util.List;

import lzn.chat.main.item.contactItem.chat.model.MsgModel;

/**
 * Created by Allen on 2016/5/31.
 */
public interface absMessagePresenter {
    List<MsgModel> getAllChatHistory();
}
