package com.agile.news.fragment;

import java.util.ArrayList;
import java.util.List;

import com.agile.news.MainActivity;
import com.agile.news.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment {

	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = LayoutInflater.from(getActivity()).inflate(R.layout.list_view, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ListView list_view = (ListView) view.findViewById(R.id.list_view);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, iniData());
		list_view.setAdapter(adapter);

		list_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Fragment f = null;
				
				switch (position) {
				case 0:
					f = new Fragment1();
					break;
				case 1:
					f = new Fragment2();
					break;
				case 2:
					f = new Fragment3();
					break;
				case 3:
					f = new Fragment4();
					break;
				case 4:
					f = new Fragment5();
					break;
				case 5:
					f = new Fragment6();
					break;

				default:
					break;
				}
				
				switchFragment(f);

			}
		});

	}

	protected void switchFragment(Fragment f) 
	{
		
		if(f != null)
		{
			if(getActivity() instanceof MainActivity)
			{
				((MainActivity)getActivity()).switchFragment(f);
			}
		}
		
	}

	private List<String> iniData() {

		List<String> list = new ArrayList<String>();

		list.add("fragment1");
		list.add("fragment2");
		list.add("fragment3");
		list.add("fragment4");
		list.add("fragment5");
		list.add("fragment6");

		return list;
	}

}
