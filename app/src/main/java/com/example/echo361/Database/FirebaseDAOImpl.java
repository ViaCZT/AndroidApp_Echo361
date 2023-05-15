/**
 * This class provides an implementation of FirebaseDAO interface, which defines methods for
 * retrieving and storing data to the Firebase Realtime Database. The class uses Firebase SDK to
 * communicate with the database. The class includes methods for getting and storing data in the
 * database, as well as methods for initializing data for students, teachers, courses, and forums.
 *
 * The FirebaseDAOImpl class implements the Singleton pattern, which allows for a single instance
 * of the class to be created and accessed by other classes.
 *
 * The class includes the following methods:
 * - getData(): retrieves data from the database using a reference path and a child path. The
 *              retrieved data is passed to the callback method.
 * - storeData(): stores data to the database using a reference path, a child path, and the input data.
 * - initialStudentData(): initializes student data in the database by reading data from a CSV file
 *                         and storing the data to the database.
 * - initialTeacherData(): initializes teacher data in the database by reading data from a CSV file
 *                         and storing the data to the database.
 * - initialCoursesData(): initializes courses data in the database by reading data from a CSV file
 *                         and storing the data to the database.
 * - initialForumData(): initializes courses data in the database by reading data from a CSV file
 *                         and storing the data to the database.
 *
 * The class also includes an instance variable to implement the Singleton pattern.
 *
 * @author Zetian Chen, u7564812
 */
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

public class FirebaseDAOImpl implements FirebaseDAO {
    private static FirebaseDAOImpl instance = null;

    /**
     * Constructor for FirebaseDAOImpl. Private to prevent instantiation from other classes.
     */
    private FirebaseDAOImpl() {}

    /**
     * Returns the singleton instance of FirebaseDAOImpl.
     *
     * @return The singleton instance of FirebaseDAOImpl.
     */
    public static synchronized FirebaseDAOImpl getInstance() {
        if (instance == null) {
            instance = new FirebaseDAOImpl();
        }
        return instance;
    }

    // Examples for using getData() method
    /*
       firebaseDAOImpl.getData("Admin", null, new FirebaseDataCallback<Admin>() {
            @Override
            public void onDataReceived(Admin admin) {
                // 在这里处理Admin
            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */
    //Student
        /*
       firebaseDAOImpl.getData("Students", null, new FirebaseDataCallback<ArrayList<Student>>() {
            @Override
            public void onDataReceived(ArrayList<Student> students) {
                Log.d("asdfasd",students);
                // 在这里处理学生
            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */
    //Teacher
        /*
        firebaseDAOImpl.getData("Teachers", null, new FirebaseDataCallback<ArrayList<Teacher>>() {
            @Override
            public void onDataReceived(ArrayList<Teacher> teachers) {
                //在这里处理老师
            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */
    //Trees
       /*
        firebaseDAOImpl.getData("XXXXTree", null, new FirebaseDataCallback<String>() {

            @Override
            public void onDataReceived(String data) {
                //在这里处理树 比如可以对树进行修改 再储存到firebase 例子：
                Gson gson = new Gson();
                CourseAVLtree courseAVLtree = gson.fromJson(data,CourseAVLtree.class);
                courseAVLtree.delete(1);
                FirebaseDAOImpl firebaseDAO = FirebaseDAOImpl.getInstance();
                firebaseDAO.storeData("XXXXTree",null,gson.toJson(courseAVLtree));

            }

            @Override
            public void onError(DatabaseError error) {
                // 在这里处理错误
            }
        });
        */

    /**
     * Retrieves data from the database using a reference path and a child path. The retrieved data
     * is passed to the callback method.
     *
     * @param refPath The reference path to the data.
     * @param childPath The child path to the data.
     * @param callback The callback method to receive the retrieved data.
     * @param <T> The type of data to retrieve.
     */
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

    /**
     * Store data in the Firebase Realtime Database.
     * If childpath is null or empty, store the data at the root level, otherwise, store the data
     * at the specified child path.
     *
     * @param refpath   the reference path to the node in the database
     * @param childpath the child path to the node in the database
     * @param input     the data to be stored
     * @param <E>       the type of data being stored
     */
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

