package com.luv2code;

import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = SpringbootUnitTestApplication.class)
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;
    /* @Mock作为@InjectMocks的属性，就相当于service包含dao 。service就是@InjectMocks，dao就是@Mock*/
//     @Mock
    //
    @MockBean
    private ApplicationDao applicationDao;

    //     @InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        studentOne.setFirstname("Eric");
        studentOne.setLastname("Roby");
        studentOne.setEmailAddress("eric.roby@luv2code_school.com");
        studentOne.setStudentGrades(studentGrades);
    }


    @Test
    @DisplayName("When & Verify")
    public void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.0);

        assertEquals(
                100.0,
                applicationService.addGradeResultsForSingleClass(studentOne.getStudentGrades().getMathGradeResults())
        );

        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
        verify(applicationDao,times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @Test
    @DisplayName("Find GPA")
    public void assertEqualsTestFindGpa() {
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(
                88.31,
                applicationService.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults())
        );
    }

    @Test
    @DisplayName("Not Null")
    public void testAssertNotNull() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

        assertNotNull(
                applicationService.checkNull(studentOne.getStudentGrades().getMathGradeResults()),
                "Object should not be null"
        );
    }

    @Test
    @DisplayName("Throw runtime exception")
    public void throwRuntimeException() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));

        verify(applicationDao).checkNull(nullStudent);
    }

    @Test
    @DisplayName("Multiple stubbing")
    public void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw an exception a second time");

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));

        assertEquals("Do not throw an exception a second time", applicationService.checkNull(nullStudent));

        verify(applicationDao, times(2)).checkNull(nullStudent);
    }
    @Test
    public void test() throws Exception {
        when(applicationService.checkNull(1)).thenReturn(null);
        assertNull(
                applicationService.checkNull(1),
                "Object should not be null"
        );

    }

    @Test
    public void whenCreateMock_thenCreated() {
        List mockedList = Mockito.mock(ArrayList.class);

        mockedList.add("one");
//        for (Object o : mockedList) {
//            System.out.println(o);
//        }
        assertEquals(0, mockedList.size());

        Mockito.verify(mockedList).add("one");

    }
    @Test
    public void whenCreateSpy_thenCreate() {
        List spyList = Mockito.spy(new ArrayList());
        spyList.add("one");
        for (Object o : spyList) {
            System.out.println(o);
        }
        Mockito.verify(spyList).add("one");

        assertEquals(1, spyList.size());
    }
    //spy如果进行了stub，打桩，那么就会返回打桩的，如果没有进行打桩，那么就去调用真实的
    @Test
    public void whenCreateSpy_thenCreate2() {
        List spyList = Mockito.spy(new ArrayList());
        when(spyList.size()).thenReturn(100);
        spyList.add("one");
        for (Object o : spyList) {
            System.out.println(o);
        }
        Mockito.verify(spyList).add("one");

        assertEquals(100, spyList.size());
    }
    @Test
    public void whenCreateSpy() {
        List list = new LinkedList();
        List spy = spy(list);

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }

}
