package com.magic.admin.hutoolsource.MyMutable;


import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyMutableTest {

    @Test
    public void testMutable() {
        TestObj testObj = new TestObj();
        testObj.setName("test");
        testObj.setAge(18);

        MyMutableObj<TestObj> myMutable = MyMutableObj.of(testObj);

        System.out.println(myMutable.get());

    }
}


@Data
class TestObj{
    private String name;
    private int age;
}
