package com.suming.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.blog.BaseGrammarActivity

//kotlin的基础语法
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.btn_example).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_kotlin_example).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_base_grammar).setOnClickListener(this)
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

        }
    }
}
