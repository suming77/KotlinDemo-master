package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 1.顶层声明
 * 函数，属性和类，对象和接口可以在顶层top-level声明，即直接在包中声明：
 * 如果没有指定任何可见修饰符，默认为 public，所以这些声明将随处可见；
 * 如果将声明标记为私有 private ,那么它将只在包含该声明的文件中可见；
 * 如果将声明标记为内部 internal ，它在同一个模块中随处可见；
 * protected 对顶级声明不可用。
 */
fun send() { //默认public修饰
    //TODO
}

private fun share() {//只在 ModifierActivity.kt 文件中可见
    //TODO
}

public var type: Int = 5 //public修饰，公共的，属性在哪都可见
    private set   //setter方法私有，仅在 ModifierActivity.kt 文件中可见

internal val name: String = "Kotlin" //在同一个模块内可见

//protected var sign = 0 //报错，protected不能用于顶级声明中

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/13 18:37
 * @类描述 {$TODO}可见修饰符
 */

class ModifierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 2.类和接口
     * 对于在类中声明的成员：
     * public：   表示任何看到已声明类的客户端都会看到它的公共成员；
     * private：   表示只在类内部可见（包括其所有成员）；
     * protected：  表示只在当前类和子类中可见；
     * internal：   表示这个模块中任何看到声明类的客户端都看到了它的internal内部成员。
     * 注意：在 Kotlin 中，外部类不会看到其内部类的私有成员。
     *
     * 如果你重写了一个 protected 修饰的成员而没有显式指定它的可见性，那么重写的成员也将具有protected的可见性。
     */
    //基类
    open class Outer {
        private val numA = 0
        protected open val numB = 1
        internal val numC = 2
        public val numD = 3

        //内部类
        protected class Inner {
            public val numE = 4
            private val numF = 5 //不可以被Outer访问
        }
    }

    //子类
    class Student : Outer() {
        //numA 不可见
        //numB、numC、numD 可见
        //Inner 和 numE 可见

        override val numB: Int = 10 // numB 是 protected 修饰的，同类和子类可见

        //使用如下
        fun foo() {
            //numA //报错,不可见
            numB //可见
            numC //可见
            numD //可见
            Inner() //可见
            Inner().numE //可见
        }
    }

    class People(val outer: Outer) {
        //outer.numA, outer.numB 不可见
        //outer.numC, outer.numD 可见，属于同一个Module
        //Outer.Inner不可见，Inner::numE也不可见

        //使用如下
        fun foo() {
            //outer.numA //报错,不可见
            //outer.numB //报错,不可见

            outer.numC //可见
            outer.numD //可见

            //Outer.Inner //报错,不可见
            //Inner::numE //报错,不可见

        }
    }

    /**
     * 3.构造函数
     * 要指定类的主构造函数的可见性，有可见性修饰符时， `constructor` 关键字不能隐藏
     * 这里的构造函数是私有的。默认情况下，所有的构造函数是 public 的，这意味着它们在类可见的任何地方都是可见的
     * （例如，一个内部类的构造函数只在同一个模块中可见）。辅助构造函数{}中的属性和本地变量一样，不能有可见性修饰符。
     */
    class Dragon private constructor(name: String) {
        //辅助构造函数
        constructor(name: String, type: Int) : this(name) {
            //private val numA = 0 //报错，本地变量不能有可见性修饰符
            //protected val numB = 1 //报错
            //internal val numC = 2 //报错
            //public val numD = 3 //报错

            val numE = 4 //正确
        }
    }

    /**
     * 4.局部声明
     * 本地(局部)变量，函数和类不能有可见修饰符。
     */
    class Rabbit {
        fun foo() {
            //public var textView = TextView() //报错，本地变量不能有可见性修饰符
            //private var textView = TextView() //报错
            //protected var textView = TextView() //报错
            //internal var textView = TextView() //报错

            //public fun invite() {} //报错，本地函数不能有可见性修饰符
            //private fun invite() {} //报错
            //protected fun invite() {} //报错
            //internal fun invite() {} //报错

            //public class Goose {} //报错，局部类不能有可见性修饰符
            //private class Goose {} //报错
            //protected class Goose {} //报错
            //internal class Goose {} //报错
        }
    }

    /**
     * 5.接口
     * 接口中的属性只能用 `public` 修饰，函数只能用 `public` 和 `private` 修饰，
     * 但是 `private` 修饰的函数不能被重写。要使用接口中的属性和方法则需要在实现中重写
     */
    interface baseInterface {
        //接口中的属性默认是抽象的，private，protected，internal都不能与抽象 abstract 共用
        //private val numA: Int //报错
        //protected val numB: Int //报错
        //internal val numC: Int //报错
        public val numD: Int

        public fun share() {//能被重写
            numD
        }

        //private 可以修饰方法，并且可以使用接口中的属性，但是不能被子类重写
        private fun share2() {
        }

        //protected fun share3() //报错，protected不能修饰接口中的函数

        //internal fun share4() //报错，protected不能修饰接口中的函数
    }

    //实现类，要使用接口的属性则需要重写
    class Chicken : baseInterface {
        override val numD: Int = 0
        override fun share() {
            super.share()
        }
    }

    /**
     * 6.模块
     * internal 修饰符表示该成员在同一个模块中可见。更具体的说，模块是一组共同编译的 Kotlin 文件：
     * 一个IntelliJ IDEA模块；
     * 一个Maven项目；
     * Gradle源集（test 源集可以访问 main 的内部声明除外）；
     * 使用< kotlinc > Ant任务的一次调用编译的一组文件。
     */

}