package com.suming.kotlindemo.blog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/10/13 18:16
 * @类描述 {$TODO}object 对象表达式和对象声明
 * Kotlin 提供 object 关键字的用意就是，在生成在一个对当前类进行轻微修改的类对象，且不需要声明一个新的子类的时候使用。
 */
class ObjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 建一个匿名类的对象，object 实际上是作为表达式存在，产生的是 object 类型的匿名对象，
         */
        setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                println("匿名object对象访问外部成员：name = $name")
            }
        })

        /**
         * 如果超类有一个构造函数，生成的匿名对象则必须将适当的构造函数参数传递给它。如果有多个超类型可以在冒号后面指定一个逗号分隔：
         */
        val people: OnClickListener = object : People("Android"), OnClickListener {
            override fun onClick(v: View?) {
                //TODO
            }
        }

        /**
         * 如果在任何情况下，我们需要的仅仅是一个对象，并没有特别的超类，可以简单为：
         */
        val add = object {
            //没有任何超类，只是作为一个对象存在
            val numA: Int = 1
            val numB: Int = 2
        }

        println("numA + numB = ${add.numA + add.numB}") //打印数据：3

        /**
         * 匿名对象只能在本地和私有声明中作为类型使用。如果使用匿名对象作为公共函数的返回类型或公共属性的类型，
         * 该函数或属性的实际类型将是匿名对象声明的超类型，如果没有声明任何超类型，则为 Any 。添加到匿名对象中的成员将不可访问。
         */
        val str1 = privateFoo().str // compile success，private修饰的匿名 object 返回的类型是 object 类型
        //val str2 = publicFoo().str // compile error，public 修饰的匿名 object 返回的类型是其超类，如果没有超类则是 Any,而 Any 类中没有 str 字段

        //单例
        DataProviderManager.registerDataProvider("")

        //object 作为声明时可以继承超类，即单例可以有超类。
        object : MyAdapter() {
            override fun mouseClicked() {}
        }

        /**
         * 可以简单地使用类名作为限定符来调用伴生对象的成员：
         */
//        val instance = Student.create()
        //伴生对象的名称可以省略，在这种情况下，将使用名称 `Companion` 来调用：
//        val companion = Student.Companion

        //无论伴生对象有没有命名，只要使用包含伴生对象的类名进行赋值时，此值实际上就是该类所持有的其内部伴生对象的引用地址：
//        val student1 = Student
        val student2 = Student

        val factory: Factory<MyClass> = MyClass
    }

    //Kotlin 允许在匿名类对象内部访问外部的成员，与 Java 不同的是此时外部成员不必再声明为 final （Kotlin 的val）。
    var name: String = "Kotlin"

    interface OnClickListener {
        fun onClick(v: View?)
    }

    private fun setOnClickListener(listener: OnClickListener?) {

    }

    open class People(name: String) {}

    //私有函数，返回类型为匿名对象类型
    private fun privateFoo() = object {
        val str: String = "private"
    }

    //公有函数，返回对象为超类型，如果没有超类则为Any
    public fun publicFoo() = object {
        val str: String = "public"
    }

    /**
     * 对象声明
     * 单例可能在一些情况下有用，Kotlin（在Scala之后）使声明单例变得简单。在 Kotlin 中，
     * 可以不需要像 Java 那样自己去实现单例，而是提供关键字来保证单例，这个关键字就是 object。
     * 当 object 修饰一个 class 时，这个类就只有一个对象。
     */
    object DataProviderManager {//使用object来声明一个单例对象
        fun registerDataProvider(str: String) {
            //TODO
        }
    }

    //抽象类
    abstract class MyAdapter {
        open fun mouseClicked() {}//open 修饰，子类可重写
    }

    /**
     * 类内的对象声明可以用 companion 关键字进行标记：
     */
    class Student {
//        companion object newInstance {
//            fun create(): People = People("ALL")
//        }

        companion object {}
//        companion object Named{ }
    }

    interface Factory<T> {
        fun create(): T
    }

    class MyClass {
        companion object : Factory<MyClass> {
            override fun create(): MyClass = MyClass()
        }
    }

}