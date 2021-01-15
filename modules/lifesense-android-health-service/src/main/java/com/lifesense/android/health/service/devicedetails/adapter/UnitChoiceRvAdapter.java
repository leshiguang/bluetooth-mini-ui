package com.lifesense.android.health.service.devicedetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.devicedetails.model.HeightUnit;
import com.lifesense.android.health.service.devicedetails.model.Unit;
import com.lifesense.android.health.service.devicedetails.model.WeightUnit;
import com.lifesense.android.health.service.devicedetails.model.UnitCategory;

import java.util.Arrays;
import java.util.List;

/**
 * Create by qwerty
 * Create on 2020/6/10
 *
 **/
public class UnitChoiceRvAdapter extends RecyclerView.Adapter<UnitChoiceRvAdapter.UnitChoiceViewHolder> {
    private List<UnitCategory> unitList;
    private WeightUnit choiceWeightUnit = null;
    private HeightUnit choiceHeightUnit = null;
    private OnChoiceUnitListener onChoiceUnitListener;
    private LinearLayoutManager layoutManager;
    public UnitChoiceRvAdapter() {
        this.unitList = Arrays.asList(UnitCategory.UNIT_WEIGHT);
    }

    @NonNull
    @Override
    public UnitChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UnitChoiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scale_item_unit_setting, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final UnitChoiceViewHolder holder, int flatPosition) {
        Context context  = holder.itemView.getContext();
        int parentPoi = getParentPoi(flatPosition);
        final UnitCategory unitCategory = unitList.get(parentPoi);
        int childPoi = getChildPoi(flatPosition);
        if(childPoi == 0) {
            holder.setCellHeaderTitle(unitCategory.getTypeString(context));
            holder.tvCellHeader.setVisibility(View.VISIBLE);
        } else {
            holder.tvCellHeader.setVisibility(View.GONE);
        }
        final Unit unit = unitCategory.getUnits().get(childPoi);
        if(unitCategory == UnitCategory.UNIT_HEIGHT) {
            holder.setChecked(unit == choiceHeightUnit);
        } else {
            holder.setChecked(unit == choiceWeightUnit);
        }

        holder.setUnitName(unit.getUnitName(holder.itemView.getContext()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onChoiceUnitListener != null) {
                    onChoiceUnitListener.onChoiceUnit(unit);
                }
                if(unitCategory == UnitCategory.UNIT_HEIGHT) {
                    setChoiceHeightUnit((HeightUnit) unit);
                    holder.setChecked(unit == choiceHeightUnit);
                } else {
                    setChoiceWeightUnit((WeightUnit) unit);
                    holder.setChecked(unit == choiceWeightUnit);
                }
            }
        });
    }

    public int getParentPoi(int flatPoi) {
        int parentCount  = getParentCount();
        int count = 0;
        for (int i = 0; i < parentCount; i++) {
            count += unitList.get(i).getUnitCounts();
            if(flatPoi > count - 1) {
                continue;
            }
            return i;
        }
        return -1;
    }

    public int getChildPoi(int flatPoi) {
        int parentCount  = getParentCount();
        int preCount = 0;
        int count = 0;
        if(parentCount != 0) {
            for (int i = 0; i < parentCount; i++) {
                count += unitList.get(i).getUnitCounts();
                if (flatPoi <= count - 1) {
                    return flatPoi - preCount;
                }
                preCount = count;
            }
        }
        return -1;
    }

    public int getParentCount() {
        if(unitList == null) return 0;
        return unitList.size();
    }

    @Override
    public int getItemCount() {
        if(unitList == null) return 0;
        int count = 0;
        for (UnitCategory unitCategory: unitList) {
            count += unitCategory.getUnitCounts();
        }
        return count;
    }

    public void setOnChoiceUnitListener(OnChoiceUnitListener onChoiceUnitListener) {
        this.onChoiceUnitListener = onChoiceUnitListener;
    }


    public void bindView(RecyclerView recyclerView) {
        recyclerView.setAdapter(this);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    static class UnitChoiceViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCellHeader;
        private TextView tvUnitName;
        private View choiceIcon;

        public UnitChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvCellHeader = itemView.findViewById(R.id.tv_cell_header);
            this.tvUnitName = itemView.findViewById(R.id.tv_unit_name);
            choiceIcon = itemView.findViewById(R.id.rb_unit_choice);
        }

        public void setUnitName(String unitName) {
            this.tvUnitName.setText(unitName);
        }

        public void setChecked(boolean checked) {
            choiceIcon.setBackgroundResource(checked ? R.drawable.scale_checkbox_on : R.drawable.scale_checkbox_off);
        }

        public void setCellHeaderTitle(String title) {
            this.tvCellHeader.setText(title);
        }
    }

    public interface OnChoiceUnitListener {
        void onChoiceUnit(Unit unit);
    }

    public void setChoiceWeightUnit(WeightUnit choiceWeightUnit) {
        this.choiceWeightUnit = choiceWeightUnit;
        notifyDataSetChanged();
    }

    public void setChoiceHeightUnit(HeightUnit choiceHeightUnit) {
        this.choiceHeightUnit = choiceHeightUnit;
        notifyDataSetChanged();
    }
}
