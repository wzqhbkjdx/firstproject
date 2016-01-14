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
import com.agile.news.pullrefreshview.PullToRefreshBase;
import com.agile.news.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.agile.news.pullrefreshview.PullToRefreshListView;
import com.agile.news.utils.CommonUtil;
import com.agile.news.utils.Constants;
import com.agile.news.utils.QLParser;
import com.agile.news.utils.SharePrefUtil;
import com.agile.news.view.RollViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
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
				Intent intent = new Intent(context,NewsDetailActivity.class);
				String url = "";
				String title;
				News newsItem;
				if(ptrLv.getRefreshableView().getHeaderViewsCount() > 0) {
					newsItem = news.get(position - 1);
				} else {
					newsItem = news.get(position);
				}
				url = newsItem.url;
				if(!newsItem.isRead) {
					readSet.add(newsItem.id);//该新闻加入到readSet集合中
					newsItem.isRead = true;//设为已读
					//将已读新闻的id保存到SharePreferences
					SharePrefUtil.saveString(context, Constants.READ_NEWS_IDS, hasReadIds+","+newsItem.id);
				}
				title = newsItem.title;
				intent.putExtra("url", url);
				intent.putExtra("title", title);
				context.startActivity(intent);
			}
		});
		setLastUpdateTime();	
		//设置下拉刷新的listener
		ptrLv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				getNewsList(url,true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getNewsList(moreUrl,false);
			}
		
		});
		
		return view;
	}

	protected void getNewsList(final String loadUrl, final boolean isRefresh) {
		loadData(HttpMethod.GET, loadUrl, null, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> info) {
				if(isRefresh) {
					SharePrefUtil.saveString(context, url, info.result);
				}
				processData(isRefresh, info.result);
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				onLoaded();
			}
		});
	}

	private void onLoaded() {
		dismissLoadingView();
		ptrLv.onPullDownRefreshComplete();
		ptrLv.onPullUpRefreshComplete();
	}
	private String countCommentUrl;
	protected void processData(final boolean isRefresh, String result) {
		
		NewsListBean newsListBean = QLParser.parse(result,NewsListBean.class);
		if (newsListBean.retcode != 200) {
			
		} else {
			isLoadSuccess = true;
			countCommentUrl = newsListBean.data.countcommenturl;
			if(isRefresh) {
				topNews = newsListBean.data.topnews;
				if(topNews != null){
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					for(TopNews news : topNews) {
						titleList.add(news.title);
						urlList.add(news.topimage);
					}
					initDot(topNews.size());
//					mViewPager = new RollViewPager();
				}
			}
		}
	}

	private void initDot(int size) {
		dotList = new ArrayList<View>();
		dotLl.removeAllViews();
		for(int i = 0; i < size; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtil.dip2px(context, 6), CommonUtil.dip2px(context, 6));
			params.setMargins(5, 0, 5, 0);
			View m = new View(context);
			if(i == 0){
				m.setBackgroundResource(R.drawable.dot_focus);
			}else {
				m.setBackgroundResource(R.drawable.dot_normal);
			}
			m.setLayoutParams(params);
			dotLl.addView(m);
			dotList.add(m);
		}
	}

	private void setLastUpdateTime() {
		String text = CommonUtil.getStringDate();
		ptrLv.setLastUpdatedLabel(text);
	}
	
	
	

	@Override
	public void initData() {
		
	}

	@Override
	protected void processClick(View v) {
		
	}

}



















