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
        String unbrokenString = createUnbrokenString(10);
        ArrayList<String> input = createStringSegments(unbrokenString, 3, 3);
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

    
    //not tested yet
    @Test
    public void testFindOverlaps(){
        String str1 = "";
        String str2 = "";
        Random rnd = new Random();
        int trials = 20;
        int strLen = 5;
        for(int i=0;i<trials;i++){
            for(int j=0;j<strLen;j++){
                str1 += randChar();
            }
            int str2Start = rnd.nextInt((str1.length()+2));
            if(str2Start<str1.length()){
                str2 += str1.substring(str2Start);
            }
            for(int j=str2.length();j<strLen;j++){
                str2 += randChar();
            }
            GenomeString gstr1 = new GenomeString(str1);
            GenomeString gstr2 = new GenomeString(str2);
            gef.findOverlaps(gstr1, gstr2, 0);
            boolean foundMatch = false;
            for(int[] ol:gstr2.overlaps){
                if(matchOverlaps(gstr1.str, gstr2.str, ol[1])){
                    foundMatch = true;
                    System.out.println("Found match between " + str1 + " and " + str2 + ": " + str1.substring(ol[1]));

                }
            }
            String failString = "No match found between " + str1 + " and " + str2 +"\n";
            failString += "gstr1 string:" + gstr1.str + "\n";
            failString += "gstr2 string: " + gstr2.str + "\n";
            for(int[]ol:gstr2.overlaps){
                failString += "gstr2 overlap:" + ol[0] + ", " + ol[1] + "\n";
            }
            assertTrue(failString, foundMatch==(str2Start<str1.length()));
        }
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
            lastStrBegin=lastStrBegin+rnd.nextInt(strLen-1)+1;
        }
//        for(int i=rtrn.size();i<numberOfSegments;i++){
//            lastStrBegin = rnd.nextInt(cString.length());
//            rtrn.add(cString.subString(lastStrBegin, lastStrBegin+strLen));
//        }
        return rtrn;
    }
    /**
     * @deprecated 
     * @param unbrokenString the unbroken string we're getting the letters from
     * @param numStrings the number of strings being returned
     * @param strLen the length of each string (fixed for now)
     * @param expectedStrings is a copy of the return except with all the overlaps filled in,
     *                        should match the result of test
     * @return ArrayList of randomly sampled strings
     * TODO: Make sure that string is fully covered
     * Do this with a boolean array for each character
     * But don't need this yet
     */
    private ArrayList<GenomeString> createGenomeStrings(String unbrokenString, int numStrings, int strLen, ArrayList<GenomeString> expectedStrings){
        ArrayList<GenomeString> rtrn = new ArrayList<>();
        //polsArr is an array where each index of the string
        //represents all the strings that that position is part of
        //and where they are
        ArrayList<PositionOverlap>[] polsArr = new ArrayList[unbrokenString.length()];
        for(int i=0;i<polsArr.length;i++){
            polsArr[i]=new ArrayList<>();
        }
        //for each string you're going to create
        for(int i=0;i<numStrings;i++){
            int startIndex = rnd.nextInt(unbrokenString.length()-strLen);
            GenomeString g =new GenomeString(
                    unbrokenString.substring(
                            startIndex, startIndex+strLen));
            rtrn.add(g);
            GenomeString gCopy = new GenomeString(g.str, startIndex);
            expectedStrings.add(gCopy);
            for(int j=0;j<g.str.length();j++){
                int currentLoc = startIndex+j;
                polsArr[startIndex+j].add(new PositionOverlap(i, j));
            }
        }
        for(int i=0;i<expectedStrings.size();i++){
            GenomeString exStr = expectedStrings.get(i);
            int strPos = exStr.location;
            ArrayList<PositionOverlap> pols = polsArr[strPos];
            for(PositionOverlap pol:pols){
                int[] ols = new int[2];
                ols[0]=pol.gStr;
                ols[1]=pol.loc;
                if(!(ols[0]==0 && ols[1]==0))
                    exStr.overlaps.add(ols);
            }
        }
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
    @Test
    public void testFindAllOverlaps() {
        System.out.println("findAllOverlaps");
        ArrayList<GenomeString> gs = null;
        GenomeErrorFree instance = new GenomeErrorFree();
        ArrayList<GenomeString> expResult = new ArrayList<>();
        String unbrokenString = createUnbrokenString(20);
        gs = createGenomeStrings(unbrokenString, 20, 5, expResult);
        ArrayList<GenomeString> result = instance.findAllOverlaps(gs);
        assertEquals(expResult, result);
    }
    
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
