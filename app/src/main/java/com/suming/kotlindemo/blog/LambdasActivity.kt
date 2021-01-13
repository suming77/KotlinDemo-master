package com.suming.kotlindemo.blog


import android.app.AlertDialog
import android.database.Observable
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/11/27 16:26
 * @类描述 {$TODO}高阶函数和Lambdas
 * Kotlin 函数是一级函数，这意味着它们可以存储在变量和数据结构中，作为参数传递给其他高阶函数，也可以从其他高阶函数返回。
 * 对于其他非函数值，你可以以任何可能的方式对函数进行操作。提供了一系列函数类型来表示函数和lambda表达式。
 */
typealias StringEmpty = (String) -> Unit

fun saulte() = println("fun saulte")

class LambdasActivity : AppCompatActivity() {

/*    tv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO
        }
    });*/

//   等价于

/*    tv.setOnClickListener(View.OnClickListener {
        //TODO
    })*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)

        /**
         * 18.lambda 表达式的作用域中访问变量
         * (1)在 Java 函数内部定义一个匿名内部类或者 lambda，内部类访问的局部变量必须是 `final` 修饰的，
         * 意味着在内部类内部或者 lambda 表达式的内部无法修改函数局部变量的值。
         *
         * （2）Kotlin 中在函数定义的内部类或 lambda，既可以访问 `final` 修饰的变量，也可以访问非 `final` 修饰的变量，
         * 意味着 lambda 内部是可以直接修改函数局部变量的值。
         */
        var count = 0//声明非final类型
        val countFinal = 0//声明final类型
        textView.setOnClickListener {
            println(count++)//访问并修改非final修饰的变量
            println(countFinal)//访问final修饰的变量，和Java一样
        }
        stringMapper("Android", { input ->
            input.length
        })

        stringMapper("Android") { input ->
            input.length
        }

        val items = arrayOf(1, 2, 3, 4, 5)
        //Lambdas是大括号括起来的代码块
        items.fold(0, { acc: Int, i: Int -> //当lambda 有参数时，参数在前面，然后是 `->` 符号
            print("acc == $acc | i == $i | ")
            val result = acc + i
            println("result == $result")
            //lambda 中的最后一个表达式被认定为是返回值
            result
        })

        //参数类型在lambda中如果能自动推断则可以省略
        val itemsStr = items.fold("Elements:", { acc, i -> acc + "" + i })

        //函数引用也可以用于高阶函数调用
        val products = items.fold(1, Int::times)


        val product1 = items.fold(1, { acc, e -> acc * e })
        val product2 = items.fold(1) { acc, e -> acc * e }//fold()函数的最后一个参数{ acc, e -> acc * e }被提到函数括号外面

        val intFunction: (Int) -> Int = IntTransformer()

        val result = { i: Int -> i * 2 } //推断出的类型是(Int) -> Int

        val trans = runTransformation(substringStr)
        println("trans == $trans")

        /**
         * 4.调用函数类型实例
         * 函数类型的值可以通过使用其 invoke(...) 操作符来调用：f.invoke(x) 或者 f(x)。invoke() 表示通过 函数变量 调用自身。
         */
        //String.plus()通过将该字符串与给定的其他对象的字符串表示形式链接起来而获得的字符串
        val stringPlus: (String, String) -> String = String::plus
        val intPlus: Int.(Int) -> Int = Int::plus //Int.plus()将另一个值添加到此值

        val str = stringPlus.invoke("<-", "->") //打印：<-->
        val str2 = stringPlus("Hello", "World") //打印：HelloWorld

        println("Invoke str == $str")
        println("Invoke str == $str2")

        val int1 = intPlus(1, 1) //打印：2
        val int2 = intPlus.invoke(2, 3) //打印：5


        val int3 = 4.intPlus(5) //打印：9

        println("Invoke int == $int1")
        println("Invoke int == $int2")
        println("Invoke int == $int3")

        /**
         * 10.Passing trailing lambdas
         */
        //check表示传入一个Int类型参数，返回一个Boolean类型返回值
        fun getResult(a: Int, check: (Int) -> Boolean): String {
            val result = if (check(a)) "Android" else "null"
            return result
        }

