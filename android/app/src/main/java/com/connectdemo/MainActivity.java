package com.connectdemo;

import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {
    //用来存储从原生Android界面返回的数据
    //    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "FirstRNPage";
    }

    //    @Override
    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        Log.d("AAA", "onActivityResult被调用,requestCode:" + requestCode + ",resultCode:" + resultCode);
    //        super.onActivityResult(requestCode, resultCode, data);
    //        if (resultCode == 500 && requestCode == 100) {//两个值都是自定义的
    //            String resultStr = data.getStringExtra("result");
    //            if (!TextUtils.isEmpty(resultStr)) {
    //                MainActivity.queue.add(resultStr);
    //            } else {
    //                MainActivity.queue.add("返回数据为空");
    //            }
    //        } else {
    //            MainActivity.queue.add("没有返回");
    //        }
    //    }
}
