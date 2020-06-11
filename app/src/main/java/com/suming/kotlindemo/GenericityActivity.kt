package com.suming.kotlindemo

import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/9 18:30
 * @类描述 ${TODO}泛型
 */
class GenericityActivity : AppCompatActivity() {
    //1.泛型，即参数化类型，将类型参数化，可以用在类，接口，方法上
    //与java一样，kotlin也提供泛型，为类型安全提供保证，消除类型强转的烦恼
    class Box<T>(t: T) {
        var value = t
    }

    //创建类实例时需要指定类型参数
    val box: Box<Int> = Box<Int>(1)
    //或者 编译器会进行类型推断，1是Int类型，所以编译器知道我们说的是Box<Int>
    val box2 = Box(1)

    val box3 = Box("runoob")

    //定义泛型类型变量，可以完整地写明类型参数，如果编译器可以自动推定类型参数，也可以省略类型参数
    //kotlin泛型函数的声明和java相同，泛型参数要放在函数名前面

    fun <T> boxIn(value: T) = Box(value)
    //以下都是合法
    val box4 = boxIn(1)
    val box5 = boxIn<String>("122333")

    //在调用泛型函数时，如果可以推断出类型参数，可以省略类型参数
    //例子：

    fun <T> doPrintln(content: T) {
        when (content) {
            is Int -> println("整型数字：$content")
            is String -> println("字符串转为大写：${content.toUpperCase()}")
            else -> println("T不是整形也不是字符串")
        }
    }

    fun main(args: Array<String>) {
        val age = 23
        val name = "名字"
        val bool = true
        doPrintln(age)//整型数字：23
        doPrintln(name)//字符串转为大写：名字
        doPrintln(bool)//T不是整形也不是字符串
    }


    //2.泛型约束
    //使用泛型约束来设定一个给定参数允许使用的类型，kotlin中对泛型的类型上限进行约束，最常见的约束是上界(upper bound)
    fun <T : Comparable<T>> sort(list: List<T>) {

    }

    //Comparable的子类型可以代替T,例如：
    fun main1(args: Array<String>) {
        sort(listOf(1, 2, 3))//OK，Int是comparable<Int>的子类型
//        sort(listOf(HashMap<Int,String>()))错误，HashMap<Int,String>()不是comparable<HashMap<Int,String>()>的子类
    }

    //默认上界是Any?
    //对于多个上界的约束条件，可以用where语句
    fun <T> copyWhenGreater(list: List<T>, thresold: T): List<String>
            where T : CharSequence,
                  T : Comparable<T> {
        return list.filter { it > thresold }.map { it.toString() }
    }


    //3.型变
    //kotlin中没有通配符类型，他有两个其他东西：声明处变形declaration-site variance，类型投影type projections
    //声明处的类型变异使用协变注解修饰符：in， out， 消费者in， 生产者out

    //使用out使一个类型参数协变，协变类型参数只能用作输出，可以作为返回值类型但是无法作为入参类型
    //定义一个支持协变得类：
    class Runoob<out A>(val a: A) {
        fun foo(): A {
            return a
        }
    }

    fun main2(args: Array<String>) {
        var strCo: Runoob<String> = Runoob("a")
        var anyCo: Runoob<Any> = Runoob("b")
        anyCo = strCo
        println(anyCo.foo())//输出结果：a
    }

    //in使得一个类型参数逆变，逆变类型参数只能用作输入，可以用作入参类型但是无法作为返回值类型
    //定义一个支持以逆变的类：
    class RunoobIn<in A>(a: A) {
        fun foo(a: A) {

        }
    }

    fun mian(args: Array<String>) {
        var strCo: RunoobIn<String> = RunoobIn("a")
        var anyCo: RunoobIn<Any> = RunoobIn("b")
        strCo = anyCo
    }


    //4.星号投射
    //有时候你想表示你并不知道类型参数的任何信息，但是仍然希望能够安全地使用它，这里的安全使用是指对泛型类型定义一个类型投射
    //要求这个泛型类型的所有实体实例，都是这个投射的子类型，对于这个问题，kotlin提供了一种语法，星号投射
    //1.假如类型定义为Foo<out T>,其中T是一个协变类型的参数,上界(upper bound)是TUpper,Foo<>等价于FOo<out TUpper>
    //它表示，当T未知时，你可以安全地从Foo<>读取TUpper的类型值

    //2.假如类型定义为Foo<in T>,其中T是反向协变类型的参数，Foo<>等价于Foo<inNothing>,它表示，当T未知时你
    // 不能安全地向Foo<>写入任何东西

    //3.假如类型定义为Foo<T>,其中T是一个协变的参数类型，上界（upper bound）是TUpper，对于读取值得场合，Foo<*>
    // 等价于Foo<out TUpper>,对于写入值得场合，等价于Foo<in Nothing>

    //如果一个泛型类型中存在多个类型参数，那么每个类型参数都可以单独的投射，如果类型定义为interface Function<in T,out U>
    //那么可以出现以下几种型号投射：
    //1.Function<*,String>,代表Function<in Nothing,String>

    //2.Function<Int,*>,代表Function<Int,out Any?>

    //3.Function<*,*>,代表Function<in Nothing,out Any?>
    //注意：星号投射与java的原生类型（raw type）非常相似，但可以安全使用

    //其实*代指所有类型，相当于Any?
    class A<T>(val t: T, val t2: T, val t3: T)

    class Apple(name: String)

    fun main3(args: Array<String>) {
        val a1: A<*> = A(12, "string", Apple("pingguo"))
        val a2: A<Any?> = A(14, "string2", Apple("pingguo2"))//等价于a1
        val apple = a1.t3//参数类型为Any?
        println(apple)

        val apple2 = apple as Apple//强转成Apple类
//        println(apple2.name)

        //使用数组
        val l: ArrayList<*> = arrayListOf("string", 1, 1.2f, Apple("苹果"))
        for (item in l) {
            println(item)
        }
    }
}