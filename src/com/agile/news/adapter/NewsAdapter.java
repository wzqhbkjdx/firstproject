package com.agile.news.adapter;

import java.util.List;

import com.agile.news.R;
import com.agile.news.base.CGBaseAdapter;
import com.agile.news.bean.NewsListBean.News;
import com.agile.news.utils.CommonUtil;
import com.agile.news.utils.SharePrefUtil;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.provider.SyncStateContract.Constants;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsAdapter extends CGBaseAdapter<News, ListView>{
	BitmapUtils bitmapUtils;
	int type;
	
	public NewsAdapter(Context context, List<News> list, int type) {
		super(context, list);
		bitmapUtils = new BitmapUtils(context);
		this.type = type;
	}
	
	
	/**
	 * 刷新所在的listview
	 * 它在什么时候调用的呢？就是在每一次item从屏幕外滑进屏幕内的时候，或者程序刚开始的时候创建第一屏item的时候
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		News news = list.get(position);
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.layout_news_item, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_img);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.pub_date = (TextView) convertView.findViewById(R.id.tv_pub_date);
			holder.comment_count = (TextView) convertView.findViewById(R.id.tv_comment_count);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(news.isRead){
			holder.title.setTextColor(context.getResources().getColor(R.color.news_item_has_read_textcolor));
		}else {
			holder.title.setTextColor(context.getResources().getColor(R.color.news_item_no_read_textcolor));
		}
		
		holder.title.setText(news.title);
		holder.pub_date.setText(news.pubdate);
		
		if(news.comment){
			holder.comment_count.setVisibility(View.VISIBLE);
			if(news.commentcount > 0){
				holder.comment_count.setText(news.commentcount + "");
			}else{
				holder.comment_count.setText("");
			}
		}else{
			holder.comment_count.setVisibility(View.INVISIBLE);
		}
		
		if(type == 0){
			if(TextUtils.isEmpty(news.listimage)){
				holder.iv.setVisibility(View.GONE);
			}else{
				int read_model = SharePrefUtil.getInt(context,
						Constants.READ_MODEL, 1);
				switch (read_model) {
				case 1:
					int type = CommonUtil.isNetworkAvailable(context);
					if(type==1){
						holder.iv.setVisibility(View.VISIBLE);
						bitmapUtils.display(holder.iv, news.listimage);
					}else{
						holder.iv.setVisibility(View.GONE);
					}
					break;
				case 2:
					holder.iv.setVisibility(View.VISIBLE);
					bitmapUtils.display(holder.iv, news.listimage);
					break;
				case 3:
					holder.iv.setVisibility(View.GONE);
					break;

				default:
					break;
				}
			}
			
		}else{
			holder.iv.setVisibility(View.GONE);
		}
		
		return null;
	}
	
	class ViewHolder{
		ImageView iv;
		TextView title;
		TextView pub_date;
		TextView comment_count;
	}
	
	
	

}
