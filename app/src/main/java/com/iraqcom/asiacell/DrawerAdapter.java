package com.iraqcom.asiacell;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DrawerAdapter extends ArrayAdapter<String> {

    Context context;

    String data[] = null;

    public DrawerAdapter(Context context,  String[] data) {
        super(context, R.layout.drawer_list_item1, data);

        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DrawerHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.drawer_list_item1, parent, false);

            holder = new DrawerHolder();

            holder.text1 = (TextView)row.findViewById(R.id.txt_item);

            row.setTag(holder);
        }
        else
        {
            holder = (DrawerHolder)row.getTag();
        }
        if (data[position].equalsIgnoreCase("Privacy Policy")||data[position].equalsIgnoreCase("Terms and Conditions")){
            holder.text1.setTextSize(15f);

        }else {
            holder.text1.setTextSize(20f);
        }


        holder.text1.setText( data[position]);


        return row;
    }

    static class DrawerHolder
    {
        TextView text1;
    }
}