package com.example.echo361.FactoryTest;

import com.example.echo361.Factory.*;

import org.junit.Assert;
import org.junit.Test;

public class UserFactoryTest {
    @Test
    public void testGetUser_Student() {
        UserFactory factory = new UserFactory();
        User student = factory.getUser("student", "name","psw",null);
        Assert.assertTrue(student instanceof Student);
    }

    @Test
    public void testGetUser_Teacher() {
        UserFactory factory = new UserFactory();
        User teacher = factory.getUser("teacher","name","psw",null);
        Assert.assertTrue(teacher instanceof Teacher);
    }

    @Test
    public void testGetUser_Admin() {
        UserFactory factory = new UserFactory();
        User admin = factory.getUser("admin","name","psw",null);
        Assert.assertTrue(admin instanceof Admin);
    }

    @Test
    public void testGetUser_InvalidType() {
        UserFactory factory = new UserFactory();
        User invalidUser = factory.getUser("invalid","name","psw",null);
        Assert.assertNull(invalidUser);
    }

    @Test
    public void testGetUser_NullType() {
        UserFactory factory = new UserFactory();
        User invalidUser = factory.getUser(null,"name","psw",null);
        Assert.assertNull(invalidUser);
    }
}

