package com.suming.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/11 17:04
 * @类描述 ${TODO}委托
 */
class EntrustActivity : AppCompatActivity() {

    //测试github
    //委托模式是软件设计模式中的一项基本技巧。在委托模式中，有两个对象参与处理同一请求，接受请求的对象将请求委托给另一个对象来处理
    //kotlin支持委托模式，通过关键字by实现委托

    //分支测试11111111111111111111111111111111
    //1.类委托
    //类的委托即一个类中定义的方式实际是调用另一个类的对象的方法实现的
    //以下实例派生类 Derived 继承了接口base所有方法，并且委托一个传入base类的对象来执行这些方法

    //创建接口
    interface Base {
        fun print()
    }

    //实现此接口的被委托的类
    class BaseImpl(val x: Int) : Base {
        override fun print() {
            print(x)
        }
    }

    //通过关键字by建立委托类
    class Derived(b: Base) : Base by b

    fun main(args: ArrayList<String>) {
        val b = BaseImpl(10)
        Derived(b).print()//输出10
    }
    //在 Derived 声明中，by句子表示，将b保存在Derived的对象实例内部，而且编译器将会生成继承自base接口的所有方法，并将调用转发给b

    //2.属性委托
    //属性委托指的是一个类的某个属性值不是在类中直接进行定义，而是将其托付给一个代理类，从而实现对该类的属性的统一管理
    //属性委托的语法格式：
//    val/var <属性名> ：<类型> by <表达式>
    //var/val:属性类型<可变/只读>
    //属性名：属性名称
    //类型：属性的数据类型
    //表达式：委托代理类

    //by关键字后的表达式就是委托，属性的get()和set()方法将被委托给这个对象的getValue()和setValue()方法，
    //属性委托不必实现任何接口,但是必须提供getValue()函数(对于var函数还需要提供setValue()函数)

    //该类包含setValue()和getValue()方法，且参数thisRef 为进行委托的类的对象，prop为进行委托的属性的对象
    //定义一个被委托的类
    class Example {
        var p: String by Delegate()
    }

    //委托的类
    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef,这里委托了${property.name}属性"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, name: String): String {
            return "$thisRef 的${property.name}的属性赋值为$name"
        }
    }

    fun mian(args: ArrayList<String>) {
        val e = Example()
        println(e.p)//访问该属性，调用getValue()函数

        e.p = "kotlin"//调用setValue()函数
        println(e.p)
    }
    //Example@433c675d, 这里委托了P属性
    //Example@433c675d的P属性赋值为kotlin
    //Example@433c675d, 这里委托了P属性


    //2.标准委托
    //kotlin标准库中已经内置了很多工厂方法来实现属性的委托

    //3.延迟属性 Lazy
    //lazy是一个函数，接受一个lambda表达式作为参数，返回一个Lazy<T>的实例函数，返回的实例可以作为实现延迟属性的委托，
    //第一次调用get()会执行已经传递给lazy()的lambda表达式并记录结果，后续调用get()只是返回记录结果
    val lazyValue: String by lazy {
        println("computed!")//第一次调用输出，第二次调用不执行
        "Hello"
    }

    fun main1(args: ArrayList<String>) {
        println(lazyValue)//第一次执行，执行两次输出表达式
        println(lazyValue)//第二次执行，只输出表达式
    }
    //computed!
    //Hello
    //Hello


    //4.可观察属性Observable
    //Observable可以用于实现观察者模式
    //Delegates.observable()函数接受两个参数，第一个是初始化值，第二个是属性值变化事件的响应器（handler）
    //在属性赋值后执行事件的响应器（handler）,它有三个参数，被赋值的属性，旧值和新值
    class User {
        var name: String by Delegates.observable("初始化值") { property, oldValue, newValue ->
            println("旧值：$oldValue ->新值：$newValue")
        }
    }

    fun main2(args: ArrayList<String>) {
        val user = User()
        user.name = "第一次赋值"
        user.name = "第二次赋值"
    }
    //输出结果：旧值：初始化值->新值：第一次赋值
    //旧值：第一次辅助->新值：第二次赋值


    //5.把属性存储在影射中
    //一个常见的用例是在一个影射（map）里存储属性的值，这经常出现在像解析json或者其他动态事情的应用中，
    // 这种情况下，您可以使用影射实例自身作为委托来实现委托属性
    class Site(val map: Map<String, Any?>) {
        val name: String by map
        val url: String by map
    }

    fun main3(args: ArrayList<String>) {
        //构造函数接受一个影射参数
        val site = Site(mapOf(
                "name" to "安卓",
                "url" to "kotlin"
        ))
        //读取影射值
        println(site.name)
        println(site.url)
    }
    //输出结果：安卓
    //kotlin
    //如果使用var属性需要把Map换成MutableMap

    class Site2(val map: MutableMap<String, Any?>) {
        val name: String by map
        val url: String by map
    }

    fun main4(args: ArrayList<String>) {
        var map: MutableMap<String, Any?> = mutableMapOf(
                "name" to "爪哇",
                "url" to "java"
        )

        val site2 = Site2(map)
        println(site2.name)
        println(site2.url)

        map.put("name", "Google")
        map.put("url", "www.google.com")

        println(site2.name)
        println(site2.url)
    }//输出结果：爪哇   java   Google  www.google.com


    //6.Not Null
    //notNull 适合那些无法在初始化值阶段就确定属性值得场合
    class Foo {
        var notNullBar: String by Delegates.notNull<String>()
    }

    fun main5(args: ArrayList<String>) {
        val foo = Foo()
        foo.notNullBar = "bar"
        println(foo.notNullBar)
        //注意：如果属性在赋值前就被访问则会抛出异常
    }

    //7.局部委托属性
    //你可以将局部变量声明为委托属性，例如，使一个局部变量惰性初始化
    fun example(computeFoo: () -> Foo) {
        val memoizedFoo by lazy(computeFoo)
//        if (someCondition && memoizedFoo.isVaild()){
//            memoizedFoo.doSomething()
//        }
        //memoizedFoo变量只会在第一次访问时计算，如果someCondition 失败则根本不会计算
    }

    //8.属性委托要求
    //对于只读性(也就是val属性)，它的委托必须提供一个getValue()的函数，该函数接受以下参数：
    //thisRef -必须与属性所有者类型(对于拓展属性-指被拓展的类型)相同或者是他的超类型
    //property -必须是类型KProperty<*>或者是其超类
    //这个函数必须返回与属性相同的类型，或者其子类型

    //对于一个值可变(mutable)属性(也就是说var属性)，除了getValue()函数之外它的委托必须再提供一个setValue()函数，这个函数接受以下参数：
    //property —— 必须是类型 KProperty<*> 或其超类型
    //new value —— 必须和属性同类或者是他的超类

    //8.翻译规则
    //在每个委托属性实现的背后，Kotlin编译器都会生成辅助属性并委托给它，例如：对于属性prop，生成隐藏属性prop$delegate
    //而访问器的代码只是简单地委托给这个附加的属性
