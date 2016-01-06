package com.agile.news;

import com.agile.news.fragment.HomeFragment;
import com.agile.news.fragment.MenuFragment2;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity 
{
	private SlidingMenu sliddingmenu;
	private MenuFragment2 menufragment;
	private HomeFragment homeFragment;

	/**
	 * 1. 得到滑动菜单
	 * 2. 设置滑动菜单是在左边出来还是在右边出来
	 * 3. 设置滑动菜单宽度
	 * 4. 设置滑动菜单的阴影 阴影随着滑动慢慢变淡
	 * 5. 设置阴影的宽度
	 * 6. 设置滑动菜单touch的范围
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//去掉上方的title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setBehindContentView(R.layout.menu_frame);
		setContentView(R.layout.content_frame);  //这里用content FrameLayout 帧布局 便于动态加载
		
		sliddingmenu = getSlidingMenu();
		
		//设置菜单从左边滑动出来
		sliddingmenu.setMode(SlidingMenu.LEFT);
		
		//设置滑动时的渐变程度
		sliddingmenu.setFadeDegree(0.35f);
		
		//设置滑动菜单宽度
		sliddingmenu.setBehindWidthRes(R.dimen.slidingmenu_offset);
		
		//设置滑动菜单的阴影
		sliddingmenu.setShadowDrawable(R.drawable.shadow);
		
		//设置阴影的宽度
		sliddingmenu.setShadowWidth(R.dimen.shadow_width);
		
		//SlidingMenu划出时主页面显示的剩余宽度
		//sliddingmenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		
		//设置滑动菜单的touch范围
		sliddingmenu.setTouchModeAbove(sliddingmenu.TOUCHMODE_MARGIN);
		
		//从右边滑出的侧滑菜单
//		sliddingmenu.setSecondaryMenu(R.layout.right_menu);
//		sliddingmenu.setSecondaryShadowDrawable(R.drawable.shadowright);
//		RightMenuFragment rightMenuFragment = new RightMenuFragment();
//		getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_frame, rightMenuFragment).commit();
		
		if(savedInstanceState == null)
		{
			//用homefragment替换content
			menufragment = new MenuFragment2();
			//将侧滑菜单的FrameLayout布局替换为menufragment
			getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menufragment, "Menu").commit();
			homeFragment = new HomeFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, homeFragment,"Home").commit();
		}
		
	}
	
	/**
	 * 获取菜单
	 */
	public MenuFragment2 getMenuFragment2()
	{
		menufragment = (MenuFragment2) getSupportFragmentManager().findFragmentByTag("Menu");
		return menufragment;
	}
	
	
	
	
	/**
	 * 回调 用f替换当前framelayout布局
	 * @param f
	 */
	public void switchFragment(Fragment f)
	{
		//不知道fragment的内部实现机制，不知道beginTransaction().replace()方法是引用传递还是值传递，所以最好不要直接返回，如果是值而不是引用传递就会导致错误
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
		//自动切换
		sliddingmenu.toggle();
	}
	
	

	
}




















