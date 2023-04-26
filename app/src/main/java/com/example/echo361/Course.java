package com.example.echo361;

import android.provider.MediaStore.Video;

public class Course implements Comparable<Course> {
    private String courseName;
    private String courseID;
    private AVLTree students;
    private AVLTree teachers;
    private Forum forum;
    private AVLTree videos;


    @Override
    public int compareTo(Course course) {
        return 0;
    }
}
