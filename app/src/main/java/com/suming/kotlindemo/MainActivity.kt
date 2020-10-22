package com.suming.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.blog.*
import com.suming.kotlindemo.blog.DataTypeActivity
import com.suming.kotlindemo.blog.EnumActivity
import com.suming.kotlindemo.blog.ObjectActivity
import java.lang.reflect.Type

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
        findViewById<TextView>(R.id.btn_class_inheritance).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_get_set).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_imports_packages).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_interfaces).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_visibility_modifiers).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_extensions).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_data_class).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_sealed_class).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_generics).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_nest_inner_class).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_enum).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_object).setOnClickListener(this)
        findViewById<TextView>(R.id.btn_type_aliases).setOnClickListener(this)
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
            R.id.btn_empty_judgment -> {//空类型相关 ?、?.、?:、!!、as?
                startActivity(Intent(this, EmptyJudgmentActivity::class.java))
            }
            R.id.btn_function -> {//函数的声明和使用
                startActivity(Intent(this, FunctionActivity::class.java))
            }
            R.id.btn_class_inheritance -> {//类和继承
                startActivity(Intent(this, ClassInheritanceActivity::class.java))
            }
            R.id.btn_get_set -> {//属性与字段：get()和set()
                startActivity(Intent(this, GetSetActivity::class.java))
            }
            R.id.btn_imports_packages -> {//导包
                startActivity(Intent(this, GetSetActivity::class.java))
            }
            R.id.btn_interfaces -> {//接口的使用
                startActivity(Intent(this, InterfacesActivity::class.java))
            }
            R.id.btn_visibility_modifiers -> {//可见性修饰符
                startActivity(Intent(this, ModifierActivity::class.java))
            }
            R.id.btn_extensions -> {//拓展
                startActivity(Intent(this, ExtensionsActivity::class.java))
            }
            R.id.btn_data_class -> {//数据类
                startActivity(Intent(this, DataClassActivity::class.java))
            }
            R.id.btn_sealed_class -> {//密封类
                startActivity(Intent(this, SealedClassActivity::class.java))
            }
            R.id.btn_generics -> {//泛型
                startActivity(Intent(this, GenericsActivity::class.java))
            }
            R.id.btn_nest_inner_class -> {//嵌套类和内部类
                startActivity(Intent(this, NestInnerActivity::class.java))
            }
            R.id.btn_enum -> {//枚举类
                startActivity(Intent(this, EnumActivity::class.java))
            }
            R.id.btn_object -> {//object
                startActivity(Intent(this, ObjectActivity::class.java))
            }
            R.id.btn_type_aliases -> {//类型别名
                startActivity(Intent(this, TypeAliasesActivity::class.java))
            }

        }
    }
}
