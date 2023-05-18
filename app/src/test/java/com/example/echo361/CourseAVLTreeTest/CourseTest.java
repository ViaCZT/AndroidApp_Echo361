package com.example.echo361.CourseAVLTreeTest;

import com.example.echo361.Course;
import com.example.echo361.Factory.Student;
import com.example.echo361.Factory.Teacher;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CourseTest {
    Course course = new Course(null,null,null,null,null,null,null,null);
    @Test
    public void codeTest(){
        course.setCareer(Course.CAREER.Postgraduate);
        Assert.assertEquals(Course.CAREER.Postgraduate,course.getCareer());
        course.setCode(Course.CODE.BIOL);
        Assert.assertEquals(Course.CODE.BIOL,course.getCode());
        course.setCourseID(1);
        Integer a = 1;
        Assert.assertEquals(a,course.getCourseID());
        course.setDelivery(Course.DELIVERY.Blended);
        Assert.assertEquals(Course.DELIVERY.Blended,course.getDelivery());
        course.setTerm(Course.TERM.Semester1);
        Assert.assertEquals(Course.TERM.Semester1,course.getTerm());
        ArrayList<String> students = new ArrayList<>();
        students.add(new Student("L","u123",null).getUserName());
        course.setStudents(students);
        Assert.assertEquals(course.getStudents().get(0),"L");
        course.setTeacher(new Teacher("B","u321",null).getUserName());
        Assert.assertEquals("B",course.getTeacher());
        course.setTitle("ab");
        Assert.assertEquals("ab",course.getTitle());
        String re = "Course{title='ab', courseID=1, students=[L], teacher='B', code=BIOL, career=Postgraduate, delivery=Blended, term=Semester1}";
        Assert.assertEquals(re,course.toString());
    }
}
