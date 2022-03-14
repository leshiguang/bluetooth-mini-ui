package com.lifesense.android.health.service.devicedetails.ui.activity.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lifesense.android.ble.device.band.model.config.Page;
import com.lifesense.android.health.service.BR;
import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseDataBindingRvAdapter;
import com.lifesense.android.health.service.common.ui.DefaultDataBindingViewHolder;
import com.lifesense.android.health.service.databinding.ScaleItemScreenContentBinding;
import com.lifesense.android.health.service.devicedetails.utils.DataTransformUtil;
import com.lifesense.android.health.service.util.DragSortItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 手环页面显示
 *
 * @author alexwu
 */
public class PedometerScreenContentRvAdapter extends BaseDataBindingRvAdapter<ScaleItemScreenContentBinding, Page.PageType> implements DragSortItemTouchHelper.DragSortAdapter {
    private DragSortItemTouchHelper dragSortItemTouchHelper;
    private OnAdapterListener onAdapterListener;
    private List<Page.PageType> selectedPage = new ArrayList<>();

    public PedometerScreenContentRvAdapter() {
        dragSortItemTouchHelper = new DragSortItemTouchHelper(this);
    }

    @Override
    public int getItemLayoutId(int poi) {
        return R.layout.scale_item_screen_content;
    }

    @Override
    public int getItemVariableId() {
        return BR.item;
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultDataBindingViewHolder<ScaleItemScreenContentBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        Page.PageType pageType = items.get(holder.getLayoutPosition());
        holder.getBinding().setSelected(isChecked(holder.getLayoutPosition()));
        holder.getBinding().setTitle(DataTransformUtil.getScreenText(pageType));
        holder.getBinding().checkbox.setOnClickListener(v -> {
            items.remove(pageType);
            if (isChecked(pageType)) {
                items.add(pageType);
                selectedPage.remove(pageType);
            } else {
                int index = getFirstUnCheckedDataBlockIndex();
                if (index < 0) {
                    items.add(pageType);
                } else {
                    items.add(index, pageType);
                }
                selectedPage.add(pageType);
            }
            if (onAdapterListener != null) {
                onAdapterListener.onCheckedChange();
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public boolean swapPosition(int srcPoi, int tarPoi) {
        if (srcPoi < 0 || tarPoi < 0 || srcPoi > getItemCount() - 1 || tarPoi > getItemCount() - 1 || !isChecked(tarPoi)) {
            return false;
        }
        Collections.swap(items, srcPoi, tarPoi);
        List<Page.PageType> selectedPage = new ArrayList<>();
        for (Page.PageType pageType : items) {
            if (isChecked(pageType)) {
                selectedPage.add(pageType);
            }
        }
        this.selectedPage.clear();
        this.selectedPage.addAll(selectedPage);
        notifyItemMoved(srcPoi, tarPoi);
        return true;
    }

    private boolean isChecked(int pos) {
        return isChecked(items.get(pos));
    }

    private boolean isChecked(Page.PageType pageType) {
        return selectedPage.indexOf(pageType) >= 0;
    }

    @Override
    public boolean isDragEnabled(RecyclerView.ViewHolder holder) {
        int poi = holder.getLayoutPosition();
        if (poi > -1 && poi < getItemCount()) {
            Page.PageType pageType = items.get(poi);
            return pageType != null && isChecked(pageType);

        }
        return false;
    }

    @Override
    public boolean isDragEnabled() {
        return true;
    }

    @Override
    public void onDragStart(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onDragStop() {
        if (onAdapterListener != null) {
            onAdapterListener.onDragStop();
        }

    }

    public int getFirstUnCheckedDataBlockIndex() {
        for (int i = 0; i < items.size(); i++) {
            if (!isChecked(i)) return i;
        }
        return -1;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        dragSortItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setSelectedPage(List<Page.PageType> selectedPage) {
        this.selectedPage = selectedPage;
        if (items != null) {
            Collections.sort(items, (o1, o2) -> {
                if (isChecked(o1) && !isChecked(o2)) {
                    return -1;
                } else if (!isChecked(o1) && isChecked(o2)) {
                    return 1;
                } else {
                    return 0;
                }
            });
        }
        notifyDataSetChanged();
    }

    public OnAdapterListener getOnAdapterListener() {
        return onAdapterListener;
    }

    public void setOnAdapterListener(OnAdapterListener onAdapterListener) {
        this.onAdapterListener = onAdapterListener;
    }

    @Override
    public void setItems(List<Page.PageType> items) {
        Collections.sort(items, (o1, o2) -> {
            if (isChecked(o1) && !isChecked(o2)) {
                return -1;
            } else if (!isChecked(o1) && isChecked(o2)) {
                return 1;
            } else {
                return 0;
            }
        });
        super.setItems(items);
    }

    public List<Page.PageType> getSelectedPage() {
        return selectedPage;
    }

    public interface OnAdapterListener {
        void onCheckedChange();

        void onDragStop();
    }
}