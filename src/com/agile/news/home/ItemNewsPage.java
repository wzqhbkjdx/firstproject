package com.agile.news.home;

import com.agile.news.R;
import com.agile.news.base.BasePage;
import com.agile.news.pullrefreshview.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ItemNewsPage extends BasePage{
	@ViewInject(R.id.lv_item_news)
	private PullToRefreshListView ptrLv;
	@ViewInject(R.id.top_news_title)
	private TextView topNewsTitle;

	public ItemNewsPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		return null;
	}

	@Override
	public void initData() {
		
	}

	@Override
	protected void processClick(View v) {
		
	}

}
