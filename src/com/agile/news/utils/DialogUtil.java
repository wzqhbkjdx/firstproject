package com.agile.news.utils;

import android.app.Dialog;
import android.content.Context;

public class DialogUtil {
	
	/**
	 * 创建进度对话框
	 */
	public static Dialog createProgressDialog(Context context, String content) {
		return new CustomProgressDialog(context, content);
	}

}
