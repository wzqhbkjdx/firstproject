package com.agile.news.utils;

import android.app.Dialog;
import android.content.Context;

public class DialogUtil {
	
	/**
	 * �������ȶԻ���
	 */
	public static Dialog createProgressDialog(Context context, String content) {
		return new CustomProgressDialog(context, content);
	}

}
