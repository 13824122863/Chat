package lzn.chat.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lzn.chat.R;

/**
 * Created by Allen on 2016/5/19.
 */

public class FragmentDemo extends Fragment {
    static int[] lvInts ;
    static {
       lvInts = new int[]{R.layout.main_news,R.layout.main_contacts,R.layout.main_discovery,R.layout.main_mine};
     }
    private int mvPosition;
    public FragmentDemo(){}
    public FragmentDemo(int pPosition) {
        mvPosition = pPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lvView = inflater.inflate(lvInts[mvPosition],container,false);
        return lvView;
    }
}
