package com.example.echo361.CourseAVLTreeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.echo361.Course;
import com.example.echo361.Search.CourseAVLtree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

public class CourseAVLTreeTest {

    Course c1 = new Course("aa",1,null,null,null,null,null,null);
    Course c2 = new Course("bb",2,null,null,null,null,null,null);
    Course c3 = new Course("cc",3,null,null,null,null,null,null);
    Course c4 = new Course("dd",4,null,null,null,null,null,null);
    Course c5 = new Course("ee",5,null,null,null,null,null,null);
    Course c6 = new Course("ff",6,null,null,null,null,null,null);
    Course c7 = new Course("gg",7,null,null,null,null,null,null);

    Course c8 = new Course("hh",8,null,null,null,null,null,null);
    Course c9 = new Course("ii",9,null,null,null,null,null,null);

    CourseAVLtree avlTree = new CourseAVLtree(c1.getCourseID(),c1);

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInsert1()
    {
        CourseAVLtree courseAVLtree1 = new CourseAVLtree();
        courseAVLtree1=  courseAVLtree1.insert(c1.getCourseID(),c1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInsert2()
    {
        CourseAVLtree courseAVLtree1 = new CourseAVLtree();
        courseAVLtree1=  courseAVLtree1.insert(null,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyConstructor()
    {
        CourseAVLtree courseAVLtree1 = new CourseAVLtree(c1.getCourseID(),null,null,c1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyfind()
    {
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree.find(null);
    }

    @Test
    public void testInsertAndFind1() {
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        avlTree = avlTree.insert(c7.getCourseID(),c7);

        // Find existing courses
        assertEquals(c1, avlTree.find(1).course);
        assertEquals(c2, avlTree.find(2).course);
        assertEquals(c3, avlTree.find(3).course);

        // Find non-existing courses
        assertNull(avlTree.find(101));
        assertNull(avlTree.find(111));
    }

    @Test
    public void testInsert2() {

        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);

        // Find existing courses
        assertEquals(c1, avlTree.find(1).course);
        assertEquals(c2, avlTree.find(2).course);
        assertEquals(c3, avlTree.find(3).course);
    }

    @Test
    public void testInsert3() {
        avlTree  = new CourseAVLtree(c4.getCourseID(),c4);

        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);

        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);

        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);


        // Find existing courses
        assertEquals(c2, avlTree.find(2).course);
        assertEquals(c3, avlTree.find(3).course);

        // Find non-existing courses
        assertNull(avlTree.find(101));
        assertNull(avlTree.find(111));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptydelete()
    {
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.delete(7);
    }
    @Test
    public void testDelete() {
        // Insert some courses
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        // Delete a course
        avlTree = avlTree.delete(2);
        avlTree = avlTree.delete(7);
        avlTree = avlTree.delete(8);
        avlTree = avlTree.delete(3);
        avlTree = avlTree.delete(4);

        // Find remaining courses
        assertEquals(c1, avlTree.find(1).course);
        assertNull(avlTree.find(2));
        assertNull(avlTree.find(7));
        assertNull(avlTree.find(8));
        assertNull(avlTree.find(3));
        assertNull(avlTree.find(4));
        assertEquals(c9, avlTree.find(9).course);

    }

    @Test
    public void testDelete2() {
        // Insert some courses
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        // Delete a course
        avlTree = avlTree.delete(1);
        avlTree = avlTree.delete(2);
        avlTree = avlTree.delete(3);
        // Find remaining courses
        assertNull(avlTree.find(2));
    }

    @Test
    public void testDelete3() {
        // Insert some courses
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        // Delete a course
        avlTree = avlTree.delete(8);
        avlTree = avlTree.delete(7);
        avlTree = avlTree.delete(9);
        avlTree = avlTree.delete(3);
        avlTree = avlTree.delete(1);
        avlTree = avlTree.delete(2);

        // Find remaining courses
        assertNull(avlTree.find(8));
    }

    @Test
    public void testDelete4() {
        // Insert some courses
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        // Delete a course
        avlTree = avlTree.delete(7);
        avlTree = avlTree.delete(9);
        avlTree = avlTree.delete(3);
        avlTree = avlTree.delete(1);
        avlTree = avlTree.delete(2);

        // Find remaining courses
        assertNull(avlTree.find(7));
    }


    @Test
    public void testDelete5() {
        // Insert some courses
        avlTree = new CourseAVLtree(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c1.getCourseID(),c1);
        System.out.println(avlTree.toString());
        // Delete a course
        avlTree = avlTree.delete(7);
        avlTree = avlTree.delete(9);
        avlTree = avlTree.delete(3);
        avlTree = avlTree.delete(1);
        avlTree = avlTree.delete(2);

        // Find remaining courses
        assertNull(avlTree.find(7));
    }

    @Test
    public void testDelete6() {
        // Insert some courses
        avlTree = new CourseAVLtree(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c1.getCourseID(),c1);
        System.out.println(avlTree.toString());
        // Delete a course
        avlTree = avlTree.delete(1);
        avlTree = avlTree.delete(2);
        avlTree = avlTree.delete(3);
        avlTree = avlTree.delete(7);
        avlTree = avlTree.delete(8);
        avlTree = avlTree.delete(9);

        // Find remaining courses
        assertNull(avlTree.find(7));
    }
    @Test
    public void testDelete7() {
        // Insert some courses
        avlTree = new CourseAVLtree(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c1.getCourseID(),c1);
        // Delete a course
        avlTree = avlTree.delete(1);
        avlTree = avlTree.delete(3);
        avlTree = avlTree.delete(7);
        avlTree = avlTree.delete(9);
        avlTree = avlTree.delete(8);

        // Find remaining courses
        assertNull(avlTree.find(7));
    }



    @Test
    public void testFindMin() {

        // Insert some courses
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        avlTree = avlTree.insert(c1.getCourseID(),c1);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);

        // Find the minimum course

        // Check the minimum course ID
        assertEquals(c1, avlTree.findMin().course);
    }


    @Test
    public void testFindSuccessor() {
        avlTree  = new CourseAVLtree(c4.getCourseID(),c4);
        // Insert some courses
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c1.getCourseID(),c1);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c6.getCourseID(),c6);

        // Find the minimum course
        // Check the minimum course ID
        avlTree.setParent();
        assertEquals(c4, avlTree.findSuccessor(avlTree.find(c3.getCourseID())).course);
    }


