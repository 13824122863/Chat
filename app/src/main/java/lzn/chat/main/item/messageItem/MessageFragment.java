package lzn.chat.main.item.messageItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lzn.chat.R;
import lzn.chat.absFragment;
import lzn.chat.main.item.contactItem.RecycleViewDivider;
import lzn.chat.main.item.contactItem.chat.model.ReceiveMsgModel;

public class MessageFragment extends absFragment {
	private RecyclerView mvRecyclerView;
	private Context mvContext;
	public MessageFragment(Context pContext)
	{
		mvContext = pContext;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View lvView = inflater.inflate(R.layout.message_layout, container, false);
		initView(lvView);
		Toolbar lvToolbar = (Toolbar) lvView.findViewById(R.id.toolbar);
		if(lvToolbar != null)
		{
			lvToolbar.setTitle("Message");
			((AppCompatActivity) mvApplication.getMainActivity()).setSupportActionBar(lvToolbar);
		}
		return lvView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	private void initView(View pView) {
		mvRecyclerView = (RecyclerView) pView.findViewById(R.id.recyclerView);
	}

	public void onRecevieNewMsg(List<ReceiveMsgModel> pMessage)
	{
		MessageRecycleviewAdapter lvAdapter = new MessageRecycleviewAdapter(mvContext,pMessage);
		mvRecyclerView.setLayoutManager(new LinearLayoutManager(mvContext));
		mvRecyclerView.addItemDecoration(new RecycleViewDivider(mvContext, LinearLayoutManager.HORIZONTAL));
		mvRecyclerView.setAdapter(lvAdapter);
		mvRecyclerView.invalidate();
	}
}
