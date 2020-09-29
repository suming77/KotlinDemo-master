package com.suming.kotlindemo.blog;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Vector;

public class Stack<E> extends Vector<E> {

    public Stack() {
    }

    public E push(E item) {
        addElement(item);
        return item;
    }

    public synchronized E pop() {
        E obj;
        int len = size();

        obj = peek();
        removeElementAt(len - 1);
        return obj;
    }

    public synchronized E peek() {
        int len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

//    public void pushAll(ArrayList<E> src) {
  public void pushAll(ArrayList<? extends E> src) {//作为E生成器的参数的通配符类型
        for (E e : src)//获取
            push(e);
    }

    //popAll方法无通配符类型缺陷
    public void popAll(ArrayList<E> dst) {
//  public void popAll(ArrayList<? super E> dst) {//作为E使用者的参数的通配符类型
        while (!isEmpty())
            dst.add(pop());//添加
    }
}
