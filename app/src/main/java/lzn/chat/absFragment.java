package lzn.chat;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Allen on 2016/5/24.
 */
public abstract class absFragment  extends Fragment {
    protected myApplication mvApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvApplication = myApplication.getInstance();
    }

}
