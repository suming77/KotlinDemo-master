package com.suming.kotlindemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @创建者 mingyan.su
 * @创建时间 2020/06/08 18:17
 * @类描述 {$TODO}原生例子
 */
public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "JAVA";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("ExampleActivity");
        findViewById(R.id.btn_let).setOnClickListener(this);
        findViewById(R.id.btn_with).setOnClickListener(this);
        findViewById(R.id.btn_run).setOnClickListener(this);
        findViewById(R.id.btn_apply).setOnClickListener(this);
        findViewById(R.id.btn_also).setOnClickListener(this);
        findViewById(R.id.btn_take_if).setOnClickListener(this);
        findViewById(R.id.btn_take_unless).setOnClickListener(this);
        findViewById(R.id.btn_syn).setOnClickListener(this);

        TextView mView = new TextView(this);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_let:
                letForJava();
                break;
            case R.id.btn_with:
                withForJava();
                break;
            case R.id.btn_run:
                runForJava();
                break;
            case R.id.btn_apply:
                applyForJava();
                break;
            case R.id.btn_also:
                alsoForJava();
                break;
            case R.id.btn_take_if:
                takeIfForJava(2222);
                break;
            case R.id.btn_take_unless:
                takeUnlessForJava(2323);
                break;
            case R.id.btn_syn:
                synForJava("HelloWord");
                synForJava("");
                break;

            default:
                break;
        }
    }

    private void synForJava(String str) {
        if (!TextUtils.isEmpty(str)) {
            Log.e(TAG, "syn == result：" + str.toUpperCase());
        }
    }

    private void takeUnlessForJava(int number) {
        Integer result = number > 0 ? null : number;
        Log.e(TAG, "takeUnless == result：" + result);
    }

    private void takeIfForJava(int number) {
        Integer result;
        if (number > 0) {
            result = number;
        } else {
            result = null;
        }
        Log.e(TAG, "takeIf == result：" + result);
    }

    private void alsoForJava() {
        String result = "HelloWord";
        Log.e(TAG, "also == length：" + result.length());
        int i = 2121;
        Log.e(TAG, "also == result：" + result);
    }

    private void applyForJava() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        String value = "apply == list第一个参数：" + list.get(0) + ", 列表长度为：" + list.size();
        Log.e(TAG, value);
        String result = "apply == list：" + list;
        Log.e(TAG, result);
    }

    private void runForJava() {
        User user = new User("李思思", 18, "女");
        String userStr = "姓名：" + user.getName() + ", 年龄：" + user.getAge() + ", 性别：" + user.getSex();
        Log.e(TAG, "run == user：" + userStr);
        int result = 1919;
        Log.e(TAG, "run == result：" + result);
    }

    private void withForJava() {
        User user = new User("张三", 27, "男");
        String result = "姓名：" + user.getName() + ", 年龄：" + user.getAge() + ", 性别：" + user.getSex();
        Log.e(TAG, "with == " + result);
    }

    private void letForJava() {
        String str = "HelloWord";
        Log.e(TAG, "let == length：" + str.length());
        int result = 1818;
        Log.e(TAG, "let == result：" + result);
    }
}
