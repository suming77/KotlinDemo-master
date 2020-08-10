package com.suming.kotlindemo.blog

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/04 11:46
 * @类描述 {$TODO}属性与字段
 */

//10.const与val的区别：const val与 val 都会生成对应 Java 的static final修饰的字段，而 const val会以 public 修饰，
// 而 val 会以 private 修饰。同时，编译器还会为 val 字段生成 get 方法，以便外部访问。
//下面代码属于kotlin代码，位于kotlin文件Person.kt中，属于top-level级别
const val NAME = "HelloWord"
val age = 20

class Person {
    //1.声明属性:Kotlin 类中的属性可以使用var关键字声明为可变，也可以使用val关键字声明为只读
    val id: String = "0" //不可变，值为0
    var nameA: String? = "Android" //可变，允许为null
    var age: Int = 22 //可变，非空类型

    //    val id: String = "0"
    //用val修饰时，用getter()函数属性指定其他值时可以不赋默认值，但是不能有setter()函数
    val code: String
        get() = "0"

    //2.Getter()与Setter()完整语法
    //用var修饰时，必须为其赋初始化值，即使有getter()也必须初始化，不过获取的数值是getter()返回的值
    var name2: String? = "Android"
        get() = field
        set(value) {
            field = value//value是setter()方法参数值，field是属性本身
        }

    var sex: String = ""
        get() = "女"
        set(value) {
            field = value
        }

    //3.从初始化器(或者getter返回类型)推断出属性的类型
    //var weight: Int?//报错,需要初始化,默认实现了getter和setter方法

    var height = 172 //类型为Int,默认实现了getter和setter方法

    val type: Int?//报错,需要初始化,默认实现了getter方法

    val cat = 10//类型为Int,默认实现getter方法

    init {//初始化属性
        type = 0
    }

    //4.val修饰的属性的getter()函数自定义
    //使用了val修饰的属性不能有setter()方法；
    //属性默认实现了getter()和setter()方法，如果不重写则可以省略。

    //color根据条件返回值
    val color = 0
        get() = if (field > 0) 100 else -1

    //isEmpty属性是判断color是否等于0
    val isEmpty get() = this.color == 0

    //5.var修饰的属性的getter()和setter()函数自定义
    var hair: String = ""
        get() = field //可以省略，默认的实现方式
        set(value) {
            field = if (value.isNotEmpty()) value else ""
        }

    var nose: String = ""
        get() = "Kotlin"//值一直是Kotlin，不会改变
        set(value) {
            field = if (value.isNotEmpty()) value else ""
        }

    //6.可见性：如果你需要改变访问器的可见性或者注释它，但不需要改变默认实现，你可以定义访问器而不定义它的主体。
    val tea: Int = 0
    //private set  报错，val修饰的属性不能有setter

    var soup: String = "Java"
    //@Inject set   用Inject注解去实现setter()

    var dish: String = "Android"
    //private get   报错，不能有getter()访问器的可见性

    var meal = "Kotlin"
        private set   //setter访问器私有化，并且它拥有kotlin的默认实现

    //7.后备字段（Backing Fields）：如果属性至少使用一个访问器的默认实现，或者自定义访问器通过field标识符引用该属性，则将生成该属性支持的字段
    var name: String = "" //注意：初始化器直接分配后备字段
    //get() = "HelloWord"
    //get() = name//为name定义了get方法，这里返回name
//        set(value) {//为name定义了set方法
//            name = value//为name赋值一个新值，即value
//        }

        get() = field //直接返回field
        set(value) {
            field = value//直接将value赋值给field
        }

    //8.后备属性（Backing Properties）：访问私有属性的get()和set()方法，会被编译器优化成直接访问其实际字段，不会引入函数调用的开销。
    private var _table: Map<String, Int>? = null//后备属性
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap()//初始化
            }
            //如果_table不为空则返回_table，否则抛出异常
            return _table ?: throw AssertionError("Set to null by another thread")
        }

    //9.编译时常量
    //const val CONST_TYPE_A = 0 编译错误，这里没有违背只能使用val修饰，但是这里的属性不属于top-level级别的，也不属于object里面的

    object Instance { //这里的Instance使用了object关键字修饰，表示Instance是个单例，Kotlin其实已经为我们内置了单例，无需向 Java那样手写单例
        //const var CONST_TYPE_B = 0 编译错误，var修饰的变量无法使用const修饰
        const val CONST_TYPE_C = 0 //正确，属于object类
    }

}

const val CONST_STR = "" //正确，属于top-level级别的成员
//const val CONST_USER = User() //错误，const只能修饰基本类型以及String类型的值，Point是对象

//这些属性还可以在注释中使用：
const val CONST_DEPRECATED: String = "This subsystem is deprecated"

@Deprecated(CONST_DEPRECATED)
fun foo() {

}