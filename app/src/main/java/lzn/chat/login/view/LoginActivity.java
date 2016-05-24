package lzn.chat.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import lzn.chat.R;
import lzn.chat.login.presenter.AbsLoginPresenter;
import lzn.chat.login.presenter.LoginPresenterImpl;
import lzn.chat.main.view.MainActivity;

/**
 * Created by Allen on 2016/5/19.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener ,ILoginView{
    private ImageView mvUserIconView;
    private EditText mvUserName;
    private EditText mvUserPassword;
    private Button mvBtnLogin;
    private TextView mvForgetPwdView;
    private AbsLoginPresenter mvAbsLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mvAbsLoginPresenter = new LoginPresenterImpl(this,this);
        initView();
    }

    private void initView() {
        mvUserIconView = (ImageView) this.findViewById(R.id.user_icon);
        mvUserName = (EditText) this.findViewById(R.id.edit_user_name);
        mvUserPassword = (EditText) this.findViewById(R.id.edit_user_pwd);
        mvBtnLogin = (Button) this.findViewById(R.id.btnLogin);
        mvForgetPwdView = (TextView) this.findViewById(R.id.forget_pwd);

        mvBtnLogin.setOnClickListener(this);
        mvForgetPwdView.setOnClickListener(this);
    }

    @Override
    public void onClick(View pView) {
        switch (pView.getId()){
            case R.id.btnLogin:
                if(checkEmpty()){
                    mvAbsLoginPresenter.doLogin(mvUserName.getText().toString(),mvUserPassword.getText().toString());
                }
                break;
            case R.id.forget_pwd:
                mvAbsLoginPresenter.doForgetPwd();
                break;
        }
    }

    private boolean checkEmpty() {
        if(mvUserName.getText().toString().equals(""))
        {
            showToast("用户名不能为空!");
            return  false;
        }
        if(mvUserPassword.getText().toString().equals(""))
        {
            showToast("密码不能为空!");
            return  false;
        }
        return true;
    }

    private void showToast(String pString) {
        Toast.makeText(LoginActivity.this, pString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginResult(int pResultCode) {

        if(1 == pResultCode)
        {

            Intent lvIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(lvIntent);
            LoginActivity.this.finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvAbsLoginPresenter = null;
    }
}
