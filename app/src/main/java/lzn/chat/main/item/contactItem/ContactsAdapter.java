package lzn.chat.main.item.contactItem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lzn.chat.R;
import lzn.chat.main.item.contactItem.chat.view.ChatActivity;

/**
 * Created by Allen on 2016/5/23.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder>  {
    private Context mvContext ;
    private List<String> mvNameLists;
    public ContactsAdapter (Context pContext , List<String> pNameLists){
        mvContext = pContext;
        mvNameLists = pNameLists;
    }

    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lvView = LayoutInflater.from(mvContext).inflate(R.layout.contacts_recyclerview_item,null);
        lvView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ContactsHolder(lvView);
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {
        holder.mvNameText.setText(mvNameLists.get(position));
        holder.mvNameText.setTag(mvNameLists.get(position));
    }

    @Override
    public int getItemCount() {
        return mvNameLists.size();
    }

    class ContactsHolder extends RecyclerView.ViewHolder{
        public TextView mvNameText;
        public ImageView mvUserIcon;
        private LinearLayout mvRootLayout;
        public ContactsHolder(View pView) {
            super(pView);

            mvNameText = (TextView) pView.findViewById(R.id.user_name);
            mvUserIcon = (ImageView) pView.findViewById(R.id.user_icon);
            mvRootLayout = (LinearLayout) pView.findViewById(R.id.rootLayout);

            mvRootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent lvIntent = new Intent(mvContext, ChatActivity.class);
                    lvIntent.putExtra(ChatActivity.CHATTOWHO,(String)mvNameText.getTag());
                    mvContext.startActivity(lvIntent);
                }
            });
        }
    }
}