    @Test
    public void testInOrderBSTqualify() {

        c1.setTerm(Course.TERM.Semester1);
        c2.setCareer(Course.CAREER.Undergraduate);
        c3.setDelivery(Course.DELIVERY.Blended);
        c5.setCode(Course.CODE.BIOL);

        avlTree = new CourseAVLtree(c6.getCourseID(),c6);
        avlTree = avlTree.insert(c5.getCourseID(),c5);
        avlTree = avlTree.insert(c9.getCourseID(),c9);
        avlTree = avlTree.insert(c8.getCourseID(),c8);
        avlTree = avlTree.insert(c4.getCourseID(),c4);
        avlTree = avlTree.insert(c2.getCourseID(),c2);
        avlTree = avlTree.insert(c3.getCourseID(),c3);
        avlTree = avlTree.insert(c7.getCourseID(),c7);
        avlTree = avlTree.insert(c1.getCourseID(),c1);


        ArrayList<Course> a1 = new ArrayList<>();
        ArrayList<Course> a2 = new ArrayList<>();
        ArrayList<Course> a3 = new ArrayList<>();
        ArrayList<Course> a4 = new ArrayList<>();
        ArrayList<Course> a5 = new ArrayList<>();
        ArrayList<Course> a6 = new ArrayList<>();

        a1 = avlTree.inOrderBSTqualify(a1,null,null,null,null,null);
        a2 = avlTree.inOrderBSTqualify(a2,null,null,Course.TERM.Semester1,null,null);
        a3 = avlTree.inOrderBSTqualify(a3,Course.CAREER.Undergraduate,null,null,null,null);
        a4 = avlTree.inOrderBSTqualify(a4,null, Course.DELIVERY.Blended,null,null,null);
        a5 = avlTree.inOrderBSTqualify(a5,null,null,null, Course.CODE.BIOL,null);
        a6 = avlTree.inOrderBSTqualify(a6,null,null,null,null,"6");
        // Check the qualified courses
        assertNotNull(a1);
        assertEquals(a2.get(0),c1);
        assertEquals(a3.get(0),c2);
        assertEquals(a4.get(0),c3);
        assertEquals(a5.get(0),c5);
        assertEquals(a6.get(0),c6);
    }
}
