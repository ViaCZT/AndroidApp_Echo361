package com.example.echo361.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.echo361.Course;
import com.example.echo361.Factory.Student;
import com.example.echo361.Factory.Teacher;
import com.example.echo361.Factory.User;
import com.example.echo361.Factory.UserFactory;
import com.example.echo361.Forum;
import com.example.echo361.ForumPost;
import com.example.echo361.Search.CourseAVLtree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class FirebaseDAOImpl extends AppCompatActivity implements FirebaseDAO {
    private static FirebaseDAOImpl instance = null;

    private FirebaseDAOImpl() {}

    public static synchronized FirebaseDAOImpl getInstance() {
        if (instance == null) {
            instance = new FirebaseDAOImpl();
        }
        return instance;
    }

    private static final String TAG = "FirebaseDAOImpl";

    @Override
    public <T> void getData(String refPath, String childPath, FirebaseDataCallback<T> callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(refPath);

        if (childPath != null && !childPath.equals("")) {
            ref = ref.child(childPath);
        }

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<T> type = new GenericTypeIndicator<T>() {};
                T result = dataSnapshot.getValue(type);
               callback.onDataReceived((T) result);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MainActivity","wrong");
                callback.onError(error);
            }
        });

    }



//    public
    @Override
    public <E> void storeData(String refpath,String childpath,E input){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(refpath);
        if (childpath == null||childpath.equals("")){
            databaseReference.setValue(input);
        }else {
            databaseReference.child(childpath).setValue(input);
        }
    }

    @Override
    public void initialStudentAndTeacherData(Context context) throws IOException {
//        FirebaseApp.initializeApp()
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Teachers");
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("teachers.csv"), StandardCharsets.UTF_8));
        String line;
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Teacher> teachers = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals("NAME")||fields[0].equals("Ad Admin")) {
                continue;
            }
            ArrayList<String> courses = new ArrayList<>();

//            String[] c = fields[2].split(" ");
//            Collections.addAll(courses,c);
//            Student student = new Student(fields[0],fields[1],courses,null);
//            students.add(student);
            ArrayList<String> cou = new ArrayList<>();
            cou.add(fields[2]);
            UserFactory userFactory = new UserFactory();
            User teacher = userFactory.getUser("teacher",fields[0],fields[1],cou,null);
            teachers.add((Teacher) teacher);
//            Log.d(TAG,"teacher: "+teacher.);
        }
//        databaseReference.setValue("");

        databaseReference.setValue(teachers);
//        databaseReference = firebaseDatabase.getReference("Students");
//        databaseReference.child("1");
        reader.close();
//        reader = new BufferedReader(new InputStreamReader(context.getAssets().open("students.csv"), StandardCharsets.UTF_8));
//        while ((line = reader.readLine()) != null) {
//            String[] fields = line.split(",");
//            if (fields[0].equals("NAME")) {
//                continue;
//            }
//            ArrayList<String> courses = new ArrayList<>();
//            String[] c = fields[2].split(" ");
//            Collections.addAll(courses,c);
//            Student student = new Student(fields[0],fields[1],null,null);
//            students.add(student);
//            databaseReference = firebaseDatabase.getReference("Students").child();
//        }
//        reader.close();
    }
