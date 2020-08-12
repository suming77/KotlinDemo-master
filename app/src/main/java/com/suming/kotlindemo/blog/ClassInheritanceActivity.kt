package com.suming.kotlindemo.blog

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/07/30 17:06
 * @类描述 {$TODO}类和继承
 */
class ClassInheritanceActivity : AppCompatActivity() {
    val TAG = "ClassInheritanceActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var person = Person("Android")
        var dragon = Dragon(10)

        //19.类的实例化和属性使用
        var chicken = Chicken()
        var chicken2 = Chicken("HelloWord")
        var chicken3 = Chicken("Do My Self", 20)

        var test = Test()//类
        test.name//调用类中的属性，使用.号来引用
        test.num
        test.refresh()//调用成员方法

        var tiger = Tiger("老虎", "母老虎", 3)
        tiger.describe()
        tiger.Cat().appearance()

        var bovine = Bovine("Kotlin", 4)

        var mixedBlood = MixedBlood()
        mixedBlood.describe()
    }


    //1.类的声明：Kotlin 类可以包含构造函数、初始化代码块、函数、属性、内部类、对象声明等，使用关键字 class 声明类，后面紧跟类名：
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

    //2.类的声明由类名，类头（指定其类型参数，主构造函数等）和类主体组成（初始化代码块、函数、属性、内部类、对象声明等），用花括号{}括起来。
    // 类头和类主体都是可选的，如果类没有主体，那么花括号{}可以省略。
    class B

    //定义类的成员属性和函数
    class Test {
        //成员属性
        var name = "Kotlin"
        val num = 0

        fun refresh() {
            Log.e("ClassInheritanceActivity", "成员函数")
        }
    }

    /**
     * 3.构造函数
     * 在 Kotlin 中，允许有一个主构造函数和多个辅助构造函数，使用constructor修饰主构造函数，
     * 主构造函数是类头的一部分，类名后面跟上构造函数关键字以及类型参数。
     */
    class Student constructor(name: String) {

    }

    /**
     * 如果构造函数不具备任何的注解或者默认的可见修饰符时，关键字constructor可以省略；如果构造器有注解，
     * 或者有可见性修饰符，constructor关键字是必须的，注解和修饰符要放在它之前。
     */
    class Student2(name: String) {

    }


    //4.如果构造函数不具备任何的注解或者默认的可见修饰符public时，关键字constructor可以省略
    //下面两种情况constructor不可省
