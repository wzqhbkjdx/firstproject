package com.agile.news.base;

import com.agile.news.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements OnClickListener{

	public View view;
	public Context context;
	public SlidingMenu slidingMenu;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		slidingMenu = ((MainActivity)getActivity()).getSlidingMenu();
		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		context = getActivity();
		
		//slidingMenu = ((MainActivity)getActivity()).getSlidingMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = initView(inflater);
		return view;
	}
	
	public View getRootView(){
		return view;
	}
	
	
	
	
	/**
	 * 初始化view
	 * @param inflater
	 * @return
	 */
	public abstract View initView(LayoutInflater inflater);
	
	/**
	 * 初始化数据
	 */
	public abstract  void initData(Bundle savedInstanceState);
		
		
	
	

}
