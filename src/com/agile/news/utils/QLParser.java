package com.agile.news.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.agile.news.bean.BaseBean;

import android.text.TextUtils;

public class QLParser {
	public static <T extends BaseBean> T parse(String jsonString, Class<T> cls) {
		if(jsonString != null) {
			String json = beforeParse(jsonString);
			if(!TextUtils.isEmpty(json))
				return GsonTools.changeGsonToBean(json, cls);
		}
		return null;
	}

	private static String beforeParse(String jsonString) {
		int code;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			code = jsonObject.getInt("retconde");
			return jsonString;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
