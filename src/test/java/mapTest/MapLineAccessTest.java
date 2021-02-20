package mapTest;

import static org.junit.Assert.*;

import map.*;
import org.junit.Test;

import java.io.File;

/**
 * This class is for testing MapLineAccess
 * @author Zitao Wang
 * @version 1.0.0
 */

public class MapLineAccessTest {


    @Test
    /**
     * This method is test case1 for total lines in the map file.
     */
    public void test1(){
        File file = new File("test_02.map");
        MapLineAccess l_case1 = new MapLineAccess();
        int l_a = l_case1.getlines(file);
        assertEquals(30,l_a);
    }

    @Test
    /**
     * This method is test case1 for continent lines in the map file.
     */
    public void test2(){
        File file = new File("test_02.map");
        MapLineAccess l_case1 = new MapLineAccess();
        int l_a = l_case1.getcontinentlines(file);
        assertEquals(8,l_a);
    }

    @Test

    /**
     * This method is test case1 for country lines in the map file.
     */
    public void test3(){
        File file = new File("test_02.map");
        MapLineAccess l_case1 = new MapLineAccess();
        int l_a = l_case1.getcountrylines(file);
        assertEquals(12,l_a);
    }

    @Test
    /**
     * This method is test case1 for borders lines in the map file.
     */
    public void test4(){
        File file = new File("test_02.map");
        MapLineAccess l_case1 = new MapLineAccess();
        int l_a = l_case1.getborderlines(file);
        assertEquals(19,l_a);
    }

}