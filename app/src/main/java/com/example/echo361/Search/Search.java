package com.example.echo361.Search;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.echo361.Course;
import com.example.echo361.Database.FirebaseDAOImpl;
import com.example.echo361.Database.FirebaseDataCallback;
import com.example.echo361.Factory.Student;
import com.example.echo361.Forum;
import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search {

    private static final String[] allColleges = {"BIOL", "BUSN", "CBEA", "CHEM", "COMP", "ENGN",
            "HIST", "LAWS", "MATH", "MGMT", "PHYS"};

    public static boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static String[] inputToCourse(String searchInput){
        StringBuilder courseCode = new StringBuilder();
        StringBuilder collegeCode = new StringBuilder();
        int numOfCourseCode = 0;
        int numOfCollegeCode = 0;

        for (int i = 0; i < searchInput.length(); i++){
            if (numOfCourseCode < 4 && Character.isDigit(searchInput.charAt(i))){
                numOfCourseCode++;
                courseCode.append(searchInput.charAt(i));
            }
            if (numOfCollegeCode < 4 && isWord(searchInput.charAt(i))){
                numOfCollegeCode++;
                collegeCode.append(searchInput.charAt(i));
            }
        }
        String[] result = new String[]{String.valueOf(collegeCode), String.valueOf(courseCode)};
        return result;

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
        ArrayList<Course> courselist6 = new ArrayList<>();
        ArrayList<Course> courselist7 = new ArrayList<>();
        ArrayList<Course> courselist8 = new ArrayList<>();
        ArrayList<Course> courselist9 = new ArrayList<>();

        if (underG && !postG && !onCampus && !Online){
            courselist1 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Undergraduate,null,null,null,courseCode);
        }
        if (!underG && postG && !onCampus && !Online){
            courselist2 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Postgraduate,null,null,null,courseCode);
        }
        if (!underG && !postG && onCampus && !Online){
            courselist3 = courseAVLtree.inOrderBSTqualify(courselist1, null, Course.DELIVERY.OnCampus,null,null,courseCode);
        }
        if (!underG && !postG && !onCampus && Online){
            courselist4 = courseAVLtree.inOrderBSTqualify(courselist1, null, Course.DELIVERY.Online,null,null,courseCode);
        }
        if (!(underG || postG || onCampus || Online) || (underG && postG && onCampus && Online)){
            courselist5 = courseAVLtree.inOrderBSTqualify(courselist1, null, null,null,null,courseCode);
        }

        if (underG && postG && !onCampus && !Online){
            courselist6 = courseAVLtree.inOrderBSTqualify(courselist1, null,null,null,null,courseCode);
        } else if(underG && !postG && onCampus && !Online){
            courselist6 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Undergraduate, Course.DELIVERY.OnCampus,null,null,courseCode);
        } else if(underG && !postG && !onCampus && Online){
            courselist6 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Undergraduate, Course.DELIVERY.Online,null,null,courseCode);
        }

        if (!underG && postG && onCampus && !Online){
            courselist7 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Postgraduate, Course.DELIVERY.OnCampus,null,null,courseCode);
        } else if (!underG && postG && !onCampus && Online){
            courselist7 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Postgraduate, Course.DELIVERY.Online,null,null,courseCode);
        }

        if (!underG && !postG && onCampus && Online){
            courselist8 = courseAVLtree.inOrderBSTqualify(courselist1, null, Course.DELIVERY.Blended,null,null,courseCode);
        }



        if ( underG && postG && onCampus && !Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist1, null, Course.DELIVERY.OnCampus,null,null,courseCode);
        }else if ( underG && postG && !onCampus && Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist1, null, Course.DELIVERY.Online,null,null,courseCode);
        }else if ( underG && !postG && onCampus && Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Undergraduate, Course.DELIVERY.Online,null,null,courseCode);
        }else if ( !underG && postG && onCampus && Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist1, Course.CAREER.Postgraduate, Course.DELIVERY.Online,null,null,courseCode);
        }





        courselist.addAll(courselist1);
        courselist.addAll(courselist2);
        courselist.addAll(courselist3);
        courselist.addAll(courselist4);
        courselist.addAll(courselist5);
        courselist.addAll(courselist6);
        courselist.addAll(courselist7);
        courselist.addAll(courselist8);
        courselist.addAll(courselist9);

        return courselist;

    }

    public static ArrayList<Integer> indexOfSpace(String inputPersed){
        ArrayList<Integer> indexOfSpce= new ArrayList<Integer>();
        for (int i = 0; i < inputPersed.length(); i++){
            if (!isWord(inputPersed.charAt(i))){
                indexOfSpce.add(i);
            }
        }
        return indexOfSpce;
    }

    public static String[] getName(String inputPersed){

        String firstName = "";
        String lastName = "";

        ArrayList space = indexOfSpace(inputPersed);
        if (space.size() == 0){
            firstName = inputPersed;
        }else if (space.size() == 1){
            firstName = inputPersed.substring(0, (Integer) space.get(0));
            lastName = inputPersed.substring((Integer) space.get(0));
        }else{
            firstName = inputPersed.substring(0,7);
            lastName = inputPersed.substring((Integer) space.get(space.size()-1) +1);
        }

        String[] result = new String[]{firstName, lastName};

        return result;

    }

    public static ArrayList<String> partialNameSearch(ArrayList<String> students, String[] firstAndLastName){

        ArrayList<String> results = new ArrayList<>();
        Map<String , String[]>  studentsFLname = new HashMap<>();
//        ArrayList<String[]> studentsFLname = new ArrayList<>();

        for (String i : students){
            studentsFLname.put(i,getName(i));
        }

        for (String i : studentsFLname.keySet()){
            if (studentsFLname.get(i)[0].contains(firstAndLastName[0]) && studentsFLname.get(i)[1].contains(firstAndLastName[1])){
                results.add(i);
            }
        }

        return results;
    }



    public static void readCourseDate(FirebaseDAOImpl firebaseDAOImpl, ArrayList list, ArrayAdapter arrayAdapter, String courseName){
        firebaseDAOImpl.getData(courseName.substring(0,4)+"Tree", null, new FirebaseDataCallback<String>() {

            @Override
            public void onDataReceived(String data) {

                Gson gson = new Gson();
                CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
                ArrayList<Course> courselist = new ArrayList<>();
                courselist = courseAVLtree.inOrderBSTqualify(courselist, null,null,null,null, courseName.substring(4));

                for (Course i : courselist){
                    list.add(i);
                }

                System.out.println("N" + list);

                arrayAdapter.notifyDataSetChanged();

            }
            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
    }



    //    public String getCourseCode(Course course){
    //
    //
    //
    //        FirebaseDAOImpl firebaseDAOImpl = FirebaseDAOImpl.getInstance();
    //        return "None";
    //    }


}
