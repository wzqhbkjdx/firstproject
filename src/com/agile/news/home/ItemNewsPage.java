package com.agile.news.home;

import java.util.ArrayList;
import java.util.HashSet;

import com.agile.news.R;
import com.agile.news.adapter.NewsAdapter;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsListBean;
import com.agile.news.bean.NewsListBean.News;
import com.agile.news.bean.NewsListBean.TopNews;
import com.agile.news.pullrefreshview.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemNewsPage extends BasePage{
	@ViewInject(R.id.lv_item_news)
	private PullToRefreshListView ptrLv;
	@ViewInject(R.id.top_news_title)
	private TextView topNewsTitle;
	@ViewInject(R.id.top_news_viewpager)
	private LinearLayout mViewPagerLay;
	@ViewInject(R.id.dots_ll)
	private LinearLayout dotLl;
	private View topNewsView;
	private String url;
	private String moreUrl;
	private ArrayList<News> news = new ArrayList<NewsListBean.News>();
	private ArrayList<TopNews> topNews;
	private NewsAdapter adapter;
	private ArrayList<View> dotList;
	private ArrayList<String> titleList, urlList;
	private HashSet<String> readSet = new HashSet<String>();
	private String  hasReadIds;
	private RollViewPager mViewPager;
	public boolean isLoadSuccess;

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
