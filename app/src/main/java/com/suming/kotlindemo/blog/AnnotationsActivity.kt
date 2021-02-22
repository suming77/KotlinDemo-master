@file:JvmName("foo")

package com.suming.kotlindemo.blog

import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.reflect.KClass

/**
 * @创建者 mingyan.su
 * @创建时间 2021/01/14 15:11
 * @类描述 {$TODO}注解
 * 一个注解允许你把额外的元数据关联到一个声明上，然后元数据就可以被相关的源代码工具访问，
 * 通过编译好的类文件或是在运行时，取决于这个注解是如何配置的。
 */
const val TIMEOUT = 100L

class AnnotationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remove(0)
    }

    /**
     * 1.声明注解
     * 要声明注解，**在类 Class 关键字前面加上注解修饰符 `annotation`。
     * 因为注解类只是用来定义关联声明和表达式的元数据的结构**，它们不能包含任何代码。
     * 所以，编译器禁止为一个注解类指定主体。
     */
    //声明注解，极简形式
    annotation class Suspendable

    /**
     * 对于拥有参数的注解来说，在注解类的主构造方法中声明这些参数
     * （使用的是常规的主构造函数的声明语法，对于一个注解类的所有参数来说，`val` 关键字是必须强制的）
     */
    annotation class Animal(val name: String)

    /**
     * 注解一个类的主构造函数，你需要在构造函数声明中添加 `constructor` 关键字
     */
    class Mouse @Animal("m") constructor(val name: String) {}

    /**
     * 注解也可以在 lambdas 上使用，他们将被应用到 `invoke()` 方法中，lambdas 的主体将生成在该方法中
     */
    val light = @Suspendable {
//        Fiber.sleep(10)
    }

    /**
     * 如果有多个注解与同一个目标，可以通过 `@set:[]` 集合的形式将所有注解放在括号内来避免重复目标：
     */
    class Teacher {
        @set:[Suspendable Animal("no")]
        var name: String = ""
    }

    /**
     * 注意：在 Kotlin 中应用注解就是常规的构造方法调用。可以使用命名实参语法让实参的名称变成显式的。
     * 如果你需要把 Java 中的注解声明到 Kotlin 元素上，必须对所有实参使用命名实参语法。
     * Java 中由于没有定义编写的注解的参数顺序，所以不能使用常规函数调用语法来传递参数。
     */
    @ExampleActivity.AnnNormal(age = 10, value = "Kotlin")
    class Chicken {}


    /**
     * 2.应用注解
     * 要引用一个注解，以 `@` 字符作为（注解）名字的前缀，并放在**你要注解的声明最前面**
     * 注解构造函数参数类型只能拥有如下几种类型：**基本数据类型，字符串，枚举，类引用，其他的注解类，以及前面这些类型的数组**
     * 类作为实参：`类名::class`；注解作为实参：去掉注解名称前面的 @ 符号；数组作为实参：使用 `arrayOf()` 函数。
     */

    //不能引用任意的属性作为实参，否则需要使用  `const` 修饰。
    @Test(timeout = TIMEOUT)
    fun testMethod() {
        Assert.assertTrue(true)
    }

    @Deprecated("Use removeAt(index) instead", ReplaceWith("removeAt(index)"))
    fun remove(index: Int) {
        //TODO
    }

    fun removeAt(index: Int) {
        //TODO
    }


    /**
     * 3.注解目标
     * 使用点目标声明被用来说明要注解的元素,点目标放在@符号和注解名称之间，并用冒号`:`和注解名称隔开
     * 声明被直接对应到属性上的注解:file、property、field、get、set、receiver、param、setparam、delegate
     */

    //@Rule注解应用到属性 getter 中，注解的是 getter 不是属性
    @get:Rule
    val folder = TemporaryFolder()

    @Test
    fun testTemporaryFolder() {
        val file = folder.newFile("myFile.txt")
    }

    fun suppressMethod(list: List<*>) {
        /* 代码黄色警告Unchecked_cast：List<*> to List<String> */
        val strings = list as List<String>

        //警告消失，被抑制
        @Suppress("UNCHECKED_CAST")//names 表示要抑制的编译器诊断程序的名称
        val strings2 = list as List<String>
    }

    /**
     * 4.元注解:控制如何处理一个注解
     * 可以应用到注解类上的注解被称为元注解
     */

    @Target(AnnotationTarget.PROPERTY)
    annotation class Person

    /* 元注解 */
    @Target(AnnotationTarget.CLASS)
    annotation class AnnBinding

    @AnnBinding//注解应用元注解
    annotation class MyBanding

    /**
     * 5.注解参数
     * 能够引用类作为声明的元数据。通过声明一个拥有类引用作为形参的注解来实现
     */

    //使用类做注解参数
    annotation class DeserializeInterface(val cla: KClass<out Any>)

    interface Company {
        val name: String
    }

    data class CompanyImpl(override val name: String) : Company

    data class Student2(
            val name: String,
            @DeserializeInterface(CompanyImpl::class) val company: Company
    )

    //使用泛型类做注解参数
    interface ValueSerializer<T> {
        fun toJsonValue(value: T): Any?
        fun fromJsonValue(jsonValue: Any): T?
    }

    data class Rabbit(
            val name: String,
            @CustomSerializer(DataSerializer::class) val data: ContactsContract.Data
    )

    annotation class CustomSerializer(
            val cla: KClass<out ValueSerializer<*>>
    )

    class DataSerializer<T> : ValueSerializer<T> {
        override fun toJsonValue(value: T): Any? {
            TODO("Not yet implemented")
        }

        override fun fromJsonValue(jsonValue: Any): T? {
            TODO("Not yet implemented")
        }
    }

}

