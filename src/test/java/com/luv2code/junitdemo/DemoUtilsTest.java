package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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


    @BeforeAll
    static void setupBeforeEachClass() {
        System.out.println("@BeforeAll executes only once before all test methods execution in the class");
    }

    @AfterAll
    static void tearDownAfterAll() {
        System.out.println("@AfterAll executes only once after all test methods execution in the class");
    }

    @Test
    @DisplayName("测试乘法")
    public void testMultiPlay() throws Exception{
        assertEquals(12,demoUtils.multiply(3,4),"3 * 4 must be equal 12");
    }

    @Test
    @DisplayName("测试加法")
    public void testEqualAndNotEqual() throws Exception{
        assertEquals(6,demoUtils.add(2,4),"2 + 4 must be equal 6");
        assertNotEquals(6,demoUtils.add(1,9),"1 + 9 must not be equal 6");
    }

    @Test
    @DisplayName("测试空和非空")
    public void testNullAndNotNull() throws Exception{
        String str1 = null;
        String str2 = "houjt";
        assertNull(demoUtils.checkNull(str1),"object should be null");
        assertNotNull(demoUtils.checkNull(str2),"object should not be null");
    }
    @Test
    @DisplayName("测试是否引用了同一个对象")
    public void testSameAndNotSame() throws Exception{
        Integer integer1 = 888;
        Integer integer2 = 888;
        assertSame(demoUtils.getAcademy(),demoUtils.getAcademyDuplicate(),
                "Objects should refer to the same object");
        assertNotSame(integer1,integer2,
                "Objects should not refer to the same object");
    }

    @Test
    @DisplayName("测试True和False")
    public void testTrueAndFalse() throws Exception {
        int gradeOne = 10;
        int gradeTwo = 20;
        assertTrue(demoUtils.isGreater(gradeTwo,gradeOne),"20 must be greater than 10");
        assertFalse(demoUtils.isGreater(gradeOne,gradeTwo),"10 must be less than 20");
    }
    @Test
    @DisplayName("测试数组是否相等")
    public void testArrayEqual() throws Exception{
        String[] stringArray = {"A","B","C"};
        assertArrayEquals(stringArray,demoUtils.getFirstThreeLettersOfAlphabet(),"数组必须相等");
    }
    @Test
    @DisplayName("测试可迭代对象是否相等")
    public void testIterableEquals() throws Exception{
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");


        assertIterableEquals(list1,list2,"可迭代对象要相等");
    }

    @Test
    @DisplayName("测试字符串相等")
    public void testStringLine() throws Exception{
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");

        assertLinesMatch(list1,list2,"字符串要相等");
    }

    @Test
    @DisplayName("测试抛出异常和不抛出异常")
    public void testThrowAndNotThrow() throws Exception{
        assertThrows(Exception.class,()->demoUtils.throwException(-1),"应该要抛出异常");
        assertDoesNotThrow(()->demoUtils.throwException(5),"不应该抛出异常");
    }
    @Test
    @DisplayName("测试超时")
    public void testTimeout() throws Exception{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        assertTimeout(Duration.ofSeconds(3),() -> demoUtils.checkTimeout(),"超时了");
        assertTimeoutPreemptively(Duration.ofSeconds(3),() -> demoUtils.checkTimeout(),"超时了");
        assertTimeoutPreemptively(Duration.ofSeconds(3),() -> demoUtils.checkTimeout(),"超时了");
        stopWatch.stop();
        System.out.println("消耗时间"+stopWatch.getTotalTimeMillis());
    }
}
