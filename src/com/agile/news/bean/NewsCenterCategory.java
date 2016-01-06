package com.agile.news.bean;

import java.util.List;

public class NewsCenterCategory {
	
	  public List<CenterCategory> data;
	    public List<Integer> extend;
	    public int retcode;
      public static class CenterCategory{
      	public List<CenterCategoryItem> children ;
      	public int id;
      	public String title;
      	public int type;
      	public String url;
      	public String url1;
      	public String dayurl;
      	public String excurl;
      	public String weekurl;
      	
      }
      public static class CenterCategoryItem{
      	public String id;
      	public String title;
      	public String type;
      	public String url;
      }

}
