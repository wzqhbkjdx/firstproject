package com.agile.news.home;

import java.util.ArrayList;
import java.util.HashSet;

import com.agile.news.NewsDetailActivity;
import com.agile.news.R;
import com.agile.news.adapter.NewsAdapter;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsListBean;
import com.agile.news.bean.NewsListBean.News;
import com.agile.news.bean.NewsListBean.TopNews;
import com.agile.news.pullrefreshview.PullToRefreshListView;
import com.agile.news.view.RollViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		this.url = url;
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_item_news, null);
		topNewsView = inflater.inflate(R.layout.layout_roll_view, null);
		ViewUtils.inject(this,view);
		ViewUtils.inject(this,topNewsView);
		//上拉加载不可用
		ptrLv.setPullLoadEnabled(false);
		//滚动到底部自动加载可用
		ptrLv.setScrollLoadEnabled(true);
		//得到实际的listview 设置点击事件
		ptrLv.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
//				Intent intent = new Intent(context,NewsDetailActivity.class);
				
			}
		});
		
		
		
		return null;
	}

	@Override
	public void initData() {
		
	}

	@Override
	protected void processClick(View v) {
		
	}

}



