//    code_prefixes = ["COMP", "CBEA", "BUSN", "MGMT", "LAWS", "ENGN", "MATH", "BIOL", "CHEM", "PHYS", "HIST"]

    @Override
    public void initialCoursesData(Context context){

        try {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference;
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("courses.csv"), StandardCharsets.UTF_8));
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
                String[] stu = fields[5].split(" ");
                Collections.addAll(student,stu);

                Course.CODE code;
                Course.TERM term = null;
                Course.DELIVERY delivery = null;
                Course.CAREER career = null;
                switch (fields[2]){
                    case "Autumn" : {
                        term = Course.TERM.Autumn;
                        break;
                    }
                    case "Summer" : {
                        term = Course.TERM.Summer;
                        break;
                    }
                    case "Semester 2" : {
                        term = Course.TERM.Semester2;
                        break;
                    }
                    case "Semester 1" : {
                        term = Course.TERM.Semester1;
                        break;
                    }
                    case "Spring" : {
                        term = Course.TERM.Spring;
                        break;
                    }
                    case "Winter" : {
                        term = Course.TERM.Winter;
                        break;
                    }
                }
                switch (fields[3]) {
                    case "Postgraduate" : {
                        career = Course.CAREER.Postgraduate;
                        break;
                    }
                    case "Undergraduate" : {
                        career = Course.CAREER.Undergraduate;
                        break;
                    }
                }
                switch (fields[4]) {
                    case "Online" : {
                        delivery = Course.DELIVERY.Online;
                        break;
                    }
                    case "Blended" : {
                        delivery = Course.DELIVERY.Blended;
                        break;
                    }
                    case "On Campus" : {
                        delivery = Course.DELIVERY.OnCampus;
                        break;
                    }
                }
                Course course = new Course(fields[0],Integer.parseInt(fields[0].substring(4)),student,fields[6],null,career,delivery,term,null,null);
                course.setForum(new Forum(fields[0],new ArrayList<ForumPost>()));
                switch (fields[0].substring(0,4)){
                    case "COMP" : {
                        code = Course.CODE.COMP;
                        course.setCode(code);
                        COMP = COMP.insert(course.getCourseID(),course);
                        break;
                    }
                    case "CBEA" : {
                        code = Course.CODE.CBEA;
                        course.setCode(code);
                        CBEA= CBEA.insert(course.getCourseID(),course);
                        break;
                    }
                    case "BUSN" : {
                        code = Course.CODE.BUSN;
                        course.setCode(code);
                        BUSN = BUSN.insert(course.getCourseID(),course);
                        break;
                    }
                    case "MGMT" : {
                        code = Course.CODE.MGMT;
                        course.setCode(code);
                        MGMT = MGMT.insert(course.getCourseID(),course);
                        break;
                    }
                    case "LAWS" : {
                        code = Course.CODE.LAWS;
                        course.setCode(code);
                        LAWS =LAWS.insert(course.getCourseID(),course);
                        break;
                    }
                    case "ENGN" : {
                        code = Course.CODE.ENGN;
                        course.setCode(code);
                        ENGN = ENGN.insert(course.getCourseID(),course);
                        break;
                    }
                    case "MATH" : {
                        code = Course.CODE.MATH;
                        course.setCode(code);
                        MATH = MATH.insert(course.getCourseID(),course);
                        break;
                    }
                    case "BIOL" : {
                        code = Course.CODE.BIOL;
                        course.setCode(code);
                        BIOL= BIOL.insert(course.getCourseID(),course);
                        break;
                    }
                    case "CHEM" : {
                        code = Course.CODE.CHEM;
                        course.setCode(code);
                        CHEM = CHEM.insert(course.getCourseID(),course);
                        break;
                    }
                    case "PHYS" : {
                        code = Course.CODE.PHYS;
                        course.setCode(code);
                        PHYS =PHYS.insert(course.getCourseID(),course);
                        break;
                    }
                    case "HIST" : {
                        code = Course.CODE.HIST;
                        course.setCode(code);
                        HIST =HIST.insert(course.getCourseID(),course);
                        break;
                    }
                }
            }
            reader.close();
            Gson gson = new Gson();

            databaseReference = firebaseDatabase.getReference("COMPTree");
            COMP = COMP.delete(501);
            databaseReference.setValue(gson.toJson(COMP));
            databaseReference = firebaseDatabase.getReference("CBEATree");
            CBEA=CBEA.delete(501);
            databaseReference.setValue(gson.toJson(CBEA));
            databaseReference = firebaseDatabase.getReference("BUSNTree");
            BUSN= BUSN.delete(501);
            databaseReference.setValue(gson.toJson(BUSN));
            databaseReference = firebaseDatabase.getReference("MGMTTree");
            MGMT= MGMT.delete(501);
            databaseReference.setValue(gson.toJson(MGMT));
            databaseReference = firebaseDatabase.getReference("LAWSTree");
            LAWS=LAWS.delete(501);
            databaseReference.setValue(gson.toJson(LAWS));
            databaseReference = firebaseDatabase.getReference("ENGNTree");
            ENGN= ENGN.delete(501);
            databaseReference.setValue(gson.toJson(ENGN));
            databaseReference = firebaseDatabase.getReference("MATHTree");
            MATH = MATH.delete(501);
            databaseReference.setValue(gson.toJson(MATH));
            databaseReference = firebaseDatabase.getReference("BIOLTree");
            BIOL= BIOL.delete(501);
            databaseReference.setValue(gson.toJson(BIOL));
            databaseReference = firebaseDatabase.getReference("CHEMTree");
            CHEM = CHEM.delete(501);
            databaseReference.setValue(gson.toJson(CHEM));
            databaseReference = firebaseDatabase.getReference("PHYSTree");
            PHYS =  PHYS.delete(501);
            databaseReference.setValue(gson.toJson(PHYS));
            databaseReference = firebaseDatabase.getReference("HISTTree");
            HIST = HIST.delete(501);
            databaseReference.setValue(gson.toJson(HIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public Student getStudent(Context context){
//        DatabaseReference studentsReference = FirebaseDatabase.getInstance().getReference("Students");
////        studentsReference.orderByChild()
//
//    }

    @Override
    public void initialForum(Context context) throws IOException {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Forums");
        databaseReference.removeValue();
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("courses.csv")));
        br.readLine(); //跳过第一行
        String line;
        ForumPost post = new ForumPost("","","",new ArrayList<>(),true);
        ForumPost post1 = new ForumPost();
        System.out.println("post: " + post);
//        Gson gson = new Gson();
//        String postJson = gson.toJson(post);
        while ((line = br.readLine()) != null){
            String[] tokens = line.split(",");
            databaseReference.child(tokens[0]).setValue("0");
        }
        br.close();
    }




}
