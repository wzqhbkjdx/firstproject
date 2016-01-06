package com.agile.news.base;

import com.agile.news.MainActivity;
import com.agile.news.R;
import com.agile.news.utils.CommonUtil;
import com.agile.news.utils.CustomToast;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BasePage implements OnClickListener{
	
	private View contentView;
	public Context context;
	@ViewInject(R.id.loading_view)
	private View loadingView;
	private LinearLayout loadingfailView;
	private SlidingMenu slidingMenu;
	private Button leftBtn;
	private ImageButton rightBtn;
	private ImageButton leftImgBtn;
	private ImageButton rightImgBtn;
	private TextView titleTv;
	public boolean isLoadSuccess = false;
	
	/**
	 * 1. 画界面
	 * 2. 初始化数据
	 * @param context
	 */
	
	public BasePage(Context context)
	{
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = initView(inflater);
		loadingView = contentView.findViewById(R.id.loading_view);
		loadingfailView = (LinearLayout) contentView.findViewById(R.id.ll_load_fail);
		if(context instanceof MainActivity)//父类是子类实例化的结果，父类就可以强制转换为子类
		{
			slidingMenu = ((MainActivity)context).getSlidingMenu();
		}
	}
	
	protected void initTitleBar(View view)
	{
		leftBtn = (Button) view.findViewById(R.id.btn_left);
		rightBtn = (ImageButton) view.findViewById(R.id.btn_right);
		leftImgBtn = (ImageButton) view.findViewById(R.id.imgbtn_left);
		rightImgBtn = (ImageButton) view.findViewById(R.id.imgbtn_right);
		leftImgBtn.setImageResource(R.drawable.img_menu);
		titleTv = (TextView) view.findViewById(R.id.txt_title);
		leftBtn.setVisibility(View.GONE);
		rightBtn.setVisibility(View.GONE);
		if(leftImgBtn!=null)
		leftImgBtn.setOnClickListener(this);
	}
	
	public View getContentView()
	{
		return contentView;
	}
	
	protected void dismissLoadingView() {
		if (loadingView != null)
			loadingView.setVisibility(View.INVISIBLE);
	}
	
	public void onResume() {

	}
	
	public void showToast(String msg) {
		showToast(msg, 0);
	}
	
	public void showToast(String msg, int time) {
		CustomToast customToast = new CustomToast(context, msg, time);
		customToast.show();
	}
	
	protected void loadData(HttpRequest.HttpMethod method, String url,
			RequestParams params, RequestCallBack<String> callback) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 1);
		LogUtils.allowD = true;
		if (params != null) {
			if (params.getQueryStringParams() != null)
				LogUtils.d(url + params.getQueryStringParams().toString());
		} else {
			params = new RequestParams();
		}
		//设备ID
//		params.addHeader("x-deviceid", app.deviceId);
		//渠道，统计用
//		params.addHeader("x-channel", app.channel);
		if (0 == CommonUtil.isNetworkAvailable(context)) {
			showToast("无网络，请检查网络连接！");
		} else {
			http.send(method, url, params, callback);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_left:
			Handler handler  = new Handler();
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					slidingMenu.toggle();
					
				}
			}, 100);
		
			break;

		default:
			break;
		}
		
	}
	
	
	
	public abstract View initView(LayoutInflater inflater);
	public abstract void initData();
	protected abstract void processClick(View v);
	

}


































