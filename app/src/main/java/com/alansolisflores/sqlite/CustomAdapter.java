package com.alansolisflores.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Car> carList;

    private Context activityContext;

    private int layout;


    public CustomAdapter(List<Car> carList,Context context,int layout){
     this.carList = carList;
     this.activityContext = context;
     this.layout = layout;
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return carList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.activityContext);
            convertView = inflater.inflate(this.layout,null);
            holder = new ViewHolder();

            holder.nameTextView = convertView.findViewById(R.id.nameTextView);
            holder.colorTextView = convertView.findViewById(R.id.colorTextView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        String name = this.carList.get(position).getName();
        String color = this.carList.get(position).getColor();

        holder.nameTextView.setText(name);
        holder.colorTextView.setText(color);

        return convertView;
    }

    public static class ViewHolder{

        private TextView nameTextView;

        private TextView colorTextView;

    }
}
