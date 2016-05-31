package lzn.chat.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import lzn.chat.login.view.ILoginView;
import lzn.chat.login.view.LoginActivity;
import lzn.chat.myApplication;

/**
 * Created by Allen on 2016/5/19.
 */
public class LoginPresenterImpl implements AbsLoginPresenter {
    private ILoginView mvILoginView;
    private Context mvContext;
    private myApplication mvApplication;
    public LoginPresenterImpl (Context pContext,ILoginView pILoginView)
    {
        mvContext = pContext;
        mvILoginView = pILoginView;
        mvApplication = myApplication.getInstance();
        //注册一个监听连接状态的listener
//        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            ((LoginActivity)mvContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        Toast.makeText(mvContext,"帐号已经被移除",Toast.LENGTH_SHORT).show();
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        Toast.makeText(mvContext,"帐号在其他设备登录",Toast.LENGTH_SHORT).show();
                    } else {
                        //连接不到聊天服务器
                        if (NetUtils.hasNetwork(mvContext)) {
                            Toast.makeText(mvContext,"连接不到聊天服务器",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(mvContext,"网络不可用，请检查网络设置",Toast.LENGTH_SHORT).show();
                        }
                        //当前网络不可用，请检查网络设置
                    }
                }
            });
        }
    }

    @Override
    public void doLogin(final String pUserAcc, String pUserPwd) {
        //这里应该用异步去数据库查询账号密码，根据查询结果回调
        //注册失败会抛出HyphenateException
        try {
//            EMClient.getInstance().createAccount(pUserAcc, pUserPwd);//同步方法
            EMClient.getInstance().login(pUserAcc, pUserPwd, new EMCallBack() {//回调
                @Override
                public void onSuccess() {
                    ((LoginActivity)mvContext).runOnUiThread(new Runnable() {
                        public void run() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Toast.makeText(mvContext, "Login successful!", Toast.LENGTH_SHORT).show();
                            mvILoginView.loginResult(1);
                            mvApplication.setAccountName(pUserAcc);
                        }
                    });
                }
                @Override
                public void onProgress(int progress, String status) {

                }
                @Override
                public void onError(int code, final String message) {
                    ((LoginActivity)mvContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mvContext,message,Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doForgetPwd() {
        Intent lvIntent = new Intent();
        lvIntent.setAction(Intent.ACTION_VIEW);
        Uri lvURI = Uri.parse("https://www.baidu.com/");
        lvIntent.setData(lvURI);
        mvContext.startActivity(lvIntent);
    }
}