//    class Student private constructor(name: String) {
//
//    }
//    class Student @Inject constructor(name: String) {
//
//    }

    /**
     * 5.主构造函数
     * 主构造函数不能包含任何代码，初始化代码可以放在初始化代码块中，初始化代码块使用 `init` 关键字作为前缀：
     * 在实例化过程中，初始化块按照它们在类体中出现的顺序执行，与属性初始化块交叉执行。
     * 注意：主构造函数的参数可以在初始化代码块中使用，也可以在类主体定义的属性初始化代码中使用。
     */
    class Person constructor(name: String) {
        val TAG = "Person"
        val firstProperty = "First property: $name".also {
            Log.e(TAG, "初始化代码块 == $it")
        }

        init { //TODO 初始化代码块
            Log.e(TAG, "初始化代码块 == First initializer block：$name")
        }

        val secondProperty = "Second property: ${name.length}".also {
            Log.e(TAG, "初始化代码块 == $it")
        }

        init {
            Log.e(TAG, "初始化代码块 == Second initializer block：${name.length}")
        }
    }

    class People2(name: String) {
        var keyWord = name
    }


    /**
     * 6.一种简洁的语法
     * 可以通过函数来定义属性并初始化属性值（var或val）
     * 与常规属性的方式非常相似，主构造函数中声明的属性可以是可变的 var 或只读的 val。
     * 如果参数没有声明`var`或`val`，则默认为`val`类型。
     */
    class People(var name: String, val type: Int) {

    }

    /**
     * 7.辅助构造函数
     * 也是使用constructor关键字修饰为前缀,如果类有主构造函数，则每个辅助构造函数需要直接或者间接通过
     * 另一个辅助构造函数代理主构造函数，在同一个类中代理另一个构造函数使用this关键字.
     */
    class cat {
        constructor(name: String)
    }

    class Dog(var name: String) {
        //二级构造函数中的参数name是委托了主构造函数中的参数name
        constructor(name: String, age: Int, sex: String) : this(name) {

        }
    }

    /**
     * 8.注意：初始化块中的代码有效地成为主构造函数的一部分。委托给主构造函数是作为辅助构造函数的第一条语句执行的。
     * 因此所有初始化块和属性初始化器中的代码都在辅助构造函数体之前执行。即使类没有主构造函数，委托仍然隐式地发生，初始化块仍然被执行。
     */
    class Dragon {
        val TAG = "Dragon"

        init {
            Log.e(TAG, "初始化代码块")
        }

        constructor(type: Int) {
            Log.e(TAG, "辅助构造函数：type == " + type)
        }
    }

    /**
     * 9.构造函数私有
     * 如果一个非抽象类没有声明任何构造函数(主构造函数和辅助构造函数)，那么它将生成一个没有参数的主构造函数。
     * 构造函数的可见性是公有的，如果不希望你的类有一个公共构造函数，你需要声明一个空的主构造函数的非默认可见性。
     */
    class Sheep private constructor() {//构造函数私有

    }

    /**
     * 10. 构造器参数默认值
     * 注意：在JVM虚拟机中，如果主构造函数所有参数都有默认值，编译器会生成一个附加的无参的构造函数,这个构造
     * 函数会直接使用默认值，这样Kotlin可以更简单地使用像Jackson或者JPA这样使用无参构造函数来创建类实例的库。
     */
    class Chicken(var name: String = "Kotlin") {
        init {//初始化代码块
            Log.e("ClassInheritanceActivity", "构造函数有默认值: name == $name")
        }

        //辅助构造函数
        constructor(name: String = "Java", age: Int = 10) : this(name) {
            Log.e("ClassInheritanceActivity", "构造函数有默认值: name == $name  | age == $age")
        }
    }

    /**
     * 11.超类Any
     * Kotlin 中的所有类都有一个通用的超类`Any`，它是没有声明超类的类的默认超类，
     * 也就是说如果一个类没有继承任何超类，那么这个类默认隐式继承超类`Any`。
     */
    class Snake {}

    //基类
    open class Base2(type: Int) {//继承类是Open的
        //TODO
    }

    /**
     * 要声明一个显式超类型，可以将类型放在类头的冒号后面：
     * 如果派生类有主构造函数，则必须在那里使用主构造函数的参数初始化基类。
     */
    class Mouse(type: Int) : Base2(type) {////有主构造函数，使用主构造函数的参数初始化Base
        //TODO
    }

    /**
     * 12.如果子类没有主构造函数，那么每个辅助构造函数都必须使用`super`关键字初始化基类型，或者委托给
     * 另一个构造函数来执行该操作。注意：在这种情况下，不同的辅助构造函数可以调用基类型的不同构造函数：
     */
    class MyView : View {
        constructor(ctx: Context) : super(ctx)
        constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    }

    /**
     * 13.方法重写
     * 在基类中，使用 fun 声明函数时，此函数默认为 final 修饰，不能被子类重写，如果要允许子类重写该函数，
     * 那么要手动添加 open 关键字修饰它，子类重写方法使用 override 关键字：
     */
    //基类
    open class Animal(name: String) {
        constructor(name: String, sex: String) : this(name) {
            Log.e("ClassInheritanceActivity", "基类辅助构造函数: name == $name | sex == $sex")
        }

        //open 关键字修饰才可以被子类重写
        open fun describe() {
            Log.e("ClassInheritanceActivity", "父类方法：金色花边老虎")
        }

        //没有 open 关键字修饰
        fun preyOn() {
            //TODO
        }

        //属性重写
//        open val type: Int = 0
        open val type: Int
            get() = 1000
    }

    /**
     * `describe()`需要用 `override` 关键字修饰，否则编译器会报错；如果函数上没有 `open` 修饰符，
     * 比如上面的基类中的函数 `preyOn()` ，那么在子类中声明具有相同名字的方法是非法的，
     * 无论是否使用 `override` 关键字修饰它。当 `open` 修饰符被添加到最终类的成员上时，
     * 它没有任何作用（即没有 `open` 修饰的类）。
     */

    //子类
    class Tiger : Animal {
        constructor(name: String, sex: String, age: Int) : super(name, sex) {
            Log.e("ClassInheritanceActivity", "子类辅助构造函数: name == $name | sex == $sex | age == $age")
        }

//        final override fun describe() {//重写的方法被override标记，默认是 open 修饰的，如果要禁止这个函数被子类重写，使用 final 修饰
//        }

        override fun describe() {//重写父类中的方法，override修饰
            super.describe()//super关键字调用基类的函数
            Log.e("ClassInheritanceActivity", "子类方法重写：它是纸老虎")
        }

        //override fun preyOn() {//报错，基类中preyOn()函数没有声明`open`，无论是否有`override`修饰都是非法的
        //
        //  }

        //重写父类中属性
//        override val type = 8

        //super关键字调用基类的属性
        override val type = super.type

        //在一个内部类中，访问外部类的超类是通过 `super` 关键字完成的，该关键字由外部类名称指定：`super@Outer`：
        inner class Cat {
            fun appearance() {
                super@Tiger.describe() //调用基类Animal中的describe()的实现
                super@Tiger.type
                Log.e("ClassInheritanceActivity", "内部类访问外部类的超类：type == ${super@Tiger.type}")
            }
        }
    }

    /**
     * 14.调用基类实现
     * 可以在子类中使用 super 关键字调用其基类的函数和属性访问器：
     * 在一个内部类中，访问外部类的超类是通过 super 关键字完成的，该关键字由外部类名称指定：super@Outer，即 super@ + 外部类超类的类名：
     */

    /**
     * 15.属性重写
     * 属性重写的方式与方法重写类似，在基类上声明然后在子类上重新声明的属性必须用 override 关键字修饰作为前缀，
     * 而且它们不行具有兼容类型。每个声明的属性都可以由初始化器或get方法重写。
     * 您可以用 var 属性重写 val 属性，但是反过来不行。
     */

    /**
     * 注意：可以在主构造函数中使用 `override` 关键字作为声明属性的一部分。
     */
    interface Shape {
        val type: Int
    }

    class Rectangle(override val type: Int = 2) : Shape

    class Rectangle2 : Shape {
        override val type: Int = 10 //以后可以设置为任何数字
    }

    /**
     * 16.子类初始化顺序:
     * 在构造子类的新实例时，基类初始化是作为第一步完成的（之前只有求值基类构造函数的参数），因此在运行子类的初始化逻辑之前进行。
     * 在设计基类时，因此应该避免在构造函数，属性初始化和 init 块中使用 open 成员
     */
    open class Base(name: String) {//继承类是Open的
    init {
        println("基类初始化")
    }

        open val length: Int = name.length.also {
            println("基类初始化 length == $it")
        }
    }

    class Bovine(name: String, type: Int) : Base(name.toUpperCase().also { println("基类参数 name == $it") }) {
        init {
            println("子类初始化")
        }

        override val length: Int = (super.length + type).also {
            println("子类初始化 length == $it")
        }
    }

    /**
     * 17.规则重写
     * 在 Kotlin 中，实现继承由以下规则管理：如果一个类从其直接多个基类中继承了同一个(同名)成员的多个实现，
     * 那么它必须重写这个成员并提供自己的实现（可能是使用继承的实现之一）。为了表示继承的实现所来自的基类类型，
     * 我们使用尖括号  `super<>` 中的基类型名称限定的，例如：`super<Base>`。
     */
    open class D {
        open fun describe() {
            println("父类D的函数")
        }
    }

    interface E {
        fun describe() {
            println("父类E的函数")
        }
    }

    //为了消除歧义，重写的函数必须调用 D 或者 E 中该函数的实现，并提供自己的实现。
    class MixedBlood : D(), E {
        override fun describe() {
            super<D>.describe()//调用类 D 中的函数
            super<E>.describe()//调用接口 E 中的函数
            println("子类MixedBlood的实现")//MixedBlood 提供自己的实现
        }
    }

    /**
     * 18.抽象类
     * 一个类和它的一些成员可以被声明为 `abstract`。抽象成员在其类中没有实现。注意：我们不需要用 `open` 修饰抽象类或者函数。
     * 可以用抽象成员覆盖非抽象的 `open` 成员。
     */
    open class Birds {
        open fun fly() {
            //TODO
        }
    }

    abstract class Glede : Birds() {
        abstract override fun fly()//抽象成员在其类中没有实现
    }
}