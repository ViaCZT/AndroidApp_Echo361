package com.example.echo361.Search;


import com.example.echo361.Course;
import java.util.ArrayList;

/**
 * @Author Yitao Zhang, u7504766
 * A class stores some methods for searching courses and students.
 */
public class Search {

    private final String[] allColleges = {"BIOL", "BUSN", "CBEA", "CHEM", "COMP", "ENGN",
            "HIST", "LAWS", "MATH", "MGMT", "PHYS"};

    /**
     * @Author Yitao Zhang, u7504766
     * Check that whether the parameter is a letter, regardless of case.
     * @param c A char waiting to be distinguished whether it is a letter
     * @return true is the char is a letter
     */
    public boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * @Author Yitao Zhang, u7504766
     *Get the first four letters and first four digits of the parameter, return as college code and course code respectively,
     * if the letters or digits are less than four, only the corresponding number of letters and digits will be return.
     * @param searchInput a String from the EditView
     * @return a String array,  college code and course code respectively, both not more than 4 letters or digits
     */
    public String[] inputToCourse(String searchInput){
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



    /**
     * @Author Yitao Zhang, u7504766
     * After using inputToCourse, determine if the collegeCode in it belongs to any known college.
     * The patameter can be part of a known college code and does not need to be identical, and regardless of case.
     * @param collegeCode a string from inputToCourse[0]
     * @return a boolean, return true if the parameter is a part of any of the known college codes
     */
    public boolean isInCollege(String collegeCode){
        if (!collegeCode.isEmpty()){
            boolean a;
            for (String i : allColleges){
                a = i.toLowerCase().contains(collegeCode.toLowerCase());
                if (a){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @Author Yitao Zhang, u7504766
     * If isInCollege return true, you can use getCollege to get all known college codes that contain this parameter, regardless of case.
     * @param collegeCode a String made isInCollege return true
     * @return an String ArrayList stored each college codes that contains this parameter, return null is the parameter is empty
     */
    public ArrayList<String> getCollege(String collegeCode){
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


    /**
     * @Author Yitao Zhang, u7504766
     * Use courseAVLtree.inOrderBSTqualify to get courses that meet all the restrictions from the parameter courseAVLtree
     * If onCampus and Online are both true, add all Blended courses to the Arraylist returned from this method
     * @param courseAVLtree a CourseAVLtree, get courses information from this parameter
     * @param underG boolean, if ture, add all undergraduate courses to the Arraylist returned from this method
     * @param postG boolean, if ture, add all graduate courses to the Arraylist returned from this method
     * @param onCampus boolean, if ture, add all on campus courses to the Arraylist returned from this method
     * @param Online boolean, if ture, add all online courses to the Arraylist returned from this method
     * @param courseCode String, indicate the course want to filter, from getCollege
     * @return
     */
    public ArrayList<Course> courseListFilted(CourseAVLtree courseAVLtree, Boolean underG, Boolean postG,
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
        }else if (!underG && postG && !onCampus && !Online){
            courselist2 = courseAVLtree.inOrderBSTqualify(courselist2, Course.CAREER.Postgraduate,null,null,null,courseCode);
        }else if (!underG && !postG && onCampus && !Online){
            courselist3 = courseAVLtree.inOrderBSTqualify(courselist3, null, Course.DELIVERY.OnCampus,null,null,courseCode);
        }else if (!underG && !postG && !onCampus && Online){
            courselist4 = courseAVLtree.inOrderBSTqualify(courselist4, null, Course.DELIVERY.Online,null,null,courseCode);
        }else if (!(underG || postG || onCampus || Online) || (underG && postG && onCampus && Online)){
            courselist5 = courseAVLtree.inOrderBSTqualify(courselist5, null, null,null,null,courseCode);
        }

        if (underG && postG && !onCampus && !Online){
            courselist6 = courseAVLtree.inOrderBSTqualify(courselist6, null,null,null,null,courseCode);
        } else if(underG && !postG && onCampus && !Online){
            courselist6 = courseAVLtree.inOrderBSTqualify(courselist6, Course.CAREER.Undergraduate, Course.DELIVERY.OnCampus,null,null,courseCode);
        } else if(underG && !postG && !onCampus && Online){
            courselist6 = courseAVLtree.inOrderBSTqualify(courselist6, Course.CAREER.Undergraduate, Course.DELIVERY.Online,null,null,courseCode);
        }

        if (!underG && postG && onCampus && !Online){
            courselist7 = courseAVLtree.inOrderBSTqualify(courselist7, Course.CAREER.Postgraduate, Course.DELIVERY.OnCampus,null,null,courseCode);
        } else if (!underG && postG && !onCampus && Online){
            courselist7 = courseAVLtree.inOrderBSTqualify(courselist7, Course.CAREER.Postgraduate, Course.DELIVERY.Online,null,null,courseCode);
        }

        if (!underG && !postG && onCampus && Online){
            courselist8 = courseAVLtree.inOrderBSTqualify(courselist8, null, Course.DELIVERY.Blended,null,null,courseCode);
        }



        if ( underG && postG && onCampus && !Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist9, null, Course.DELIVERY.OnCampus,null,null,courseCode);
        }else if ( underG && postG && !onCampus && Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist9, null, Course.DELIVERY.Online,null,null,courseCode);
        }else if ( underG && !postG && onCampus && Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist9, Course.CAREER.Undergraduate, Course.DELIVERY.Online,null,null,courseCode);
        }else if ( !underG && postG && onCampus && Online){
            courselist9 = courseAVLtree.inOrderBSTqualify(courselist9, Course.CAREER.Postgraduate, Course.DELIVERY.Online,null,null,courseCode);
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

    /**
     * @Author Yitao Zhang, u7504766
     * Get the index of a non-letter char in a string, stored in an ArrayList<Integer>.
     * @param inputPersed a string, will detect what is the index of a non-letter characters of it
     * @return an ArrayList<Integer>, return the index of a non-letter char in the parameter
     */
    public ArrayList<Integer> indexOfSpace(String inputPersed){
        ArrayList<Integer> indexOfSpce= new ArrayList<Integer>();
        for (int i = 0; i < inputPersed.length(); i++){
            if (!isWord(inputPersed.charAt(i))){
                indexOfSpce.add(i);
            }
        }
        return indexOfSpce;
    }

    /**
     * @Author Yitao Zhang, u7504766
     * Separates the parameter with non-chars, if there is no interval, returns the parameter as firstName and lastName as empty;
     * if there is an interval/intervals, the first part part is firstName and the last part is lastName.
     * If the parameter is empty, return both firstName and lastName are empty.
     * @param inputPersed a String, will detect what is firstName and lastName
     * @return a String array only contain two elements, the first element is firstName, the second element is lastName.
     */
    public String[] getName(String inputPersed){

        String firstName = "";
        String lastName = "";

        ArrayList space = indexOfSpace(inputPersed);
        if (space.size() == 0){
            firstName = inputPersed;
        }else if (space.size() == 1){
            firstName = inputPersed.substring(0, (Integer) space.get(0));
            lastName = inputPersed.substring((Integer) space.get(0)+1);
        } else{
            firstName = inputPersed.substring(0, (Integer) space.get(0));
            lastName = inputPersed.substring((Integer) space.get(space.size()-1) +1);
        }

        String[] result = new String[]{firstName, lastName};

        return result;

    }
}
