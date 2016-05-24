package lzn.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Allen on 2016/5/24.
 */
public abstract class absActivity extends AppCompatActivity {
    protected myApplication mvApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明，经测试在代码里直接声明透明状态栏更有效
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        mvApplication = myApplication.getInstance();
    }
}
