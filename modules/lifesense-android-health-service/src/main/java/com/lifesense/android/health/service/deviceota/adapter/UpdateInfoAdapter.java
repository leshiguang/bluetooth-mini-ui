package com.lifesense.android.health.service.deviceota.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.common.ui.BaseAdapter;

public class UpdateInfoAdapter extends BaseAdapter<String> {

    public UpdateInfoAdapter(Context context) {
        super(context);
    }


    @Override
    public View getView(int position, View convertView) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.scale_listitem_device_update_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String content = mList.get(position);
        holder.bindData(content);
        return convertView;
    }

    class ViewHolder {
        TextView tvUpdateInfo;

        ViewHolder(View view) {
            this.tvUpdateInfo = view.findViewById(R.id.ldui_content_tv);
        }

        public void bindData(String content) {
            tvUpdateInfo.setText(content);
        }
    }

}