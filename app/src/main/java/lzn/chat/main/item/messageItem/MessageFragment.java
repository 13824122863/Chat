package lzn.chat.main.item.messageItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lzn.chat.R;
import lzn.chat.absFragment;

public class MessageFragment extends absFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View messageLayout = inflater.inflate(R.layout.message_layout,
				container, false);
		return messageLayout;
	}

}
