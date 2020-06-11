package com.suming.kotlindemo
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/10 16:21
 * @类描述 ${TODO}对象表达式和对象声明
 */
class ObjectActivity : AppCompatActivity() {
    //对象表达式和对象声明来创建一个对某个类做了轻微改动的类的对象，且不需要声明一个薪的子类
    //1.对象表达式
    //通过对象表达式实现一个匿名内部类的对象用于方法的参数中：
//    window.addMouseListener(object : MouseAdapter() {
//        override fun mouseClicked(e: MouseEvent) {
//            // ...
//        }
//        override fun mouseEntered(e: MouseEvent) {
//            // ...
//        }
//    })

    //对象可以继承某个基类或者实现接口
    open class A(x: Int) {
        public open val y: Int = x
    }

    interface B {}

    val ab: A = object : A(1), B {
        override val y = 15
    }
    //如果超类型有一个构造函数，则必须传递参数给他，多个超类型和接口可以用逗号分隔
    //通过对象表达式可以超过类的定义直接得到一个对象

    fun main(args: ArrayList<String>) {
        val state = object {
            val name: String = "刘亦菲"
            val age: Int = 32
        }

        print(state.name + state.age)
    }

    //注意：匿名对象可以用作只在本地和私有作用域中声明的类型，如果你使用匿名对象作为共有函数的返回类型或者用作公有属性类型
    //那么该函数或者属性的实际类型，会是匿名对象声明的超类型，如果你没有声明任何超类型，就会是Any,在匿名对象中添加的成员将无法访问
    class C {
        //私有函数，返回类型是匿名对象类型
        private fun foo() = object {
            val x: String = "x"
        }

        //共有函数，返回类型是Any
        fun publicFoo() = object {
            val x: String = "x"
        }

        fun bar() {
            val x1 = foo().x
//            val x2 = publicFoo().x 错误：未能解析的引用“X”
        }
    }

    //在对象表达式中可以方便的访问到作用域的其他变量
//    fun countClicks(window: JComponent) {
//        var clickCount = 0
//        var enterCount = 0
//
//        window.addMouseListener(object : MouseAdapter() {
//            override fun mouseClicked(e: MouseEvent) {
//                clickCount++
//            }
//
//            override fun mouseEntered(e: MouseEvent) {
//                enterCount++
//            }
//        })
//        // ……
//    }


    //对象声明
    //kotlin使用object关键字来声明一个对象
    //kotlin中我们可以方便的通过对像声明来获得一个实例
//    object DataProviderManager {
//        fun registerDataProvider(provide: DataProvider) {
//
//        }
//
//        val allDataProviders: Collection<DataProvider>
//            get() = //
//    }
//
//    //引用该对象，我们直接使用其名称即可
//    fun useDataProviderManager(){
//        DataProviderManager.registerDataProvider()
//    }

    //当然你也可以定义一个变量来获取这个对象，当你定义两个不同的变量来获取这个对象时，
    // 你会发现你并不能得到两个不同的变量，就是说通过这种方式我们获得一个单例
//    var data1 = DataProviderManager
//    var data2 = DataProviderManager
//    data1.name = "test"
//    print("data1 name = ${data2.name}")


    object Site {
        var url: String = ""
        var name: String = "kotlin"
    }

    fun main1(args: ArrayList<String>) {
        var s1 = Site
        var s2 = Site
        s1.url = "https"
        println("s1:${s1.url}+,s2:${s2.url}")
        //输出结果：https，https
    }

    //对象可以有超类型
//    object DefaultListener : MouseAdapter() {
//        override fun mouseClicked(e: MouseEvent) {
//            // ……
//        }
//
//        override fun mouseEntered(e: MouseEvent) {
//            // ……
//        }
//    }

    //与对象表达式不同，当对象声明在另一个内部时，这个对象并不能通过外部类的实例来访问到该对象，而只能通过类名来访问
    //同样该对象也不能访问到外部类的方法和变量


    class Seit {
        var name = "安卓"

        object DeskTop {
            var url = "https\\baidu.com"
            fun showName() {
//                println("desk legs $name")//错误无法访问到外部类的方法和变量
            }
        }
    }

    fun main2(args: ArrayList<String>) {
        var s: Seit = Seit()
//        s.DeskTop.url  错误，不能通过外部类的实例访问到该对象
        Seit.DeskTop.url
    }


    //2.伴生对象
    //类内部的对象声明可以用companion关键字标记，这样他就与外部类关联在一起了，我们就可以直接通过外部类访问对象的内部元素

    class MyClass {
        companion object Factory {
            fun create(): MyClass = MyClass()
        }
    }

    val instance = MyClass.create()//访问到对象的内部元素

    //我们可以省略对象名，然后使用companicon代替需要声明的对象名
    class MyClass2 {
        companion object {

        }
    }

    val myClass2 = MyClass2.Companion

    //注意：一个类里面只能声明一个内部关联对象，即关键字companicon只能使用一次
    //伴生对像的成员看起来像其他语言的静态成员，但是运行时他们仍然是真是对象的实例成员，还可以实现接口
    interface Factory<T> {
        fun create(): T
    }

    class Myclass3 {
        companion object : Factory<Myclass3> {
            override fun create(): Myclass3 = Myclass3()
        }
    }

    //对象表达式和对象声明之间的语义差异：
    //对象表达式是在使用他们的地方立即执行
    //对象声明是在第一次被访问到时延迟初始化的
    //伴生对象的初始化相应的类被加载（解析）时，与java静态初始化器的语义相匹配
}
