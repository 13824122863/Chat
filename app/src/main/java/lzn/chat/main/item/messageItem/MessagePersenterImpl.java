package lzn.chat.main.item.messageItem;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import lzn.chat.main.item.contactItem.chat.model.MsgModel;
import lzn.chat.myApplication;

/**
 * Created by Allen on 2016/5/31.
 */
public class MessagePersenterImpl implements absMessagePresenter {
    private myApplication mvApplication ;
    private Context mvContext;
    public MessagePersenterImpl(Context pContext)
    {
        mvContext = pContext;
        mvApplication = myApplication.getInstance();
    }
    @Override
    public List<MsgModel> getAllChatHistory() {
        List<MsgModel> lvAllList = mvApplication.getDBManager().getAllMsgHistoryFromDb();
        List<MsgModel> lvResultList = new ArrayList<>();
        MsgModel lvMsgModel = new MsgModel() ;
        MsgModel lvNextData = new MsgModel();
        for (int i = 0 ; i < lvAllList.size() ; i++)
        {
            lvMsgModel =  lvAllList.get(i);
            if(i+1 < lvAllList.size())
            {
                lvNextData = lvAllList.get(i+1);
                if(!lvMsgModel.getTo().equals(lvNextData.getTo()))
                {
                    lvResultList.add(lvMsgModel);
                }
            }else {
                lvResultList.add(lvMsgModel);
            }
        }
        return lvResultList;
    }
}
