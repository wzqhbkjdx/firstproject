package com.agile.news.home;

import com.agile.news.base.BasePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SmartServicePage extends BasePage{

	public SmartServicePage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(context);
		textView.setText("我是服务");
		return textView;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
