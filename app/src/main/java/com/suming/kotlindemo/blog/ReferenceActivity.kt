package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.log
import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

/**
 * @创建者 mingyan.su
 * @创建时间 2021/01/08 12:29
 * @类描述 {$TODO}引用
 */
class ReferenceActivity : AppCompatActivity() {

    /**
     * 1.要使用反射属性就的添加依赖Kotlin -reflect.jar
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 2.Lambda 表达式可以直接把一个代码块作为一个参数传递给函数，但是如果要当做参数传递的代码已经被定义成了函数，
         * 此时还需要重复写一个代码块传递过去吗？肯定不是，Kotlin拒绝重复啰嗦的代码，这时候就需要 把函数转换成一个值，这种方式称为 成员引用。
         */
        val persons = arrayListOf(Person("Java", 20), Person("Android", 5))
        println(persons.maxBy { p: Person -> p.name })/* 成员引用和调用函数的 lambda 具有一样的类型，所以可以相互转换 */
        println(persons.maxBy(Person::name))//成员的引用类型和maxBy()传入Lambda表达式的一致

        /**
         * 3.成员引用的基本语法
         * 使用 :: 运算符来来创建一个单个方法或者单个属性的函数值。成员引用由类名，双冒号，成员三个元素组成。
         * 成员是函数名表示引用函数，成员是属性表示引用属性。
         * 例如：Person::name
         */

