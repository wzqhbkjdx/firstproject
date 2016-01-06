package com.agile.news.fragment;

import java.util.ArrayList;
import java.util.List;

import com.agile.news.MainActivity;
import com.agile.news.R;
import com.agile.news.base.BaseFragment;
import com.agile.news.base.BasePage;
import com.agile.news.home.FunctionPage;
import com.agile.news.home.GovAffairsPage;
import com.agile.news.home.NewsCenterPage;
import com.agile.news.home.SettingPage;
import com.agile.news.home.SmartServicePage;
import com.agile.news.view.CustomViewPager;
import com.agile.news.view.LazyViewPager;
import com.agile.news.view.LazyViewPager.OnPageChangeListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


/**
 * 继承父类BaseFragment 父类里的东西子类里边都有，这样子类的代码就较为简洁
 * @author BYM
 *
 */

public class HomeFragment extends BaseFragment 
{
	@ViewInject(R.id.viewpager)
	private CustomViewPager viewPager;
	@ViewInject(R.id.main_radio)
	private RadioGroup mainRadio;
	private int checkedId = R.id.rb_function;
	List<BasePage> list = new ArrayList<BasePage>();
	private MenuFragment2 menuFragment;
	private int curIndex;
	private int curCheckId = R.id.rb_function;
	private HomePageAdapter adapter;

	@Override
	public View initView(LayoutInflater inflater) 
	{
		View view = inflater.inflate(R.layout.frag_home2, null);
		ViewUtils.inject(this, view); //注入view和事件
		return view;
	}
	@Override
	public void initData(Bundle savedInstanceState) 
	{
		menuFragment = (MenuFragment2) ((MainActivity)getActivity())
				.getSupportFragmentManager().findFragmentByTag("Menu");
		list.add(new FunctionPage(context));
		list.add(new GovAffairsPage(context));
		list.add(new NewsCenterPage(context));
		list.add(new SettingPage(context));
		list.add(new SmartServicePage(context));
		
		adapter = new HomePageAdapter(context,list);
		viewPager.setAdapter(adapter);
		viewPager.setScrollable(false);
		//不进行预加载
		viewPager.setOffscreenPageLimit(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			/**
			 * 页面跳转完成后调用，position当前选中的页面
			 */
			@Override
			public void onPageSelected(int position) {
				//回调 初始化数据
				BasePage page = list.get(position);
				if(!page.isLoadSuccess)
					page.initData();
			}
			
			/**
			 * position 当前点击滑动的页面
			 * positionOffset 当前页面偏移的百分比
			 * positionOffsetPixels 当前页面偏移的像素位置
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				
			}
			
			/**
			 * state=1：正在滑动
			 * state=2：滑动完毕
			 * state=0：什么都没做
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
		list.get(0).initData();
		viewPager.setCurrentItem(0);
		
		/**
		 * radiogroup的切换事件
		 */
		mainRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch (checkedId) {
				case R.id.rb_function:
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					curIndex = 0;
					viewPager.setCurrentItem(0,false);
					break;

				case R.id.rb_news_center:
					//全屏可以滑动出菜单
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					NewsCenterPage page = (NewsCenterPage) list.get(2);
					list.get(2).onResume();
					curIndex = 2;
					viewPager.setCurrentItem(2,false);
					if(menuFragment != null)
					{
						menuFragment.setMenuType(MenuFragment2.NEWS_CENTER);
					}
					break;

				case R.id.rb_smart_service:
					viewPager.setCurrentItem(4,false);
					curIndex = 4;
					break;

				case R.id.rb_gov_affairs:
					viewPager.setCurrentItem(1,false);
					curIndex = 1;
					break;

				case R.id.rb_setting:
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					viewPager.setCurrentItem(3,false);
					curIndex = 3;
					break;

				default:
					break;
				}
				curCheckId = checkedId;
			}
		});	
		mainRadio.check(curCheckId);
	}
	
	public NewsCenterPage getNewsCenterPage(){
		NewsCenterPage page = (NewsCenterPage) list.get(2);
		return page;
	}
	
	class HomePageAdapter extends PagerAdapter{
		
		private Context context;
		private List<BasePage> list;
		
		public HomePageAdapter(Context context, List<BasePage> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			//super.destroyItem(container, position, object);
			((CustomViewPager)container).removeView(list.get(position).getContentView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			((CustomViewPager)container).addView(list.get(position).getContentView(),0);
			
			return list.get(position).getContentView();
		}
		
		
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	
	
	

}
