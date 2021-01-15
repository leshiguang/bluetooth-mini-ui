package com.lifesense.android.health.service.util;


import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by qwerty
 * Create on 2019-09-17
 **/
public class DragSortItemTouchHelper extends ItemTouchHelper {


    public DragSortItemTouchHelper(DragSortAdapter adapter) {
        super(new Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlag = 0;
                if (adapter.isDragEnabled(viewHolder)) {
                    dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlag, swipeFlag);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return adapter.swapPosition(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return adapter.isDragEnabled();
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ACTION_STATE_DRAG) {
                    adapter.onDragStart(viewHolder);
                } else if (actionState == ACTION_STATE_IDLE) {
                    adapter.onDragStop();
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
            }
        });


    }

    public interface DragSortAdapter{
        boolean swapPosition(int srcPoi, int tarPoi);

        boolean isDragEnabled();

        boolean isDragEnabled(RecyclerView.ViewHolder holder);

        void onDragStart(RecyclerView.ViewHolder viewHolder);

        void onDragStop();
    }
}


