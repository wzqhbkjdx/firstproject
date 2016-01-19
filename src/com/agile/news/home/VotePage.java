package com.agile.news.home;

import com.agile.news.R;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsCenterCategories.NewsCategory;
import com.lidroid.xutils.ViewUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class VotePage extends BasePage{
	
	private NewsCategory category;
	public VotePage(Context context, NewsCategory newsCategory) {
		super(context);
		category = newsCategory;
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_topic, null);
		ViewUtils.inject(this,view);
		return view;
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
