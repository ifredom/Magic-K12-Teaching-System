package com.magic.server.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIterator {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("C++");

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            String language = iterator.next(); // 获取下一个元素
            System.out.println("========"+language);
            if ("Python".equals(language)) {
                iterator.remove(); // 删除当前元素
            }
        }

        System.out.println(list);


    }
}
