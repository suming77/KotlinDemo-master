package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.suming.kotlindemo.R
import com.suming.kotlindemo.User
/**
 * @创建者 mingyan.su
 * @创建时间 2020/06/08 18:28
 * @类描述
 */
class KExampleActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "Kotlin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        val tv_title = findViewById<TextView>(R.id.tv_title)
        tv_title.setText("KExampleActivity")
        findViewById<TextView>(R.id.btn_let).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_with).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_run).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_apply).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_also).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_take_if).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_take_unless).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_syn).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_repeat).setOnClickListener(this)

        val mView = TextView(this)

        //2、在Kotlin中实现一个接口的回调，不使用lambda表达式(这种方法非常适用于Kotlin中一个接口有多个回调方法)
        mView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //TODO
            }
        })

        //3、如果在Kotlin中接口的回调方法只有一个，那么就符合使用lambda函数，我们可以把以上代码简化：
        mView.setOnClickListener({ view: View? ->
            //TODO
        })

        //再进一步简化，可以把View?直接直接省略
        mView.setOnClickListener({ view ->
            //TODO
        })

        //4、如果上面的参数view没有使用到的话，可以直接把view去掉：
        mView.setOnClickListener({
            //TODO
        })

        //5、上面的代码还可以做进一步的调整，如果setOnClickListener()函数的最后一个参数是一个函数的话，可以把函数{}的实现提到圆括号()外面：
        mView.setOnClickListener() {
            //TODO
        }

        //6、如果setOnClickListener()函数只有一个参数的话，则可以直接省略圆括号()：
        mView.setOnClickListener {
            //TODO
        }
    }

    fun let(mTextView: TextView?) {

        //let函数优化前
        mTextView?.setLines(2)
        mTextView?.setText("HelloWord")
        mTextView?.setOnClickListener(this)
        mTextView?.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))

        //let函数优化后
        mTextView?.let {
            it.setLines(2)
            it.setText("HelloWord")
            it.setOnClickListener(this)
            it.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        }

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_let -> {
                letForKotlin()
            }
            R.id.btn_with -> {
                withForKotlin()
            }
            R.id.btn_run -> {
                runForKotlin()
            }
            R.id.btn_apply -> {
                applyForKotlin()
            }
            R.id.btn_also -> {
                alsoForKotlin()
            }
            R.id.btn_take_if -> {
                takeIfForKotlin(2222)
            }

            R.id.btn_take_unless -> {
                takeUnlessForKotlin(2323)
            }
            R.id.btn_syn -> {
                synForKotlin("HelloWord")
                synForKotlin("")
            }
            R.id.btn_repeat -> {
                var s = 0
                repeat(10) {
                    s++
//                  Log.e(TAG, "repeat == result：" + it)
                }
                Log.e(TAG, "repeat == result：" + s)

            }

            else -> {
            }
        }
    }

    private fun synForKotlin(str: String) {
        str.takeIf { !it.isNullOrEmpty() }?.let {
            Log.e(TAG, "syn == result：" + it.toUpperCase())
        }
    }

    private fun takeIfForKotlin(number: Int) {
        val result = number.takeIf {
            //条件成立返回number，不成立返回null
            it > 0
        }
        Log.e(TAG, "takeIf == result：" + result)
    }

    private fun takeUnlessForKotlin(number: Int) {
        val result = number.takeUnless {
            //条件成立返回null，不成立返回number
            it > 0
        }
        Log.e(TAG, "takeIf == result：" + result)
    }

    private fun alsoForKotlin() {
        val result = "HelloWord".also {
            Log.e(TAG, "also == length：" + it.length)
            2121
        }
        Log.e(TAG, "also == result：" + result)
    }

    private fun applyForKotlin() {
        val list = mutableListOf("one", "two", "three")
        val result = list.apply {
            Log.e(TAG, "apply == list第一个参数：" + first() + ", 列表长度为：" + size)
            2020
        }
        Log.e(TAG, "apply == list：" + result)
    }

    private fun runForKotlin() {
        val user = User("李思思", 18, "女")
        val result = user?.run {
            val userStr = "姓名：" + name + ", 年龄：" + age + ", 性别：" + sex
            Log.e(TAG, "run == user：" + userStr)

            1919
        }
        Log.e(TAG, "run == result：" + result)
    }

    private fun withForKotlin() {
        val user = User("张三", 27, "男")
        val result = with(user, {
            "姓名：" + name + ", 年龄：" + age + ", 性别：" + sex
        })
        Log.e(TAG, "with == " + result)

//        val list = mutableListOf("one", "two", "three")
//        with(list) {
//            Log.e(TAG, "with == list：" + this + ", 列表长度为：" + size)
//        }

//        val result1 = with(list) {
//            "with == list第一个参数：" + first() + ", 最后一个参数是：" + last()
//        }
//        Log.e(TAG, result1)
    }

    private fun letForKotlin() {
        val result = "HelloWord".let {
            Log.e(TAG, "let == length：" + it.length)
            1818
        }
        Log.e(TAG, "let == length：$result")
    }


}