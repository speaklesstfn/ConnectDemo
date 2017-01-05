package com.connectdemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.support.annotation.Nullable;

/**
 * 自定义RN与原生通信模块
 * <p>
 * Created by tfn on 17-1-5.
 */

public class MyMessageModule extends ReactContextBaseJavaModule {
    public MyMessageModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "MyMessageModule";
    }

    /**
     * Callback方式
     *
     * @param successCallback 成功回调
     */
    @ReactMethod
    public void getCallbackMsg(Callback successCallback) {
        successCallback.invoke(getTime());
    }

    /**
     * Promise方式
     *
     * @param promise promise方法
     */
    @ReactMethod
    public void getPromiseMsg(Promise promise) {
        promise.resolve(getTime());
    }

    /**
     * RCTDeviceEventEmitter方式
     */
    @ReactMethod
    public void getListenerMsg() {
        String time = getTime();
        WritableMap map = Arguments.createMap();
        map.putString("TIME", time);
        sendEvent(getReactApplicationContext(), "EventFromNative", map);
    }

    /**
     * 向RN发送通知
     *
     * @param reactContext react上下文对象
     * @param eventName    通知事件的名称
     * @param params       通知时间携带的数据
     */
    private void sendEvent(ReactContext reactContext, String eventName,
            @Nullable
                    WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    /**
     * 获取时间
     *
     * @return 当前的时间
     */
    private String getTime() {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = formatDate.format(date);
        return time;
    }
}
