package com.luv2code.junitdemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DemoUtilsTest {

    DemoUtils demoUtils;
    @BeforeEach
    void setupBeforeEach(){
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach executes before the execution of each test method");
    }
    @AfterEach
    void testAfterDownEach(){
        System.out.println("Running afterEach");
    }

    @Test
    @DisplayName("乘法测试")
    public void testMultiPlay() throws Exception{
        assertEquals(12,demoUtils.multiply(3,4),"3 * 4 must be equal 12");
    }

    @Test
    @DisplayName("加法测试")
    public void testEqualAndNotEqual() throws Exception{
        assertEquals(6,demoUtils.add(2,4),"2 + 4 must be equal 6");
        assertNotEquals(6,demoUtils.add(1,9),"1 + 9 must not be equal 6");
    }
}