        //（1）如果一个函数的最后一个参数是一个函数，那么作为对应参数传递的 lambda 表达式可以放在圆括号 () 外：
        getResult(10, { num: Int -> num > 0 })

        fun getResult(check: (Int) -> Boolean) {}

        val ee = { num: Int -> num > 0 }
//
        getResult(10, { num: Int -> num > 0 })
        getResult(10) { num: Int -> num > 0 }
        //（2）如果 lambda 是被调用的函数的唯一一个参数，函数的圆括号 () 可以被完全省略：
        getResult { num: Int -> num > 0 }

        //（3） it 单个参数的隐式名称
        getResult(10) {
            it > 0
        }

        /**
         * 11.从lambda表达式返回值
         * 如果 lambda 表达式有返回值，则 lambda 主体内的最后一行表达式将被视为返回值返回，即lambda会将
         * 最后一条语句作为其返回值。如果使用限定的返回语法显式地从 lambda 返回一个值，则不会再以默认的最后一行返回值。
         */
        getResult(10) {//it 表示参数名称
            val result = it > 0
            result //lambda 表达式默认最后一行为返回值
        }

        val apple = getResult(10) {
            val result = it > 0
            return@getResult result //限定的返回语法指定返回值
            false
        }
        println("getResult == $apple")

        //这个习惯，连在括号外传递一个 lambda 表达式，允许链式风格的代码：
        val strs = arrayOf("sum", "java", "android", "kotlin")
        strs.filter { it.length == 5 }.sortedBy { it }.map { it.toUpperCase() }

        /**
         * 12.下划线用于未使用的变量（自1.1以来）
         */
        //如果参数未使用，用下划线 `_` 来代替它的名称
        val radioGroup = RadioGroup(this)
        //原型
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

        }

        //简化后，参数group未被使用，用下划线 `_` 来代替
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == 0) {
                //TODO
            }
        }

        /**
         * 13.匿名函数
         * 并非每个函数都需要一个名称，某些函数通过输入和输出更直接地进行标识，这些函数称为匿名函数。
         * 匿名函数都是函数字面量，都可以在不显式定义方法的时候提供提供具有同样形式，功能的实现。
         * 匿名函数可以明确指定返回类型，匿名函数与常规函数不同的是没有函数名。
         *
         * lambda 表达式中的 return 将从封闭函数返回，而匿名函数内部的 return 将从匿名函数本身返回。
         */
        val stringLengthFunc: (String) -> Int = { input ->
            input.length
        }
        val stringLength: Int = stringLengthFunc("Android")

        fun test(x: Int, y: Int): Int = x + y
        fun(x: Int, y: Int): Int = x + y
        fun(x: Int, y: Int): Int {
            return x + y
        }

        //返回符合条件的元素的列表
        val list = items.filter(fun(item) = item > 2)
        println("list == $list")

        /**
         * 14.闭包
         * 即函数包含函数，这里的函数指（Lambda表达式，匿名函数，局部函数，对象表达式等）。
         * 可以理解为：当一个作用域A位于另一个作用域B中时，A可以访问B的相关环境，所构成的环境称之为闭包。
         */
        var total = 0
        items.filter { it > 0 }.forEach {
            total += it//可以访问外部作用域中声明的变量
        }

        println("total == $total")//打印 total == 15

        /**
         * 15.带有接收器的函数字面量
         */
        val all: Int.(Int) -> Int = { other -> plus(other) }
        val all2 = fun Int.(other: Int): Int = this + other

        study {//带有接收器的lambda从这里开始
            body()//调用接收器对象上的方法
        }

        noParameter()

        hasParameter(4, 2)//打印为 6

        expressionParameter(2, hasParameter)//打印为 6

        println("表达式格式：${hasParameter(4, 2)} | ${expressionParameter(2, hasParameter)}")

        items.joinToString(separator = ",", prefix = "<", postfix = ">", transform = { "index$it" })

        /**
         * 16.使用typealias关键字给Lambda类型命名
         */
        val strEmpty: StringEmpty = {
            if (it.isEmpty()) {
                //TODO
            }
        }
        val strTrue: StringEmpty = {
            if (it.equals("true")) {
                //TODO
            }
        }

        /**
         * 17.Lambda表达式常用场景
         */
        //场景一： lambda 表达式与集合一起使用是最常见的场景，可以各种帅选、映射、变换操作符和对集合数据进行各种操作，
        //非常灵活，类似使用 RxJava 函数式编程，Kotlin 在语言层面无需增加额外库，就给你提供函数式编程API。
        val lists = arrayListOf("Java", "Android", "Kotlin")
        lists.filter {
            it.startsWith("K")
        }.map {
            "$it 是一门非常好的语言！"
        }.forEach {
            println(it)
        }

        //场景二：替代原有匿名内部类，但是注意只能替代单抽象方法的类。
        textView.setOnClickListener {
            //TODO
        }

        //（1）最常见的使用方式是类名+双冒号+成员（属性或函数）：
        val persons = arrayListOf(Person("Java", 20), Person("Android", 5))
        println(persons.maxBy({ p: Person -> p.age }))
        //使用成员引用的方式
        println(persons.maxBy(Person::age))//成员的引用类型和maxBy()传入Lambda表达式的一致

        //（2）省略类名，直接使用顶层函数：
        run({ saulte() })
        //使用成员引用简化后
        run(::saulte)

        //（3）如果 lambda 要委托给一个接收多个参数的函数，提供成员引用代替它将会非常方便：
        //有多个参数的 lambda
        val action = { person: Person, message: String -> sendEmail(person, message) }
        //使用成员引用代替
        val nextAction = ::sendEmail

        //调用
        nextAction(Person("name", 10), "msg")

        //（4）成员引用用于构造方法
        val getPerson = ::Person //创建的实例动作就保存成了值
        println("构造方法 == " + getPerson)//打印 Person(name=Kotlin, age=3)

        //（5）成员引用用于拓展函数
        val isChild = Person::isChild
        println("isChild == " + isChild(Person("Java", 20)))//打印 true

        //18.绑定引用
        val person = Person("Android", 24) //创建实例
