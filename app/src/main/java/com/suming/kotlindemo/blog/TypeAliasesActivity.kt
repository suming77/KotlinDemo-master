package com.suming.kotlindemo.blog

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.suming.kotlindemo.R
import com.suming.kotlindemo.User
import kotlinx.android.synthetic.main.activity_type_aliases.btn_upgrade_button as btnUpgrade
import java.io.File

import com.suming.kotlindemo.blog.User as DatabaseUser
import android.support.v4.app.NotificationCompat.Builder as NotificationBuilder

typealias DatabaseUser2 = com.suming.kotlindemo.blog.User

typealias NodeSet = Set<String>

typealias FileTable<K> = MutableMap<K, MutableList<File>>

//我们通过创建类型别名来优化：
typealias Restaurant = Organization<(String, Int?) -> String>
//修改
typealias Restaurant2 = Organization<(String, Int?) -> User>

/**
 * 1.可读性
 * 有些人会说不明白这是如何有助于可读性，上面的示例中的参数名称已经明确表明了 restaurant，
 * 为什么还需要 Restaurant 类型，难道我们不能使用具体的类型名称和抽象类型吗？没错，
 * 参数的名称更应该具体地表示类型，但是上面的 RestaurantPatron 接口的别名版本更具有可读性，也不容易受到侵入。
 */
interface RestaurantService {
    //    var locator: (String, Person) -> List<Organization<(String, Int?) -> String>>
    var locator: (String, Person) -> List<Restaurant>
}

/**
 * 2. 继承性
 * 类型别名适用性很广，它也适用于你通常不能或者不会去继承的类型。
 * - 非 open 的一些类，例如 String 或者 Java 的 Optional<T>;
 * - Kotlin 中的单例对象实例( object )；
 * - 函数类型，比如： (String, Int?) -> String;
 * - 函数接收者类型，比如： (String, Int?) -> String。
 */
class Restaurant3 : Organization<(String, Int?) -> String>()

/**
 * 3.类型别名和类型安全
 * 类型别名不会创建新的类型，它们只是给现有的类型取一个别名而已。
 */
typealias UserId = UniqueIdentifier
typealias ProductId = UniqueIdentifier

//1.使用 类型别名；
typealias AliasedSupplier = () -> String

//2.使用 继承 去创建一个子类型。
interface InheritedSupplier : () -> String

/**
 * 5.基于类型别名特别操作
 * 一旦创建了类型别名，就可以在各种场景中使用它来替代底层类型。
 * - 在声明变量类型，参数类型和返回值类型的时候；
 * - 在作为类型参数约束和类型参数的时候；
 * - 在使用比较类型 is 或者强转类型 as 的时候；
 * - 在获得函数引用的时候。
 */

/**
 * 6.构造器
 * 如果底层类型有构造器，那么它的类型别名也是如此，甚至可以在一个可空类型的别名上调用构造函数：
 */
class TeamMember(val name: String)
typealias MaybeTeamMember = TeamMember?

/**
 * 7.伴生对象
 * 你可以通过包含伴生对象类的别名来调用该类的伴生对象中的属性和方法。即使底层类型具有指定的具体类型参数，例如：
 */
class Container<T>(var item: T) {
    companion object {
        const val classVersion = 5
    }
}

//注意此处的String是具体的参数类型
typealias BoxedString = Container<String>

/**
 * 8.只能定义在顶层位置
 * 类型别名只能定义在顶层位置，也就是说，它不能被内嵌到一个类，对象，接口或者其他代码块中。否则会报错：
 * 你可以限制类型别名的访问权限，比如常见的访问修饰符 internal 和 private。
 * 所以如果你想让一个类型别名只能在一个类中被访问，你只需要将类型别名和这个类放在同一个文件即可，
 * 并且这个别名标记为 private 修饰。
 */
private typealias Message = String

object Messages {
    val greeting: Message = "Hello"
}

/**
 * 9.与Java的互操作性
 * 你能在 Java 代码中使用 Kotlin 的类型别名吗？答案是不能。它们在 Java 中是不可见的。
 * 但是如果在 Kotlin 代码中你有引用类型别名，虽然 Java 代码不能使用类型别名，但是可以通过使用底层类型继续与它进行交互.
 */

typealias Greeting = String

fun welcomeUser(greeting: Greeting) {
    println("$greeting, user!")
}

/**
 * 10.递归别名
 */
typealias Salutation = Greeting
//typealias Greeting = Comparable<Greeting> 报错：Recursive type alias in expansion: Greeting

/**
 * 11.类型投影
 * 因为 Boxes<out String> 会拓展成 ArrayList<Box<out T>> 而不是 ArrayList<out Box<out T>>。
 */
class Box<T>(var item: T)
typealias Boxes<T> = ArrayList<Box<T>>

fun read(boxes: Boxes<out String>) = boxes.forEach(::println)


