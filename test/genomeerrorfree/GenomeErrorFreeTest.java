/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import genomeerrorfree.GenomeErrorFreeTest.TestStringSeg;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.stream.Collectors;
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
<<<<<<< HEAD
        for(int i=0;i<5;i++){
=======
        for(int i=0;i<200;i++){
>>>>>>> NoPackageNoTree
            int numberOfSegments = 1618; 
            int strLen = 100;
            int maxOlPoint = rnd.nextInt(50)+10;
            System.out.println("returnGenome trial " + i);
            String unbrokenString = createUnbrokenString(0, true);
            ReturnGenomeInputAndPath giap = new ReturnGenomeInputAndPath(unbrokenString, numberOfSegments, strLen, maxOlPoint);
            //giap.mixUpArrayListAndPath();
            ArrayList<String> input = giap.inputAsStringList();
            GenomeErrorFree instance = new GenomeErrorFree();
            CircularString expResult = new CircularString(unbrokenString);
            String result = instance.returnGenome(input);
            CircularString cResult = new CircularString(result);
            assertEquals(getTRGFailString(giap, unbrokenString, result), expResult, cResult);
        }
    }
    
    private String getTRGFailString(ReturnGenomeInputAndPath giap, String expectedString, String actualString){
        String failString = "";
        failString += "failed on string \n";
        failString += expectedString;
        failString += "instead got\n";
        failString += actualString;
        failString += "input:\n";
        for(TestStringSeg seg:giap.input){
            failString += seg.str;
            failString += "\n";
        }
        return failString;
    }


   
     
    private Integer[][] createExpectedPath(ReturnGenomeInputAndPath input,ArrayList<String> inputStrings){
        Integer[][] expectedPath = new Integer[input.input.size()][2];
        for(int i=0;i<input.input.size();i++){
            TestStringSeg n = input.input.get(i);
            inputStrings.add(n.str);
            expectedPath[i][0] = n.getOverlappedByIndex(0); 
            expectedPath[i][1] = n.getOverlappedByOlPoint(0);
            
        }
        return expectedPath;
    }
    
        /**
     * Creates unbroken string to get genome strings from
     * @return string of ATCGG... etc. of unbrokenStrLen length
     */
    private String createUnbrokenString(int unbrokenStrLen, boolean actualGenome){
        if(actualGenome)
            //debug remove ";//
            return "gagttttatcgcttccatgacgcagaagttaacactttcggatatttctgatgagtcgaaaaattatcttgataaagcaggaattactactgcttgtttacgaattaaatcgaagtggactgctggcggaaaatgagaaaattcgacctatccttgcgcagctcgagaagctcttactttgcgacctttcgccatcaactaacgattctgtcaaaaactgacgcgttggatgaggagaagtggcttaatatgcttggcacgttcgtcaaggactggtttagatatgagtcacattttgttcatggtagagattctcttgttgacattttaaaagagcgtggattactatctgagtccgatgctgttcaaccactaataggtaagaaatcatgagtcaagttactgaacaatccgtacgtttccagaccgctttggcctctattaagctcattcaggcttctgccgttttggatttaaccgaagatgatttcgattttctgacgagtaacaaagtttggattgctactgaccgctctcgtgctcgtcgctgcgttgaggcttgcgtttatggtacgctggactttgtgggataccctcgctttcctgctcctgttgagtttattgctgccgtcattgcttattatgttcatcccgtcaacattcaaacggcctgtctcatcatggaaggcgctgaatttacggaaaacattattaatggcgtcgagcgtccggttaaagccgctgaattgttcgcgtttaccttgcgtgtacgcgcaggaaacactgacgttcttactgacgcagaagaaaacgtgcgtcaaaaattacgtgcggaaggagtgatgtaatgtctaaaggtaaaaaacgttctggcgctcgccctggtcgtccgcagccgttgcgaggtactaaaggcaagcgtaaaggcgctcgtctttggtatgtaggtggtcaacaattttaattgcaggggcttcggccccttacttgaggataaattatgtctaatattcaaactggcgccgagcgtatgccgcatgacctttcccatcttggcttccttgctggtcagattggtcgtcttattaccatttcaactactccggttatcgctggcgactccttcgagatggacgccgttggcgctctccgtctttctccattgcgtcgtggccttgctattgactctactgtagacatttttactttttatgtccctcatcgtcacgtttatggtgaacagtggattaagttcatgaaggatggtgttaatgccactcctctcccgactgttaacactactggttatattgaccatgccgcttttcttggcacgattaaccctgataccaataaaatccctaagcatttgtttcagggttatttgaatatctataacaactattttaaagcgccgtggatgcctgaccgtaccgaggctaaccctaatgagcttaatcaagatgatgctcgttatggtttccgttgctgccatctcaaaaacatttggactgctccgcttcctcctgagactgagctttctcgccaaatgacgacttctaccacatctattgacattatgggtctgcaagctgcttatgctaatttgcatactgaccaagaacgtgattacttcatgcagcgttaccatgatgttatttcttcatttggaggtaaaacctcttatgacgctgacaaccgtcctttacttgtcatgcgctctaatctctgggcatctggctatgatgttgatggaactgaccaaacgtcgttaggccagttttctggtcgtgttcaacagacctataaacattctgtgccgcgtttctttgttcctgagcatggcactatgtttactcttgcgcttgttcgttttccgcctactgcgactaaagagattcagtaccttaacgctaaaggtgctttgacttataccgatattgctggcgaccctgttttgtatggcaacttgccgccgcgtgaaatttctatgaaggatgttttccgttctggtgattcgtctaagaagtttaagattgctgagggtcagtggtatcgttatgcgccttcgtatgtttctcctgcttatcaccttcttgaaggcttcccattcattcaggaaccgccttctggtgatttgcaagaacgcgtacttattcgccaccatgattatgaccagtgtttccagtccgttcagttgttgcagtggaatagtcaggttaaatttaatgtgaccgtttatcgcaatctgccgaccactcgcgattcaatcatgacttcgtgataaaagattgagtgtgaggttataacgccgaagcggtaaaaattttaatttttgccgctgaggggttgaccaagcgaagcgcggtaggttttctgcttaggagtttaatcatgtttcagacttttatttctcgccataattcaaactttttttctgataagctggttctcacttctgttactccagcttcttcggcacctgttttacagacacctaaagctacatcgtcaacgttatattttgatagtttgacggttaatgctggtaatggtggttttcttcattgcattcagatggatacatctgtcaacgccgctaatcaggttgtttctgttggtgctgatattgcttttgatgccgaccctaaattttttgcctgtttggttcgctttgagtcttcttcggttccgactaccctcccgactgcctatgatgtttatcctttgaatggtcgccatgatggtggttattataccgtcaaggactgtgtgactattgacgtccttccccgtacgccgggcaataacgtttatgttggtttcatggtttggtctaactttaccgctactaaatgccgcggattggtttcgctgaatcaggttattaaagagattatttgtctccagccacttaagtgaggtgatttatgtttggtgctattgctggcggtattgcttctgctcttgctggtggcgccatgtctaaattgtttggaggcggtcaaaaagccgcctccggtggcattcaaggtgatgtgcttgctaccgataacaatactgtaggcatgggtgatgctggtattaaatctgccattcaaggctctaatgttcctaaccctgatgaggccgcccctagttttgtttctggtgctatggctaaagctggtaaaggacttcttgaaggtacgttgcaggctggcacttctgccgtttctgataagttgcttgatttggttggacttggtggcaagtctgccgctgataaaggaaaggatactcgtgattatcttgctgctgcatttcctgagcttaatgcttgggagcgtgctggtgctgatgcttcctctgctggtatggttgacgccggatttgagaatcaaaaagagcttactaaaatgcaactggacaatcagaaagagattgccgagatgcaaaatgagactcaaaaagagattgctggcattcagtcggcgacttcacgccagaatacgaaagaccaggtatatgcacaaaatgagatgcttgcttatcaacagaaggagtctactgctcgcgttgcgtctattatggaaaacaccaatctttccaagcaacagcaggtttccgagattatgcgccaaatgcttactcaagctcaaacggctggtcagtattttaccaatgaccaaatcaaagaaatgactcgcaaggttagtgctgaggttgacttagttcatcagcaaacgcagaatcagcggtatggctcttctcatattggcgctactgcaaaggatatttctaatgtcgtcactgatgctgcttctggtgtggttgatatttttcatggtattgataaagctgttgccgatacttggaacaatttctggaaagacggtaaagctgatggtattggctctaatttgtctaggaaataaccgtcaggattgacaccctcccaattgtatgttttcatgcctccaaatcttggaggcttttttatggttcgttcttattacccttctgaatgtcacgctgattattttgactttgagcgtatcgaggctcttaaacctgctattgaggcttgtggcatttctactctttctcaatccccaatgcttggcttccataagcagatggataaccgcatcaagctcttggaagagattctgtcttttcgtatgcagggcgttgagttcgataatggtgatatgtatgttgacggccataaggctgcttctgacgttcgtgatgagtttgtatctgttactgagaagttaatggatgaattggcacaatgctacaatgtgctcccccaacttgatattaataacactatagaccaccgccccgaaggggacgaaaaatggtttttagagaacgagaagacggttacgcagttttgccgcaagctggctgctgaacgccctcttaaggatattcgcgatgagtataattaccccaaaaagaaaggtattaaggatgagtgttcaagattgctggaggcctccactatgaaatcgcgtagaggctttgctattcagcgtttgatgaatgcaatgcgacaggctcatgctgatggttggtttatcgtttttgacactctcacgttggctgacgaccgattagaggcgttttatgataatcccaatgctttgcgtgactattttcgtgatattggtcgtatggttcttgctgccgagggtcgcaaggctaatgattcacacgccgactgctatcagtatttttgtgtgcctgagtatggtacagctaatggccgtcttcatttccatgcggtgcactttatgcggacacttcctacaggtagcgttgaccctaattttggtcgtcgggtacgcaatcgccgccagttaaatagcttgcaaaatacgtggccttatggttacagtatgcccatcgcagttcgctacacgcaggacgctttttcacgttctggttggttgtggcctgttgatgctaaaggtgagccgcttaaagctaccagttatatggctgttggtttctatgtggctaaatacgttaacaaaaagtcagatatggaccttgctgctaaaggtctaggagctaaagaatggaacaactcactaaaaaccaagctgtcgctacttcccaagaagctgttcagaatcagaatgagccgcaacttcgggatgaaaatgctcacaatgacaaatctgtccacggagtgcttaatccaacttaccaagctgggttacgacgcgacgccgttcaaccagatattgaagcagaacgcaaaaagagagatgagattgaggctgggaaaagttactgtagccgacgttttggcggcgcaacctgtgacgacaaatctgctcaaatttatgcgcgcttcgataaaaatgattggcgtatccaacctgca";
           
        String unbrokenString = "";
        for(int i=0;i<unbrokenStrLen;i++){
            unbrokenString+=randChar();
        }
        return unbrokenString;
    }
    /**
      * @param input
     * @return 
     */
    private ArrayList<String> mixUpStringSegments(ArrayList<String> input){
        for(int i=0;i<input.size();i++){
            input = swapSegments(input,i,rnd.nextInt(input.size()));

        }
        return input;   
        
    }
    
    
    
    private char randChar(){
        return letters[rnd.nextInt(letters.length)];
    }
    
    
    
    /**
     * This allows me to save the correct input and path 
     * input is the actual arraylist of input strings
     * path is the string and where they overlap
     */
    private class ReturnGenomeInputAndPath{
        ArrayList<TestStringSeg> input;
        
        private ReturnGenomeInputAndPath(int pathSize){
            input = new ArrayList<>();
         }
        
        public ReturnGenomeInputAndPath(String unbrokenString, int numberOfSegments, int strLen, int maxOlPoint){
            input = new ArrayList<>();
            createStringSegments(unbrokenString, numberOfSegments, strLen, maxOlPoint);
        }
        
        public ArrayList<String> inputAsStringList(){
            ArrayList<String> rtrn = new ArrayList<>();
            for(TestStringSeg n:input){
                rtrn.add(n.str);
            }
            return rtrn;
        }
        
        
        /**
         * This will create the string segments unsorted, so the path should be {0, ol}, {1, ol}, {2, ol}...etc
         * @param unbrokenString the original string
         * @param numberOfSegments number of segments
         * @param strLen length of a segment
         * @param maxOlPoint the maximum point of overlap
         */
        private void createStringSegments(String unbrokenString, int numberOfSegments, int strLen, int maxOlPoint){
            Random rnd = new Random();
            String multString = unbrokenString;
            ArrayList<String> rtrn;
            int lastStrBegin = 0;
            String nextString;
            int absLocation = 0;
            for(int i=0;i<numberOfSegments;i++){
                nextString = multString.substring(lastStrBegin, lastStrBegin+strLen);
                //System.out.println(nextString);
                multString = multString.substring(lastStrBegin);
                TestStringSeg nextStringSeg = new TestStringSeg(nextString, i, absLocation);
                input.add(nextStringSeg);
                        
                lastStrBegin =  rnd.nextInt(maxOlPoint); 
                absLocation = (absLocation + lastStrBegin)%unbrokenString.length();
                if((lastStrBegin+200)>multString.length())
                    multString += unbrokenString;
            }
            for(TestStringSeg seg:input){
                findAllOverlaps(seg, input, strLen, unbrokenString.length());
            }

            
            int x=0;
        }
        
        /**
         * general function to assign all overlaps using absolute location
         * @param seg the segment to find overlaps for
         * @param input all the segments
         * @param strLen the length of the strings
         */
        private void findAllOverlaps(TestStringSeg seg, ArrayList<TestStringSeg> input, int strLen, int unbrStrLen){
            ArrayList<TestStringSeg> possibleOverlaps = (ArrayList<TestStringSeg>)input
                    .stream()
                    .filter(olSeg->
                            Math.abs((olSeg.absLocation + unbrStrLen - seg.absLocation) % unbrStrLen)<strLen
                            &&!olSeg.equals(seg))
                    .collect(Collectors.toList());
            possibleOverlaps.stream().forEach((olSeg) -> {
                assignOverlap(seg, olSeg, strLen, unbrStrLen);
            });
        }
        
        /**
         * assigns overlap only at end
         * @param seg the segment to overlap
         * @param olStringSeg the last string segment
         * @return 
         */
        private void assignOverlap(TestStringSeg seg, TestStringSeg olStringSeg, int strLen, int unbrStrLen){
            TestStringSeg laterString = seg.absLocation>olStringSeg.absLocation?seg:olStringSeg;
            TestStringSeg earlierString = laterString.equals(seg)?olStringSeg:seg;
            int earlierStringModLocation = earlierString.absLocation+unbrStrLen;
            int earlierStringCircularLocation = earlierString.absLocation;
            int laterStringLocation = laterString.absLocation;
            if(earlierStringModLocation-laterString.absLocation<strLen){
                earlierStringCircularLocation = earlierStringModLocation;
            }
            int olPoint = Math.abs(earlierStringCircularLocation - laterStringLocation);
            laterString.addOverlaps(earlierString, olPoint);
            earlierString.addOverlappedBy(laterString, olPoint);
            int x=0;        
        }
        
        private void mixUpArrayListAndPath(){
            for(int i=0;i<input.size();i++){
                int swapWith = rnd.nextInt(input.size());
                input = swapSegments(input,i, swapWith);
            }

        }
        
        
           
        private ArrayList<TestStringSeg> swapSegments(ArrayList<TestStringSeg> segments, int first, int second){
            TestStringSeg firstNode = segments.get(first).copy();
            segments.set(first, segments.get(second));
            segments.set(second, firstNode);
            return segments;
        }
        
        
    }
    
    
    /**
     * A test of the string segment
     * This should have really just been a subclass
     * but I just don't want to fix it
     */
    class TestStringSeg {
        String str;
        int index;
        final int absLocation;
        ArrayList<TestOverlap> overlappedBy;
        ArrayList<TestOverlap> overlaps;
        
        public TestStringSeg copy(){
            TestStringSeg rtrn = new TestStringSeg(str,index,absLocation);
            rtrn.overlappedBy=overlappedBy;
            rtrn.overlaps=overlaps;
            return rtrn;
        }
        
        public TestStringSeg(String str, int index, int absLocation){
            this.str = str;
            this.index = index;
            this.absLocation = absLocation;
            this.overlappedBy=new ArrayList<>();
            this.overlaps = new ArrayList<>();
        }
        
        public void addOverlappedBy(TestStringSeg sst, int overlapPoint){
            overlappedBy.add(new TestOverlap(false, sst, overlapPoint));
        }
        
        public TestOverlap getOverlappedBy(int index){
            return overlappedBy.get(index);
        }
        
        public int getOverlappedByIndex(int index){
            return overlappedBy.get(index).stringSeg.index;
        }
        
        public int getOverlappedByOlPoint(int index){
            return overlappedBy.get(index).overlapPoint;
        }
        
        public ArrayList<Integer> getOverlappedByIndexList(){
            ArrayList<Integer> rtrn = new ArrayList<>();
            for(TestOverlap olby:overlappedBy){
                rtrn.add(olby.stringSeg.index);
            }
            return rtrn;
        }
        
        public ArrayList<TestOverlap> getOverlappedByList(){
            return overlappedBy;
        }
        
        public void addOverlaps(TestStringSeg sst, int overlapPoint){
            overlaps.add(new TestOverlap(true, sst, overlapPoint));
        }
        
        public TestOverlap getOverlaps(int index){
            return overlaps.get(index);
        }

        public int getOverlapsIndex(int index){
            return overlaps.get(index).stringSeg.index;
        }
        
        public ArrayList<TestOverlap> getOverlapsList(){
            return overlaps;
        }
        
        public ArrayList<Integer> getOverlapsIndexList(){
            ArrayList<Integer> rtrn = new ArrayList<>();
            for(TestOverlap ol:overlaps){
                rtrn.add(ol.stringSeg.index);
            }
            return rtrn;
        }
        
    }
    
    /**
     * Just a class to keep the overlaps in the TestStringSegment
     */
    class TestOverlap {
        public TestOverlap(boolean suffix, TestStringSeg stringSeg, int overlapPoint){
            this.suffix = suffix;
            this.stringSeg = stringSeg;
            this.overlapPoint = overlapPoint;
        }
        boolean suffix;
        TestStringSeg stringSeg;
        int overlapPoint;
    }

    /**
     * Test of findAllOverlaps method, of class GenomeErrorFree.
     */
    @Test
    public void testFindAllOverlaps() {
        System.out.println("findAllOverlaps");
        OverlapGraph gr = null;
        GenomeErrorFree instance = new GenomeErrorFree();
        OverlapGraph expResult = null;
        OverlapGraph result = instance.findAllOverlaps(gr);
        assertEquals(expResult, result);
        // TODO LATER review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    



}
