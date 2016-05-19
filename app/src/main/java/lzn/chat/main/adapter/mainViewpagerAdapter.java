package lzn.chat.main.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import lzn.chat.main.view.FragmentDemo;

/**
 * Created by Allen on 2016/5/19.
 */
public class mainViewpagerAdapter extends FragmentStatePagerAdapter {

    public String[] pagers = new String[]{"News","Contacts","Discovery","Mine"};

    public mainViewpagerAdapter(FragmentManager pFragmentManager)
    {
        super(pFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return new FragmentDemo(position);
    }

    @Override
    public int getCount() {
        return pagers.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return pagers[position];
    }

}
