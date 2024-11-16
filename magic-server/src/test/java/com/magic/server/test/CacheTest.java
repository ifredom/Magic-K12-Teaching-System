package com.magic.server.test;


import com.magic.framework.cache.MagicLocalCache;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheTest {

    @Test
    public void testCache() {
        TestCacheObj obj1 = new TestCacheObj();
        obj1.setName("testCacheObj1");
        obj1.setAge(18);

        MagicLocalCache cache = new MagicLocalCache(2,"LRU");

        // 通过控制容量，进行缓存算法测试
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key1", obj1);

        TestCacheObj obj = (TestCacheObj) cache.get("key1");
        System.out.println(obj.getName() + " " + obj.getAge());
        System.out.println(cache.get("key1"));
        System.out.println(cache.get("key2"));
        System.out.println(cache.get("key3"));
    }
}
@Data
class TestCacheObj{
    private String name;
    private int age;
}
