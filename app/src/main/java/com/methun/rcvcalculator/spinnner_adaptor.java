package com.methun.rcvcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Custom BaseAdapter for resistor band selection spinners.
 * Displays color swatch images, color names, and corresponding numerical/multiplier values.
 * Optimized with the ViewHolder pattern for smooth dropdown rendering.
 */
public class spinnner_adaptor extends BaseAdapter {

    private final Context context;
    private final String[] colorName;
    private final String[] colorValue;
    private final int[] colorImage;
    private final LayoutInflater inflater;

    public spinnner_adaptor(Context context, String[] colorName, String[] colorValue, int[] colorImage) {
        this.context = context;
        this.colorName = colorName;
        this.colorValue = colorValue;
        this.colorImage = colorImage;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (colorName != null) ? colorName.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return (colorName != null && position >= 0 && position < colorName.length) ? colorName[position] : null;
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
            convertView = inflater.inflate(R.layout.spinner_row, parent, false);
            holder = new ViewHolder();
            holder.colorImg = convertView.findViewById(R.id.colorImage_row);
            holder.colorNam = convertView.findViewById(R.id.colorName_row);
            holder.colorVal = convertView.findViewById(R.id.colorValue_row);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind data
        if (colorImage != null && position < colorImage.length) {
            holder.colorImg.setImageResource(colorImage[position]);
        }
        if (colorName != null && position < colorName.length) {
            holder.colorNam.setText(colorName[position]);
        }
        if (colorValue != null && position < colorValue.length) {
            holder.colorVal.setText(colorValue[position]);
        }

        return convertView;
    }

    /**
     * ViewHolder cache to minimize findViewById calls
     */
    private static class ViewHolder {
        ImageView colorImg;
        TextView colorNam;
        TextView colorVal;
    }
}
