package com.suming.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.blog.*
import com.suming.kotlindemo.blog.DataTypeActivity

//kotlin的基础语法
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.btn_example).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_kotlin_example).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_base_grammar).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_data_type).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_logic_control).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_empty_judgment).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_function).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_example -> {//java例子
                startActivity(Intent(this, ExampleActivity::class.java))
            }
            R.id.btn_kotlin_example -> {//kotlin例子
                startActivity(Intent(this, KExampleActivity::class.java))
            }
            R.id.btn_base_grammar -> {//变量，常量，注释
                startActivity(Intent(this, BaseGrammarActivity::class.java))
            }
            R.id.btn_data_type -> {//数据类型
                startActivity(Intent(this, DataTypeActivity::class.java))
            }
            R.id.btn_logic_control -> {//逻辑控制语句
                startActivity(Intent(this, LogicControlGrammarActivity::class.java))
            }
            R.id.btn_empty_judgment -> {//空类型相关
                startActivity(Intent(this, EmptyJudgmentActivity::class.java))
            }
            R.id.btn_function -> {//函数的使用
                startActivity(Intent(this, FunctionActivity::class.java))
            }

        }
    }
}
