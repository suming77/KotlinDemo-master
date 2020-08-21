package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/19 10:54
 * @类描述 {$TODO} 数据类
 *
 * 我们经常创建主要用于保存接口返回的Json数据的类。
 * 在这样的类中，只包含了一些需要的数据，以及处理这些数据编写的方法。
 * 在 Kotiln 中，这被称为一个数据类，并使用 data 修饰。
 */
class DataClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 8.`componentN()` 函数是编译器会自动地从主构造函数中根据所声明的属性派生的函数，并对应属性的声明顺序。
         * N表示主构造函数中参数个数，比如 `component1()`表示第一个参数、`component2()`表示第二个参数，依次类推。
         * 注意，只能是主构造函数中的属性。
         */
        var user0 = User("Kotlin", 23)
        println("User：name == ${user0.name} | age == ${user0.age}")
        println("componentN：component1 == ${user0.component1()} | component2 == ${user0.component2()}")

        var user = User()
        println("User：name == ${user.name} | age == ${user.age}")

        var person = Person()
        var person2 = Person()
        person.age = 10
        person2.age = 20
        person.component1()
        println("Person：name == ${person.name} | age == ${person.age}")
        println("Person2：name == ${person2.name} | age == ${person2.age}")

        var oldUser = user.copy(age = 500)
        println("copy()：name == ${oldUser.name} | age == ${oldUser.age}")

        /**
         * 5.解构声明时把一个对象分解成许多变量，变量可以独立使用。
         */
        val jack = User(name = "Jack", age = 99)
        val (name, age) = jack
//        println("析构声明：name == $name | age == $age")
        print("析构声明：name == $name | age == $age")

        println("标准库：Pair == $pair | Triple == $triple")
        println("Pair.toList  == ${pair.toList()}")
        println("Triple.toList  == ${triple.toList()}")


        var dataUser = User("数据类", 10)
        var student = Student("普通类", 20)

        println("toString(): dataUser == $dataUser | student == $student")
        var user1 = User("数据类", 10)
        var user2 = User("数据类", 10)

        var student1 = Student("普通类", 20)
        var student2 = Student("普通类", 20)

        println("数据类:equals == ${user1.equals(user2)} | user1 == ${user1.hashCode()} | user2 == ${user2.hashCode()}")
        println("普通类:equals == ${student1.equals(student2)} | student1 == ${student1.hashCode()} | student2 == ${student2.hashCode()}")
    }

    /**
     *7.通常情况下，我们需要复制一个对象，改变它的一些属性，但保持其余的不变。这就是 `copy()` 函数作用，
     * 目的是便于数据复制。可以类比于 Java 中的 `clone()` 方法：
     */
    fun copy(name: String, age: Int) = User(name, age)

    /**
     * 3.注意：因为数据类的构造函数是必须至少有一个参数的，如果需要有一个无参数的构造函数，则必须为所有属性指定默认值构造函数，那么就可以使用无参构造函数了。
     */
    //1.数据类构造函数中必须至少有一个参数，并且必须是使用 var 或者 val修饰。如果没有结构体时，大括号 {} 可以省略。
    //    data class User(val name: String, val age: Int)
    data class User(val name: String = "som", val age: Int = 0)

    /**
     * 4.注意，编译器仅为自动生成的函数使用主构造函数中定义属性。你也可以在类主体中声明属性：
     */
    data class Person(val name: String = "Android") {
        var age: Int = 0
    }

    /**
     * 6.标准库提供了类 `Pair` 和类 `Triple`
     */
    val pair = Pair(1, 2)        // 实例

    val triple = Triple("一", "二", "三")

    class Student(val name: String, val age: Int)//普通类


    /**
     * 2.注意事项
     *（1）编译器会自动地从主构造函数中根据所声明的属性派生下列函数：
     *- `equals()`/`hashCode() `；
     *- `toString()` 格式如：`User(name="jimi", age=20)`；
     *- 自动生成 `component1()`、`component2()`…`componentN()` 函数并对应属性的声明顺序；
     *- `copy()` 函数（下面会讲解到）。

     *（2）数据类为了确保生成代码的一致性和有意义的行为，必须满足以下条件：
     *- 主构造函数至少有一个参数；
     *- 所有主构造函数的参数需要标记为 `var` 或者 `val`；
     *- 数据类不能声明为  `abstract` 、`open` 、`sealed`、`inner`；
     *- Kotlin1.1之前，数据类只能实现接口，不能继承其他类。

     *（3）此外，在成员继承方面，成员生成遵循以下规则：
     *- 如果数据类中有`equals()`、`hashCode()` 或 `toString()` 的明确定义，或者在超类中有 `final` 实现，那么就不会生成这些函数，而使用现有的实现；
     *- 如果一个超类型是 `open` 且返回兼容类型的 `componentN()` 函数，则为数据类生成相应的函数并覆盖超类型的函数。如果超类型的函数由于不兼容的签名或 `final` 不能被覆盖，则会报错；
     *- 从已经具有相匹配签名的 `copy()` 函数的类型派生数据类，在 Kotlin 1.2中是不赞成的，在 Kotlin 1.3中是禁止的；
     *- 不允许为 `componentN()` 和 `copy()` 函数提供显式实现。
     */

}