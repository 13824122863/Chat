package lzn.chat.main.item.contactItem.addFriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import lzn.chat.R;

/**
 * Created by Allen on 2016/5/24.
 */
public class addFriendActivity extends AppCompatActivity {
    private RecyclerView mvRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend_layout);
        initTooBar();
        initView();
    }

    private void initView() {
        mvRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        mvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTooBar() {
        Toolbar lvToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        if(lvToolbar != null)
        {
            setSupportActionBar(lvToolbar);
            View lvView = LayoutInflater.from(this).inflate(R.layout.add_friend_input_layout,null);
            lvView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            lvToolbar.addView(lvView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_friend_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
