package com.agile.news.fragment;

import java.util.ArrayList;
import java.util.List;

import com.agile.news.MainActivity;
import com.agile.news.R;
import com.agile.news.base.BaseFragment;
import com.agile.news.base.CGBaseAdapter;
import com.agile.news.home.NewsCenterPage;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MenuFragment2 extends BaseFragment {
	//
	public static final int NEWS_CENTER = 1;
	public static int newsCenterPosition = 0;
	// 注入
	@ViewInject(R.id.lv_menu_news_center)
	private ListView lv_menu_news_center; //新闻中心侧滑菜单的listview
	@ViewInject(R.id.lv_menu_smart_service)
	private ListView lv_menu_smart_service; //智能服务侧滑菜单的listview
	@ViewInject(R.id.lv_menu_govaffairs)
	private ListView lv_menu_govaffairs; //滑菜单的listview
	@ViewInject(R.id.tv_menu_classify)
	private TextView classifyTv; //滑菜单的listview

	private View view; 
	private List<String> newsCenterMenu = new ArrayList<String>(); //新闻分类的容器
	private MenuAdapter newsCenterAdapter = null; //侧滑菜单的数据适配器
	private MainActivity mainActivity;
	private int menuType = 0;
	private FragmentManager fragmentManager;
	private NewsCenterPage newsCenterFragment; //新闻中心页面
	
	/**
	 * 初始化view 加载注入布局文件
	 */
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.layout_left_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}
	
	/**
	 * 初始化数据 切换菜单页
	 */
	@Override
	public void initData(Bundle savedInstanceState) {
		mainActivity = (MainActivity) context;
		switchMenu(menuType);
	}
	
	/**
	 * 初始化新闻中心菜单页面
	 * @param menuList
	 */
	public void initNewsCenterMenu(ArrayList<String> menuList) {
		newsCenterMenu.clear();
		newsCenterMenu.addAll(menuList);
		if (newsCenterAdapter == null) {
			newsCenterAdapter = new MenuAdapter(context, newsCenterMenu);
			lv_menu_news_center.setAdapter(newsCenterAdapter);
		} else {
			newsCenterAdapter.notifyDataSetChanged();
		}
		newsCenterAdapter.setSelectedPosition(newsCenterPosition);
	}
	
	/**
	 * 设置菜单页
	 * @param menuType
	 */
	public void setMenuType(int menuType) {
		this.menuType = menuType;
		switchMenu(menuType);
	}
	
	/**
	 * 切换菜单页
	 * @param type
	 */
	public void switchMenu(int type) {

		lv_menu_news_center.setVisibility(View.GONE);
		lv_menu_smart_service.setVisibility(View.GONE);
		lv_menu_govaffairs.setVisibility(View.GONE);

		switch (type) {
		case NEWS_CENTER:
			lv_menu_news_center.setVisibility(View.VISIBLE);
			fragmentManager = mainActivity.getSupportFragmentManager();
			//homefragment中切换菜单页，切换到新闻中心菜单页
			newsCenterFragment = ((HomeFragment) fragmentManager.findFragmentByTag("Home")).getNewsCenterPage(); 
			classifyTv.setText("分类");
			if (newsCenterAdapter == null) {
				newsCenterAdapter = new MenuAdapter(context, newsCenterMenu);
				lv_menu_news_center.setAdapter(newsCenterAdapter);
			} else {
				newsCenterAdapter.notifyDataSetChanged();
			}
			newsCenterAdapter.setSelectedPosition(newsCenterPosition);
			break;

		default:
			break;
		}
	}
	
	/**
	 * 保存当前新闻页的阅读位置，在恢复的时候使用
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("newsCenter_position", newsCenterPosition);
		super.onSaveInstanceState(outState);
	}

	@OnItemClick(R.id.lv_menu_news_center)
	public void onNewsCenterItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 当前位置等于点击位置，直接切换
		if (position == newsCenterPosition) {
			slidingMenu.toggle();
			return;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null && savedInstanceState.containsKey("newsCenter_position")) {
			newsCenterPosition = savedInstanceState.getInt("newsCenter_position");
		}
		super.onCreate(savedInstanceState);
	}

	class MenuAdapter extends CGBaseAdapter<String, ListView> {
		private int selectedPosition = 0;// 选中的位置

		@SuppressWarnings("unchecked")
		public MenuAdapter(Context context, List<String> menuList) {
			super(context, menuList);
		}

		public void setSelectedPosition(int curPosition) {
			this.selectedPosition = curPosition;
			notifyDataSetInvalidated();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.layout_item_menu, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv_menu_item);
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_menu_item);
			tv.setText(newsCenterMenu.get(position));

			// 选中的位置变为红色
			if (selectedPosition == position) {
				tv.setTextColor(getResources().getColor(R.color.red));
				iv.setBackgroundResource(R.drawable.menu_arr_select);
				convertView.setBackgroundResource(R.drawable.menu_item_bg_select);
			} else {
				tv.setTextColor(getResources().getColor(R.color.white));
				iv.setBackgroundResource(R.drawable.menu_arr_normal);
				convertView.setBackgroundResource(R.drawable.transparent);
			}
			return convertView;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
