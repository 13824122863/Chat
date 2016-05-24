package lzn.chat.main.presenter;

import android.app.FragmentTransaction;

/**
 * Created by Allen on 2016/5/23.
 */

public interface AbsMainPresenter {
    /**
     * @param pPosition 0：消息界面  1：联系人界面  2：发现界面  3：设定界面
     * **/
    void selectTab(int pPosition);
    void hideFragment(FragmentTransaction pTransaction);
}
