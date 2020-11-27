package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @创建者 mingyan.su
 * @创建时间 2020/11/17 18:04
 * @类描述 {$TODO}委托属性
 *有两个对象参与处理同一个请求，接收请求的对象将请求委托给另一个对象来处理，这就是委托。
 * Kotlin直接支持委托模式，更优雅简洁，通过关键字 `by` 实现委托。
 *
 * 语法：val/var <属性名>: <类型> by <表达式>
 */
class DelegatedActivity : AppCompatActivity() {

    /**
     * 6.Lazy属性
     * 懒加载是指在程序第一次使用到时再初始化，当再次调用时只会得到结果不会再次初始化。
     *
     * `lazy()` 是一个函数，它接收一个 lambda 并返回一个 `lazy <T>` 的实例，
     * 它可以作为实现一个 lazy 属性的委托：对 `get()` 的第一次调用执行传递给 `lazy()` 的
     * lambda 并记住结果，对 `get()` 的后续调用只返回记住的结果。
     */
    val lazyValue: String by lazy {
        println("---lazyValue惰性初始化---")//第一次调用输出，第二次调用不执行
        "Kotlin"
    }
    val lazyValue2: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
        println("---lazyValue惰性初始化---")
        "Kotlin"
    }
    val lazyValue3: String by lazy(LazyThreadSafetyMode.NONE) {
        println("---lazyValue惰性初始化---")
        "Kotlin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //在 Derived 声明中，`by` 字句表示：将 b 保存在 Derived 的对象实例内部，而且编译器会让 Derived 生成继承自 Base 接口的所有方法，并将调用转发给 b。
        val baseImpl = BaseImpl()
        Derived(baseImpl).share()

        val school = School()
        println(school.str)//访问该属性，调用了getValue()函数
        school.str = "Android"//调用 setValue() 函数


        println(lazyValue)//第一次执行，执行两次输出结果
        println(lazyValue)//第二次执行，值输出返回值

        val user = User()
        user.name = "first"//第一次赋值
        user.name = "second"//第二次赋值

        val student: Student = Student(mapOf(
                "name" to "Kotlin",
                "age" to 20))

        println("Student: name == ${student.name}, age == ${student.age}")


        val map = mutableMapOf<String, Any?>(
                "name" to "Android",
                "age" to 100)
        val studentMutl: MutlStudent = MutlStudent(map)
        println("MutlStudent: name == ${studentMutl.name}, age == ${studentMutl.age}")

        map["name"] = "Java"
        map["age"] = 2000
        println("MutlStudent: name == ${studentMutl.name}, age == ${studentMutl.age}")

        Foo().notNullName = "初始化值"
        println(Foo().notNullName)
    }

    /**
     * 1.装饰器模式:本质就是创建一个新类，实现与基类一样的接口，并且将类的实现作为一个子段保存，
     * 这样就能在基类不被修改的情况下直接修改基类的实例。但是缺点是造成很多样板代码。
     */
    class CustomList<T>(val innerList: Collection<T> = mutableListOf<T>()) : Collection<T> {
        override val size: Int = innerList.size

        override fun contains(element: T): Boolean = innerList.contains(element)

        override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)

        override fun isEmpty(): Boolean = innerList.isEmpty()

        override fun iterator(): Iterator<T> = innerList.iterator()
    }

    /**
     * 2.委托
     */
    class DelegatingCollection2<T>(val innerList: Collection<T> = mutableListOf<T>()) : Collection<T> by innerList {
        override fun isEmpty(): Boolean {
            return innerList.isEmpty()
        }
    }

    /**
     * 3.类委托
     * 类的委托即一个类中定义的方法实际是调用另一个类对象的方法来实现的。
     * 以下实例中， 派生类 Derived 继承了接口 Base 的所有方法，并且委托一个传入的 Base 类的对象来执行这些方法。
     */
    //接口
    interface Base {
        fun share()
    }

    //实现此接口的被委托的类
    class BaseImpl : Base {
        override fun share() {
            println("BaseIMPL:实现Base接口被委托的类")
        }
    }

    //通过关键字 by 创建委托类
    class Derived(b: Base) : Base by b {
//        override fun share() {
//            println("Derived:委托类重写Base中的方法")
//        }
    }

    /**
     * 4.属性委托
     * 属性委托是指一个类的某个属性值不是在类中直接定义的，而是将其托付给一个代理类，从而实现对该类的属性的统一管理。
     * 有一些常见的属性类型，尽管我们可以在每次需要时手动实现它们，但最好是只实现一次，放入库中一直使用。
     *
     *
     * by 后面的表达式就是委托，属性 prop 的 `get()` 和 `set()` 方法将委托给 delegate 对象的 `getValue()` 和 `setValue()` 方法。
     *
     * 属性委托不必实现任何接口，但必须提供一个 `getValue()` 函数（对于 var 属性，还需要`setValue()`函数）。
     * 这两个函数都需要使用 `operator` 关键字进行标记，意味着委托属性依赖于约定的公能，像其他约定的函数一样，
     * `getValue()` 和`setValue()`可以是成员函数，也可以是扩展函数。
     *
     * thisRef：进行委托的类的对象，必须是属性所有者的相同或超类型（对于扩展属性-被扩展的类型）；
     * property：为进行委托的属性的对象，property.name 表示属性名称，必须是 KProperty<*> 类型或是其超类型。
     * value：表示当前属性值，必须和属性同类型或者是它的超类型。
     */

    class School {
        var str: String by Delegate()

        var newName: Int = 0

//        @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
//        var oldName: Int by this::newName
    }

    class Delegate {
        //方法使用operator 修饰，thisRef 为进行委托的类的对象，property 为进行委托的属性的对象。
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, 这里委托了${property.name}属性!"
        }

        //value：表示当前属性值，必须和属性同类型或者是它的超类型。
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$thisRef 的属性${property.name}赋值为 $value ")
        }
    }


    /**
     * 5.标准库
     */
    fun resourceDelegate(): ReadWriteProperty<Any?, Int> = object : ReadWriteProperty<Any?, Int> {
        var curValue = 0
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int = curValue
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            curValue = value
        }
    }

    val readOnly: Int by resourceDelegate()//ReadWriteProperty 是 val 的
    val readWrite: Int by resourceDelegate()


    /**
     *  7.被观察者observable
     *  `observable` 可以用于实现观察者模式。`Delegates.observable()` 有两个参数：第一个是初始化值；
     *  第二个是属性值变化事件的响应器（handler）。
     *
     *  在执行了赋值之后都会执行属性值变化事件的响应器（handler）。它有三个参数：一个被分配的属性，旧值和新值：
     *
     *  如果你想拦截修改属性动作并禁止修改它们，请使用 `vetoable()` 取代 `observable()`。
     *  handler 需要返回一个 Boolean 值，true 表示同意修改，false 表示禁止修改。该回调会在修改属性值之前调用。
     */
    class User {
        //        var name: String by Delegates.observable("初始值") {
        var name: String by Delegates.vetoable("初始值") { property, oldValue, newValue ->
            println("observable: 旧值 == $oldValue | 新值 == $newValue")
            return@vetoable false
        }
    }

    /**
     * 8.在Map中存储属性
     * 一个常见的用法是在 `Map` 中存储属性值。这经常出现在解析 JSON 或做其他动态事情的应用程序中。
     * 在这种情况下，您可以使用 `Map` 实例本身作为委托属性的委托。
     */
    class Student(val map: Map<String, Any?>) {
        val name: String by map
        val age: Int by map
    }

    //如果你使用 `var` 的属性，需要把 `Map` 换成 `MutableMap`。
    class MutlStudent(val map: MutableMap<String, Any?>) {
        val name: String by map
        val age: Int by map
    }


    /**
     * 9.Not Null
     * `Not Null` 适合那些无法在初始化阶段就确定属性值的场合。
     */
    class Foo {
        var notNullName: String by Delegates.notNull<String>()
    }


    /**
     * 10. 本地委托属性
     * 你可以将局部变量声明为委托属性。例如，你可以使一个局部变量惰性初始化：
     * 变量 memoizedFoo 只在第一次访问时计算。如果 someCondition 失败，则根本不会计算该变量。
     */
