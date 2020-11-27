package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/10/22 15:34
 * @类描述 {$TODO}内联类
 *
 * 有时候，业务逻辑需要围绕某种类型创建包装器。但是，由于额外的堆分配，它引入了运行时开销。
 * 此外，如果被包装的类型是原语类型，性能损失会很严重，因为原语类型通常由运行时进行了大量优化，
 * 而它们的包装器没有得到任何特殊处理。
 */
class InlineClassesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val securePassword = Password("Don't try this in production")

        val name = People("Kotlin")
        name.greet()
        println("name长度 == ${name.length}")

        val student = Student("Android")
        println(student.prettyPrint())//让然作为静态方法调用

        val our = Our(42)
        asInline(our)//未装箱：作为Our本身使用
        asGeneric(our)//已装箱:用作通用类型T
        asInterface(our)//已装箱:用作类型IA
        asNullable(our)//已装箱:用作"Our?",这是与Our不同的

        //下面，"our"首先被装箱(传递给"id"时)，然后是未被装箱(从"id"返回时)
        //最后，"C"包含无框表示(只有"42")，比如"our"
        val o = id(our)


        val userName: UserName = "Jack"
        val password: PassWord = "12346"
        authenticate(userName, password)

        val userName1: UserName1 = UserName1("Jack")
        val password1: PassWord1 = PassWord1("12346")
        //authenticate1(password1, userName1) 参数类型不匹配

        val hous = Hours(24)
//        val hous = 24

        Hours(24).toMinutes()
    }
}

inline class Hours(private val value: Int) {
    fun toMinutes() = Minutes(value * 60)
}

fun wait(hours: Hours) {}
//void wait(int hours) {}

class Email {}
class Minutes(val value: Int) {}

interface MailScheduler {
    fun sendEmail(email: Email, delay: Int)
    fun sendEmail(email: Email, delay: Minutes)
}

/**
 * 1.内联类必须在主构造函数中初始化一个属性。在运行时，内联类的实例将使用这个单独的属性来表示：
 * 注意：内联类只有在 Kotlin1.3 之后才可用，目前处于开始阶段。
 */
inline class Password(val value: String)

//inline class Password2(var value: String)
//inline class Password3(val value: Int, val name: String)

//内联类必须有且有一个构造函数参数，这个参数可以是私有 private 的，但是构造函数必须是公有的 public：
//inline class Password4 private constructor(val value: String)
inline class Password5 constructor(private val value: String)

const val MINUTES_PER_HOUR = 60

/**
 * 2.内联类的成员
 * 内联类的成员有一些限制：
 * - 内联类不能有init块；
 * - 内联类属性不能有后备字段；
 * - 内联类只能拥有简单的可以计算属性（没有延迟初始化、委托属性）
 */

/**
 * 内联类的内部是允许成员属性的，只要它们仅基于构造器中那个基础值计算，或者从可以静态解释的某个值或对象计算，来自单例，顶级对象，常量等。
 */
inline class People(val name: String) {
    val length: Int
        //属性getter作为静态方法调用
        get() = name.length

    val minutes get() = name.length * MINUTES_PER_HOUR

    fun greet() {//作为静态方法调用
        println("People == $name")
    }
}

/**
 * 3.内联类可以实现接口，而不是继承基类：
 *  禁止内联类参加与类层次结构，这意味着内联类不能拓展其他类，必须是 final 的。
 */
interface Printable {
    fun prettyPrint(): String
}

inline class Student(val name: String) : Printable {
    override fun prettyPrint(): String = "内联类继承接口：$name"
}


/**
 * 4 内联类不允许类继承，即不允许继承其他类，也不允许被其他类继承。
 */
open class Animal

//inline class Chicken(val name: String) : Animal() {} // compile error，内联类不允许类继承
//open inline class Tiger(val name: String) {} // compile error，内联类只能是final的


/**
 * 5 内联类必须在顶层声明，嵌套、内部类不能内联的。
 */
class Outer {
    //inline class Inner() {} // compile error，内联类只允许在顶级声明
}

/**
 * 6 目前，也不支持内联类枚举。
 */
//inline enum class Orientation {
//    NORTH,
//    SOUTH,
//    EAST,
//    WEST
//}

/**
 * 7.内联类的表示
 * 在生成的代码中，Kotlin 编译器为每一个内联类保留一个包装器。内联类实例可以在运行时表示为包装器或底层类型。
 * 这类似于 Int 可以表示为原语 int 或 Integer。
 *
 * Kotlin 编译器更喜欢使用底层类型而不是包装器来产生性能最好的优化代码。然而，有时有必要保留包装器。
 * 根据经验，只要内联类被用作另一种类型，它们就会被装箱。
 */
interface IA

inline class Our(val i: Int) : IA

fun asInline(our: Our) {}
fun <T> asGeneric(t: T) {}
fun asInterface(i: IA) {}
fun asNullable(our: Our?) {}

fun <T> id(x: T): T = x

/**
 * 8.由于内联类被编译成它们的底层类型，它可能会导致各种模糊的错误，例如平台签名冲突：
 * 为了减轻这类问题，通过在函数名中添加一些稳定的 hashcode 来破坏使用内联类的函数。
 * 因此 fun compute(x: UInt) 将表示为 public final void compute-<hashcode>(int x)，这样就解决了冲突问题。
 */
inline class UInt(val i: Int)

//在JVM上表示为"public final void compute(int x)"
fun compute(x: Int) {}

//在JVM上也表示为"public final void compute(int x)"
fun compute(x: UInt) {}

/**
 * 9.内联类与类型别名
 * 咋一看，内联类可能看起来非常类似于类型别名。实际上，两者似乎都引入了一种新类型，并且在运行时表示为底层类型。
 * 然而，关键的区别是类型别名与它们的基础类型分配兼容（以及与具有相同基础类型的其他类型别名分配兼容），而内联类则不是。
 * 换句话说，内联类引入了一个真正的新类型，而类型别名只是为现有类型引入了一个替代名称(别名)：
 */
typealias UserName = String
typealias PassWord = String

fun validate(name: UserName) {
    if (name.length > 5) {
        println("UserName $name is too short.")
    }
}

fun authenticate(userName: UserName, password: PassWord) {}


inline class UserName1(val value: String)
inline class PassWord1(val value: String)

fun validate1(name: UserName1) {
    if (name.value.length > 5) {
        println("UserName ${name.value.length} is too short.")
    }
}

fun authenticate1(userName: UserName1, password: PassWord1) {}




