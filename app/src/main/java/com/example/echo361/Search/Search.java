package com.example.echo361.Search;

import com.example.echo361.Course;
import com.example.echo361.Forum;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Search {

    private static final String[] allColleges = {"BIOL", "BUSN", "CBEA", "CHEM", "COMP", "ENGN",
            "HIST", "LAWS", "MATH", "MGMT", "PHYS"};

    public static boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static String getCourseCode(String inputPersed){
        String courseCode = "";
        int courseCodeLen = 0;
        for (int i = 0; i < inputPersed.length(); i++) {
            if (Character.isDigit(inputPersed.charAt(i)) && courseCodeLen < 4) {
                courseCode = courseCode + inputPersed.charAt(i);
                courseCodeLen++;
            }
        }

        return courseCode;
    }

    public static String getCollegeCode(String inputPersed){
        String collegeCode = "";
        int collegeCodeLen = 0;
        for (int i = 0; i < inputPersed.length(); i++) {
            if (isWord(inputPersed.charAt(i)) && collegeCodeLen < 4) {
                collegeCode = collegeCode + inputPersed.charAt(i);
                collegeCodeLen++;
            }
        }

        return collegeCode;
    }

    public static boolean isInCollege(String collegeCode){
        if (!collegeCode.isEmpty()){
            boolean a;
            for (String i : allColleges){
                a = i.toLowerCase().contains(collegeCode.toLowerCase());
                if (a){
                    return a;
                }
            }
        }
        return false;
    }

    public static ArrayList<String> getCollege(String collegeCode){
        if (!(collegeCode.isEmpty())){
            ArrayList<String> collegeName = new ArrayList<>();
            if ((isInCollege(collegeCode))){
                for (String i : allColleges){
                    if(i.toLowerCase().contains(collegeCode.toLowerCase())){
                        collegeName.add(i);
                    }
                }
            }

            return collegeName;
        }
        return null;
    }

    public static ArrayList<Course> courseListFilted(CourseAVLtree courseAVLtree, Boolean underG, Boolean postG,
                                                     Boolean onCampus, Boolean Online, String courseCode){
        ArrayList<Course> courselist = new ArrayList<>();
        ArrayList<Course> courselist1 = new ArrayList<>();
        ArrayList<Course> courselist2 = new ArrayList<>();
        ArrayList<Course> courselist3 = new ArrayList<>();
        ArrayList<Course> courselist4 = new ArrayList<>();
        ArrayList<Course> courselist5 = new ArrayList<>();

        if (underG){
            courselist1 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Undergraduate,null,null,null,courseCode);
        }
        if (postG){
            courselist2 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Postgraduate,null,null,null,courseCode);
        }
        if (onCampus){
            courselist3 = courseAVLtree.inOrderBSTqualify(courselist1, null, Course.DELIVERY.OnCampus,null,null,courseCode);
        }
        if (Online){
            courselist4 = courseAVLtree.inOrderBSTqualify(courselist1, null, Course.DELIVERY.Online,null,null,courseCode);
        }
        if (!(underG || postG || onCampus || Online)){
            courselist5 = courseAVLtree.inOrderBSTqualify(courselist1, null, null,null,null,courseCode);
        }

//        courselist.add(new Course("1", 12, new ArrayList<>(), "teacher", Course.CODE.ENGN, Course.CAREER.Undergraduate,
//                Course.DELIVERY.OnCampus, Course.TERM.Semester1, new Forum("sdf", new ArrayList<>()), "sdf"));
//        System.out.println("COURESE1: " + courselist1);
        System.out.println("course1       " + courselist1);
        courselist.addAll(courselist1);
        courselist.addAll(courselist2);
        courselist.addAll(courselist3);
        courselist.addAll(courselist4);
        courselist.addAll(courselist5);

        return courselist;



    }



    //    public String getCourseCode(Course course){
    //
    //
    //
    //        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
    //        return "None";
    //    }


}
