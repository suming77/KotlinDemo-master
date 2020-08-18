package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.User

import com.suming.kotlindemo.normal.share

/**
 * 7.注意可见性
 * 在文件顶层声明的扩展可以访问同一文件中的其他 private 顶层声明；
 * 如果扩展在其接受者之外声明，则此类扩展不能访问接受者的私有成员。
 */
private var sign = "访问顶层声明私有成员"

fun User.topLevelType() {
    println("sign == $sign")
}

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/14 18:13
 * @类描述 {$TODO}Kotlin 扩展 Extensions
 * Kotlin 提供了扩展具有新功能的类的能力，而不必从类继承或使用设计模式（如Decorator），这是通过称为扩展的特殊声明来实现的。
 *
 * 简单点来说，当你想在某个类加新功能时，为了避免在原来的类做修改，可以在别的类中为该类设计新代码。
 * 扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响。
 */
class ExtensionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //调用扩展函数
        val user = User("Android", 0, "none")
        user.extFunction("Kotlin")

        val list = mutableListOf(1, 2, 3, 4)
        list.swap(0, 2)//this中的swap()将会保存list的值
        println("扩展函数:$list")

        println("扩展属性:${list.lastIndex}")//调用拓展属性,在同一文件中，顶层拓展可以访问顶层 private 声明

        printName(Student())

        var people = People()
        people.functionType(0)

        val name = null
        name.toString()

        Dragon.printCompanion()
        println("伴生函数的扩展：type == ${Dragon.type}")

        /**
         * 6.作用域的扩展
         * 通常扩展的函数和属性定义在顶级top-level包下：
         * 要在声明包之外使用定义的扩展，通过 import 导入扩展的函数名进行使用：
         */
        var user2 = User("Android", 0, "none")
        user2.share()

        Minor(Host()).printInvite()
        //Host().printInvite() //错误，扩展函数在 Minor() 不可用

        Person2().printShare(Base())
        Student2().printShare(Base())//分发接受者虚拟解析
        Student2().printShare(Child())//扩展接收者虚拟解析

        user.topLevelType()
    }

    /**
     * 1.扩展函数
     * 扩展函数可以在已有类中添加新的方法，不会对原类做修改。要声明一个扩展函数，我们需要在它的名字前面加上一个接收方类型，也就是被扩展的类型。
     * 扩展函数定义形式：fun receiverType.functionName(params){}
     *  receiverType：  表示函数接收者，也就是函数扩展对象；
     *  functionName： 表示扩展函数的名称；
     *  params：    表示扩展函数的参数，可以为 null。
     */

    //扩展函数
    fun User.extFunction(name: String) {
        println("扩展函数：User.extFunction() | name == $name")
    }

    //扩展函数中的 this 关键字是指接收者对象（点之前传递的对象，比如上面的 MutableList）。
    // 现在，我们可以在任何 MutableList<Int> 上面调用这样的函数：
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val temp = this[index1]//this 表示接受者对象，即MutableList
        this[index1] = this[index2]
        this[index2] = temp
    }

    //在函数名之前声明泛型类型参数，方便它在接收方类型表达式中可用。
    fun <T> MutableList<T>.swap2(index1: Int, index2: Int) {
        val temp = this[index1]//this 表示接受者对象，即MutableList
        this[index1] = this[index2]
        this[index2] = temp
    }

    /**
     * 2.扩展函数是静态解析的
     * 扩展实际上并不修改它们所扩展的类。通过定义扩展，不必向类中插入新成员，而只需要使用新函数可通过点符号对这种类的变量调用。
     */

    open class Person//基类

    class Student : Person()//Student 继承自 Person

    fun Person.getName() = "Person"//扩展函数

    fun Student.getName() = "Student"//扩展函数

    //扩展函数是静态分配的，也就是说，它们不是按接收类型的虚拟成员。这意味着在调用扩展函数时，
    //具体被调用的是哪一个函数，由调用该函数的表达式的类型决定的，而不是动态类型决定的。
    fun printName(person: Person) {
        println("扩展函数是静态解析的：${person.getName()}")
    }

    class People() {
        //如果一个类有一个成员函数，并且定义了一个具有相同接收类型，函数名称相同，给定参数相同的扩展函数，那么会优先是使用成员函数：
        fun functionType() {
            println("People：成员函数")
        }
    }

    //    fun People.functionType() {
