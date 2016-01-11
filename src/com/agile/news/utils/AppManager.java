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
	 * 添加Activity到堆
	 */
	public void addActivity(Activity activity) {
		if(activityStack == null){
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 获取当前的activity(堆栈中刚刚压入的)
	 * @return
	 */
	public Activity currentActivity(){
		Activity activity = activityStack.lastElement();
		return activity;
	}
	/**
	 * 结束当前的activity(堆栈中刚刚压入的)
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * 结束所有的activity
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
	 * 退出应用程序
	 */
	public void AppExit(Context context){
		
		finishAllActivity();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.killBackgroundProcesses(context.getPackageName());
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
		
	}
	
	
}












































