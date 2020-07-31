package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/07/30 17:06
 * @类描述 {$TODO}类的声明和使用
 */
class ClassActivity : AppCompatActivity() {
    private val TAG = "ClassActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //8.类的实例化
        var test = Test()//类
        test.name//调用类中的属性，使用.号来引用
        test.num
        test.refresh()//调用成员方法

        var chicken = Chicken()
        var chicken2 = Chicken("HelloWord")
        var chicken3 = Chicken("Do My Self", 20)
    }

    //1.类：Kotlin 类可以包含构造函数、初始化代码块、函数、属性、内部类、对象声明等，使用关键字 class 声明类，后面紧跟类名：
    class A {
        //属性
        //TODO

        //函数
        //TODO

        //构造函数
        //TODO

        //内部类
        //TODO
    }

    //2.空类:定义一个空类，没有机构体的时候，大口号{}也可以省略。
    class B

    class Test {
        //成员属性
        var name = "Kotlin"
        val num = 0

        fun refresh() {
            Log.e("ClassActivity", "成员函数")
        }
    }

    /**
     * 3.构造函数
     * 在 Kotlin 中，允许有一个主构造函数和多个辅助构造函数，使用constructor修饰主构造函数，
     * 主构造函数是类头的一部分，类名后面跟上构造函数关键字以及类型参数。
     */
    class Student constructor(name: String) {

    }

    class Student2(name: String) {

    }

    //如果一个非抽象类没有声明构造函数（主构造函数或者辅助构造函数），它会产生一个没有参数的构造函数，
    //构造函数是`public`，如果你的类不想有公共构造函数，那就需要声明一个空的构造函数：
    class Pig private constructor() {

    }

    //4.如果构造函数不具备任何的注解或者默认的可见修饰符public时，关键字constructor可以省略
    //下面两种情况constructor不可省
//    class Student private constructor(name: String) {
//
//    }
//    class Student @Inject constructor(name: String) {
//
//    }

    //5.主构造函数 :
    class Person constructor(var name: String) {
        init {
            //TODO 初始化代码块
            name = "Kotlin"
            Log.e("ClassActivity", "初始化代码块 == $name")
        }
    }

    //一种简洁的语法，可以通过函数来定义属性并初始化属性值（var或val）
    //如果参数没有声明`var`或`val`，则默认为`val`类型。
    class People(var name: String, val type: Int) {

    }

    //6.辅助构造函数
    class cat {
        constructor(name: String)
    }

    //如果类有主构造函数，则每个辅助构造函数需要直接或者间接通过另一个辅助构造函数代理主构造函数，在同一个类中代理另一个构造函数使用this关键字
    class Dog(var name: String) {
        //二级构造函数中的参数name是委托了主构造函数中的参数name
        constructor(name: String, age: Int, sex: String) : this(name) {

        }
    }

    //7.注意：在JVM虚拟机中，如果主构造函数所有参数都有默认值，编译器会生成一个附加的无参的构造函数,这个构造
    //函数会直接使用默认值，这样Kotlin可以更简单地使用像Jackson或者JPA这样使用无参构造函数来创建类实例的库。
    class Chicken(var name: String = "Kotlin") {
        init {//初始化代码块
            Log.e("ClassActivity", "构造函数有默认值: name == $name")
        }
        //辅助构造函数
        constructor(name: String = "Java", age: Int = 10) : this(name) {
            Log.e("ClassActivity", "构造函数有默认值: name == $name  | age == $age")
        }
    }
}