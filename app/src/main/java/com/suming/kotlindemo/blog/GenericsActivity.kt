package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/24 11:44
 * @类描述 {$TODO}泛型
 *
 * 在 Java 中，其实所谓的泛型就是类型的参数化。如果方法有入参，那么这些入参面前往往会有类型，这个类型就是修饰参数所用。
 * 假如我们在创建类型的时候，也为其指定了参数，这个参数又是个类型，这种我们就称为泛型。
 */
class GenericsActivity : AppCompatActivity() {
    private val TAG = "GenericsActivity"

    /**
     * - out ：<font color=#86CA5E>型变注释，使参数类型协变。由于它是在类型参数的声明侧（如 MyList< out T>）所以称为声明点变量，
     * 这与 Java 的协变< ? extends T>类似，在 Java 中，使用类型中的通配符使类型协变，适用于生产者场景；
     *
     * - in：<font color=#86CA5E>型变注释，声明点变量，与 out 相反，使参数类型逆变。
     * 与 Java 的逆变<? super T>类似，它只能被消费，不能被生产，适用于消费者场景。
     *
     * 协变和逆变主要用来描述类型转换后的继承关系。
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var people: People<String> = People("Kotlin")
        var people = People("Kotlin")

        val ints: kotlin.Array<Int> = arrayOf(1, 2, 3)
        val anys: kotlin.Array<Any> = kotlin.Array<Any>(3) { "" }

        copy(ints, anys) //类型为Array<Int>，但应为Array<Any>


        val chars: kotlin.Array<CharSequence> = arrayOf("1", "2", "3")


        fill(chars, "1") //类型为Array<Int>，但应为Array<Any>

        val list = signleList(0)

        sort(listOf(1, 2, 3))//Int是Comparable<Int>的子类型
        //sort(listOf(HashMap<Int, String>()))//报错，HashMap<Int, String>()不是Comparable<HashMap<Int, String>>的子类型

        var whenList = copyWhenGreater(listOf("一", "二", "三"), "三")
        Log.e(TAG, "whenList == $whenList")

        /**
         * 5.星状投影
         * 有些时候，我们并不知道类型参数到底是什么，但是我们依然想安全的使用这些类型参数，该怎么办？
         * 正式基于上面的考虑，kotlin为我们提供了星号映射，其修饰符为*。
         *
         * Kotlin 为此提供了星状投影语法：
         *
         * - Foo< out T : TUpper>：  其中 T 是具有上限 TUpper 的协变型参数，Foo<*> 等效于 Foo<out TUpper>。
         *      这意味着当 T 未知时，你可以安全地从 Foo<*> 读取TUpper 的值；
         * - Foo< in T >：  其中 T 是一个协变类型参数，Foo<*> 等效于 Foo<in Nothing>。这意味着当 T 未知时，你不能以安全的方式写任何东西给Foo<*> ；
         * - Foo< T : TUpper >：  其中 T 是上限类型为 TUpper 的不变类型参数，Foo<*> 等效于Foo<out TUpper>读取值，以及 Foo<in Nothing> 写入值。
         *
         * 如果泛型类型有多个类型参数，则每个参数都可以独立投影。例如，如果将类型声明为接口 Function<in T, out U> ，我们可以想象以下星像投影：
         *
         * - Function<*, String>表示 Function<in Nothing, String>；
         * - Function<Int, *> 表示 Function<Int, out Any?>；
         * - Function<*, *> 表示 Function<in Nothing, out Any?>。
         */
        val student: Student<*, String> = Student(1, "hello")//*代替了in修饰的类型，表示In Nothing
        val student2: Student<Number, *> = Student(1, "hello")//*代替了out修饰的类型，表示out Any?
        val student3: Student<*, *> = Student(1, "hello")

    }

    /**
     * 1.声明泛型
     */
    class People<T>(arg: T) {
        var name = arg
    }

    //Java
//    List<String> strings = new ArrayList<>();
//    List<Object> objects = strings;//这里编译错误避免了下面的出现的运行异常
//    objects.add(10);//将一个字符串放入字符串列表中
//    String a = strings.get(0);//不能整数转换为字符串


    class Student<in T, out E>(t: T, val e: E) {

    }

    /**
     * 1.协变：协变就是只要类型参数具有继承关系就认为整个泛型类型具有继承关系，比如：String 继承自 Any，
     * 那么就可以认为 MyList< String > 是 MyList< Any > 的子类型，于是 MyList< String > 类型变量赋值给
     * MyList< Any > 类型，这就是协变。
     */
    //T 使用 out 修饰符修饰
    interface Source<out T> {
        fun shareT(): T
    }

    fun invite(strs: Source<String>) {
        val any: Source<Any> = strs //这是可以的，因为 T 是一个 out 类型参数
    }

    /**
     * 2.逆变:如果 String 继承自 Any，那么就可以认为 MyList< String > 是 MyList< Any > 的父类型，
     * 可以允许父类型变量赋值给子类型变量，比如上面的 Comparable< Number > 类型变量 c 赋值给
     * Comparable< Double > 类型变量 y，这就是协变。
     */
    //T 使用 in 修饰符修饰
//    interface Comparable<in T> {
//        operator fun compareTo(other: T): Int
//    }

    fun demo(c: Comparable<Number>) {
        c.compareTo(1.0) //1.0是Double类型，它是Number的子类型
        //因此，我们可以将c分配给Comparable<Double>类型的变量
        val y: Comparable<Double> = c
    }


    class Array<T>(val size: Int) {
//        fun get(index: Int): T {
//            //TODO
//        }

        fun set(index: Int, value: Int) {
            //TODO
        }
    }

    /**
     * 3.这里将from声明为了<out Any> 泛型协变，类似 Java 的协变<? extends T>
     *  能接收Any或者Any子类类型，表示不可写，只可读。
     */
    fun copy(from: kotlin.Array<out Any>, to: kotlin.Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    /**
     * 4.dest使用了in修饰，表示可写，类似于java中的<? super T>
     *  接收String类型及其超类型。
     */
    fun fill(dest: kotlin.Array<in String>, value: String) {
        for (i in dest.indices)
            dest[i] = value
    }

    /**
     * 6.泛型函数
     * 不仅类可以有类型参数，函数也可以有，类型参数放在函数名称的前面.
     * 要调用泛型函数，需要在函数名称后的调用站点上指定类型参数.
     */
    fun <T> T.basic(): String {
        return ""
    }

    fun <T> signleList(item: T): List<T> {
        return listOf()
    }

    /**
     * 7.泛型约束
     * 最常见的约束是与 Java 的 `extends` 关键字相对应的上限。
     * 冒号 `:` 后面指定的类型是上限：只能将 `Comparable<T>` 的子类型替换为 `T`。
     */
    fun <T : Comparable<T>> sort(list: List<T>) {
        //TODO
    }

    /**
     * 8.尖括号 `<>`  内只能指定一个上限，如果同一个类型参数需要两个或两个以上的上限，则需要单独的使用 where 语句。
     */
    fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
            where T : CharSequence, T : Comparable<T> {
        return list.filter { it == threshold }.map { it.toString() }
    }
}