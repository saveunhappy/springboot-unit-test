package com.luv2code.junitdemo.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    @Test
    @DisplayName("除以3")
    @Order(1)
    public void testForDivisibleByThree() throws Exception{
        String expect = "Fizz";
        assertEquals(expect,FizzBuzz.compute(3),"should return Fizz");
    }
    @Test
    @DisplayName("除以5")
    @Order(2)
    public void testForDivisibleByFive() throws Exception{
        String expect = "Buzz";
        assertEquals(expect,FizzBuzz.compute(5),"should return Buzz");
    }
    @Test
    @DisplayName("同时可以除以3和15")
    @Order(3)
    public void testForDivisibleByThreeAndFive() throws Exception{
        String expect = "FizzBuzz";
        assertEquals(expect,FizzBuzz.compute(15),"should return FizzBuzz");
    }
    @Test
    @DisplayName("不会被除以3或者5")
    @Order(4)
    public void testForNotDivisibleByThreeAndFive() throws Exception{
        String expect = "1";
        assertEquals(expect,FizzBuzz.compute(1),"should return 1");
    }
    @ParameterizedTest(name = "value={0},expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @DisplayName("测试小的csv")
    @Order(5)
    public void testSmallDataSource(int value, String expected) throws Exception{
        assertEquals(expected,FizzBuzz.compute(value));
    }

    @ParameterizedTest(name="value={0}, expected={1}")
    @CsvFileSource(resources="/medium-test-data.csv")
    @DisplayName("测试中等的csv")
    @Order(6)
    void testMediumDataFile(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }
    @ParameterizedTest(name="value={0}, expected={1}")
    @CsvFileSource(resources="/large-test-data.csv")
    @DisplayName("测试大的csv")
    @Order(7)
    void testLargeDataFile(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }

    @ParameterizedTest(name="value={0}, expected={1}")
    @CsvFileSource(resources="/small-test-data.xls")
    @DisplayName("测试使用excel")
    @Order(8)
    void testLargeExcel(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }



}