//    class C {
//        var prop: Type by MyDelegate()
//    }
//
//    // 这段是由编译器生成的相应代码：
//    class C {
//        private val prop$delegate = MyDelegate()
//        var prop: Type
//            get() = prop$delegate.getValue(this, this::prop)
//        set(value: Type) = prop$delegate.setValue(this, this::prop, value)
//    }

    //kotlin编译器在参数中提供了关于prop的所有必要信息，第一个参数引用到this外部类C的实例，而this::prop是KProperty类型的反射对象，该对象描述prop自身


    //9.提供委托
    //通过定义provideDelegate 操作符，可以拓展创建属性实现所委托对象的逻辑，如果by右侧所使用的对象将
    // provideDelegate定义为成员或者拓展函数，那么将调用该函数来创建属性委托实例

    //provideDelegate 的一个可能使用场景是在创建属性时(而不仅在get和set中)检查属性一致性
    //例如，如果要在绑定之前检查属性名称，也可以这样写

    class ResourceID<T>() {
        val image_id: String = "101"
        val text_id: String = "102"
    }
//
//    class ResourceLoader<T>(id:ResourceID<T>){
//        operator fun provideDelegate(thisRef:MyUI,
//                                     prop:KProperty<*>):ReadOnlyProperty<MyUI,T>{
//            checkProperty(thisRef,prop.name)
//            //创建委托
//        }
//
//        private fun checkProperty(thisRef: MyUI,name: String){
//
//        }
//    }
//    fun <T> bindResource(id:ResourceID<T>):ResourceLoader<T>{
//
//    }
//
//    class MyUI{
//        val image by bindResource(ResourceID.image_id)
//        val text by bindResource(ResourceID.text_id)
//    }

    //provideDelegate的参数与getValue相同
//    thisRef —— 必须与 属性所有者 类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型
//    property —— 必须是类型 KProperty<*> 或其超类型。
    //在创建MyUI 实例期间，为每个属性调用provideDelegate方法，并立即执行必要的验证
    //如果没有这种拦截属性与其委托之间绑定的能力，为了现实相同的功能，你必须现实传递属性名，这不是很方便
    //检查属性名而不使用provideDelegate功能
//    class MyUI {
//        val image by bindResourse(ResourceID.image, "image")
//        val text by bindResourse(ResourceID.text, "text")
//    }
//
//    fun <T> bindResourse(id: ResourceID<T>, propertyName: String) {
//        checkProperty(id, propertyName)
//        //创建委托
//    }
    //在生成代码中,会调用provideDelegate 方法来初始化辅助的 prop$delegate 属性，比较对于属性声明
    // val prop: Type by MyDelegate() 生成的代码与 上面（当 provideDelegate 方法不存在时）生成的代码：
//    class C2 {
//        var prop: Type by MyDelegate()
//    }
//
//    // 这段代码是当“provideDelegate”功能可用时
//// 由编译器生成的代码：
//    class C {
//        // 调用“provideDelegate”来创建额外的“delegate”属性
//        private val prop$delegate = MyDelegate().provideDelegate(this, this::prop)
//        val prop: Type
//            get() = prop$delegate.getValue(this, this::prop)
//    }
    //请注意，provideDelegate 方法只影响辅助属性的创建，并不会影响为 getter 或 setter 生成的代码。
}