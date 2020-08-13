package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/12 19:06
 * @类描述 {$TODO}接口的使用
 */
class InterfacesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var student = Student()
        student.share()
        student.invite()

        val d = D()
        d.foo()
        d.bar()

        val c = C()
        c.bar()
    }

    /**
     * 1.Kotlin 中接口与 Java8 类似，使用 interface 关键字定义接口，允许方法有默认实现：
     * 接口中未提供实现的方法默认是 `abstract` 修饰的，提供实现的则不是 `abstract` 修饰的。子类必须重写抽象成员。
     */
    interface MyInterface {
        abstract fun share() //未实现，默认abstract修饰
        fun invite() {//已实现，不是abstract修饰
            println("invite()")//可选方法体
        }

        /**
         * 3.接口属性
         * 你可以在接口中声明属性，在接口中声明的属性可以是抽象的，也可以为访问器提供实现。
         * 接口中声明的属性不能有后备字段 `field`，所以接口中声明的访问器不能引用后备字段。
         * 接口中的属性默认是抽象的，不允许被初始化值，接口不会保存属性值，实现接口时必须重写属性。
         */
        abstract val name: String //默认abstract修饰
        val type: Int
            //可以提供get实现，但是不能有后备字段field
            get() = 10

//        var age: Int  //报错，声明的属性不能有后备字段field
//            get() = field

//        var sex: String = "女性"//接口中的属性不允许被初始化

    }

    /**
     * 2.实现接口
     * 一个类或者对象可以实现一个或者多个接口。Kotlin 中没有像 Java 那样提供 `implements` 关键字实现，
     * 而是通过冒号`:`来表示，后面接需要实现的接口名称。`abstract`修饰的方法在子类中是必须要重写的，
     * 当然没有`abstract`修饰的方法你也可以重写提供自己的实现。
     */
    class Student : MyInterface {
        override val name: String
            get() = "Android"

        override fun share() {//该方法是abstract修饰的，所以子类必须实现，而invite()有默认实现，不是必须重写的
            //TODO
            println("share()")
        }
    }

    /**
     * 4.接口继承
     * Kotlin 中的接口是可以继承的，这点和 Java 类似。既可以为成员提供实现，又可以声明新的成员（函数和属性）。
     * 所以，实现这种接口的类只需要重写尚未实现的成员即可。
     * 抽象的成员被重写后不再是抽象的。实现类必须重写尚未实现的函数和属性。
     */
    //基类接口
    interface BaseInterface {
        val name: String //声明一个属性，默认抽象

        fun send1() //声明一个方法，默认抽象

        fun send2() {//声明一个方法，提供默认实现
            println("send2()")
        }
    }

    //子类接口：继承BaseInterface，实现了属性name
    interface ChildInterface : BaseInterface {
        val childName: String //声明一个属性，默认抽象

        override val name: String //实现BaseInterface接口中的属性
            get() = "Android"
    }

    //具体实现类：实现了ChildInterface接口
    class People : ChildInterface {
        override val childName: String //接口ChildInterface中尚未实现的
            get() = "Kotlin"

        override fun send1() {//接口BaseInterface中尚未实现的
            println("send1()")
        }
    }

    /**
     * 5.接口重写冲突
     * 所谓的接口重写冲突和前面讲解的类继承方法冲突是相似的。指在一个子类中，实现了多个父接口，
     * 而不同的父接口有相同的方法名称，这就会给子类实现时造成冲突，无法确认调用的是哪个父类的实现。
     */
    interface A {
        fun foo() {//已有默认实现，不会强制子类实现
            println("A：foo()")
        }

        fun bar()//没有默认实现，会强制子类实现
    }

    interface B {
        fun foo() {//已有默认实现，不会强制子类实现
            println("B：foo()")
        }

        fun bar() {//已有默认实现，不会强制子类实现
            println("B：bar()")
        }
    }

    //实现类，实现A接口
    class C : A {
        override fun bar() {//A中bar()没有实现，仍是抽象的，需要重写，并提供实现
            println("C：bar()")
        }
    }

    //实现类，同时实现A, B两个接口
    class D : A, B {
        //强制重写 bar()，因为A中的bar()没有实现
        override fun bar() {
            super<B>.bar() //实际上调用的是B中的bar()，因为A中bar()没有实现，仍是抽象的，不能被访问
        }

        //虽然接口A和B中的foo()方法都提供了默认实现，但是名字相同给子类D带来了冲突，无法确认调用的是A中的foo()实现还是B中的foo()实现，所以Kotlin强制子类重写
        override fun foo() {
            super<A>.foo() //调用A中的foo()
            super<B>.foo() //调用B中的foo()
        }
    }
    /**
     * 接口A，B都声明了函数 foo() 和 bar()，它们都默认实现了 foo() ，但是只有接口B中的 bar() 提供了默认实现，
     * 接口A中的 bar() 没有提供默认实现，默认为抽象。如果我们在接口A派生一个具体实现类C，
     * 那么必须重写 bar() 并提供一个实现。如上面的 class C。
     *
     * 但是，如果从接口A和B派生一个具体实现类D，需要实现多个接口继承的所有方法，并指定类D应该如何确切地实现它们。
     * 当如果有多个相同方法，则必须重写该方法，使用 super<父类名> 选择性调用父类的实现。如上面的 class D。
     *
     * 虽然接口A和B中的 foo() 方法都提供了默认实现，但是名字相同给子类D带来了冲突，
     * 无法确认调用的是A中的 foo() 实现还是B中的 foo() 实现，所以Kotlin强制子类重写 foo()。
     * 因为A中的 bar() 没有提供默认实现，也需要强制重写。
     */
}