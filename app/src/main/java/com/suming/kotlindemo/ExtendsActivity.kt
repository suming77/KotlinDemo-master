package com.suming.kotlindemo

import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/4 12:09
 * @类描述 ${TODO}继承语法
 */
class ExtendsActivity : AppCompatActivity() {
    //1.继承：Kotlin中所有类都继承Any类，它是所有类的超类，对于没有超类型声明的类是默认超类
    class example//从Any隐式继承

    //  equals()
//
//  hashCode()
//
//  toString()
    //注意Any类不是java.lang.Object
    //如果一个类要被继承，需要使用open关键字进行修饰
    open class Base(p: Int)//定义基类

    class Derived(p: Int) : Base(p)//继承基类

    //2.构造函数 - 子类有主构造函数
    //如果子类有构造函数，则基类必须在主构造函数中立即初始化
    open class person(var name: String, var age: Int) {//基类

    }

    class student(name: String, age: Int, var no: String, var sorce: Int) : person(name, age) {

    }

    fun main(args: Array<String>) {
        val s = student("SUM", 27, "2019", 100)
        println("姓名：${s.name}，年龄：${s.age},号码：${s.no},分数：${s.sorce}")
    }

    //3.子类中没有主构造函数
    //如果子类没有主构造函数，则必须在每一个二级构造函数中用super关键字初始化基类，或者代理另一个构造函数
    //初始化基类时，可以调用基类的不同构造方法
    class Student : person {
//        constructor(ctx:Context) :super(ctx){}

        constructor(name: String, age: Int) : super(name, age) {

        }
    }

    //示例
    //用户基类
    open class Person(name: String) {
        //次级构造函数
        constructor(name: String, age: Int) : this(name) {
            //初始化
            println("-----基类次级构造函数-----")
        }
    }

    //子类构造函数继承Person类
    class Student1 : Person {
        constructor(name: String, age: Int, no: String, score: Int) : super(name, age) {
            println("-----继承类次级构造函数-----")
            println("学生名字：${name},年龄：${age},学号：${no},分数：${score}")
        }
    }

    fun main2(args: Array<String>) {
        val s = Student1("ming", 28, "10086", 150)
    }

    //4，重写
    //在基类中，使用fun声明函数时，次函数默认为final修饰，不能被子类重写，如果允许子类重写改函数，
    // 则需要手动添加open关键字修饰他，子类重写方法使用override关键词

    //用户基类
    open class Person2 {
        open fun study() {//允许子类重写
            println("我毕业了")
        }
    }

    //子类继承Person类
    class Student2 : Person2() {
        override fun study() {
            println("我在读大学")
        }
    }

    fun main3(args: Array<String>) {
        val s = Student2()
        s.study()
        //输出为：我在读大学
    }

    //如果有多个相同方法（继承或者实现其他类，A类和B类），则别需重写改方法，使用super<泛型>选择性去调用父类方法
    open class A {
        open fun f() {
            println("A")
        }

        fun a() {
            println("a")
        }
    }

    interface B {
        fun f() {//接口的成员变量默认是open
            println("B")
        }

        fun b() {
            println("b")
        }
    }

    class C() : A(), B {
        override fun f() {
            super<A>.f()
            super<B>.f()
        }
    }

    fun main4(args: Array<String>) {
        val c = C()
        c.f()
        //打印输出为：AB
    }
    //C()继承自A()或者B,C不仅可以从A或者B中继承函数，而且C可以继承A,B的共有函数，此时函数中只有一个实现，
    // 为了消除歧义，该函数必须调用A或者B中该函数的实现，并提供自己实现

    //5.属性重写
    //属性重写使用override关键字，属性必须具有兼容类型，每一个声明的属性都可以通过初始化程序或者getter()方法重写
    open class Foo {
//        open val x :Int get() {....}
    }

    class Bar1 : Foo() {
//        override val x :Int = ......
    }

    //你可以用一个var属性去重写一个val属性，但是反过来不行，因为val属性本省定义了getter方法，
    // 重写var方法会在衍生类中额外生命一个setter方法
    //你可以在主构造函数中使用override关键字作为属性声明的一部分

    interface foo {
        val count: Int
    }

    class Bar2(override val count: Int) : foo {
    }

    class Bar3 : foo {
        override val count: Int = 0
    }

    //补充：1.子类继承父类时，不能有跟父类同名的变量，除非父类中该变量为private，
    // 或者父类中该变量为open并且子类中用override关键字重写

    open class person1(var name: String, var age: Int) {
        open var sex: String = "unknow"

        init {
            println("基类初始化")
        }
    }

    //子类的主构造方法name前面也加了var，这是不允许的，会报'name' hides member of supertype and needs 'override' modifier
    class student1(name: String, age: Int, no: String, sorce: Int) : person1(name, age) {
        override var sex: String = "man"
    }

    //2.子类不一定要调用父类和接口中共同拥有的同名的方法
    //3.子类不能用val重写父类中得var:子类重写父类属性，相当于必须重写该属性的getter和setter方法，
    // 而子类的val不能有setter方法，所以无法覆盖父类中得var的setter方法相当于缩小了父类中相应属性的使用范围

    //4.如果一个变量想要在定义的时候被初始化，该变量必须拥有backing field,该变量默认的getter和setter方法中是有field字段的，
    // 但是我们重写了getter和setter方法，但是方法中没有出现field字段的时候会报错： Initializer is not allowed here because this property has no backing field
    //除非显示写出关键字field(表示已声明）
    var a: Int = 0
        get() {
            field//这里必须出现field关键字，否则var a :Int = 0会报错，除非你出掉= 0这部分，不要给 他赋初始化值
            return 0
        }
        set(value) {}
}