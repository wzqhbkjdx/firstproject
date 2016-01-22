package com.agile.news;

import com.agile.news.fragment.HomeFragment;
import com.agile.news.fragment.MenuFragment2;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity {
	private SlidingMenu sliddingmenu;
	private MenuFragment2 menufragment;
	private HomeFragment homeFragment;

	/**
	 * 1. �õ������˵� 2. ���û����˵�������߳����������ұ߳��� 3. ���û����˵���� 4. ���û����˵�����Ӱ ��Ӱ���Ż��������䵭 5.
	 * ������Ӱ�Ŀ�� 6. ���û����˵�touch�ķ�Χ
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ���Ϸ���title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setBehindContentView(R.layout.menu_frame);
		setContentView(R.layout.content_frame); // ������content FrameLayout ֡���� ���ڶ�̬����

		sliddingmenu = getSlidingMenu();
		
		// ���ò˵�����߻�������
		sliddingmenu.setMode(SlidingMenu.LEFT);

		// ���û���ʱ�Ľ���̶�
		sliddingmenu.setFadeDegree(0.35f);

		// ���û����˵����
		sliddingmenu.setBehindWidthRes(R.dimen.slidingmenu_offset);

		// ���û����˵�����Ӱ
		sliddingmenu.setShadowDrawable(R.drawable.shadow);

		// ������Ӱ�Ŀ��
		sliddingmenu.setShadowWidth(R.dimen.shadow_width);

		// SlidingMenu����ʱ��ҳ����ʾ��ʣ����
		// sliddingmenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);

		// ���û����˵���touch��Χ
		sliddingmenu.setTouchModeAbove(sliddingmenu.TOUCHMODE_MARGIN);

		// ���ұ߻����Ĳ໬�˵�
		// sliddingmenu.setSecondaryMenu(R.layout.right_menu);
		// sliddingmenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		// RightMenuFragment rightMenuFragment = new RightMenuFragment();
		// getSupportFragmentManager().beginTransaction().replace(R.id.right_menu_frame,
		// rightMenuFragment).commit();

		if (savedInstanceState == null) {
			// ��homefragment�滻content
			menufragment = new MenuFragment2();
			// ���໬�˵���FrameLayout�����滻Ϊmenufragment
			getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, menufragment, "Menu").commit();
			homeFragment = new HomeFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, homeFragment, "Home").commit();
		}

	}

	/**
	 * ��ȡ�˵�
	 */
	public MenuFragment2 getMenuFragment2() {
		menufragment = (MenuFragment2) getSupportFragmentManager().findFragmentByTag("Menu");
		return menufragment;
	}

	/**
	 * �ص� ��f�滻��ǰframelayout����
	 * 
	 * @param f
	 */
	public void switchFragment(Fragment f) {
		// ��֪��fragment���ڲ�ʵ�ֻ��ƣ���֪��beginTransaction().replace()���������ô��ݻ���ֵ���ݣ�������ò�Ҫֱ�ӷ��أ������ֵ���������ô��ݾͻᵼ�´���
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
		// �Զ��л�
		sliddingmenu.toggle();
	}

}
