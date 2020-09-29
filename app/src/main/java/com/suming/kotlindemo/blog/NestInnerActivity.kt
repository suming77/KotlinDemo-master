package com.suming.kotlindemo.blog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/09/29 10:52
 * @类描述 {TODO}嵌套类和内部类
 */
class NestInnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nest = Outer.Nested().foo()
        println("nest == $nest") //nest == 2

        val inner = Outer().Inner().foo()
        println("inner == $inner") //inner == 0

        /**
         *3.我们不再通过显式定义一个父实现的子类来创建对象，而是直接通过父实现来创建一个没有名字的对象，
         * 这个对象就是匿名内部类，这里是指接口或者抽象类。匿名内部类实例是使用对象表达式创建的：
         * Kotlin 匿名内部类的写法：object + 冒号":" + 类名
         */
        object : MyAdapter() {
            override fun mouseClicked() {
                println("MyAdapter == mouseClicked")
            }
        }

        //注意：在JVM上，如果对象是一个函数式 Java 接口的实例（即一个带有单一抽象方法的 Java 接口），
        // 你可以使用一个 lambda表达式来创建它，该表达式前缀为接口的类型：
        val listener = View.OnClickListener { println("View.OnClickListener") }
    }


    class Outer {
        private val number: Int = 0

        /**
         * 1.类 Nested 嵌套在类 Outer 内部，称为嵌套类；类 Outer 相对于类 Nested 来说是外部类。
         * 嵌套类能通过外部类直接访问，但是不能访问外部了的私有或公有成员。
         */
        class Nested {
            ////嵌套类不能访问外部了的私有或公有成员
            fun foo() = 2
        }

        /**
         * 2.在 Kotlin 中，只有被 `inner` 标记的嵌套类才叫内部类，其他在类内部写的类统称为嵌套类。
         * 内部类可以访问其他外部类的成员，内部类引用外部类的对象：
         */
        inner class Inner {
            fun foo() = number//内部类可以访问外部类私有成员
        }
    }


    //抽象类
    abstract class MyAdapter {
        open fun mouseClicked() {}//open 修饰，子类可重写
    }

}