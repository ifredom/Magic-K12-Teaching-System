package com.magic.admin.hutoolsource.MyMutable;

import cn.hutool.core.lang.mutable.MutableObj;
import cn.hutool.core.util.ObjUtil;

import java.io.Serializable;

/**
 * 作用：用于缓存数据
 * 这是在查看缓存算法 LRUCache 时，看到的一个类，一开始以为是获取泛型使用的，泛型实际上可以通过反射获取到。
 * @param <T>
 */
public class MyMutableObj<T> implements MyMutable<T>, Serializable {
    private static final long serialVersionUID = 1L;
    private T value;

    public static <T> MyMutableObj<T> of(T value){return new MyMutableObj<>(value);}

    public MyMutableObj(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T v) {
        this.value = v;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (this.getClass() == obj.getClass()) {
            MyMutableObj<?> that = (MyMutableObj)obj;
            return ObjUtil.equals(this.value, that.value);
        } else {
            return false;
        }
    }

    public int hashCode(){ return this.value == null? 0 :this.value.hashCode();}

    public String toString() {
        return this.value == null ? "null" : this.value.toString();
    }
}
