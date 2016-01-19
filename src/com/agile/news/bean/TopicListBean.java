package com.agile.news.bean;

import java.util.ArrayList;

public class TopicListBean extends BaseBean{
	
	public TopicList data;
	public static class TopicList {
		public String more;
		public ArrayList<Topic> topic;
	}
	
	public static class Topic {
		public String id;
		public String title;
		public String url;
		public String listmage;
		public String description;
	}

}
