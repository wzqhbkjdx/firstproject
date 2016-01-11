package com.agile.news.utils;

import java.util.Stack;

import android.R.anim;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {
	
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	
	/**
	 * 
	 */
	public static AppManager getAppManager(){
		if(instance == null)
			instance=new AppManager();
		return instance;
	}
	/**
	 * ���Activity����
	 */
	public void addActivity(Activity activity) {
		if(activityStack == null){
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * ��ȡ��ǰ��activity(��ջ�иո�ѹ���)
	 * @return
	 */
	public Activity currentActivity(){
		Activity activity = activityStack.lastElement();
		return activity;
	}
	/**
	 * ������ǰ��activity(��ջ�иո�ѹ���)
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * ����ָ����Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * ����ָ��������Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * �������е�activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	/**
	 * �˳�Ӧ�ó���
	 */
	public void AppExit(Context context){
		
		finishAllActivity();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.killBackgroundProcesses(context.getPackageName());
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
		
	}
	
	
}












































