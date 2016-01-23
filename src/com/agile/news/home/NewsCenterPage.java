package com.agile.news.home;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import com.agile.news.MainActivity;
import com.agile.news.R;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsCenterCategories;
import com.agile.news.bean.NewsCenterCategories.NewsCategory;
import com.agile.news.bean.NewsCenterCategory;
import com.agile.news.bean.NewsCenterCategory.CenterCategory;
import com.agile.news.fragment.MenuFragment2;
import com.agile.news.utils.GsonTools;
import com.agile.news.utils.GsonUtils;
import com.agile.news.utils.HQApi;
import com.agile.news.utils.QLParser;
import com.agile.news.utils.SharePrefUtil;
import com.agile.news.utils.SharePrefUtil.KEY;
import com.agile.news.utils.SharedPreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsCenterPage extends BasePage {

	public NewsCenterPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void onResume() {
		super.onResume();
	};

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.news_center_frame, null);
		ViewUtils.inject(this, view);
		System.out.print("初始化view");
		// TextView textView = new TextView(context);
		// textView.setText("我是新闻中心");
		return view;
	}

	private ArrayList<BasePage> pageList;
	public ArrayList<String> newsCenterMenuList = new ArrayList<String>();

	@Override
	public void initData() {
		// TODO 联网引擎 图片处理 JSON解析 开源框架 xUTils
		// 判断有没有网络 有网络 通过网络更新数据 没有网络 读取缓存数据
		// ConnectivityManager cm = (ConnectivityManager)
		// context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// NetworkInfo info = cm.getActiveNetworkInfo();
		// if(info != null && info.isConnected()){
		// // 缓存数据后，仍然联网获取数据 便于更新
		// testTarget();
		// } else
		// {
		// // 获取缓存的数据 用于没有网络的时候
		// Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
		// String value = SharedPreferencesUtils.getString(context,
		// HQApi.LOCAL_CATEGORIES);
		// if(TextUtils.isEmpty(value)) {
		// System.out.println("value是空");
		// }
		// if (!TextUtils.isEmpty(value)) {
		// ProcessData(value);
		// }
		// }

		pageList = new ArrayList<BasePage>();
		if (newsCenterMenuList.size() == 0) {
			String result = SharePrefUtil.getString(context, HQApi.LOCAL_CATEGORIES, "");
			if (!TextUtils.isEmpty(result)) {
				ProcessData(result);
				// System.out.print("初始化数据");
			}
			getNewsCenterCategories();
		}

	}

	private void getNewsCenterCategories() {
		loadData(HttpMethod.GET, HQApi.LOCAL_CATEGORIES, null, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> info) {
				System.out.print("success");
				SharePrefUtil.saveString(context, HQApi.LOCAL_CATEGORIES, info.result);
				ProcessData(info.result);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				System.out.print("response_fail");

			}
		});
	}

	/**
	 * 用来获取数据的方法，被getNewsCenterCategories()取代
	 */
	// private void testTarget() {
	//
	// HttpUtils http = new HttpUtils();
	// http.send(HttpRequest.HttpMethod.GET, HQApi.LOCAL_CATEGORIES,
	// new RequestCallBack<String>(){
	// @Override
	// public void onLoading(long total, long current, boolean isUploading) {
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<String> responseInfo) {
	// LogUtils.d(responseInfo.result);
	// SharedPreferencesUtils.saveString(context, HQApi.LOCAL_CATEGORIES,
	// responseInfo.result);
	// ProcessData(responseInfo.result);
	//
	// }
	//
	// @Override
	// public void onStart() {
	// }
	//
	// @Override
	// public void onFailure(HttpException error, String msg) {
	// }
	// });
	//
	// }

	// private List<String> menuNewCenterList = new ArrayList<String>();
	public List<CenterCategory> categoriesList;

	protected void ProcessData(String result) {

		System.out.println(result);
		NewsCenterCategory categories = GsonUtils.jisonToBean(result, NewsCenterCategory.class);
		System.out.print(categories.retcode);
		if (categories.retcode == 200) {
			categoriesList = categories.data;
			for (CenterCategory cate : categoriesList) {
				newsCenterMenuList.add(cate.title);
				System.out.println(cate.title);
			}
			MenuFragment2 menuFragment2 = ((MainActivity) context).getMenuFragment2();
			menuFragment2.initNewsCenterMenu((ArrayList<String>) newsCenterMenuList);
		}
		CenterCategory newsCategory = categoriesList.get(0);
		SharePrefUtil.saveString(context, KEY.CATE_ALL_JSON, GsonTools.creatGsonString(newsCategory.children));
		SharePrefUtil.saveString(context, KEY.CATE_EXTEND_ID, GsonTools.creatGsonString(categories.extend));
		pageList.clear();
		BasePage newsPage = new NewsPage(context, newsCategory);
		BasePage topicPage = new TopicPage(context, categoriesList.get(1));
		BasePage PicPage = new PicPage(context, categoriesList.get(2));
		pageList.add(newsPage);
		pageList.add(topicPage);
		pageList.add(PicPage);
		switchFragment(MenuFragment2.newsCenterPosition);

	}

	@ViewInject(R.id.news_center_fl)
	private FrameLayout news_center_fl;

	private void switchFragment(int newsCenterPosition) {
		BasePage page = pageList.get(newsCenterPosition);
		
		switch (newsCenterPosition) {
		case 0:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 1:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 2:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 3:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;
		case 4:
			news_center_fl.removeAllViews();
			news_center_fl.addView(page.getContentView());
			break;

		default:
			break;
		}
		
		page.initData();

	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub

	}

}
