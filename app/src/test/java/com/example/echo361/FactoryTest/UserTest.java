package com.example.echo361.FactoryTest;

import com.example.echo361.Factory.Student;
import com.example.echo361.Factory.Teacher;
import com.example.echo361.Factory.User;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserTest {
    @Test
    public void testUserInitialization() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2100");
        courses.add("comp6442");
        User user = new Student("John Doe", "u1234", courses);
        Assert.assertEquals("John Doe", user.getUserName());
        Assert.assertEquals("u1234", user.getPassWord());
        Assert.assertEquals(courses, user.getCourses());
    }

    @Test
    public void testUserSetter(){
        User user = new Teacher();
        user.setUserName("Sam Smith");
        Assert.assertEquals("Sam Smith", user.getUserName());
        user.setPassWord("u4321");
        Assert.assertEquals("u4321", user.getPassWord());
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2000");
        courses.add("comp6000");
        user.setCourses(courses);
        Assert.assertEquals(courses, user.getCourses());
    }

    @Test
    public void testUserToString() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2100");
        courses.add("comp6442");
        User user = new User("John Doe", "u1234", courses) {};
        Assert.assertEquals("userName='John Doe', passWord='u1234', courses=[comp2100, comp6442]", user.toString());
    }
}
