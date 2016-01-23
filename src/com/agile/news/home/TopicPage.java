package com.agile.news.home;

import java.util.ArrayList;

import com.agile.news.R;
import com.agile.news.TopicListActivity;
import com.agile.news.adapter.TopicAdapter;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsCenterCategories.NewsCategory;
import com.agile.news.bean.NewsCenterCategory.CenterCategory;
import com.agile.news.bean.TopicListBean;
import com.agile.news.bean.TopicListBean.Topic;
import com.agile.news.pullrefreshview.PullToRefreshBase;
import com.agile.news.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.agile.news.pullrefreshview.PullToRefreshListView;
import com.agile.news.utils.CommonUtil;
import com.agile.news.utils.QLParser;
import com.agile.news.utils.SharePrefUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TopicPage extends BasePage{
	private String moreUrl;
	private CenterCategory category;

	public TopicPage(Context context, CenterCategory newsCategory) {
		super(context);
		category = newsCategory;
	}
	
	@ViewInject(R.id.lv_topic)
	private PullToRefreshListView ptrLv;
	private ArrayList<Topic> topicList = new ArrayList<Topic>();
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_topic, null);
		ViewUtils.inject(this, view);
		ptrLv.setPullLoadEnabled(false);
		ptrLv.setScrollLoadEnabled(true);
		setLastUpdateTime();
		ptrLv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				getTopicList(category.url1, true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getTopicList(moreUrl, false);
			}
		});
		ptrLv.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Topic topic = topicList.get(position);
				Intent intent = new Intent(context, TopicListActivity.class);
				intent.putExtra("url", topic.url);
				intent.putExtra("url", topic.title);
				context.startActivity(intent);
			}
		});
		return view;
	}

	protected void getTopicList(final String loadUrl, final boolean isRefresh) {
		loadData(HttpMethod.GET, loadUrl, null, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> info) {
				LogUtils.d("response_json---" + info.result);
				if(isRefresh) {
					SharePrefUtil.saveString(context, loadUrl, info.result);
				}
				processedData(isRefresh, info.result);
				
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				LogUtils.d("fail_json---" + arg1);
				onLoaded();
			}
		});
	}

	@Override
	public void initData() {
		String result = SharePrefUtil.getString(context, category.url1, "");
		if(!TextUtils.isEmpty(result)) {
			processedData(true, result);
		}
		getTopicList(category.url1, true);
	}
	
	protected void onLoaded() {
		dismissLoadingView();
		ptrLv.onPullDownRefreshComplete();
		ptrLv.onPullUpRefreshComplete();
	}
	
	private String countCommentUrl;
	private TopicAdapter adapter;
	protected void processedData(boolean isRefresh, String result) {
		TopicListBean topicListBean = QLParser.parse(result, TopicListBean.class);
		if(topicListBean.retcode != 200) {
		} else {
			isLoadSuccess = true;
			if(isRefresh) {
				topicList.clear();
			}
			topicList.addAll(topicListBean.data.topic);
			moreUrl = topicListBean.data.more;
			if(adapter == null) {
				adapter = new TopicAdapter(context, topicList, countCommentUrl);
				ptrLv.getRefreshableView().setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
			onLoaded();
			LogUtils.d("moreUrl---" + moreUrl);
			if(TextUtils.isEmpty(moreUrl)){
				ptrLv.setHasMoreData(false);
			} else {
				ptrLv.setHasMoreData(false);
			}
			setLastUpdateTime();
		}
		
	}

	private void setLastUpdateTime() {
		String text = CommonUtil.getStringDate();
		ptrLv.setLastUpdatedLabel(text);
	}

	

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
