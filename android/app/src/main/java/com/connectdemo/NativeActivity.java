package com.connectdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NativeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mToRNBtn = null;
    private TextView mValueFromRNTxt = null;
    private Button mToRNWithValueBtn = null;
    private Button mToRNWithResultBtn = null;
    private TextView mResultFromRNTxt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        mToRNBtn = (Button) findViewById(R.id.native_to_rn_btn);
        mToRNBtn.setOnClickListener(this);

        mValueFromRNTxt = (TextView) findViewById(R.id.native_from_rn_value);

        mToRNWithValueBtn = (Button) findViewById(R.id.native_to_rn_with_value_btn);
        mToRNWithValueBtn.setOnClickListener(this);
        mToRNWithResultBtn = (Button) findViewById(R.id.native_to_rn_with_result_btn);
        mToRNWithResultBtn.setOnClickListener(this);

        mResultFromRNTxt = (TextView) findViewById(R.id.native_from_rn_result);
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("AAA", "onStop被调用");
    }

    @Override
    protected void onDestroy() {
        Log.d("AAA", "onDestroy被调用");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.native_to_rn_btn:
                toRNBtnOnClick();
                break;
            case R.id.native_to_rn_with_value_btn:
                toRNWithValueBtnOnClick();
                break;
            case R.id.native_to_rn_with_result_btn:
                toRNWithResultBtnOnClick();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("AAA", "NativeActivity, onActivityResult,requestCode:" + requestCode + ", resultCode:" + resultCode);
        String resultStr = "";
        if (resultCode == 300 && requestCode == 400) {//两个值都是自定义的
            resultStr = data.getStringExtra("result2");
            if (TextUtils.isEmpty(resultStr)) {
                resultStr = "返回数据为空";
            }
        } else {
            resultStr = "没有返回";
        }

        if (null != mResultFromRNTxt) {
            mResultFromRNTxt.setText(resultStr);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("AAA", "onBackPressed被调用");
        Intent intent = new Intent();
        intent.putExtra("result", "这是从原生Android界面带回给RN界面的数据");
        setResult(500, intent);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        Log.d("AAA", "onPause被调用");
        super.onPause();
    }

    /**
     * 点击跳转至RN界面按钮，不携带数据
     */
    private void toRNBtnOnClick() {
        Intent intent = new Intent(NativeActivity.this, SecondRNActivity.class);
        NativeActivity.this.startActivity(intent);
        if (null != mResultFromRNTxt) {
            mResultFromRNTxt.setText("");
        }
    }

    /**
     * 点击跳转至RN界面按钮，携带数据
     */
    private void toRNWithValueBtnOnClick() {
        Intent intent = new Intent(NativeActivity.this, SecondRNActivity.class);
        intent.putExtra("test2", "这是从原生Android界面传递过来的数据");
        NativeActivity.this.startActivity(intent);
        if (null != mResultFromRNTxt) {
            mResultFromRNTxt.setText("");
        }
    }

    /**
     * 点击跳转至RN界面按钮，同时接收RN界面回传的数据
     */
    private void toRNWithResultBtnOnClick() {
        Intent intent = new Intent(NativeActivity.this, SecondRNActivity.class);
        intent.putExtra("test2", "这是从原生Android界面传递过来的数据");
        NativeActivity.this.startActivityForResult(intent, 400);
        if (null != mResultFromRNTxt) {
            mResultFromRNTxt.setText("");
        }
    }

    private void init() {
        Intent intent = getIntent();
        if (null != intent) {
            String str = intent.getStringExtra("test");
            if (!TextUtils.isEmpty(str) && null != mValueFromRNTxt) {
                mValueFromRNTxt.setText(str);
            }
        }
    }
}
