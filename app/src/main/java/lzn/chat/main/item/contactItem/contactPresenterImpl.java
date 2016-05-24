package lzn.chat.main.item.contactItem;

import android.content.Context;
import android.os.AsyncTask;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by Allen on 2016/5/24.
 */
public class contactPresenterImpl implements absContactPresenter {
    private IContactView mvIContactView;
    private Context mvContext;
    public contactPresenterImpl(Context pContext ,IContactView pIContactView ){
        mvContext = pContext;
        mvIContactView = pIContactView;
    }

    @Override
    public void getFriendList() {
       new getFriendsNameListAsy().execute();
    }

    class getFriendsNameListAsy extends AsyncTask<Void,Void,List<String>>
    {


        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> lvList = null;
            try {
                lvList = EMClient.getInstance().contactManager().getAllContactsFromServer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            return lvList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            mvIContactView.showFriendList(result);
        }
    }
}
