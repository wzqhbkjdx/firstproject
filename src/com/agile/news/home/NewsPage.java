package com.agile.news.home;

import java.util.ArrayList;

import com.agile.news.R;
import com.agile.news.base.BasePage;
import com.agile.news.bean.NewsCenterCategory.CenterCategory;
import com.agile.news.bean.NewsCenterCategory.CenterCategoryItem;
import com.agile.news.pagerindicator.TabPageIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.opengl.Visibility;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsPage extends BasePage{

	private CenterCategory category;
	
	public NewsPage(Context context, CenterCategory newsCategory) {
		super(context);
		category = newsCategory;
		// TODO Auto-generated constructor stub
	}

	private NewsCenterPage NewsCenterFragment;
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_news, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		initIndicator();
	}
	
	private ArrayList<ItemNewsPage> pages = new ArrayList<ItemNewsPage>();
	@ViewInject(R.id.indicator)
	private TabPageIndicator indicator;
	private NewsPagerAdapter adapter;
	@ViewInject(R.id.pager)
	private ViewPager pager;
	private int curIndex = 0;
	
	private void initIndicator() {
		pages.clear();
		for(CenterCategoryItem cate : category.children) {
			pages.add(new ItemNewsPage(context,cate.url));
		}
		adapter = new NewsPagerAdapter(context,pages);
		pager.removeAllViews();
		pager.setAdapter(adapter);
		
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if(arg0==0) {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
				ItemNewsPage page = pages.get(arg0);
				if(!page.isLoadSuccess) {
					page.initData();
				}
				curIndex = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		pages.get(0).initData();
		
		indicator.setViewPager(pager);
		
		indicator.setCurrentItem(curIndex);
		indicator.setVisibility(View.VISIBLE);//在indicator加载数据之前，setVisibility 为GONE，加载数据之后再设置为VISIBLE
		isLoadSuccess = true;
	}
	


	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	class NewsPagerAdapter extends PagerAdapter {
		private ArrayList<ItemNewsPage> page;
		
		public NewsPagerAdapter(Context context, ArrayList<ItemNewsPage> pages) {
			this.page = page;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			if(position>=pages.size()) return;
			((ViewPager)container).removeView(pages.get(position).getContentView());
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			int size = category.children.size();
			return category.children.get(position % size).title;
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager)container).addView(pages.get(position).getContentView(),0);
			return pages.get(position).getContentView();
		}

		@Override
		public int getCount() {
			return category.children.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
	}

}










