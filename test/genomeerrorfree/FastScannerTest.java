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
public class FastScannerTest {
    
    public FastScannerTest() {
    }

    /**
     * Test of next method, of class FastScanner.
     */
    @Test
    public void testNext() throws Exception {
        System.out.println("next");
        FastScanner instance = new FastScanner();
        String expResult = "";
        String result = instance.next();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextInt method, of class FastScanner.
     */
    @Test
    public void testNextInt() throws Exception {
        System.out.println("nextInt");
        FastScanner instance = new FastScanner();
        int expResult = 0;
        int result = instance.nextInt();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
