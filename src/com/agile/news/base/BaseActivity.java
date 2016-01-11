package com.agile.news.base;

import com.agile.news.R;
import com.agile.news.application.QLApplication;
import com.agile.news.utils.AppManager;
import com.agile.news.utils.CommonUtil;
import com.agile.news.utils.CustomProgressDialog;
import com.agile.news.utils.CustomToast;
import com.agile.news.utils.DialogUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener{
	
	protected Context context;
	protected QLApplication app;
	@ViewInject(R.id.loading_view)
	protected View loadingView;
	@ViewInject(R.id.ll_load_fail)
	protected LinearLayout loadfailView;
	@ViewInject(R.id.btn_left)
	protected Button leftBtn;
	protected ImageButton rightBtn;
	protected ImageButton leftImgBtn;
	protected ImageButton rightImgBtn;
	protected TextView titleTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		AppManager.getAppManager().addActivity(this);
		context = this;
		initView();
		loadingView = findViewById(R.id.loading_view);
		loadfailView = (LinearLayout) findViewById(R.id.ll_load_fail);
		initData();
		
	}
	
	protected void initTitleBar(){
		leftBtn = (Button) findViewById(R.id.btn_left);
		rightBtn = (ImageButton) findViewById(R.id.btn_right);
		if(leftBtn!=null){
			leftBtn.setVisibility(View.GONE);
		}
		if(rightBtn!=null){
			rightBtn.setVisibility(View.GONE);
		}
		
		leftImgBtn = (ImageButton) findViewById(R.id.imgbtn_left);
		rightImgBtn = (ImageButton) findViewById(R.id.imgbtn_right);
		if(rightImgBtn!=null){
			rightImgBtn.setVisibility(View.INVISIBLE);
		}
		if(leftImgBtn!=null){
			leftImgBtn.setImageResource(R.drawable.back);
		}
		
		titleTv = (TextView) findViewById(R.id.txt_title);
		if(leftImgBtn!=null){
			leftImgBtn.setOnClickListener(this);
		}
		if(rightBtn!=null){
			rightBtn.setOnClickListener(this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgbtn_left:
			this.finish();
			break;

		default:
			break;
		}
		processClick(v);
	}
	
	protected void showToast(String msg) {
		showToast(msg, 0);
	}

	protected void showToast(String msg, int time) {
		CustomToast customToast = new CustomToast(context, msg, time);
		customToast.show();
	}
	
	protected CustomProgressDialog dialog;
	
	protected void showProgressDialog(String content) {
		if(dialog == null && content != null) {
			dialog = (CustomProgressDialog) DialogUtil.createProgressDialog(context, content);
		}
		dialog.show();
	}
	
	protected void closeProgressDialog() {
		if(dialog != null) {
			dialog.dismiss();
		}
	}
	
	public void showLoadingView() {
		if(loadingView != null) {
			loadingView.setVisibility(View.VISIBLE);
		}
	}
	
	public void dismissLoadingView() {
		if(loadingView != null) {
			loadingView.setVisibility(View.INVISIBLE);
		}
	}
	
	public void showLoadFailView() {
		if (loadingView != null) {
			loadingView.setVisibility(View.VISIBLE);
			loadfailView.setVisibility(View.VISIBLE);
		}

	}

	public void dismissLoadFailView() {
		if (loadingView != null)
			loadfailView.setVisibility(View.INVISIBLE);
	}
	
	protected void loadData(HttpRequest.HttpMethod method, String url,
			RequestParams params, RequestCallBack<String> callback) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		
		LogUtils.allowD = true;
		if(params != null) {
			if(params.getQueryStringParams() != null)
				LogUtils.d(url + "?" + params.getQueryStringParams().toString());
		}else{
			params = new RequestParams();
		}
		
		if(CommonUtil.isNetworkAvailable(context) == 0) {
			showToast("����ʧ�ܣ��������磡");
		} else {
			LogUtils.d(url);
			http.send(method, url, params, callback);
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}
	
	
	protected abstract void initView();

	protected abstract void initData();

	protected abstract void processClick(View v);


}




















