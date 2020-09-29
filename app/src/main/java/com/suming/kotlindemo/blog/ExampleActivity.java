package com.suming.kotlindemo.blog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.suming.kotlindemo.R;
import com.suming.kotlindemo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @创建者 mingyan.su
 * @创建时间 2020/06/08 18:17
 * @类描述 {$TODO}原生例子
 */
public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "JAVA";


    private static final String mark = "HelloWord";
    private static final String mark2 = String.valueOf("HelloWord");

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

    //==========================================Java泛型问题=====================================

    private void test(){
        Map map = new HashMap();
        map.put("key", 10);
        Integer i = (Integer) map.get("key"); //正确，返回的就是Integer类型
        String str = (String) map.get("key"); //错误，运行时会报异常，map存放的值实际是Integer类型，不是String类型

        Map<String, Integer> strMap = new HashMap<>();
        strMap.put("key", 10);
        Integer i2 = strMap.get("key"); //正确，不用考虑类型转换
        //String str2 = strMap.get("key"); //错误，编译不通过，strMap的值只能是Integer类型，其他类型会报错

        List<String> strings = new ArrayList<>();
//        List<Object> objects = strings;//这里编译错误避免了下面的出现的运行异常
//        objects.add(10);//将一个字符串放入字符串列表中
//        String string = (String) objects.get(0);//不能整数转换为字符串

//        List<Object> objects = new ArrayList<>();
//        objects.addAll(strings);
//        Object o = objects.get(0);
//
//        MyList<String> stringMyList = new MyList<>();
//        MyList<Object> objectMyList = new MyList<>();
        //objectMyList.addAll(stringMyList); //编译错误

        Number num = new Integer(10);
        ArrayList<? extends Number> list = new ArrayList<Integer>();// compile error
        ArrayList<? super Number> list2 = new ArrayList<Object>();
        Number[] numbers = new Integer[0];
    }

    public class Dragon<T> {//创建类的时候，通过<T>为其指定了类型参数T

        public void share(T t) {
            //TODO
        }
    }

    public class Tiger {
        public <T> void invite(T t) {//方法泛型化，声明方法的时候，为其指定了类型参数T
            //TODO
        }
    }

//    //定义一个泛型接口
//    interface IList<T> {
//        void addAll(IList<T> list);//定义addAll()方法，用于添加集合
//    }
//
//    //MyList提供IList的默认实现
//    class MyList<T> implements IList<T> {
//        @Override
//        public void addAll(IList<T> list) {
//
//        }
//    }


//    interface MyList<T> {
//        T shareT();
//    }
//
//    void invite(MyList<String> strs) {
//        //MyList<Object> objects = strs;//报错，在Java中是不允许的
//        MyList<? extends Object> objects = strs;//java通配符写法
//    }

    interface MyList<T> {
        T shareT();
    }

    void invite(MyList<Number> strs) {
        //MyList<Object> objects = strs;//报错，在Java中是不允许的
        MyList<? super Double> objects = strs;//java通配符写法
    }
}
