/**
 * @author Zetian Chen, u7564812
 */
package com.example.echo361.FactoryTest;

import com.example.echo361.Factory.Teacher;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TeacherTest {
    @Test
    public void testTeacherInitialization() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2100");
        courses.add("comp6442");
        Teacher teacher = new Teacher("Jane Smith", "u123", courses);
        Assert.assertEquals("Jane Smith", teacher.getUserName());
        Assert.assertEquals("u123", teacher.getPassWord());
        Assert.assertEquals(courses, teacher.getCourses());
    }

    @Test
    public void testTeacherToString() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2100");
        courses.add("comp6442");
        Teacher teacher = new Teacher("Jane Smith", "u123", courses);
        Assert.assertEquals("Teacher{userName='Jane Smith', passWord='u123', courses=[comp2100, comp6442]}", teacher.toString());
    }
}

