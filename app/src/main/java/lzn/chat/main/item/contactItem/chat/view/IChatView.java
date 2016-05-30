package lzn.chat.main.item.contactItem.chat.view;

/**
 * Created by Allen on 2016/5/25.
 */
public interface IChatView  {
    /**
     * @param pMsg 消息内容；
     * @param pIsReceive 是否为接收
     * **/
    void UpdateMsg(String pMsg , boolean pIsReceive);
    void sendMsgSuccessful();
    void sendMsgError(int i , String s);
    void sendMsgProgress(int i ,String s);
}
