package lzn.chat.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Allen on 2016/5/30.
 */
public class Utils {
    /**
     * @param pLong Long类型转化为时间
     * **/
    public static String long2String(long pLong) {
        String lvResult = "";
        Date lvDate = new Date(pLong);
        DateFormat lvDateFormate = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss", Locale.CHINA);
        lvResult = lvDateFormate.format(lvDate);
        return lvResult;

    }

    public static String replaceMsgContent(String pString) {
        String lvResult = "";
        String lvReplace = "txt:";
        String lvStr = pString.replace(lvReplace, "");
        lvResult = lvStr.substring(1, lvStr.length() - 1);
        return lvResult;
    }
}
