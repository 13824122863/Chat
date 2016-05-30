package lzn.chat.main.item.contactItem.chat.model;

/**
 * Created by Allen on 2016/5/25.
 */
public class ChatUserModel {
    private String mvMsgCotent;      //消息内容
    private String mvIconUrl;       //头像URL
    private String mvTime;          //聊天时间
    private boolean mvIsSender;      //是否为发送者

    public boolean isSender() {
        return mvIsSender;
    }

    public void setIsSend(boolean pIsSend) {
        mvIsSender = pIsSend;
    }

    public String getTime() {
        return mvTime;
    }

    public void setTime(String pTime) {
        mvTime = pTime;
    }
    public String getMsgCotent() {
        return mvMsgCotent;
    }

    public void setMsgCotent(String pMsgCotent) {
        mvMsgCotent = pMsgCotent;
    }

    public String getIconUrl() {
        return mvIconUrl;
    }

    public void setIconUrl(String pIconUrl) {
        mvIconUrl = pIconUrl;
    }
}
