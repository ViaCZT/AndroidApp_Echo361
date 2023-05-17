package com.example.echo361.Search;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.echo361.Course;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CourseAVLtree {
    public Integer courseID;
    public Course course;
    public CourseAVLtree leftNode;
    public CourseAVLtree rightNode;
    public CourseAVLtree parent = null;

    public CourseAVLtree(){
        courseID = null;
    }
    public CourseAVLtree(Integer courseID,Course course) {
        this.courseID = courseID;
        this.course = course;
        this.leftNode = new EmptyCourseAVLtree();
        this.rightNode = new EmptyCourseAVLtree();
    }

    public CourseAVLtree(Integer courseID, CourseAVLtree leftNode, CourseAVLtree rightNode, Course course){
        if (courseID==null||leftNode==null||rightNode ==null||course ==null){
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        this.courseID = courseID;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.course = course;
    }

    /**
     * Finds a course in the AVL tree given a course ID.
     * @param courseID the ID of the course to find
     * @return the CourseAVLtree node containing the course, or null if not found
     */
    public CourseAVLtree find(Integer courseID){
        if (courseID == null)
            throw new IllegalArgumentException("Input cannot be null");
        //go left if its smaller than the node go right if its bigger
        assert this.courseID != null;
        if (courseID.compareTo(this.courseID) == 0) {
            return this;
        } else if (courseID.compareTo(this.courseID) < 0) {
            return leftNode.find(courseID);
        } else {
            return rightNode.find(courseID);
        }
    }

    /**
     * Inserts a course into the AVL tree.
     * @param courseID the ID of the course to insert
     * @param course the Course object to insert
     * @return the updated AVL tree after insertion
     */
    public CourseAVLtree insert(Integer courseID,Course course) {
        // course cant be null
        if (courseID == null||course == null)
            throw new IllegalArgumentException("Input cannot be null");
        //go left if its smaller than the node go right if its bigger
        CourseAVLtree newTree = new CourseAVLtree(this.courseID,leftNode,rightNode,this.course);
        if (courseID.compareTo(this.courseID) > 0) {
            newTree = new CourseAVLtree(this.courseID,leftNode,rightNode.insert(courseID,course),this.course);
        } else if (courseID.compareTo(this.courseID) < 0) {
            newTree = new CourseAVLtree(this.courseID,leftNode.insert(courseID,course),rightNode,this.course);
        }
        // get the avl tree balanced
        if (newTree.getBalanceFactor()==-2&& newTree.rightNode != null){
            if (((CourseAVLtree) newTree.rightNode).getBalanceFactor() == 1){
                newTree.rightNode = ((CourseAVLtree) newTree.rightNode).rightRotate();
                newTree = newTree.leftRotate();
            }else if (((CourseAVLtree) newTree.rightNode).getBalanceFactor() == -1){
                newTree = newTree.leftRotate();
            }
        }else if (newTree.getBalanceFactor() == 2 && newTree.leftNode != null){
            if (((CourseAVLtree) newTree.leftNode).getBalanceFactor() == 1){
                newTree = newTree.rightRotate();
            }else if (((CourseAVLtree) newTree.leftNode).getBalanceFactor() == -1){
                newTree.leftNode = ((CourseAVLtree) newTree.leftNode).leftRotate();
                newTree = newTree.rightRotate();
            }
        }
        return newTree;
    }

    /**
     * Finds the successor of a given node in the AVL tree.
     * @param node the node for which to find the successor
     * @return the successor node
     */
    public CourseAVLtree findSuccessor(CourseAVLtree node){
        //get the biggest value in its right subtree
        if ((!(node.rightNode instanceof  EmptyCourseAVLtree ))|| node.rightNode.courseID != null){
            return node.rightNode.findMin();
        }
        //find its smallest node which is bigger than it in upper tree
        else {
            CourseAVLtree y = node.parent;
            CourseAVLtree x = node;
            while (y!=null&&x==y.rightNode){
                x = y;
                y = x.parent;
            }
            return y;
        }
    }
    public void setParent(){
        inOrderBST();
    }
    public void inOrderBST(){
        if (!(this instanceof EmptyCourseAVLtree)&&this.courseID!=null){
            this.leftNode.inOrderBST();
            this.leftNode.parent = this;
            this.rightNode.parent = this;
            this.rightNode.inOrderBST();
        }
    }

    /**
     * Deletes a course from the AVL tree given a course ID.
     * @param courseID the ID of the course to delete
     * @return the updated AVL tree after deletion
     */
    public CourseAVLtree delete(Integer courseID){
        //find it firest
        //go left if its smaller than the node go right if its bigger
        CourseAVLtree newtree = new CourseAVLtree(this.courseID,this.leftNode,this.rightNode,this.course);
        if(newtree.find(courseID)==null){
            throw new IllegalArgumentException("can not find this course");
        }
        if (courseID.compareTo(this.courseID) > 0) {
            newtree =  new CourseAVLtree(this.courseID,leftNode,rightNode.delete(courseID), this.course);
        } else if (courseID.compareTo(this.courseID) < 0) {
            newtree = new CourseAVLtree(this.courseID,leftNode.delete(courseID),rightNode, this.course);
        }else if(courseID.compareTo(this.courseID) == 0){
            //delete it if it has two empty child node
            if ((this.leftNode.courseID == null||this.leftNode instanceof EmptyCourseAVLtree)&&(this.rightNode instanceof EmptyCourseAVLtree||this.rightNode.courseID==null)){
                return new EmptyCourseAVLtree();
            }
            //replace it with its child tree when it only has one child node
            else if ((this.leftNode.courseID == null||this.leftNode instanceof EmptyCourseAVLtree)) {
                return this.rightNode;
            }
            //replace it with its successor and delete its successor
            else if ((this.rightNode instanceof EmptyCourseAVLtree||this.rightNode.courseID==null)) {
                return this.leftNode;
            }else {
                CourseAVLtree successor = findSuccessor(this);
                newtree = new CourseAVLtree(this.courseID,leftNode,rightNode,this.course);
                newtree = newtree.delete(successor.courseID);
                newtree.course = successor.course;
                newtree.courseID = successor.courseID;
            }
        }
        //get balanced
        if (newtree.getBalanceFactor()==-2&& newtree.rightNode != null){
            if (newtree.rightNode.getBalanceFactor() == 1){
                newtree.rightNode = newtree.rightNode.rightRotate();
                newtree = newtree.leftRotate();
            }else if (newtree.rightNode.getBalanceFactor() == -1){
                newtree = newtree.leftRotate();
            }else {
                newtree = newtree.leftRotate();
            }
        }else if (newtree.getBalanceFactor() == 2 && newtree.leftNode != null){
            if (newtree.leftNode.getBalanceFactor() == 1){
                newtree = newtree.rightRotate();
            }else if (newtree.leftNode.getBalanceFactor() == -1){
                newtree.leftNode = newtree.leftNode.leftRotate();
                newtree = newtree.rightRotate();
            }else {
                newtree = newtree.rightRotate();
            }
        }
        return newtree;

    }



    /**
     * Finds the course that has minimum courseID in the AVL tree.
     * @return the node with the minimum course ID
     */
    public CourseAVLtree findMin(){
        if (leftNode instanceof  EmptyCourseAVLtree|| leftNode.courseID == null){
            return this;
        }else {
            return this.leftNode.findMin();
        }
    }

    /**
     * Performs an in-order traversal of the AVL tree and adds qualifying courses to the given list.
     * @param courses the list of qualifying courses
     * @param career the desired career (or null for any career)
     * @param delivery the desired delivery method (or null for any delivery method)
     * @param term the desired term (or null for any term)
     * @param code the desired course code (or null for any course code)
     * @param searchCourseID the desired course ID to search (or null/empty for any course ID)
     * @return the updated list of qualifying courses
     */
    public ArrayList<Course> inOrderBSTqualify(ArrayList<Course> courses,Course.CAREER career, Course.DELIVERY delivery,
                                               Course.TERM term, Course.CODE code,String searchCourseID){
        if (!(this instanceof EmptyCourseAVLtree)&&this.courseID!=null){
            DecimalFormat g1 = new DecimalFormat("0000");
            courses = this.leftNode.inOrderBSTqualify(courses,career,delivery,term,code,searchCourseID);

            if ( (career ==null ||  this.course.getCareer()==career )
                    && (delivery==null || this.course.getDelivery()==delivery )
                    && (term==null || this.course.getTerm()==term )
                    && (code==null || this.course.getCode()==code )
                    && (searchCourseID ==null ||searchCourseID.equals("")||  g1.format(this.courseID).contains(searchCourseID))){
                courses.add(this.course);
            }
            courses = this.rightNode.inOrderBSTqualify(courses,career,delivery,term,code,searchCourseID);
        }
        return courses;
    }

    /**
     * Calculates the balance factor of the current node.
     * @return the balance factor of the node
     */
    public int getBalanceFactor() {
        return leftNode.getHeight() - rightNode.getHeight();
    }

    /**
     * Calculates the height of the current node.
     * @return the height of the node
     */
    public int getHeight() {
        int leftNodeHeight = (leftNode == null||leftNode.courseID == null|| leftNode instanceof EmptyCourseAVLtree) ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = ( rightNode == null||rightNode.courseID == null||rightNode instanceof EmptyCourseAVLtree ) ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    /**
     * Performs a left rotation on the current node.
     * @return the new parent node after rotation
     */
    public CourseAVLtree leftRotate() {
        CourseAVLtree newParent = this.rightNode;
        CourseAVLtree newRightOfCurrent = newParent.leftNode;
        newParent.leftNode = this;
        newParent.leftNode.rightNode = newRightOfCurrent;
        return (CourseAVLtree) newParent;
    }

    /**
     * Performs a left rotation on the current node.
     * @return the new parent node after rotation
     */
    public CourseAVLtree rightRotate() {
        CourseAVLtree newParent = this.leftNode;
        CourseAVLtree newLeftOfCurrent = newParent.rightNode;
        newParent.rightNode = this;
        newParent.rightNode.leftNode = newLeftOfCurrent;
        return (CourseAVLtree) newParent; //
    }


    @NonNull
    public String toString() {
        return "{" +
                "CourseID=" + courseID +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }

    public static class EmptyCourseAVLtree extends CourseAVLtree{
        public CourseAVLtree find(Integer courseID) {
            return null;
        }
        public int getHeight() {
            return -1;
        }
        @NonNull
        public String toString() {
            return "{}";
        }
        public CourseAVLtree insert(Integer courseID,Course course) {
            return new CourseAVLtree(courseID,course);
        }
    }
}