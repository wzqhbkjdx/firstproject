package com.agile.news.home;

import com.agile.news.R;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsCenterCategories.NewsCategory;
import com.agile.news.bean.NewsCenterCategory.CenterCategory;
import com.lidroid.xutils.ViewUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class PicPage extends BasePage{
	private CenterCategory category;
	public PicPage(Context context, CenterCategory newsCategory) {
		super(context);
		category = newsCategory;
	}
	
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_news, null);
		ViewUtils.inject(this,view);
		return view;
	}
	
	@Override
	public void initData() {
		
	}
	
	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