//        val personAge = { person.age } //Kotlin1.1之前显式写出 lambda
        val personAge = person::age //Kotlin1.1之后可以使用绑定引用

        println("person: age == ${personAge()}")//打印 person: age == 24
    }

    private fun sendEmail(person: Person, message: String) {
        //TODO
    }


    // 场景三：定义 Kotlin 扩展函数或者说需要把某个操作或者函数当作值传入某个函数的时候。
    fun showDialog(
            content: String = "弹框", negativeText: String = "取消", positiveText: String = "确定",
            negativeAction: (() -> Unit)? = null, positiveAction: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(this)
                .setMessage(content)
                .setNegativeButton(negativeText) { _, _ ->
                    negativeAction?.invoke()
                }
                .setPositiveButton(positiveText) { _, _ ->
                    positiveAction?.invoke()
                }
                .setCancelable(true)
                .create()
                .show()
    }

    /**
     * 1.高阶函数
     * 高阶函数是指接收函数作为参数（或返回函数）的函数。
     */
    fun stringMapper(str: String, mapper: (String) -> Int): Int {
        // Invoke function
        return mapper(str)
    }

    /**
     * 2.函数式编程习语折叠
     * 它接受一个初始的累加器值和一个组合函数，并通过连续地将当前的累加器值与每个集合元素组合来构建它的返回值，替换这个叠加器
     */
    fun <T, R> Collection<T>.fold(
            initial: R,
            combine: (acc: R, nextElement: T) -> R
    ): R {
        var accumulator = initial
        for (element: T in this) {
            accumulator = combine(accumulator, element)
        }
        return accumulator
    }

    /**
     * 3.函数类型
     * (A, B) -> C:所有的函数类型有一个带括号的参数类型列表和一个返回类型。(A, B) -> C 表示一个函数类型，
     * 该类型表示具有类型 A 和 B 的两个参数并返回类型 C 的值的函数。参数类型列表可以是空的，如 () -> A。不能省略 Unit 返回类型；
     *
     * A.(B) -> C ：  函数类型可以有一个附加的接收类型，它在点符号 . 之前指定。
     * 类型 A.(B) -> C 表示可以在接收对象 A 调用 B 类型的参数，返回值为 C 类型的函数。带有接收器的函数文字通常与这些类型一起使用；
     *
     * 挂起函数 ：  挂起函数属性特性类型的函数，在表示法中有一个挂起修饰符。例如：suspend () -> Unit 或者 suspend A.(B) -> C。
     */


    /**
     * 4.使用实现函数类型作为接口的自定义类的实例
     */
    class IntTransformer : (Int) -> Int {
        //override operator fun invoke(num: Int): Int = TODO()
        override operator fun invoke(num: Int): Int {
            return num
        }
    }

    /**
     * 没有接收方的函数类型在默认情况下会被自动推断出来，即使使用扩展函数的引用初始化变量也是如此。如果要更改，请显式指定变量类型。
     */
    //String，Int传入的参数类型 后面的String表示返回值类型，times表示Int类型参数
    val substringStr: String.(Int) -> String = { times ->
        this.substring(0, times)//times为5
    }

    val twoParameters: (String, Int) -> String = substringStr

    //(A, B) -> C 所有的函数类型有一个带括号的参数类型列表和一个返回类型,
    //表示一个类型，该类型表示具有类型 A 和 B 的两个参数并返回类型 C 的值的函数。
    fun runTransformation(ss: (String, Int) -> String): String {
        return ss("Android", 5)
    }

    val trans = runTransformation(substringStr)// substringStr()函数作为参数传递给runTransformation()函数

    /**
     * 5.Lambda表达式
     * Lambda 表达式本质其实是匿名函数，因为底层的实现还是匿名函数来实现的，Lambda 的出现确实减少了代码量的编写，变得更加简洁。
     */
