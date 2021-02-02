package com.developersrefresh.read;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class fileadapter extends ArrayAdapter<ModelClass> implements Filterable {

    private List<ModelClass> dataSet;
    private List<ModelClass> exampleListFull;
    Activity mContext;
    AlertDialog.Builder builder;
    int pos;
    ModelClass dataModel;


    private static class ViewHolder {
        TextView filename;



    }

    public fileadapter(List<ModelClass> data, Activity context) {
        super(context, R.layout.filelayout, data);
        this.dataSet = data;
        this.mContext=context;
        exampleListFull = new ArrayList<>(dataSet);

    }




    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.filelayout, parent, false);
            viewHolder.filename = (TextView) convertView.findViewById(R.id.filename);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;

        viewHolder.filename.setText(dataModel.getName());








        return convertView;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ModelClass> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelClass item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataSet.clear();
            dataSet.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