    /**
     * Initialize student data from the CSV file in Asset folder and store in Firebase database.
     *
     * @param context The context of the calling activity or fragment.
     * @throws IOException if the CSV file cannot be read.
     */
    @Override
    public void initialStudentData(Context context) throws IOException {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Students");
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("students.csv"), StandardCharsets.UTF_8));
        String line;
        ArrayList<Student> students = new ArrayList<>();
        reader.readLine(); // Skip the first line
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            ArrayList<String> courses = new ArrayList<>();
            String[] c = fields[2].split(" ");
            Collections.addAll(courses,c);
            UserFactory userFactory = new UserFactory();
            User student = userFactory.getUser("student",fields[0],fields[1],courses);
            students.add((Student) student)  ;
        }
        databaseReference.setValue(students);
        reader.close();
    }

    /**
     * Initialize teacher data from the CSV file in Asset folder and store in Firebase database.
     *
     * @param context The context of the calling activity or fragment.
     * @throws IOException if the CSV file cannot be read.
     */
    @Override
    public void initialTeacherData(Context context) throws IOException {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Teachers");
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("teachers.csv"), StandardCharsets.UTF_8));
        String line;
        ArrayList<Teacher> teachers = new ArrayList<>();
        reader.readLine(); // Skip the first line
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            ArrayList<String> courses = new ArrayList<>();
            String[] c = fields[2].split(" ");
            Collections.addAll(courses,c);
            UserFactory userFactory = new UserFactory();
            User teacher = userFactory.getUser("teacher",fields[0],fields[1],courses);
            teachers.add((Teacher) teacher)  ;
        }
        databaseReference.setValue(teachers);
        reader.close();
    }

    /**
     * This method reads course data from a CSV file, creates a Course object for each line in the file, and
     * inserts it into a corresponding AVL tree based on the course code prefix. The CSV file is stored in the
     * app's assets folder.
     *
     * It first opens the file, reads each line, and then uses the CourseAVLtree class's insert
     * method to insert the course into the AVL tree corresponding to its code prefix. It also creates a new
     * Course object for each line, and populates its fields using data from the CSV file.
     *
     * @param context The context of the calling activity or fragment.
     * @throws IOException if there is an error reading the courses.csv file
     */
    @Override
    public void initialCoursesData(Context context) throws IOException {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference;
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("courses.csv"), StandardCharsets.UTF_8));
        String line;
        //code_prefixes = ["COMP", "CBEA", "BUSN", "MGMT", "LAWS", "ENGN", "MATH", "BIOL", "CHEM", "PHYS", "HIST"]
        CourseAVLtree COMP = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree CBEA = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree BUSN = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree MGMT = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree LAWS = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree ENGN = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree MATH = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree BIOL = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree CHEM = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree PHYS = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));
        CourseAVLtree HIST = new CourseAVLtree(501, new Course(null, null, null, null, null, null, null, null));

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals("CODE")) {
                continue;
            }
            ArrayList<String> student = new ArrayList<>();
            String[] stu = fields[5].split(" ");
            Collections.addAll(student, stu);

            Course.CODE code;
            Course.TERM term = null;
            Course.DELIVERY delivery = null;
            Course.CAREER career = null;
            switch (fields[2]) {
                case "Autumn": {
                    term = Course.TERM.Autumn;
                    break;
                }
                case "Summer": {
                    term = Course.TERM.Summer;
                    break;
                }
                case "Semester 2": {
                    term = Course.TERM.Semester2;
                    break;
                }
                case "Semester 1": {
                    term = Course.TERM.Semester1;
                    break;
                }
                case "Spring": {
                    term = Course.TERM.Spring;
                    break;
                }
                case "Winter": {
                    term = Course.TERM.Winter;
                    break;
                }
            }
            switch (fields[3]) {
                case "Postgraduate": {
                    career = Course.CAREER.Postgraduate;
                    break;
                }
                case "Undergraduate": {
                    career = Course.CAREER.Undergraduate;
                    break;
                }
            }
            switch (fields[4]) {
                case "Online": {
                    delivery = Course.DELIVERY.Online;
                    break;
                }
                case "Blended": {
                    delivery = Course.DELIVERY.Blended;
                    break;
                }
                case "On Campus": {
                    delivery = Course.DELIVERY.OnCampus;
                    break;
                }
            }
            Course course = new Course(fields[0], Integer.parseInt(fields[0].substring(4)), student, fields[6], null, career, delivery, term);
            switch (fields[0].substring(0, 4)) {
                case "COMP": {
                    code = Course.CODE.COMP;
                    course.setCode(code);
                    COMP = COMP.insert(course.getCourseID(), course);
                    break;
                }
                case "CBEA": {
                    code = Course.CODE.CBEA;
                    course.setCode(code);
                    CBEA = CBEA.insert(course.getCourseID(), course);
                    break;
                }
                case "BUSN": {
                    code = Course.CODE.BUSN;
                    course.setCode(code);
                    BUSN = BUSN.insert(course.getCourseID(), course);
                    break;
                }
                case "MGMT": {
                    code = Course.CODE.MGMT;
                    course.setCode(code);
                    MGMT = MGMT.insert(course.getCourseID(), course);
                    break;
                }
                case "LAWS": {
                    code = Course.CODE.LAWS;
                    course.setCode(code);
                    LAWS = LAWS.insert(course.getCourseID(), course);
                    break;
                }
                case "ENGN": {
                    code = Course.CODE.ENGN;
                    course.setCode(code);
                    ENGN = ENGN.insert(course.getCourseID(), course);
                    break;
                }
                case "MATH": {
                    code = Course.CODE.MATH;
                    course.setCode(code);
                    MATH = MATH.insert(course.getCourseID(), course);
                    break;
                }
                case "BIOL": {
                    code = Course.CODE.BIOL;
                    course.setCode(code);
                    BIOL = BIOL.insert(course.getCourseID(), course);
                    break;
                }
                case "CHEM": {
                    code = Course.CODE.CHEM;
                    course.setCode(code);
                    CHEM = CHEM.insert(course.getCourseID(), course);
                    break;
                }
                case "PHYS": {
                    code = Course.CODE.PHYS;
                    course.setCode(code);
                    PHYS = PHYS.insert(course.getCourseID(), course);
                    break;
                }
                case "HIST": {
                    code = Course.CODE.HIST;
                    course.setCode(code);
                    HIST = HIST.insert(course.getCourseID(), course);
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
        CBEA = CBEA.delete(501);
        databaseReference.setValue(gson.toJson(CBEA));
        databaseReference = firebaseDatabase.getReference("BUSNTree");
        BUSN = BUSN.delete(501);
        databaseReference.setValue(gson.toJson(BUSN));
        databaseReference = firebaseDatabase.getReference("MGMTTree");
        MGMT = MGMT.delete(501);
        databaseReference.setValue(gson.toJson(MGMT));
        databaseReference = firebaseDatabase.getReference("LAWSTree");
        LAWS = LAWS.delete(501);
        databaseReference.setValue(gson.toJson(LAWS));
        databaseReference = firebaseDatabase.getReference("ENGNTree");
        ENGN = ENGN.delete(501);
        databaseReference.setValue(gson.toJson(ENGN));
        databaseReference = firebaseDatabase.getReference("MATHTree");
        MATH = MATH.delete(501);
        databaseReference.setValue(gson.toJson(MATH));
        databaseReference = firebaseDatabase.getReference("BIOLTree");
        BIOL = BIOL.delete(501);
        databaseReference.setValue(gson.toJson(BIOL));
        databaseReference = firebaseDatabase.getReference("CHEMTree");
        CHEM = CHEM.delete(501);
        databaseReference.setValue(gson.toJson(CHEM));
        databaseReference = firebaseDatabase.getReference("PHYSTree");
        PHYS = PHYS.delete(501);
        databaseReference.setValue(gson.toJson(PHYS));
        databaseReference = firebaseDatabase.getReference("HISTTree");
        HIST = HIST.delete(501);
        databaseReference.setValue(gson.toJson(HIST));

    }

    /**
     * Initializes forum data by reading from a CSV file and setting the corresponding values in the Firebase database.
     *
     * @param context The context of the calling activity or fragment.
     * @throws IOException if there is an error reading from the CSV file.
     */
    @Override
    public void initialForumData(Context context) throws IOException {
        // Get the Firebase database instance and reference to the "Forums" node.
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Forums");

        // Remove any existing data in the "Forums" node.
        databaseReference.removeValue();

        // Read from the courses.csv file and set the corresponding values in the Firebase database.
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("courses.csv")));
        br.readLine(); // Skip the first line since it contains column headers.
        String line;
        while ((line = br.readLine()) != null){
            String[] tokens = line.split(",");
            // Set the value of the child node corresponding to the course ID to 0.
            databaseReference.child(tokens[0]).setValue("0");
        }
        br.close();
    }


}
