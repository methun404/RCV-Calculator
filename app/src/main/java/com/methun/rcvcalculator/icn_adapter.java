package com.methun.rcvcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Custom BaseAdapter for unit multiplier selection spinner (Ω, kΩ, MΩ, GΩ).
 * Optimized with the ViewHolder pattern to ensure fluid UI scrolling.
 */
public class icn_adapter extends BaseAdapter {

    private final String[] txt;
    private final LayoutInflater inflater;

    public icn_adapter(String[] txt, Context context) {
        this.txt = txt;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (txt != null) ? txt.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return (txt != null && position >= 0 && position < txt.length) ? txt[position] : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // ViewHolder pattern for view recycling optimization
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.icn_spinner, parent, false);
            holder = new ViewHolder();
            holder.spTxt = convertView.findViewById(R.id.spn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (txt != null && position < txt.length) {
            holder.spTxt.setText(txt[position]);
        }

        return convertView;
    }

    /**
     * ViewHolder cache to minimize findViewById calls
     */
    private static class ViewHolder {
        TextView spTxt;
    }
}
