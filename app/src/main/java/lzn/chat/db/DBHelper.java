package lzn.chat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Allen on 2016/5/30.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String DBNAME = "chat.db";
    public static final String CHATMSGTABLE = "CHATMSGTABLE";
    private static final int DBVERSION = 1;
    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String chatMsgSql = "create table if not exists "+ CHATMSGTABLE +" (msgContent text,msgTime varchar , msgFrom varchar , msgTo varchar ) ";
        db.execSQL(chatMsgSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
