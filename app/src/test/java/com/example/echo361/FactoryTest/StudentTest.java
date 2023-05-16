/**
 * @author Zetian Chen, u7564812
 */
package com.example.echo361.FactoryTest;

import com.example.echo361.Factory.Student;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class StudentTest {
    @Test
    public void testStudentInitialization() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2100");
        courses.add("comp6442");
        Student student = new Student("Jane Smith", "u123", courses);
        Assert.assertEquals("Jane Smith", student.getUserName());
        Assert.assertEquals("u123", student.getPassWord());
        Assert.assertEquals(courses, student.getCourses());
    }

    @Test
    public void testStudentSetter(){
        Student student = new Student();
        student.setUserName("Sam Smith");
        Assert.assertEquals("Sam Smith", student.getUserName());
        student.setPassWord("u4321");
        Assert.assertEquals("u4321", student.getPassWord());
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2000");
        courses.add("comp6000");
        student.setCourses(courses);
        Assert.assertEquals(courses, student.getCourses());
    }

    @Test
    public void testStudentToString() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2100");
        courses.add("comp6442");
        Student student = new Student("Jane Smith", "u123", courses);
        Assert.assertEquals("Student{userName='Jane Smith', passWord='u123', courses=[comp2100, comp6442]}", student.toString());
    }
}
