package lzn.chat.main.item.contactItem.chat.model;

import java.io.Serializable;

/**
 * Created by Allen on 2016/5/30.
 */
public class ReceiveMsgModel implements Serializable {
    private String mvContent;   //聊天内容
    private String mvReceiveTime;  //接收时间
    private String mvFrom;   //谁发送
    private String mvTo;    //发送给谁

    public String getContent() {
        return mvContent;
    }

    public void setContent(String pContent) {
        mvContent = pContent;
    }

    public String getReceiveTime() {
        return mvReceiveTime;
    }

    public void setReceiveTime(String pReceiveTime) {
        mvReceiveTime = pReceiveTime;
    }

    public String getFrom() {
        return mvFrom;
    }

    public void setFrom(String pFrom) {
        mvFrom = pFrom;
    }

    public String getTo() {
        return mvTo;
    }

    public void setTo(String pTo) {
        mvTo = pTo;
    }
}
