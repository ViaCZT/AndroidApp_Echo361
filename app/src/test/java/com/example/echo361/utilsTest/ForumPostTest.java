package com.example.echo361.utilsTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.echo361.ForumPost;

public class ForumPostTest {

    private ForumPost forumPost;
    private ArrayList<String> floors;

    @Before
    public void setUp() {
        floors = new ArrayList<>();
        floors.add("Floor 1");
        floors.add("Floor 2");

        forumPost = new ForumPost("Title", "Content", "Author", floors, true);
    }

    @Test
    public void testConstructor() {
        assertNotNull(forumPost);
    }

    @Test
    public void testGetTitle() {
        assertEquals("Title", forumPost.getTitle());
    }

    @Test
    public void testGetContent() {
        assertEquals("Content", forumPost.getContent());
    }

    @Test
    public void testSetContent() {
        forumPost.setContent("New Content");
        assertEquals("New Content", forumPost.getContent());
    }

    @Test
    public void testGetFloors() {
        assertEquals(floors, forumPost.getFloors());
    }

    @Test
    public void testGetVisible() {
        assertTrue(forumPost.getVisible());
    }

    @Test
    public void testSetVisible() {
        forumPost.setVisible(false);
        assertFalse(forumPost.getVisible());
    }

    @Test
    public void testGetAuthor() {
        assertEquals("Author", forumPost.getAuthor());
    }

    @Test
    public void testSetAuthor() {
        forumPost.setAuthor("New Author");
        assertEquals("New Author", forumPost.getAuthor());
    }
}
