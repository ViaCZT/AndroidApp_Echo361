package com.example.echo361;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Course {
    private String title;
    private Integer courseID; //4 digit number from code without college identity
    private ArrayList<String> students;
    private String teacher;
    private CODE code;
    private CAREER career;
    private DELIVERY delivery;
    private TERM term;

    public Course(String title, Integer courseID,
                  ArrayList<String> students, String teacher, CODE code, CAREER career, DELIVERY delivery, TERM term) {
        this.title = title;
        this.courseID = courseID;
        this.students = students;
        this.teacher = teacher;
        this.code = code;
        this.career = career;
        this.delivery = delivery;
        this.term = term;
    }

    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", courseID=" + courseID +
                ", students=" + students +
                ", teacher='" + teacher + '\'' +
                ", code=" + code +
                ", career=" + career +
                ", delivery=" + delivery +
                ", term=" + term +
                '}';
    }

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public enum TERM{
        Spring,Summer,Autumn,Winter,Semester1,Semester2
    }
    public enum CAREER{Postgraduate,Undergraduate}
    public enum DELIVERY{
        Online,OnCampus,Blended
    }
    public enum CODE{
        COMP,CBEA,BUSN,MGMT,LAWS,ENGN,MATH,BIOL,CHEM,PHYS,HIST
    }
}