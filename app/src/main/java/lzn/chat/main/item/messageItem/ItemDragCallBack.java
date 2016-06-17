package lzn.chat.main.item.messageItem;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Allen on 2016/6/13.
 */
public class ItemDragCallBack extends ItemTouchHelper.Callback {
    private ItemDragInterface mvDragInterface;
    private OnDragListener mvDragListener;

    public ItemDragCallBack(ItemDragInterface pDragInterface) {
        mvDragInterface = pDragInterface;
    }

    public ItemDragCallBack setDragListener(OnDragListener pDragListener) {
        mvDragListener = pDragListener;
        return this;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 判断是Listview还是GridView，设定可移动的方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int lvDragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int lvSwipFlag = 0;
            return makeMovementFlags(lvDragFlag, lvSwipFlag);
        } else {
            int lvDragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int lvSwipFlag = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(lvDragFlag, lvSwipFlag);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int lvStartPosition = viewHolder.getAdapterPosition(); //获得点击要移动Item的位置
        int lvEndPosition = target.getAdapterPosition();   //获得最终Item放置的位置
        mvDragInterface.onMove(lvStartPosition, lvEndPosition);  //扔回adapter做处理
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int lvPosition = viewHolder.getAdapterPosition();
        mvDragInterface.onSwiped(lvPosition);
    }

    //当长按选中item的时候（拖拽开始的时候）调用
    //设置点击时，背景色会灰色
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (background == null && bkcolor == -1) {
                Drawable drawable = viewHolder.itemView.getBackground();
                if (drawable == null) {
                    bkcolor = 0;
                } else {
                    background = drawable;
                }
            }
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }


    //当手指松开的时候（拖拽完成的时候）调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(1.0f);
        if (background != null) viewHolder.itemView.setBackgroundDrawable(background);
        if (bkcolor != -1) viewHolder.itemView.setBackgroundColor(bkcolor);
        //viewHolder.itemView.setBackgroundColor(0);

        if (mvDragListener!=null){
            mvDragListener.onFinishDrag();
        }
    }

    private Drawable background = null;
    private int bkcolor = -1;

    public interface ItemDragInterface {
        void onMove(int pFromPosition, int pToPosition);

        void onSwiped(int pPosition);
    }

    public interface OnDragListener {
        void onFinishDrag();
    }
}
