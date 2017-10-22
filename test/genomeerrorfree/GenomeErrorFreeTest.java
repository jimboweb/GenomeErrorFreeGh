/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author jimstewart
 */
public class GenomeErrorFreeTest {
    char[] characters = {'A','C','T','G'};
    GenomeErrorFree gef = new GenomeErrorFree();
    char[] letters = {'A','C','T','G'};
    Random rnd = new Random();

    public GenomeErrorFreeTest() {
    }

    /**
     * Test of returnGenome method, of class GenomeErrorFree.
     */
    @Test
    public void testReturnGenome() {
        System.out.println("returnGenome");
        String unbrokenString = createUnbrokenString(30);
        ArrayList<String> input = createStringSegments(unbrokenString, 3, 8);
        GenomeErrorFree instance = new GenomeErrorFree();
        CircularString expResult = new CircularString(unbrokenString);
        String result = instance.returnGenome(input);
        CircularString cResult = new CircularString(result);
        assertEquals(expResult, cResult);
    }


    @Test
    public void testCircularString(){
        Random rnd = new Random();
        String str1 = "kjshfiui009830498)(*)(";
        String str1Wrap = "i009830498)(*)(kjshfiu";
        CircularString cStr1 = new CircularString(str1);
        CircularString cStr1Wrap = new CircularString(str1Wrap);
        assertEquals(cStr1,cStr1Wrap);
        String subStrExp = ")(kjs";
        String subStrTest = cStr1.subString(20, 3);
        assertEquals(subStrExp, subStrTest);
    }
    
    /**
     * Test of main method, of class GenomeErrorFree.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        GenomeErrorFree.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
        /**
     * Creates unbroken string to get genome strings from
     * @return string of ATCGG... etc. of unbrokenStrLen length
     */
    private String createUnbrokenString(int unbrokenStrLen){
        String unbrokenString = "";
        for(int i=0;i<unbrokenStrLen;i++){
            unbrokenString+=randChar();
        }
        return unbrokenString;
    }
    
    //TODO: this never adds the last character of the string. More importantly, 
    //this whole program doesn't take into account the fact that the string is 
    //circular. 
   private ArrayList<String> createStringSegments(String unbrokenString, int numberOfSegments, int strLen){
        Random rnd = new Random();
        CircularString cString = new CircularString(unbrokenString);
        ArrayList<String> rtrn = new ArrayList<>();
        //rtrn.add(cString.subString(0, cString.length()));
        int lastStrBegin = 0;
        while(lastStrBegin<cString.length()){
            rtrn.add(cString.subString(lastStrBegin, lastStrBegin+strLen));
            lastStrBegin=lastStrBegin+rnd.nextInt(strLen/2)+1;
        }
//        for(int i=rtrn.size();i<numberOfSegments;i++){
//            lastStrBegin = rnd.nextInt(cString.length());
//            rtrn.add(cString.subString(lastStrBegin, lastStrBegin+strLen));
//        }
        return rtrn;
    }
    
    private char randChar(){
        return letters[rnd.nextInt(letters.length)];
    }
    
    private boolean matchOverlaps(String str1, String str2, int overlap){
        int overlapLength = str1.length() - overlap;
        String str1Sub = str1.substring(overlap);
        String str2Sub = str2.substring(0, overlapLength);
        return (str1Sub.equals(str2Sub));
    }

    /**
     * Test of findAllOverlaps method, of class GenomeErrorFree.
     */
    
    /**
     * This is just a way to mark one string a particular
     * position overlaps with
     * gstr is the index of the string it overlaps wtih
     * loc is the location in that string
     */
    private class PositionOverlap{
        public int gStr;
        public int loc;
        public PositionOverlap(int gStr, int loc){
            this.gStr = gStr;
            this.loc = loc;
        }
    }
}
