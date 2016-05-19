package lzn.chat.main.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import lzn.chat.R;
import lzn.chat.main.adapter.mainViewpagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mvViewPager;
    private TabLayout mvTabLayout;
    private final static int SVPAGERLIMIT = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        mvViewPager = (ViewPager) this.findViewById(R.id.viewPager);
        mvTabLayout = (TabLayout) this.findViewById(R.id.tabLayout);

        mvViewPager.setOffscreenPageLimit(SVPAGERLIMIT);
        mvViewPager.setAdapter(new mainViewpagerAdapter(getSupportFragmentManager()));

        mvTabLayout.setupWithViewPager(mvViewPager);
    }


}
