/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jimstewart
 */
public class CircularStringTest {
    
    public CircularStringTest() {
    }

    /**
     * Test of toString method, of class CircularString.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        CircularString instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of length method, of class CircularString.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        CircularString instance = null;
        int expResult = 0;
        int result = instance.length();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of charAt method, of class CircularString.
     */
    @Test
    public void testCharAt() {
        System.out.println("charAt");
        int index = 0;
        CircularString instance = null;
        char expResult = ' ';
        char result = instance.charAt(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subSequence method, of class CircularString.
     */
    @Test
    public void testSubSequence() {
        System.out.println("subSequence");
        int start = 0;
        int end = 0;
        CircularString instance = null;
        CharSequence expResult = null;
        CharSequence result = instance.subSequence(start, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subString method, of class CircularString.
     */
    @Test
    public void testSubString_int() {
        System.out.println("subString");
        int index = 0;
        CircularString instance = null;
        String expResult = "";
        String result = instance.subString(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subString method, of class CircularString.
     */
    @Test
    public void testSubString_int_int() {
        System.out.println("subString");
        int start = 0;
        int end = 0;
        CircularString instance = null;
        String expResult = "";
        String result = instance.subString(start, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of concatenate method, of class CircularString.
     */
    @Test
    public void testConcatenate() {
        System.out.println("concatenate");
        String str = "";
        CircularString instance = null;
        CircularString expResult = null;
        CircularString result = instance.concatenate(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class CircularString.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        String s = "";
        CircularString instance = null;
        boolean expResult = false;
        boolean result = instance.contains(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class CircularString.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object other = null;
        CircularString instance = null;
        boolean expResult = false;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class CircularString.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        CircularString instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