//    fun example(computeFoo: () -> Foo) {
//        val memoizedFoo by lazy(computeFoo)
//
//        if (someCondition && memoizedFoo.isValid()) {
//            memoizedFoo.doSomething()
//        }
//    }

}

/**
 * 11.委托给另一个属性
 * 自 Kotlin1.4 以来，一个属性可以将它的 getter 和 setter 委托给另一个属性。这种委托可用于顶级属性和类属性（成员和扩展）
 *
 * delegate 属性可以是：一个顶级属性；同一个类的成员或扩展属性；另一类的成员或扩展属性。
 */
//var topLevelInt: Int = 0
//
//class WithDelegate(val numA: Int)
//
//class MyClass(var memberInt: Int, val instance: WithDelegate) {
//    var delegatedToMember: Int by this::memberInt
//    var delegatedToTopLevel: Int by ::topLevelInt
//
//    val delegatedToAnotherClass: Int by instance::numA
//}
//
//var MyClass.extDelegated: Int by ::topLevelInt


class MyClass {
    var newName: Int = 0
//这可是非常有用的，例如，当你希望以后兼容的方式重命名属性时：引入一个新属性，用 `@Deprecated`  注释旧属性，并委托其实现。
//    @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
//    var oldName: Int by this::newName
}

fun main() {
    val myClass = MyClass()
    // 注意: 'oldName: Int' 已经弃用，使用'newName'替代
//    myClass.oldName = 42
    println(myClass.newName) // 42
}

