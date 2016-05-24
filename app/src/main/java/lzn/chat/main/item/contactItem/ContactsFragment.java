package lzn.chat.main.item.contactItem;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import lzn.chat.R;
import lzn.chat.absFragment;
import lzn.chat.main.item.contactItem.addFriend.addFriendActivity;

public class ContactsFragment extends absFragment {
	private RecyclerView mvRecyclerView;
	private List<String> mvNameLists  ;
	private Context mvContext;
	public ContactsFragment(Context pContext)
	{
		mvContext = pContext;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new getFriendsNameListAsy().execute();
		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View lvView = inflater.inflate(R.layout.contacts_layout, container, false);
		mvRecyclerView = (RecyclerView) lvView.findViewById(R.id.recyclerView);

		Toolbar lvToolbar = (Toolbar) lvView.findViewById(R.id.toolbar);
		if(lvToolbar != null)
		{
			lvToolbar.setTitle("Contacter");
			((AppCompatActivity) mvApplication.getMainActivity()).setSupportActionBar(lvToolbar);
		}

		return lvView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.contacter_menu,menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case R.id.addFriend:
				Intent lvIntent = new Intent(mvContext, addFriendActivity.class);
				mvContext.startActivity(lvIntent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setUpAdapter()
	{
		if(mvRecyclerView != null && mvNameLists.size() != 0)
		{
			mvRecyclerView.setLayoutManager(new LinearLayoutManager(mvContext));
			mvRecyclerView.setAdapter(new ContactsAdapter(mvContext, mvNameLists));
			mvRecyclerView.addItemDecoration(new RecycleViewDivider(mvContext, LinearLayoutManager.HORIZONTAL));

		}
	}

	class getFriendsNameListAsy extends AsyncTask<Void,Void,Void>
	{


		@Override
		protected Void doInBackground(Void... params) {
			try {
				mvNameLists = EMClient.getInstance().contactManager().getAllContactsFromServer();
			} catch (HyphenateException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			setUpAdapter();
		}
	}
}
