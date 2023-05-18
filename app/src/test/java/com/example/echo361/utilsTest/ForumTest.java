package com.example.echo361.utilsTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.echo361.Forum;
import com.example.echo361.ForumPost;

public class ForumTest {

    private Forum forum;
    private ArrayList<ForumPost> posts;

    @Before
    public void setUp() {
        posts = new ArrayList<>();
        posts.add(new ForumPost("Title 1", "Content 1", "Author 1", new ArrayList<>(), true));
        posts.add(new ForumPost("Title 2", "Content 2", "Author 2", new ArrayList<>(), true));

        forum = new Forum("Course 1", posts);
    }

    @Test
    public void testConstructor() {
        assertNotNull(forum);
    }

    @Test
    public void testGetPosts() {
        ArrayList<ForumPost> result = forum.getPosts();
        assertEquals(posts, result);
    }

    @Test
    public void testAddPost() {
        ForumPost newPost = new ForumPost("Title 3", "Content 3", "Author 3", new ArrayList<>(), true);
        posts.add(newPost);
        forum.getPosts().add(newPost);

        assertEquals(posts, forum.getPosts());
    }

    @Test
    public void testRemovePost() {
        posts.remove(0);
        forum.getPosts().remove(0);

        assertEquals(posts, forum.getPosts());
    }
}
