package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/04 11:46
 * @类描述 {$TODO}get()和Set()
 *  在 Person和ConstCompare 中
 */
class GetSetActivity : AppCompatActivity() {
    private val TAG = "GetSetActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var person = Person()//实例化person，在Kotlin中没有new关键字

        declaringProperties(person)

//        person.meal = "HelloWord" 报错，setter已经声明为私有，不能重新赋值
        //Kotlin 中使用类中的属性和 Java 中的一样，通过类名来引用：
        Log.e(TAG, "get()和set(): id == ${person.id} | name == ${person.name}")

        Log.e(TAG, "get()和set(): color == ${person.color} | isEmpty == ${person.isEmpty}")
        person.hair = "Android"
        person.nose = "Android"
        Log.e(TAG, "get()和set(): hair == ${person.hair} | nose == ${person.nose}")

        person.name = "HelloUniverse"
        Log.e(TAG, "后备字段: name == ${person.name}")

        methodLateinit()
    }

    /**
     * 属性声明和使用
     */
    private fun declaringProperties(person: Person) {
        val view1 = TextView(this)
        val view2 = TextView(this)

        view1.text = person.id //调用属性
        view2.text = person.nameA
    }

    //延迟初始化的属性和变量
    //使用 lateinit 修饰符来标记属性，延迟初始化
    lateinit var person: Person //lateinit 表示延迟初始化

    /**
     * 自 Kotlin 1.2以来，可以检查 lateinit 修饰的变量是否被初始化，在属性引用使用 this::变量名.isInitialized，this可省：
     */
    fun methodLateinit() {
        person = Person()

        if (this::person.isInitialized) {//如果已经赋值返回true，否则返回false
            //TODO
        }
        Log.e(TAG, "延迟初始化: person.isInitialized == ${::person.isInitialized}")
    }
}
