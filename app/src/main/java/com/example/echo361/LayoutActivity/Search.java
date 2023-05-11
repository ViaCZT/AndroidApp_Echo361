package com.example.echo361.LayoutActivity;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.google.firebase.FirebaseApp;

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
            for (String i : allColleges){
                return i.toLowerCase().contains(collegeCode.toLowerCase());
            }
        }
        return false;
    }

    public static ArrayList<String> getCollege(String collegeCode){
        ArrayList<String> collegeName = new ArrayList<>();
        if (!collegeCode.isEmpty()){
            for (String i : allColleges){
                if(isInCollege(collegeCode)){
                    collegeName.add(i);
                }
            }
        }
        return collegeName;
    }

//    public String getCourseCode(Course course){
//
//
//
//        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
//        return "None";
//    }


}
