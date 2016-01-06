package com.agile.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends LazyViewPager {
	private boolean isScrollable;

	public CustomViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isScrollable == false)
			return false;
		else
			return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isScrollable == false)
			return false;
		else
			return super.onTouchEvent(ev);
	}
	
	public boolean isScrollable() {
		return isScrollable;
	}
	
	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

}
