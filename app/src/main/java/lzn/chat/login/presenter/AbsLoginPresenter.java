package lzn.chat.login.presenter;


/**
 * Created by Allen on 2016/5/19.
 */
public interface AbsLoginPresenter {
    void doLogin(String pUserAcc , String pUserPwd);  //pUserAcc 用户账号   ，pUserPwd 用户密码
    void doForgetPwd();  //忘记密码

}
