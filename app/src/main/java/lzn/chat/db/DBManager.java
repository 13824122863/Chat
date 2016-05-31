package lzn.chat.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lzn.chat.main.item.contactItem.chat.model.MsgModel;

/**
 * Created by Allen on 2016/5/31.
 */
public class DBManager {
    DBHelper mvDBHelper;
    private SQLiteDatabase mvReadDatabase;
    private SQLiteDatabase mvWriteDatabase;

    public DBManager(Context pContext) {
        mvDBHelper = new DBHelper(pContext);
        mvReadDatabase = mvDBHelper.getReadableDatabase();
        mvWriteDatabase = mvDBHelper.getWritableDatabase();
    }


    public void addChatMsg(List<MsgModel> pList) {
        String sql = "insert into " + mvDBHelper.CHATMSGTABLE + " values (?,?,?,?)";
        mvWriteDatabase.beginTransaction();
        try {
            for (MsgModel lvModel : pList) {
                mvWriteDatabase.execSQL(sql, new Object[]{lvModel.getContent(), lvModel.getReceiveTime(), lvModel.getFrom(), lvModel.getTo()});
            }
            mvWriteDatabase.setTransactionSuccessful(); //一定要调用这句话，不然插进去数据读取不出来
        }catch (Exception e){
            e.printStackTrace();
            Log.e("insert Error",e.getMessage());
        }finally {
            mvWriteDatabase.endTransaction();
        }
    }

    public void addChatMsg(MsgModel pMsgModel) {
        String sql = "insert into " + mvDBHelper.CHATMSGTABLE + " values (?,?,?,?)";
        mvWriteDatabase.beginTransaction();
        try {
            mvWriteDatabase.execSQL(sql, new Object[]{pMsgModel.getContent(), pMsgModel.getReceiveTime(), pMsgModel.getFrom(), pMsgModel.getTo()});
            mvWriteDatabase.setTransactionSuccessful(); //一定要调用这句话，不然插进去数据读取不出来
        }catch (Exception e){
            e.printStackTrace();
            Log.e("insert Error",e.getMessage());
        }finally {
            mvWriteDatabase.endTransaction();
        }


    }

    public List<MsgModel> getMsgHistoryFromDb(String pWho) {
        List<MsgModel> lvList = new ArrayList<>();
        MsgModel lvModel;
        Cursor lvCurso = queryTheCursor(pWho);
        while (lvCurso.moveToNext()) {
            lvModel = new MsgModel();
            lvModel.setFrom(lvCurso.getString(lvCurso.getColumnIndex("msgFrom")));
            lvModel.setTo(lvCurso.getString(lvCurso.getColumnIndex("msgTo")));
            lvModel.setContent(lvCurso.getString(lvCurso.getColumnIndex("msgContent")));
            lvModel.setReceiveTime(lvCurso.getString(lvCurso.getColumnIndex("msgTime")));
            lvList.add(lvModel);
        }
        lvCurso.close();
        return lvList;
    }

    private Cursor queryTheCursor(String pWho) {
        String querySql = "select * from " + mvDBHelper.CHATMSGTABLE + " where msgTo = ?";
        return mvReadDatabase.rawQuery(querySql, new String[]{pWho});
    }


}
