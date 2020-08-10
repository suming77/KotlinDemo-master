package com.suming.kotlindemo.blog;

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/07 17:06
 * @类描述 {$TODO}属性与字段：const与val的区别
 * 使用 const 修饰的字段可以直接使用 类名+字段名来调用，类似于 Java 的 private static final 修饰，
 * 而 val 修饰的字段只能用get方法的形式调用。
 */
public class ConstCompare {
    public static void main(String[] args) {
        //注意下面两种的调用方式
        System.out.println(PersonKt.NAME);//这里注意：kotlin文件会默认生成kotlin文件名+Kt的java类文件
        System.out.println(PersonKt.getAge());

        //编译报错，PersonKt.age的调用方式是错误的
        //System.out.println(PersonKt.age);
    }
}