//      strMax(strings, { a, b -> a.length > b.length })

    fun compare(a: String, b: String): Boolean = a.length < b.length

    /**
     * 6.Lambda表达式语法
     * 显式定义sum的类型为(Int, Int) -> Int的函数类型：
     * val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
     * lambda 表达式总是在大括号 {} 包裹着，完整语法形式的参数声明在大括号内，并有可以省略的参数类型，
     * body 在 -> 符号之后。如果 lambda 的推断返回类型不是 Unit，则 lambda 主体内的最后一个（或者可能是单个）表达式将被默认视为返回值。
     */
    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
    val sum2 = { x: Int, y: Int -> x + y }

    /**
     * 7.无参数语法
     * val/var <变量名> = { …… }
     */
//    fun noParameter() {
//        println("无参数lambda")
//    }

    val noParameter = { println("无参数lambda") }


    /**
     * 8.有参数语法
     * val/var <变量名> : (<参数类型>，<参数类型>，...) -> 返回值类型 = { 参数，参数，... -> 操作参数的代码 }
     * 等价于：
     * val/var <变量名> = { 参数:<参数类型>，参数:<参数类型>，... -> 操作参数的代码 }
     */
//    fun hasParameter(a: Int, b: Int): Int {
//        return a + b
//    }

    val hasParameter: (Int, Int) -> Int = { a, b -> a + b }

    //简化后
    val hasParameter2 = { a: Int, b: Int -> a + b }


    /**
     * 9.lambda表达式作为函数中的参数时
     * fun test(arg : Int, <参数名> : (参数 : 类型, 参数 : 类型, ... ) -> 表达式返回类型) {……}
     */
    //lambda表达式，numB只提供了参数类型和返回类型，调用时需要写出它的具体实现
    fun expressionParameter(numA: Int, numB: Int): Int {
        return numA * numB
    }

    fun expressionParameter(numA: Int, numB: (a: Int, b: Int) -> Int): Int {
        return numA * numB.invoke(3, 4) //a * ( 2 + 4 )
    }

    //lambda表达式，这里作为上面函数的参数使用
    val expression: (Int, Int) -> Int = { a, b -> a + b }

    class Student {
        fun body() {}
    }

    fun study(init: Student.() -> Unit): Student {
        val student = Student()//创建接收器对象
        student.init()//将接收器对象传递给lambda
        return student
    }

    data class Person(val name: String, val age: Int) {

//        constructor(name: String, age: Int, numA: Int) : this(name, age)
    }
}

// 这是Person的一个扩展函数，判断是否成年
fun LambdasActivity.Person.isChild() = age > 18