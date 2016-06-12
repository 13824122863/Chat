package lzn.chat.main.presenter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import lzn.chat.R;
import lzn.chat.absFragment;
import lzn.chat.main.item.contactItem.ContactsFragment;
import lzn.chat.main.item.discoveryItem.DiscoveryFragment;
import lzn.chat.main.item.messageItem.MessageFragment;
import lzn.chat.main.item.settingItem.SettingFragment;
import lzn.chat.main.view.IMainView;

/**
 * Created by Allen on 2016/5/23.
 */
public class MainPresenterImpl implements AbsMainPresenter {
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager mvFragmentManager;
    private Context mvContext;
    /**
     * 用于展示消息的Fragment
     */
    private MessageFragment mvMessageFragment;

    /**
     * 用于展示联系人的Fragment
     */
    private ContactsFragment mvContactsFragment;

    /**
     * 用于展示动态的Fragment
     */
    private DiscoveryFragment mvDiscoveryFragment;

    /**
     * 用于展示设置的Fragment
     */
    private SettingFragment mvSettingFragment;

    private absFragment mvCurrentFragment = null;

    private IMainView mvMainView;
    public  MainPresenterImpl(Context pContext,FragmentManager pFragmentManager,IMainView pMainView){
        mvContext = pContext;
        mvFragmentManager = pFragmentManager;
        mvMainView = pMainView;
    }
    @Override
    public void selectTab(int pPosition) {
// 每次选中之前先清楚掉上次的选中状态
        mvMainView.clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = mvFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragment(transaction);
        switch (pPosition) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色

//                if (mvMessageFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    mvMessageFragment = new MessageFragment(mvContext);
                    transaction.add(R.id.content, mvMessageFragment);
//                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
//                    transaction.show(mvMessageFragment);
//                }
                mvCurrentFragment = mvMessageFragment;
                break;
            case 1:

                if (mvContactsFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    mvContactsFragment = new ContactsFragment(mvContext);
                    transaction.add(R.id.content, mvContactsFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(mvContactsFragment);
                }
                mvCurrentFragment = mvContactsFragment;
                break;
            case 2:

                if (mvDiscoveryFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    mvDiscoveryFragment = new DiscoveryFragment();
                    transaction.add(R.id.content, mvDiscoveryFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(mvDiscoveryFragment);
                }
                mvCurrentFragment = mvDiscoveryFragment;
                break;
            case 3:
            default:
                if (mvSettingFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    mvSettingFragment = new SettingFragment();
                    transaction.add(R.id.content, mvSettingFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(mvSettingFragment);
                }
                mvCurrentFragment = mvSettingFragment;
                break;
        }
        transaction.commit();
    }

    @Override
    public void hideFragment(FragmentTransaction pTransaction) {
        if (mvMessageFragment != null) {
            pTransaction.hide(mvMessageFragment);
        }
        if (mvContactsFragment != null) {
            pTransaction.hide(mvContactsFragment);
        }
        if (mvDiscoveryFragment != null) {
            pTransaction.hide(mvDiscoveryFragment);
        }
        if (mvSettingFragment != null) {
            pTransaction.hide(mvSettingFragment);
        }
    }
    @Override
    public absFragment getCurrentFragment()
    {
        return  mvCurrentFragment;
    }
}
