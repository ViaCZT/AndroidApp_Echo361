/**
 * @author Zetian Chen, u7564812
 */
package com.example.echo361.FactoryTest;

import com.example.echo361.Factory.Admin;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class AdminTest {
    @Test
    public void testAdminInitialization() {
        Admin admin = new Admin("Admin User", "u000", null);
        Assert.assertEquals("Admin User", admin.getUserName());
        Assert.assertEquals("u000", admin.getPassWord());
        Assert.assertNull(admin.getCourses());
    }

    @Test
    public void testAdminSetter(){
        Admin admin = new Admin();
        admin.setUserName("Sam Smith");
        Assert.assertEquals("Sam Smith", admin.getUserName());
        admin.setPassWord("u4321");
        Assert.assertEquals("u4321", admin.getPassWord());
        ArrayList<String> courses = new ArrayList<>();
        courses.add("comp2000");
        courses.add("comp6000");
        admin.setCourses(courses);
        Assert.assertEquals(courses, admin.getCourses());
    }

    @Test
    public void testAdminToString() {
        Admin admin = new Admin("Admin User", "u000", null);
        Assert.assertEquals("userName='Admin User', passWord='u000', courses=null", admin.toString());
    }
}

