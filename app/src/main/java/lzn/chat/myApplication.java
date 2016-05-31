package lzn.chat;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by Allen on 2016/5/19.
 */
public class myApplication extends Application {
    private static myApplication mvApplication;
    private AppCompatActivity mvMainActivity;
    private String mvAccountName;
    public static myApplication getInstance()
    {
        if(mvApplication == null)
        {
            mvApplication = new myApplication();
        }
        return mvApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //自动登录
        options.setAutoLogin(false);
        //初始化
        EMClient.getInstance().init(this.getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    public Activity getMainActivity() {
        return mvMainActivity;
    }

    public void setMainActivity(AppCompatActivity pMainActivity) {
        if(pMainActivity instanceof AppCompatActivity)
            mvMainActivity = pMainActivity;
    }

    public String getAccountName() {
        return mvAccountName;
    }

    public void setAccountName(String pAccountName) {
        mvAccountName = pAccountName;
    }
}
