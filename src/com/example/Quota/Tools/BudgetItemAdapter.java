package com.example.Quota.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.Quota.R;


import java.util.ArrayList;

public class BudgetItemAdapter extends BaseAdapter {

    private ArrayList<Item> itemData;
    private LayoutInflater layoutInflater;

    public BudgetItemAdapter(Context context, ArrayList<Item> data){
        itemData = data;
        layoutInflater = layoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return itemData.size();
    }

    @Override
    public Object getItem(int position) {
        return itemData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.budget_item_layout, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.item_name);
            holder.priceView = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameView.setText(itemData.get(position).name());
        holder.priceView.setText(String.valueOf(itemData.get(position).cost()));

        return convertView;
    }

    static class ViewHolder{
        TextView nameView;
        TextView priceView;
    }
}
