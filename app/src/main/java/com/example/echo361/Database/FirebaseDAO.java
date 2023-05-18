package com.example.echo361.Database;

import android.content.Context;

import java.io.IOException;

/**
 * Interface for Firebase Data Access Object (DAO).
 * Provides methods to retrieve and store data in Firebase.
 *
 * @author Zetian Chen, u7564812
 */
public interface FirebaseDAO {
    /**
     * Retrieves data from Firebase based on the reference and child paths provided.
     * Invokes the callback with the received data.
     *
     * @param refPath    The reference path in Firebase.
     * @param childPath  The child path under the reference in Firebase.
     * @param callback   The callback to be invoked with the received data.
     * @param <T>        The type of the data to be retrieved.
     */
    <T> void getData(String refPath, String childPath, FirebaseDataCallback<T> callback);

    /**
     * Stores data in Firebase based on the reference and child paths provided,
     * along with the input data.
     *
     * @param refpath    The reference path in Firebase.
     * @param childpath  The child path under the reference in Firebase.
     * @param input      The data to be stored in Firebase.
     * @param <E>        The type of the input data.
     */
    <E> void storeData(String refpath,String childpath,E input);

    /**
     * Initializes student data by reading it from a file
     * and storing it in Firebase.
     *
     * @param context  The Android context.
     * @throws IOException if an I/O error occurs.
     */
    void initialStudentData(Context context) throws IOException;

    /**
     * Initializes teacher data by reading it from a file
     * and storing it in Firebase.
     *
     * @param context  The Android context.
     * @throws IOException if an I/O error occurs.
     */
    void initialTeacherData(Context context) throws IOException;

    /**
     * Initializes courses data by reading it from a file
     * and storing it in Firebase.
     *
     * @param context  The Android context.
     * @throws IOException if an I/O error occurs.
     */
    void initialCoursesData(Context context) throws IOException;

    /**
     * Initializes courses data by reading it from a file
     * and storing it in Firebase.
     *
     * @param context  The Android context.
     * @throws IOException if an I/O error occurs.
     */
    void initialForumData(Context context) throws IOException;
}
