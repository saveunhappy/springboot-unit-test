package com.luv2code.junitdemo.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    @Test
    @DisplayName("除以3")
    @Order(1)
    public void testForDivisibleByThree() throws Exception {
        String expect = "Fizz";
        assertEquals(expect, FizzBuzz.compute(3), "should return Fizz");
    }

    @Test
    @DisplayName("除以5")
    @Order(2)
    public void testForDivisibleByFive() throws Exception {
        String expect = "Buzz";
        assertEquals(expect, FizzBuzz.compute(5), "should return Buzz");
    }

    @Test
    @DisplayName("同时可以除以3和15")
    @Order(3)
    public void testForDivisibleByThreeAndFive() throws Exception {
        String expect = "FizzBuzz";
        assertEquals(expect, FizzBuzz.compute(15), "should return FizzBuzz");
    }

    @Test
    @DisplayName("不会被除以3或者5")
    @Order(4)
    public void testForNotDivisibleByThreeAndFive() throws Exception {
        String expect = "1";
        assertEquals(expect, FizzBuzz.compute(1), "should return 1");
    }

    @ParameterizedTest(name = "value={0},expected={1}")
    @CsvFileSource(resources = "/small-test-data.csv")
    @DisplayName("测试小的csv")
    @Order(5)
    public void testSmallDataSource(int value, String expected) throws Exception {
        assertEquals(expected, FizzBuzz.compute(value));
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/medium-test-data.csv")
    @DisplayName("测试中等的csv")
    @Order(6)
    void testMediumDataFile(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/large-test-data.csv")
    @DisplayName("测试大的csv")
    @Order(7)
    void testLargeDataFile(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }

    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/small-test-data.xls")
    @DisplayName("测试使用excel")
    @Order(8)
    void testLargeExcel(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }


    @ParameterizedTest
    @ValueSource(ints = {2, 4, 8})
    void testNumberShouldBeEven(int num) {
        assertEquals(0, num % 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Radar", "Rotor", "Tenet", "Madam", "Racecar"})
    void testStringShouldBePalindrome(String word) {
        assertEquals(isPalindrome(word), true);
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.D, 4.D, 8.D})
    void testDoubleNumberBeEven(double num) {
        assertEquals(0, num % 2);
    }

    boolean isPalindrome(String word) {
        return word.toLowerCase().equals(new StringBuffer(word.toLowerCase()).reverse().toString());
    }

    //index和argument是固定参数，必须是这个名字，否则报错，然后使用EnumSource就会把这个类中所有的枚举给列举出来
    @ParameterizedTest(name = "[{index}] TimeUnit: {arguments}")
    @EnumSource(TimeUnit.class)
    void testTimeUnitMinimumNanos(TimeUnit unit) {
        assertTrue(unit.toMillis(2000000L) > 1);
    }
    //使用names参数就指定只传哪几个枚举
    @ParameterizedTest(name = "[{index}] TimeUnit: {arguments}")
    @EnumSource(value = TimeUnit.class, names = {"SECONDS", "MINUTES"})
    void testTimeUnitJustSecondsAndMinutes(TimeUnit unit) {
        assertTrue(EnumSet.of(TimeUnit.SECONDS, TimeUnit.MINUTES).contains(unit));
        assertFalse(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS,
                TimeUnit.MICROSECONDS).contains(unit));
    }
    //mode = EnumSource.Mode.EXCLUDE 配合names就是要排除掉names中的枚举，exclude，排除掉，默认是include
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = EnumSource.Mode.EXCLUDE, names = {"SECONDS", "MINUTES"})
    void testTimeUnitExcludingSecondsAndMinutes(TimeUnit unit) {
        assertFalse(EnumSet.of(TimeUnit.SECONDS, TimeUnit.MINUTES).contains(unit));
        assertTrue(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS,
                TimeUnit.MICROSECONDS).contains(unit));
    }
    //mode = EnumSource.Mode.MATCH_ALL ,names中传的就是一个正则表达式
    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = EnumSource.Mode.MATCH_ALL, names = ".*SECONDS")
    void testTimeUnitIncludingAllTypesOfSecond(TimeUnit unit) {
        assertFalse(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES).contains(unit));
        assertTrue(EnumSet.of(TimeUnit.SECONDS, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS,
                TimeUnit.MICROSECONDS).contains(unit));
    }

    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = EnumSource.Mode.MATCH_ANY, names = ".*SECONDS")
    void testTimeUnitIncludingAnyTypesOfSecond(TimeUnit unit) {
        assertFalse(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES).contains(unit));
        assertTrue(EnumSet.of(TimeUnit.SECONDS, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS,
                TimeUnit.MICROSECONDS).contains(unit));
    }

    @ParameterizedTest
    @MethodSource("stringGenerator")
    void shouldNotBeNullString(String arg){
        assertNotNull(arg);
    }

    @ParameterizedTest
    @MethodSource("intGenerator")
    void shouldBeNumberWithinRange(int arg){
        assertAll(
                () -> assertTrue(arg > 0),
                () -> assertTrue(arg <= 10)
        );
    }

    @ParameterizedTest(name = "[{index}] user with id: {0} and name: {1}")
    @MethodSource("userGenerator")
    void shouldUserWithIdAndName(long id, String name){
        assertNotNull(id);
        assertNotNull(name);
    }

    static Stream<String> stringGenerator(){
        return Stream.of("hello", "world", "let's", "test");
    }

    static IntStream intGenerator() {
        return IntStream.range(1,10);
    }

    static Stream<Arguments> userGenerator(){
        return Stream.of(Arguments.of(1L, "Sally"), Arguments.of(2L, "Terry"), Arguments.of(3L, "Fred"));
    }
    @ParameterizedTest
    @ArgumentsSource(CustomArgumentsGenerator.class)
    void testGeneratedArguments(double number) throws Exception {
        assertFalse(number == 0.D);
        assertTrue(number > 0);
        assertTrue(number < 1);
    }

    static class CustomArgumentsGenerator implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(Math.random(), Math.random(), Math.random(), Math.random(), Math.random())
                    .map(Arguments::of);
        }
    }

}