package lzn.chat.main.view;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import java.util.List;

import lzn.chat.R;
import lzn.chat.absActivity;
import lzn.chat.absFragment;
import lzn.chat.main.item.contactItem.chat.model.MsgModel;
import lzn.chat.main.item.messageItem.MessageFragment;
import lzn.chat.main.presenter.AbsMainPresenter;
import lzn.chat.main.presenter.MainPresenterImpl;

public class MainActivity extends absActivity implements OnClickListener ,IMainView{
    /**
     * 消息界面布局
     */
    private View messageLayout;

    /**
     * 联系人界面布局
     */
    private View contactsLayout;

    /**
     * 动态界面布局
     */
    private View newsLayout;

    /**
     * 设置界面布局
     */
    private View settingLayout;

    /**
     * 在Tab布局上显示消息图标的控件
     */
    private ImageView messageImage;

    /**
     * 在Tab布局上显示联系人图标的控件
     */
    private ImageView contactsImage;

    /**
     * 在Tab布局上显示动态图标的控件
     */
    private ImageView newsImage;

    /**
     * 在Tab布局上显示设置图标的控件
     */
    private ImageView settingImage;

    /**
     * 在Tab布局上显示消息标题的控件
     */
    private TextView messageText;

    /**
     * 在Tab布局上显示联系人标题的控件
     */
    private TextView contactsText;

    /**
     * 在Tab布局上显示动态标题的控件
     */
    private TextView newsText;

    /**
     * 在Tab布局上显示设置标题的控件
     */
    private TextView settingText;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager mvFragmentManager;
    private AbsMainPresenter mvMainPresenter;
    private absFragment mvCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mvApplication.setMainActivity(this);
        mvFragmentManager = getFragmentManager();
        mvMainPresenter = new MainPresenterImpl(this,mvFragmentManager,this);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        initView();
        onClick(messageLayout); // 默认界面
    }

    @Override
    public void newMsg(List<MsgModel> pMessage) {
        mvCurrentFragment = mvMainPresenter.getCurrentFragment();
        //如果当前界面是聊天界面，把参数传给MessageFragment，直接更新界面
        if(mvCurrentFragment instanceof MessageFragment)
        {
            ((MessageFragment) mvCurrentFragment).onRecevieNewMsg(pMessage);
        }else
        {
            //通知栏通知有新消息
            NewMsgNotification(pMessage);
        }
    }

    private void initView() {
        messageLayout = findViewById(R.id.message_layout);
        contactsLayout = findViewById(R.id.contacts_layout);
        newsLayout = findViewById(R.id.news_layout);
        settingLayout = findViewById(R.id.setting_layout);
        messageImage = (ImageView) findViewById(R.id.message_image);
        contactsImage = (ImageView) findViewById(R.id.contacts_image);
        newsImage = (ImageView) findViewById(R.id.news_image);
        settingImage = (ImageView) findViewById(R.id.setting_image);
        messageText = (TextView) findViewById(R.id.message_text);
        contactsText = (TextView) findViewById(R.id.contacts_text);
        newsText = (TextView) findViewById(R.id.news_text);
        settingText = (TextView) findViewById(R.id.setting_text);
        messageLayout.setOnClickListener(this);
        contactsLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View pView) {
        switch (pView.getId())
        {

            case R.id.message_layout:
                mvMainPresenter.selectTab(0);
                messageImage.setImageResource(R.drawable.message_selected);
                messageText.setTextColor(Color.WHITE);
                break;
            case R.id.contacts_layout:
                mvMainPresenter.selectTab(1);
                contactsImage.setImageResource(R.drawable.contacts_selected);
                contactsText.setTextColor(Color.WHITE);
                break;
            case R.id.news_layout:
                mvMainPresenter.selectTab(2);
                newsImage.setImageResource(R.drawable.news_selected);
                newsText.setTextColor(Color.WHITE);
                break;
            case R.id.setting_layout:
                mvMainPresenter.selectTab(3);
                settingImage.setImageResource(R.drawable.setting_selected);
                settingText.setTextColor(Color.WHITE);
                break;
        }

    }
    @Override
    public void clearSelection() {
        messageImage.setImageResource(R.drawable.message_unselected);
        messageText.setTextColor(Color.parseColor("#82858b"));
        contactsImage.setImageResource(R.drawable.contacts_unselected);
        contactsText.setTextColor(Color.parseColor("#82858b"));
        newsImage.setImageResource(R.drawable.news_unselected);
        newsText.setTextColor(Color.parseColor("#82858b"));
        settingImage.setImageResource(R.drawable.setting_unselected);
        settingText.setTextColor(Color.parseColor("#82858b"));
    }
}
