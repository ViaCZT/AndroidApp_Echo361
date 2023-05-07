package com.example.echo361;

import android.provider.MediaStore.Video;

import java.util.ArrayList;

public class Course {
    private String courseName;
    private Integer courseID;
    private ArrayList<String> students;
    private ArrayList<String> teachers;
    private Forum forum;
    private String videos;

    public Course(String courseName, Integer courseID, ArrayList<String> students, ArrayList<String> teachers, Forum forum, String videos) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.students = students;
        this.teachers = teachers;
        this.forum = forum;
        this.videos = videos;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<String> teachers) {
        this.teachers = teachers;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }
}