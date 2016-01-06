package com.agile.news.home;

import com.agile.news.R;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsCenterCategory;
import com.lidroid.xutils.ViewUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class InteractPage extends BasePage {
	
	private NewsCenterCategory category;

	public InteractPage(Context context, NewsCenterCategory newsCenterCategory) {
		super(context);
		category = newsCenterCategory;
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_news, null);
		ViewUtils.inject(this, view);
		return null;
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
