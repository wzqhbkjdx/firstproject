package com.agile.news.bean;

import java.util.ArrayList;

public class NewsCenterCategories {
	public ArrayList<NewsCategory> data;
	public int[] extend;
	public static class NewsCategory{
		public int id;
		public String title = "";
		public int type;
		public String url = "";
		public String url1 ="";
		public String excurl;
		public String weekurl;
		public String dayurl;
		public ArrayList<ChildNewsCate> children = new ArrayList<NewsCenterCategories.ChildNewsCate>();
		


	}

	public static class ChildNewsCate{
		public int id;
		public String title = "";
		public int type;
		public String url = "";


	}
}
