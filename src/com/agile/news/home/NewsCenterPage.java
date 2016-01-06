package com.agile.news.home;

import java.util.ArrayList;
import java.util.List;

import com.agile.news.MainActivity;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsCenterCategory;
import com.agile.news.bean.NewsCenterCategory.CenterCategory;
import com.agile.news.fragment.MenuFragment2;
import com.agile.news.utils.GsonUtils;
import com.agile.news.utils.HQApi;
import com.agile.news.utils.SharedPreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NewsCenterPage extends BasePage{

	public NewsCenterPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(context);
		textView.setText("我是新闻中心");
		return textView;
	}

	@Override
	public void initData() {
		// TODO 联网引擎 图片处理 JSON解析 开源框架	xUTils
		
		
		//判断有没有网络 有网络 通过网络更新数据 没有网络 读取缓存数据
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		
		if(info != null && info.isConnected())
		{
			// 缓存数据后，仍然联网获取数据 便于更新
			testTarget();
		}else
		{
			// 获取缓存的数据 用于没有网络的时候
			Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
			String value = SharedPreferencesUtils.getString(context, HQApi.NEWS_CENTER_CATEGORIES);
			if (!TextUtils.isEmpty(value)) {
				ProcessData(value);
			}
		}
		
		
		
		
	}

	private void testTarget() {
		
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
				HQApi.NEWS_CENTER_CATEGORIES,
		    new RequestCallBack<String>(){
		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		        }

		        @Override
		        public void onSuccess(ResponseInfo<String> responseInfo) {
		        	LogUtils.d(responseInfo.result);
		        	
		        	//缓存数据直接方法
//		        	SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
//		        	Editor editor = sp.edit();
//		        	editor.putString("news", responseInfo.result);
//		        	editor.commit();
		        	
		        	//用封装的类来缓存数据
		        	SharedPreferencesUtils.saveString(context, HQApi.NEWS_CENTER_CATEGORIES, responseInfo.result);
		        	ProcessData(responseInfo.result);
		        	
		        }

				@Override
		        public void onStart() {
		        }

		        @Override
		        public void onFailure(HttpException error, String msg) {
		        }
		});
		
	}
	
	private List<String> menuNewCenterList = new ArrayList<String>();
	
	
	
	protected void ProcessData(String result) {
		 	
		 NewsCenterCategory category = GsonUtils.jisonToBean(result, NewsCenterCategory.class);
		 if(category.retcode == 200)
		 {
			 List<CenterCategory> data = category.data;
			 for(CenterCategory cate : data)
			 {
				 menuNewCenterList.add(cate.title);
			 }
			 
			 MenuFragment2 menuFragment2 = ((MainActivity)context).getMenuFragment2();
			 menuFragment2.initNewsCenterMenu((ArrayList<String>) menuNewCenterList);
			 
		 }
			
	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub
		
	}

}





