//        println("People：扩展函数")
//    }

    //但是，扩展函数重载具有相同函数名称但是参数不同的成员函数是完全可以的
    fun People.functionType(type: Int) {
        println("People：扩展函数")
    }

    /**
     * 3.扩展属性
     * 与扩展函数类似，Kotlin 支持扩展属性：
     * 注意：由于扩展函实际上并不向类中插入成员，因此没有有效的方法让扩展属性具有后备字段.
     * 所以初始化器不允许用于扩展函数，只能由显式地提供setter/getter来定义。
     */
    val <T> List<T>.lastIndex: Int
        get() = size - 1

    //var Student.type = 0 //报错，扩展属性不能初始化


    /**
     * 4.null 的扩展函数
     * 扩展可以用 null 的接收器类型定义，这样的扩展可以在对象变量上调用，即使它的值是 null ，并且可以检查
     * 主体内的 this==null 。这就是 Kotlin 中调用 toString() 而不检查是否为 null 的原因，检查发生在扩展函数内部。
     */
    fun Any?.toString(): String {
        if (this == null) return "null"
        //在空检查之后，`this`自动转为非null类型，下面的toString()解析为Any类的成员函数
        return toString()
    }

    /**
     * 5.伴生对象的扩展
     * 如果一个类定义了一个伴生对象，你也可以为这个伴生对象定义扩展函数和属性，伴生对象通过类名.的形式调用伴生对象，
     * 伴生对象声明的扩展函数通过类名限定符来调用
     */

    class Dragon {
        companion object {//伴生对象
            //TODO
        }
    }
    //伴生对象的扩展函数
    fun Dragon.Companion.printCompanion() {
        println("伴生对象的扩展：扩展函数")
    }
    //伴生对象的扩展属性
    val Dragon.Companion.type: Int
        get() = 0

    /**
     * 8.扩展声明为成员
     * 在一个类内部你可以声明另一个类的扩展。这样的扩展中，有多个隐式接收者对象，它们的成员不需要限定符就可以访问。
     * 其中扩展所在类的实例称为分发接受者，而扩展方法的接收器类型的实例称为扩展接收者。
     *
     * 如果分发接受者和扩展接受者的成员之间存在名称冲突，则以扩展接受者优先，如果要引用分发接受者的成员，则可以使用限定的 this@类名. 语法。
     */

    class Host() {
        fun inviteHost() {
            println("扩展接受者：Host")
        }

        //与 Minor 的函数 invite() 同名
        fun invite() {
            println("扩展接受者：Host")
        }
    }

    class Minor(val host: Host) {
        fun inviteMinor() {
            println("分发接受者：Minor")
        }

        //与 Host 的函数 invite() 同名
        fun invite() {
            println("分发接受者：Minor")
        }

        fun Host.extensionInvite() {
//            inviteHost()//回调Host中成员函数inviteHost()
//            inviteMinor()//回调Minor中成员函数inviteMinor()
            invite()//这调用的是 Host 的函数 invite()，以扩展接受者优先
            this@Minor.invite() //可以通过 this@类名. 限定符调用 Minor 的函数 invite()
        }

        fun printInvite() {
            host.extensionInvite()//回调扩展函数
        }
    }

    /**
     * 9.扩展声明为 open
     * 以成员的形式定义的扩展可以声明为 open ，并可以在子类中重写。这意味着在这些扩展函数的分发过程中，
     * 对于分发接受者是虚拟的，但是对于扩展接受者扔是静态的。
     */
    open class Base {}

    class Child : Base() {}

    open class Person2 {
        open fun Base.shareBase() {
            println("Base 扩展函数在 Person")
        }

        open fun Child.shareBase() {
            println("Child 扩展函数在 Person")
        }

        fun printShare(base: Base) {
            base.shareBase()//回调扩展函数
        }
    }

    open class Student2 : Person2() {
        override fun Base.shareBase() {
            println("Base 扩展函数在 Student")
        }

        override fun Child.shareBase() {
            println("Child 扩展函数在 Student")
        }
    }

}