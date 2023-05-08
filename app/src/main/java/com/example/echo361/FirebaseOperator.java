package com.example.echo361;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FirebaseOperator extends AppCompatActivity {

//    public <E> void storeData(String path,E input){
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("Course");
//        databaseReference.setValue("Course1");
//
//    }

    public void storeData(){
//        FirebaseApp.initializeApp()
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("ab");
        databaseReference.setValue("course2");
    }
//    code_prefixes = ["COMP", "CBEA", "BUSN", "MGMT", "LAWS", "ENGN", "MATH", "BIOL", "CHEM", "PHYS", "HIST"]
    public void trans(){

        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference;
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("courses.csv"), StandardCharsets.UTF_8));
//            new BufferedReader(new FileReader("assets/courses.csv"));
            String line;
            CourseAVLtree COMP = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree CBEA = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree BUSN = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree MGMT = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree LAWS = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree ENGN = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree MATH = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree BIOL = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree CHEM = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree PHYS = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));
            CourseAVLtree HIST = new CourseAVLtree(501,new Course(null,null,null,null,null,null,null,null,null,null));

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals("CODE")){
                    continue;
                }
                ArrayList<String> student = new ArrayList<>();
                ArrayList<String> teacher = new ArrayList<>();
//                for (int j = 5; j < fields.length; j++) {
//                    if (Integer.parseInt(fields[j].substring(1))>2000){
//                        teacher.add(fields[j]);
//                    }else if(Integer.parseInt(fields[j].substring(1))>0){
//                        student.add(fields[j]);
//                    }
//                }
                Course.CODE code;
                Course.TERM term = null;
                Course.DELIVERY delivery = null;
                Course.CAREER career = null;
                switch (fields[2]){
                    case "Autumn" : {
                        term = Course.TERM.Autumn;
                    }
                    case "Summer" : {
                        term = Course.TERM.Summer;
                    }
                    case "Semester 2" : {
                        term = Course.TERM.Semester2;
                    }
                    case "Semester 1" : {
                        term = Course.TERM.Semester1;
                    }
                    case "Spring" : {
                        term = Course.TERM.Spring;
                    }
                    case "Winter" : {
                        term = Course.TERM.Winter;
                    }
                }
                switch (fields[3]){
                    case "Postgraduate" : {
                        career = Course.CAREER.Postgraduate;
                    }
                    case "Undergraduate" : {
                        career = Course.CAREER.Undergraduate;
                    }
                }
                switch (fields[4]){
                    case "Online" : {
                       delivery = Course.DELIVERY.Online;
                    }
                    case "Blended" : {
                        delivery = Course.DELIVERY.Blended;
                    }
                    case "On Campus" : {
                        delivery = Course.DELIVERY.OnCampus;
                    }
                }
                Course course = new Course(fields[0],Integer.parseInt(fields[0].substring(4)),student,teacher,null,career,delivery,term,null,null);
                switch (fields[0].substring(0,4)){
                    case "COMP" : {
                        code = Course.CODE.COMP;
                        course.setCode(code);
                        COMP = COMP.insert(course.getCourseID(),course);
                    }
                    case "CBEA" : {
                        code = Course.CODE.CBEA;
                        course.setCode(code);
                        CBEA= CBEA.insert(course.getCourseID(),course);
                    }
                    case "BUSN" : {
                        code = Course.CODE.BUSN;
                        course.setCode(code);
                        BUSN = BUSN.insert(course.getCourseID(),course);
                    }
                    case "MGMT" : {
                        code = Course.CODE.MGMT;
                        course.setCode(code);
                        MGMT = MGMT.insert(course.getCourseID(),course);
                    }
                    case "LAWS" : {
                        code = Course.CODE.LAWS;
                        course.setCode(code);
                        LAWS =LAWS.insert(course.getCourseID(),course);
                    }
                    case "ENGN" : {
                        code = Course.CODE.ENGN;
                        course.setCode(code);
                        ENGN = ENGN.insert(course.getCourseID(),course);
                    }
                    case "MATH" : {
                        code = Course.CODE.MATH;
                        course.setCode(code);
                        MATH = MATH.insert(course.getCourseID(),course);
                    }
                    case "BIOL" : {
                        code = Course.CODE.BIOL;
                        course.setCode(code);
                        BIOL= BIOL.insert(course.getCourseID(),course);
                    }
                    case "CHEM" : {
                        code = Course.CODE.CHEM;
                        course.setCode(code);
                        CHEM = CHEM.insert(course.getCourseID(),course);
                    }
                    case "PHYS" : {
                        code = Course.CODE.PHYS;
                        course.setCode(code);
                        PHYS =PHYS.insert(course.getCourseID(),course);
                    }
                    case "HIST" : {
                        code = Course.CODE.HIST;
                        course.setCode(code);
                        HIST =HIST.insert(course.getCourseID(),course);
                    }
                }
            }
            reader.close();
            databaseReference = firebaseDatabase.getReference("COMPTree");
            COMP = COMP.delete(501);
            databaseReference.setValue("2");

//            databaseReference = firebaseDatabase.getReference("COMPTree");
//            COMP = COMP.delete(501);
//            databaseReference.setValue(COMP);
//            databaseReference = firebaseDatabase.getReference("CBEATree");
//            CBEA=CBEA.delete(501);
//            databaseReference.setValue(CBEA);
//            databaseReference = firebaseDatabase.getReference("BUSNTree");
//            BUSN= BUSN.delete(501);
//            databaseReference.setValue(BUSN);
//            databaseReference = firebaseDatabase.getReference("MGMTTree");
//            MGMT= MGMT.delete(501);
//            databaseReference.setValue(MGMT);
//            databaseReference = firebaseDatabase.getReference("LAWSTree");
//            LAWS=LAWS.delete(501);
//            databaseReference.setValue(LAWS);
//            databaseReference = firebaseDatabase.getReference("ENGNTree");
//            ENGN= ENGN.delete(501);
//            databaseReference.setValue(ENGN);
//            databaseReference = firebaseDatabase.getReference("MATHTree");
//            MATH = MATH.delete(501);
//            databaseReference.setValue(MATH);
//            databaseReference = firebaseDatabase.getReference("BIOLTree");
//            BIOL= BIOL.delete(501);
//            databaseReference.setValue(BIOL);
//            databaseReference = firebaseDatabase.getReference("CHEMTree");
//            CHEM = CHEM.delete(501);
//            databaseReference.setValue(CHEM);
//            databaseReference = firebaseDatabase.getReference("PHYSTree");
//            PHYS =  PHYS.delete(501);
//            databaseReference.setValue(PHYS);
//            databaseReference = firebaseDatabase.getReference("HISTTree");
//            HIST = HIST.delete(501);
//            databaseReference.setValue(HIST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