        funQuote()
        propertyQuote()
        classQuote()
        bindingQuote()
    }

    /**
     * 4.函数引用
     * 成员是函数名表示函数引用。这种情况省略了类名称，直接以 :: 开头
     */
    fun funQuote() {
        val numbers = arrayListOf(10, 20, 30)
        //函数引用 ::isOld 被当做实参传递给 filter() 函数，它会调用相应的函数。这里 ::isOld 是一个函数类型 (Int) -> Boolean 的值。
        println(numbers.filter(::isOld))//打印：[20, 30]

        //（1）函数重载
        val strs = arrayListOf("Java", "HelloWord", "Tonight")
        println(strs.filter(::isOld))//调用 isOld(str: String)，打印：[Java]

        //（2）函数引用变量:如果 lambda 要委托给一个接收多个参数的函数，提供函数引用代替它将会非常方便。
        // 另外，也可以通过将函数引用存储在显式指定类型的变量中来提供必要的上下文：

        //有多个参数的 lambda
        val action = { person: Person, message: String -> sendEmail(person, message) }
        //使用成员引用代替, 将 ::sendEmail 存储在显式指定类型的变量 nextAction 中,并显式指定类型
        val nextAction: (Person, String) -> Unit = ::sendEmail

        //调用
        nextAction(Person(""), "msg")

        //（3）顶层函数引用
        this.run({ saulte() })
        //使用成员引用简化后
        run(::saulte)//打印：fun saulte

        //（4）构造函数引用
        //构造函数的引用，当需要函数类型的对象与构造函数相同的形参并返回适当类型的对象时，可以使用它们。
        //通过使用 :: 操作符并添加类名来引用构造函数： ::类名。
        //例如以下函数，它需要一个不带形参的函数形参，并返回类型为 Student：

        //没有参数的构造函数
        class Student

        fun constRef(factory: () -> Student) {
            val student: Student = factory()
        }

        //调用
        constRef(::Student)

        //（5）扩展函数引用
        val isChild = Person::isChild
        println("isChild == " + isChild(Person("Java")))//打印 true
        //显式返回类型
        val isChildResult: Person.() -> Boolean = Person::isChild
        println("isChild == " + isChildResult)//打印 true

        //（6）函数组合
        fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {//组合函数
            return { x -> f(g(x)) }
        }

        fun length(s: String) = s.length

        //它返回传递给它的两个函数的组合：compose(f, g) = f(g(*))。现在，你可以把它应用到可调用引用：
        val isOldLength = compose(::isOld, ::length)
        println(strings.filter(isOldLength))//打印：[AppCompatAutoCompleteTextView]
    }

    fun isOld(x: Int) = x > 18

    //函数重载
    fun isOld(str: String) = str == "Java" || str == "Android" || str == "Kotlin"

    val strings = arrayListOf("View", "TextView", "AppCompatAutoCompleteTextView")

    /**
     * 4.属性引用
     * 成员是属性名则表示属性引用
     */
    fun propertyQuote() {
        //（1）访问类成员属性
        //属性引用访问类成员的属性，也是使用 类名+双冒号+属性 的方式，对其进行限定，将属性引用存储在显式指定类型的变量中

        val prop = Person::name
        println(prop.get(Person("Android")))//打印：Android

        //（2）属性作为类对象
        val ageGet = ::age.get()
        val ageName = ::age.name
        //表达式 ::age 的计算结果是类型为 KProperty<Int> 的属性对象，它允许我们使用 get() 读取其值或使用 name 属性检索属性名。
        println("::age.get() == $ageGet")//属性当前值
        println("::age.name == $ageName")//属性当前名称

        //对于一个可变 var 属性，例如 var day = 1，::day 返回 KMutableProperty<Int> 类型的值，它有一个 set() 方法：
        ::day.set(2)
        println("::day.set() == $day")//打印：::day.set() == 2

        //（3）扩展属性
        println(String::lastChar.get("Kotlin"))//打印：n

        //属性引用也可以用于期望函数只有一个泛型参数的地方：
        println(strings.map(String::length))//打印：[4, 8, 29]
    }

    /**
     * 5.与Java反射的互操作性
     * 在 JVM 平台上，标准库包含了反射类的拓展，这些反射类提供了与 Java 反射对象之间的映射，
     * 例如：要找到作为 Kotlin 属性的 getter 后备字段或 Java 方法：
     */
    fun javaInter() {
        println(Person::name.javaGetter)//打印：public final String Person.getName()
        println(Person::name.javaField)//打印：private final String Person.name

        //要获得与 Java 类对应的 Kotlin 类，使用 .Kotlin 扩展属性：
        fun getKClass(a: Any): KClass<Any> = a.javaClass.kotlin

        println(getKClass(Person()))//打印：class com.suming.kotlindemo.blog.Person
    }

    /**
     * 6.类引用
     * 最基本的反射特性是获得对 Kotlin 类的运行时引用。要获得对静态已知的 Kotlin 类的引用，可以使用类字面量语法：
     */
    fun classQuote() {
        //Kotlin 类引用
        val reflection: KClass<ReferenceActivity> = ReferenceActivity::class//ReflectionActivity类的引用
        //引用 quo 是 KClass 类型的值， KClass 表示一个类，并提供自检功能。

        //java 类引用
        val reflectionJava: Class<ReferenceActivity> = ReferenceActivity::class.java
        val reflectionJava2= ExampleActivity::class

        reflectionJava.simpleName
        reflectionJava2.simpleName

        Log.e("smy", "reflection == $reflection | reflectionJava == $reflectionJava | reflectionJava2 == $reflectionJava2")
        //注意：Kotlin 类引用与 Java 类引用是不同的。要获取 Java 类引用，请在 KClass 实例上使用 .java 属性。
    }

    /**
     * 7.绑定引用
     * Kotlin 1.1开始，允许你使用成员引用语法 捕捉特定实例对象上的方法引用。
     * 绑定引用由对象实例，双冒号，成员三个元素组成。格式：对象实例 :: 成员。
     */
    fun bindingQuote() {
        //(1) 绑定函数

        var numberRegex = "\\d+".toRegex()//将//d+转化为正则表达式，表示匹配一个或多个数字
        println(numberRegex.matches("28"))//打印：true

        //存储numberRegex的引用,引用被绑定到它的接收者
        var isNumber = numberRegex::matches
        println(isNumber("28"))//打印：true

        //我们不是直接调用方法 matches，而是存储对它的引用。这样的引用被绑定到它的接收者，
        // 它可以直接调用（如上面）。也可以在需要函数类型的表达式时使用：
        val nums = arrayListOf("View", "123", "456")
        println(nums.filter(numberRegex::matches))//打印：[123, 456]

        //比较绑定引用和相应的未绑定引用的类型。绑定的可调用引用有它的接收者“附加”到它上面，所以接收者的类型不再是参数：
        val isNumber1: (CharSequence) -> Boolean = numberRegex::matches//已绑定
        val matches: (Regex, CharSequence) -> Boolean = Regex::matches//未绑定

        //(2)属性引用
        val person = Person("Android", 24) //创建实例
        //val personName = { person.name } //Kotlin1.1之前显式写出 lambda
        val personName = person::name

        println("person: name == ${personName()}")//打印 person: name == Android
        //注意：personAge 是一个零函数的参数，在 Kotlin1.1 之前你需要显式写出 lambda { person.age }，而不是使用绑定成员引用：person::age。
        //另外：由于在 Kotlin1.2 没有必要显式地指定 this 作为接收者，所以 this::foo 和 ::foo 是等价的。

        //(3)绑定类引用 你可以使用相同的 ::class 语法来获取特定对象的类引用，方法是将对象作为接收者：
        val view: View = AppCompatTextView(this)

        if (view is AppCompatTextView) {
            println(view::class.qualifiedName)//打印：androidx.appcompat.widget.AppCompatTextView
        }
        //不管接收方表达式类型(View)是什么，都可以获得对对象确切类的引用，例如 AppCompatTextView 或 TextView 。


        //(4)结合构造函数引用
        //内部类构造函数的可调用引用可以通过提供外部类的实例来获得，格式 外部类实例 :: 外部类：

        class Outer {
            inner class Inner
        }

        val outer = Outer()
        val boundInner = outer::Inner
    }

    val age = 18
    var day = 0

    private fun sendEmail(person: Person, message: String) {
        //TODO
    }

    class Person(val name: String) {
        constructor(name: String, age: Int) : this(name)
    }
}


//声明在最顶层
private val String.lastChar: Char
    get() = this[length - 1]

// 这是Person的一个扩展函数，判断是否成年
fun ReferenceActivity.Person.isChild() = age > 18



