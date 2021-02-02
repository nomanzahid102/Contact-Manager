package com.developersrefresh.read;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<String[]> implements Filterable {
	private List<String[]> scoreList = new ArrayList<String[]>();
    private List<String[]> exampleListFull;
    Activity contextt;

    static class ItemViewHolder {
        TextView name;
        TextView score;
        LinearLayout layout;
    }

    public ItemArrayAdapter(Context context, int textViewResourceId,Activity contextt) {
        super(context, textViewResourceId);
        this.contextt=contextt;
    }


	@Override
	public void add(String[] object) {
		scoreList.add(object);
		super.add(object);
	}

    @Override
	public int getCount() {
		return this.scoreList.size();
	}

    @Override
	public String[] getItem(int index) {
		return this.scoreList.get(index);
	}

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        ItemViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.name = (TextView) row.findViewById(R.id.name);
            viewHolder.score = (TextView) row.findViewById(R.id.score);
            viewHolder.layout=row.findViewById(R.id.layout);
            row.setTag(viewHolder);
		} else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.name.setText(stat[0]);
        viewHolder.score.setText(stat[1]);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(contextt, userdetial.class);
               intent.putExtra("name",stat[0]);
               intent.putExtra("phone",stat[1]);//                Toast.makeText(MainActivity.this, ""+adapterView.getItemAtPosition(i).getClass(), Toast.LENGTH_SHORT).show();
//                //Toast.makeText(Employdashboard.this, ""+sessionManager.get_id(), Toast.LENGTH_SHORT).show();
               contextt.startActivity(intent);



                //Toast.makeText(getContext(), ""+stat[0]+stat[1], Toast.LENGTH_SHORT).show();
            }
        });
		return row;
	}
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String[]> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String[] item : exampleListFull) {
                    if (item[0].toLowerCase().contains(filterPattern)) {
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
            scoreList.clear();
            scoreList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
