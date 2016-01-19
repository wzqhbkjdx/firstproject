package com.agile.news.home;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.agile.news.NewsDetailActivity;
import com.agile.news.R;
import com.agile.news.adapter.NewsAdapter;
import com.agile.news.base.BasePage;
import com.agile.news.bean.CountList;
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
import com.agile.news.view.RollViewPager.OnpagerClickCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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

	public ItemNewsPage(Context context, String url ) {
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
					mViewPager = new RollViewPager(context,dotList,
							R.drawable.dot_focus,R.drawable.dot_normal,
							new OnpagerClickCallback() {
								@Override
								public void onPagerClick(int position) {
									TopNews news = topNews.get(position);
									if(news.type.equals("news")) {
										Intent intent = new Intent(context, NewsDetailActivity.class);
										String url = topNews.get(position).url;
										String title = topNews.get(position).title;
										intent.putExtra("url", url);
										intent.putExtra("title", title);
										context.startActivity(intent);
									}else if(news.type.equals("topic")) {
										
									}
								}
							});
					mViewPager.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					//top新闻的图片地址
					mViewPager.setUriList(urlList);
					mViewPager.setTitle(topNewsTitle, titleList);
					mViewPager.startRoll();
					mViewPagerLay.removeAllViews();
					mViewPagerLay.addView(mViewPager);
					if (ptrLv.getRefreshableView().getHeaderViewsCount() < 1) {
						ptrLv.getRefreshableView().addHeaderView(topNewsView);
					}
				}
			}
			moreUrl = newsListBean.data.more;
			System.out.println("moreUrl="+moreUrl.toString());
			if(newsListBean.data.news != null) {
				getNewsCommentCount(newsListBean.data.countcommenturl,
						newsListBean.data.news, isRefresh);
			}
		}
	}

	private void getNewsCommentCount(String countconmmenturl,
			final ArrayList<News> newsList, final boolean isRefresh) {
		StringBuffer sb = new StringBuffer(countconmmenturl);
		for(News news : newsList) {
			sb.append(news.id + ",");
		}
		loadData(HttpMethod.GET, sb.toString(), null,
				new RequestCallBack<String>() {
			
					@Override
					public void onSuccess(ResponseInfo<String> info) {
						LogUtils.d("response_json---" + info.result);
						CountList countList = QLParser.parse(info.result, CountList.class);
						for(News news : newsList) {
							news.commentcount = countList.data.get(news.id + "");
							if(readSet.contains(news.id)) {
								news.isRead = true;
							}else {
								news.isRead = false;
							}
						}
						if(isRefresh) {
							news.clear();
							news.addAll(newsList);
						} else {
							news.addAll(newsList);
						}
						
						if(adapter == null) {
							adapter = new NewsAdapter(context, news, 0);
							ptrLv.getRefreshableView().setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}
						onLoaded();
						LogUtils.d("moreUrl---" + moreUrl);
						if(TextUtils.isEmpty(moreUrl)) {
							ptrLv.setHasMoreData(false);
						} else {
							ptrLv.setHasMoreData(true);
						}
						setLastUpdateTime();
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						LogUtils.d("fail_json---" + arg1);
						onLoaded();
					}
				});
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
	protected void processClick(View v) {
		
	}
	

	@Override
	public void initData() {
		hasReadIds = SharePrefUtil.getString(context, Constants.READ_NEWS_IDS, "");
		String[] ids = hasReadIds.split(",");
		for(String id : ids) {
			readSet.add(id);
		}
		if(!TextUtils.isEmpty(url)) {
			String result = SharePrefUtil.getString(context, url, "");
			if(TextUtils.isEmpty(result)) {
				processDataFromCache(true, result);
			}
			getNewsList(url, true);
		}
	}

	private void processDataFromCache(boolean isRefresh, String result) {
		NewsListBean newsList = QLParser.parse(result, NewsListBean.class);
		if (newsList.retcode != 200) {
		} else {
			isLoadSuccess = true;
			countCommentUrl = newsList.data.countcommenturl;
			if (isRefresh) {
				topNews = newsList.data.topnews;
				if (topNews != null) {
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					for (TopNews news : topNews) {
						titleList.add(news.title);
						urlList.add(news.topimage);
					}
					initDot(topNews.size());
					mViewPager = new RollViewPager(context, dotList,
							R.drawable.dot_focus, R.drawable.dot_normal,
							new OnpagerClickCallback() {
								@Override
								public void onPagerClick(int position) {
									TopNews news = topNews.get(position);
									if (news.type.equals("news")) {
										Intent intent = new Intent(context,
												NewsDetailActivity.class);
										String url = topNews.get(position).url;
										String commentUrl = topNews
												.get(position).commenturl;
										String newsId = topNews.get(position).id;
										String commentListUrl = topNews
												.get(position).commentlist;
										String title = topNews.get(position).title;
										String imgUrl = topNews.get(position).topimage;
										boolean comment = topNews.get(position).comment;
										intent.putExtra("url", url);
										intent.putExtra("commentUrl",
												commentUrl);
										intent.putExtra("newsId", newsId);
										intent.putExtra("imgUrl", imgUrl);
										intent.putExtra("title", title);
										intent.putExtra("comment", comment);
										intent.putExtra("countCommentUrl",
												countCommentUrl);
										intent.putExtra("commentListUrl",
												commentListUrl);
										context.startActivity(intent);
									} else if (news.type.equals("topic")) {
									
									}
								}
							});
					mViewPager.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));
					mViewPager.setUriList(urlList);
					mViewPager.setTitle(topNewsTitle, titleList);
					mViewPager.startRoll();
					mViewPagerLay.removeAllViews();
					mViewPagerLay.addView(mViewPager);
					if (ptrLv.getRefreshableView().getHeaderViewsCount() < 1) {
						ptrLv.getRefreshableView().addHeaderView(topNewsView);
					} 
				}
			} 
			moreUrl = newsList.data.more;
			LogUtils.d("111111="+newsList.data.news.size());
			if (isRefresh) {
				news.clear();
				news.addAll(newsList.data.news);
			} else {
				news.addAll(newsList.data.news);
			}
			for (News newsItem : news) {
				if(readSet.contains(newsItem.id)){
					newsItem.isRead= true;
				}else{
					newsItem.isRead = false;
				}
			}
			if (adapter == null) {
				adapter = new NewsAdapter(context, news, 0);
				ptrLv.getRefreshableView().setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
			onLoaded();
			LogUtils.d("moreUrl---" + moreUrl);
			if (TextUtils.isEmpty(moreUrl)) {
				ptrLv.setHasMoreData(false);
			} else {
				ptrLv.setHasMoreData(true);
			}
			setLastUpdateTime();
		}
	}

	

}



















