package com.agile.news.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CGBaseAdapter<T, Q> extends BaseAdapter {
	
	private Context context;
	private List<T> list;
	private Q view;
	
	public CGBaseAdapter() {
		super();
	}

	public CGBaseAdapter(Context context, List<T> list, Q view) {
		super();
		this.context = context;
		this.list = list;
		this.view = view;
	}
	

	public CGBaseAdapter(Context context, List<T> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

}






