package com.connectdemo;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * 自定义RN界面和Native界面互传数据模块
 * <p>
 * Created by tfn on 17-1-4.
 */

public class MyIntentModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private Promise mPromise = null;

    //用于处理每次从Native页面返回的数据
    private ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            Log.d("AAA", "onActivityResult被调用,requestCode:" + requestCode + ",resultCode:" + resultCode);
            if (resultCode == 500 && requestCode == 100) {//两个值都是自定义的
                if (null != mPromise) {
                    String resultStr = data.getStringExtra("result");
                    if (!TextUtils.isEmpty(resultStr)) {
                        mPromise.resolve(resultStr);
                    } else {
                        mPromise.resolve("返回数据为空");
                    }
                }
            } else {
                if (null != mPromise) {
                    mPromise.reject("NO_BACK", "没有返回");
                }
            }

            mPromise = null;//Promise需要每次都调，所以每次用完都需要释放
        }
    };

    public MyIntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(mActivityEventListener);//注册每次从Native页面返回的数据监听
        reactContext.addLifecycleEventListener(this);//注册绑定了该Module的RN页面生命周期监听
    }

    @Override
    public String getName() {
        return "MyIntentModule";
    }

    @Override
    public void onHostResume() {
        Log.d("AAA", "onHostResume被调用");
    }

    @Override
    public void onHostPause() {
        Log.d("AAA", "onHostPause被调用");

    }

    @Override
    public void onHostDestroy() {
        Log.d("AAA", "onHostDestroy被调用");

    }

    /**
     * 启动原生Android界面,传数据和不传数据方法一样，就合并了
     *
     * @param activityName 需要启动的Activity的完整包名
     * @param params       携带给需要启动的Activity的数据
     */
    @ReactMethod
    public void startNativeActivity(String activityName, String params) {
        Activity activity = getCurrentActivity();
        if (null != activity) {
            try {
                //利用反射来做到动态加载不同界面
                Class<?> clazz = Class.forName(activityName);
                Intent intent = new Intent(activity, clazz);
                intent.putExtra("test", params);
                activity.startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动原生Android界面，带数据返回
     *
     * @param activityName 需要启动的Activity的完整包名
     * @param params       携带给需要启动的Activity的数据
     * @param requestCode  请求码，用于识别请求方
     * @param promise      promise，由JS端来具体实现
     */
    @ReactMethod
    public void startNativeActivityForResult(String activityName, String params, int requestCode, Promise promise) {
        Activity activity = getCurrentActivity();

        if (null == activity) {
            promise.reject("ACTIVITY_NULL", "activity is null.");
            return;
        }

        mPromise = promise;

        try {
            Class<?> clazz = Class.forName(activityName);
            Intent intent = new Intent(activity, clazz);
            intent.putExtra("test", params);
            activity.startActivityForResult(intent, requestCode);
        } catch (ClassNotFoundException e) {
            promise.reject("CLASS_NOT_FOUND", e.getMessage());
            mPromise = null;
            e.printStackTrace();
        }
    }

    /**
     * 获取从原生Android界面传递到RN界面的Intent中的数据
     *
     * @param successCallback 成功的回调，由JS端来具体实现
     * @param errorCallback   失败的回调，由JS端来具体实现
     */
    @ReactMethod
    public void getDataFromIntent(Callback successCallback, Callback errorCallback) {
        try {
            Activity currentActivity = getCurrentActivity();//获取当前Activity
            Log.d("AAA", "当前页面：" + currentActivity.getLocalClassName());
            String result = currentActivity.getIntent().getStringExtra("test2");//会有对应数据放入
            if (TextUtils.isEmpty(result)) {
                result = "No Data";
            }
            successCallback.invoke(result);//成功回调
        } catch (Exception e) {
            errorCallback.invoke(e.getMessage());//错误回调
        }
    }

    @ReactMethod
    public void finishRNActivity(String str) {
        Activity currentActivity = getCurrentActivity();//获取当前Activity
        Intent intent = new Intent();
        intent.putExtra("result2", str);
        currentActivity.setResult(300, intent);
        currentActivity.finish();
    }
}
