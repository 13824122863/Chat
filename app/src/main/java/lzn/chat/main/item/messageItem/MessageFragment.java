package lzn.chat.main.item.messageItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lzn.chat.R;
import lzn.chat.absFragment;
import lzn.chat.main.item.contactItem.RecycleViewDivider;
import lzn.chat.main.item.contactItem.chat.model.MsgModel;

public class MessageFragment extends absFragment  {
    private RecyclerView mvRecyclerView;
    private Context mvContext;
    private absMessagePresenter mvMessagePresenter;
    private List<MsgModel> mvAllHistoryList;

    public MessageFragment(Context pContext) {
        mvContext = pContext;
        mvMessagePresenter = new MessagePersenterImpl(mvContext);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lvView = inflater.inflate(R.layout.message_layout, container, false);
        initView(lvView);
        setAdapter();
        Toolbar lvToolbar = (Toolbar) lvView.findViewById(R.id.toolbar);
        if (lvToolbar != null) {
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
        mvRecyclerView.setLayoutManager(new LinearLayoutManager(mvContext));
        mvRecyclerView.addItemDecoration(new RecycleViewDivider(mvContext, LinearLayoutManager.HORIZONTAL));
        mvRecyclerView.setHasFixedSize(true);
    }

    public void onRecevieNewMsg(List<MsgModel> pMessage) {
        MessageRecycleviewAdapter lvAdapter =(MessageRecycleviewAdapter)mvRecyclerView.getAdapter();
        if( lvAdapter != null)
        {
            lvAdapter.addItem(pMessage);
        }else {
            lvAdapter = new MessageRecycleviewAdapter(mvContext,pMessage);
            mvRecyclerView.setAdapter(lvAdapter);
        }
    }

    private void setAdapter() {
//        List<MsgModel> lvList = (List<MsgModel>) ACache.get(mvContext).getAsObject("");
//        if(lvList == null)
             mvAllHistoryList = mvMessagePresenter.getAllChatHistory();
        if (mvAllHistoryList != null) {
            MessageRecycleviewAdapter lvAdapter = new MessageRecycleviewAdapter(mvContext, mvAllHistoryList);
            mvRecyclerView.setAdapter(lvAdapter);
            final ItemTouchHelper lvItemTouchHelper = new ItemTouchHelper(new ItemDragCallBack(lvAdapter)/*.setDragListener(this)*/);
            lvItemTouchHelper.attachToRecyclerView(mvRecyclerView);

           /* mvRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mvRecyclerView) {
                @Override
                public void onLongClick(RecyclerView.ViewHolder vh) {
                    if (vh.getLayoutPosition() != mvAllHistoryList.size() - 1) {
                        lvItemTouchHelper.startDrag(vh);
                        VibratorUtil.Vibrate(getActivity(), 70);   //震动70ms
                    }
                }
            });*/
        }
    }

   /* @Override
    public void onFinishDrag() {
        ACache.get(mvContext).put("ListMsgItem", (ArrayList<MsgModel>)mvAllHistoryList);
    }*/
}
