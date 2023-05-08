package com.example.echo361;

import java.util.ArrayList;

public class searchOperator {

    public ArrayList<Course> searchcourse(CourseAVLtree courseAVLtree, Course.CAREER career, Course.DELIVERY delivery,
                                          Course.TERM term, Course.CODE code, String searchCourseID){
        ArrayList<Course> result = new ArrayList<>();
        if (searchCourseID.length()==4){
            result.add(courseAVLtree.find(Integer.parseInt(searchCourseID)).course);
            return result;
        }else {
           result =  courseAVLtree.inOrderBSTqualify(result,career,delivery,term,code,searchCourseID);
        }
        return result;
    }
}