/**
 * @创建者 mingyan.su
 * @创建时间 2020/10/15 16:42
 * @类描述 {$TODO}类型别名
 * 类型别名为现有类型提供了替代名称。如果类型名称太长，可以引入一个不同的较短名称并使用新的名称。
 */
class TypeAliasesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type_aliases)

        //所以类型别名的方式比基于继承的方式上更具有优势。当然，某些情况下类型安全更重要，类型别名可以根据实际情况来选用。
        writeAliased { "Hello" }
//        writeInherited { "Hello" } // compiler error!(编译器错误)

        writeInherited(object : InheritedSupplier {
            override fun invoke(): String = "Hello"
        })

        //使用别名来构造对象
        val member = MaybeTeamMember("Miguel")

        //以上代码不会是逐字扩展成如下无法编译的代码
        //val member1 = TeamMember?("Miguel")

        //而是扩展如下代码
        val member2 = TeamMember("Miguel")


        //通过别名获取伴侣对象的属性：
        val version = BoxedString.classVersion

        //这行代码不会是扩展成如下无法编译的代码
        //val version1 = Container<String>.classVersion

        //它是会在即将进入编译期会扩展成如下代码
        //val version2 = Container.classVersio

        // 使用String类型，而不是使用别名Greeting
        val hello = "Hello"
        welcomeUser(hello)

        val boxes: Boxes<String> = arrayListOf(Box("Hello"), Box("World"))
        //read(boxes) //Compiler error

        /**
         * 13.在 Android 项目开发中，如果使用到 Kotlin Android Extensions，
         * 那么使用 import as 将是很好的方式去重命名来自于对应 Activity 中对应布局的ID，
         * 将原来布局中下划线分割的ID，可以重命名为驼峰形式，使你的代码更具可读性。
         */
        btnUpgrade.text = "按钮"
    }

    /**
     * 14.总结
     * 使用类型别名可以将复杂，冗长和抽象的类型提供简洁和特定于域的名称。它们易于使用，
     * 并且IDE工具支持深入了解底层类型。在正确的地方使用，让你的代码更易于阅读和理解。
     *
     *  1.类型别名 type alias 不会创建新的类型，它只是给现有的类型提供一个新的名称而已；
     *  2.类型别名的实际原理，有一大部分情况下是在编译时期采取逐字替换的拓展方式，还原成真正的底层类型；
     *  3.类型别名只能定义在顶层位置，不能内嵌在类，接口，函数，代码块等内部。
     */

    interface RestaurantPatron {
        fun makeReservation(restaurant: Organization<(String, Int?) -> String>)
        fun visit(restaurant: Organization<(String, Int?) -> String>)
        fun complainAbout(restaurant: Organization<(String, Int?) -> String>)
    }

    interface Store {
        fun purchase(user: UserId, product: ProductId): Person
    }

    fun writeAliased(supplier: AliasedSupplier) = println(supplier.invoke())

    fun writeInherited(supplier: InheritedSupplier) = println(supplier.invoke())

    /**
     *  12.Import As
     *  是非常类似类型别名的概念，它允许你给一个类型，函数或者属性一个新的命名，然后你就可以把它导入到文件中。
     *  由于上面处理了两个不同的同名 User 类，因此我们无法都将他们两者都同时导入。所以我们只能将其中某个以类名+包名的形式声明 User。
     */
    fun translateUser(user: com.suming.kotlindemo.blog.User): User? {
        return User(user.name, 1, "女")
    }

    fun translateUser2(user: DatabaseUser2): User? = User(user.name, 1, "女")
}

/**
 * 4.类型别名案例
 * 这里提供一些关于类型别名的建议：
 */
// Classes and Interfaces (类和接口)
typealias RegularExpression = String
typealias IntentData = Parcelable

// Nullable types (可空类型)
typealias MaybeString = String?

// Generics with Type Parameters (类型参数泛型)
typealias MultivaluedMap<K, V> = HashMap<K, List<V>>
typealias Lookup<T> = HashMap<T, T>

// Generics with Concrete Type Arguments (混合类型参数泛型)
typealias Users = ArrayList<User>

// Type Projections (类型投影)
typealias Strings = Array<out String>
typealias OutArray<T> = Array<out T>
typealias AnyIterable = Iterable<*>

// Objects (including Companion Objects) （对象，包括伴生对象）
typealias RegexUtil = Regex.Companion

// Function Types (函数类型)
typealias ClickHandler = (View) -> Unit

// Lambda with Receiver (带接收者的Lambda)
typealias IntentInitializer = Intent.() -> Unit

// Nested Classes and Interfaces (嵌套类和接口)
typealias NotificationBuilder = NotificationCompat.Builder
typealias OnPermissionResult = ActivityCompat.OnRequestPermissionsResultCallback

// Enums (枚举类)
typealias Direction = FileWalkDirection
// (but you cannot alias a single enum *entry*)

// Annotation (注解)
typealias Multifile = JvmMultifileClass