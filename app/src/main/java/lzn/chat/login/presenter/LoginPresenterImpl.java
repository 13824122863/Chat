package lzn.chat.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import lzn.chat.login.view.ILoginView;

/**
 * Created by Allen on 2016/5/19.
 */
public class LoginPresenterImpl implements AbsLoginPresenter {
    private ILoginView mvILoginView;
    private Context mvContext;
    public LoginPresenterImpl (Context pContext,ILoginView pILoginView)
    {
        mvContext = pContext;
        mvILoginView = pILoginView;
    }

    @Override
    public void doLogin(String pUserAcc, String pUserPwd) {
        //这里应该用异步去数据库查询账号密码，根据查询结果回调
        mvILoginView.loginResult(1);
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
