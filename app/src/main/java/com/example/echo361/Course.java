package com.example.echo361;

import java.util.ArrayList;

public class Course {
    private String title;
    private Integer courseID;
    private ArrayList<String> students;
    private ArrayList<String> teachers;
    private CODE code;
    private CAREER career;
    private DELIVERY delivery;
    private TERM term;
    private Forum forum;
    private String videos;

    public CODE getCode() {
        return code;
    }

    public void setCode(CODE code) {
        this.code = code;
    }

    public CAREER getCareer() {
        return career;
    }

    public void setCareer(CAREER career) {
        this.career = career;
    }

    public DELIVERY getDelivery() {
        return delivery;
    }

    public void setDelivery(DELIVERY delivery) {
        this.delivery = delivery;
    }

    public TERM getTerm() {
        return term;
    }

    public void setTerm(TERM term) {
        this.term = term;
    }

    public Course(String title, Integer courseID,
                  ArrayList<String> students, ArrayList<String> teachers, CODE code, CAREER career, DELIVERY delivery, TERM term, Forum forum, String videos) {
        this.title = title;
        this.courseID = courseID;
        this.students = students;
        this.teachers = teachers;
        this.code = code;
        this.career = career;
        this.delivery = delivery;
        this.term = term;
        this.forum = forum;
        this.videos = videos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    enum TERM{
        Spring,Summer,Autumn,Winter,Semester1,Semester2
    }
    enum CAREER{Postgraduate,Undergraduate}
    enum DELIVERY{
        Online,OnCampus,Blended
    }
    enum CODE{
        COMP,CBEA,BUSN,MGMT,LAWS,ENGN,MATH,BIOL,CHEM,PHYS,HIST
    }
}