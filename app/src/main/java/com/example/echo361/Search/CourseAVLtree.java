package com.example.echo361.Search;
import androidx.annotation.NonNull;

import com.example.echo361.Course;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CourseAVLtree {
    public Integer courseID;
    public Course course;
    public CourseAVLtree leftNode;
    public CourseAVLtree rightNode;
    public CourseAVLtree parent;

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

    public CourseAVLtree find(Integer courseID){
        if (courseID == null)
            throw new IllegalArgumentException("Input cannot be null");

        assert this.courseID != null;
        if (courseID.compareTo(this.courseID) == 0) {
            return this;
        } else if (courseID.compareTo(this.courseID) < 0) {
            return leftNode.find(courseID);
        } else {
            return rightNode.find(courseID);
        }
    }

    public CourseAVLtree insert(Integer courseID,Course course) {

        if (courseID == null||course == null)
            throw new IllegalArgumentException("Input cannot be null");

        CourseAVLtree newTree = new CourseAVLtree(this.courseID,leftNode,rightNode,this.course);
        if (courseID.compareTo(this.courseID) > 0) {
            newTree = new CourseAVLtree(this.courseID,leftNode,rightNode.insert(courseID,course),this.course);
        } else if (courseID.compareTo(this.courseID) < 0) {
            newTree = new CourseAVLtree(this.courseID,leftNode.insert(courseID,course),rightNode,this.course);
        }

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


    public CourseAVLtree findSuccessor(CourseAVLtree node){
        if ((!(node.rightNode instanceof  EmptyCourseAVLtree ))|| node.rightNode.courseID != null){
            return node.rightNode.findMin();
        }
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

    public CourseAVLtree delete(Integer courseID){


        CourseAVLtree newtree = new CourseAVLtree(this.courseID,this.leftNode,this.rightNode,this.course);
        if (courseID.compareTo(this.courseID) > 0) {
            newtree =  new CourseAVLtree(this.courseID,leftNode,rightNode.delete(courseID), this.course);
        } else if (courseID.compareTo(this.courseID) < 0) {
            newtree = new CourseAVLtree(this.courseID,leftNode.delete(courseID),rightNode, this.course);
        }else if(courseID.compareTo(this.courseID) == 0){
            if ((this.leftNode.courseID == null||this.leftNode instanceof EmptyCourseAVLtree)&&(this.rightNode instanceof EmptyCourseAVLtree||this.rightNode.courseID==null)){
                return new EmptyCourseAVLtree();
            } else if ((this.leftNode.courseID == null||this.leftNode instanceof EmptyCourseAVLtree)) {
                return this.rightNode;
            } else if ((this.rightNode instanceof EmptyCourseAVLtree||this.rightNode.courseID==null)) {
                return this.leftNode;
            }else {
                CourseAVLtree successor = findSuccessor(this);
                newtree = new CourseAVLtree(this.courseID,leftNode,rightNode,this.course);
                newtree = newtree.delete(successor.courseID);
                newtree.course = successor.course;
                newtree.courseID = successor.courseID;
            }
        }else {
            throw new IllegalArgumentException("can not find this course");
        }

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




    public CourseAVLtree findMin(){
        if (leftNode instanceof  EmptyCourseAVLtree|| leftNode.courseID == null){
            return this;
        }else {
            return this.leftNode.findMin();
        }
    }

    /**
     *
     * @param courses
     * @param career
     * @param delivery
     * @param term
     * @param code
     * @param searchCourseID
     * @return
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

    public int getBalanceFactor() {
        return leftNode.getHeight() - rightNode.getHeight();
    }
    public int getHeight() {
        // Check whether leftNode or rightNode are EmptyTree
        int leftNodeHeight = (leftNode == null||leftNode.courseID == null|| leftNode instanceof EmptyCourseAVLtree) ? 0 : 1 + leftNode.getHeight();
        int rightNodeHeight = ( rightNode == null||rightNode.courseID == null||rightNode instanceof EmptyCourseAVLtree ) ? 0 : 1 + rightNode.getHeight();
        return Math.max(leftNodeHeight, rightNodeHeight);
    }

    public CourseAVLtree leftRotate() {
        CourseAVLtree newParent = this.rightNode;
        CourseAVLtree newRightOfCurrent = newParent.leftNode;
        newParent.leftNode = this;
        newParent.leftNode.rightNode = newRightOfCurrent;
        return (CourseAVLtree) newParent;
    }

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
                '}' + "\r\n";
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
            // The creation of a new Tree, hence, return tree.
            return new CourseAVLtree(courseID,course);
        }
    }
}