/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import genomeerrorfree.GenomeErrorFree.CircularString;
import genomeerrorfree.GenomeErrorFreeTest.TestStringSeg;
import genomeerrorfree.GenomeErrorFree.OverlapGraph.StringSegment;
import genomeerrorfree.GenomeErrorFree.OverlapGraph.SuffixOverlap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
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
        for(int i=0;i<5;i++){
            int numberOfSegments = 1618; 
            int strLen = 100;
<<<<<<< HEAD
            int maxOlPoint = 20;
            System.out.println("returnGenome");
=======
            int maxOlPoint = rnd.nextInt(50)+10;
            System.out.println("returnGenome trial " + i);
>>>>>>> EqualOverlapTree
            String unbrokenString = createUnbrokenString(0, true);
            ReturnGenomeInputAndPath giap = new ReturnGenomeInputAndPath(unbrokenString, numberOfSegments, strLen, maxOlPoint);
            //giap.mixUpArrayListAndPath();
            ArrayList<String> input = giap.inputAsStringList();
            GenomeErrorFree instance = new GenomeErrorFree();
            CircularString expResult = gef.new CircularString(unbrokenString);
            String result = instance.returnGenome(input);
<<<<<<< HEAD
            CircularString cResult = gef.new CircularString(result);
            assertEquals("Failed test number " + i + " got string " + result, expResult, cResult);
=======
            CircularString cResult = new CircularString(result);
            assertEquals(getTRGFailString(giap, unbrokenString, result), expResult, cResult);
>>>>>>> EqualOverlapTree
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


    @Test
    public void testCircularString(){
<<<<<<< HEAD
        String str1 = "kjshfiui009830498)(*)(";
        String str1Wrap = "i009830498)(*)(kjshfiu";
        CircularString cStr1 = gef.new CircularString(str1);
        CircularString cStr1Wrap = gef.new CircularString(str1Wrap);
        assertEquals(cStr1,cStr1Wrap);
        String subStrExp = ")(kjs";
        String subStrTest = cStr1.subString(20, 3);
        assertEquals(subStrExp, subStrTest);
        for(int i=0;i<1000;i++){
            int start = rnd.nextInt(str1.length());
            int end = rnd.nextInt(str1.length());
            String testContainsStr = cStr1.subString(start, start+end);
            assertTrue(cStr1.contains(testContainsStr));
        }
=======
        int maxStrLen = 10;
        int minStrLen = 5;
        int trials = 100;
        for(int trial=0;trial<trials;trial++){
            System.out.println("trial " + trial);
            int strLen = rnd.nextInt(maxStrLen)+minStrLen;
            String orgStr = "";
            for(int i=0;i<strLen;i++){
                orgStr += randChar();
            }
            int strBr = rnd.nextInt(strLen-1);
            String firstHalf = orgStr.substring(0, strBr);
            String secondHalf = orgStr.substring(strBr);
            String cmpStr = secondHalf+firstHalf;
            boolean eqls = true;
            int possibleError = rnd.nextInt(12);
            if(possibleError<4){
                eqls=false;
                int replaceCharLoc = rnd.nextInt(strLen-1);
                char replaceChar = cmpStr.charAt(replaceCharLoc);
                char newChar;
                do{
                    newChar = randChar();
                } while(newChar==replaceChar);
                cmpStr = cmpStr.substring(0,replaceCharLoc-1) + newChar + cmpStr.substring(replaceCharLoc);
            } else if(possibleError<6){
                eqls=false;
                int removeOrAdd = rnd.nextInt(2);
                int removeOrAddLocation = rnd.nextInt(strLen-2)+1;
                if(removeOrAdd<1){
                    cmpStr = cmpStr.substring(0,removeOrAddLocation) + randChar() + cmpStr.substring(removeOrAddLocation);
                } else {
                    cmpStr = cmpStr.substring(0,removeOrAddLocation-1) + cmpStr.substring(removeOrAddLocation);
                }
            }
            System.out.println("original string:  " + orgStr);
            System.out.println("compare string:" + cmpStr);
            CircularString cOrgStr = new CircularString(orgStr);
            CircularString cCmpStr = new CircularString(cmpStr);
            boolean testEqls = cOrgStr.equals(cCmpStr);
            System.out.println("Expected result " + eqls + " actual result: " + testEqls);
            assertEquals("failed on string " + orgStr + " comparison string " + cmpStr ,eqls,testEqls);
        }   
        
>>>>>>> EqualOverlapTree
    }
        @Test
        public void testStringSegment(){
            ArrayList<String> l = new ArrayList<>();
            l.add("foo");
            l.add("bar");
            l.add("qaz");
            GenomeErrorFree.OverlapGraph og = gef.new OverlapGraph(l);
            og.stringSegments[0].suffixOverlaps.add(og.new SuffixOverlap(og.stringSegments[0],2,4));
            og.stringSegments[1].suffixOverlaps.add(og.new SuffixOverlap(og.stringSegments[1], 1,3));
            og.stringSegments[2].suffixOverlaps.add(og.new SuffixOverlap(og.stringSegments[2], 2,5));
            PriorityQueue pq = new PriorityQueue<>();
            pq.addAll(Arrays.asList(og.stringSegments));
            ArrayList<GenomeErrorFree.OverlapGraph.StringSegment> expectedResult = new ArrayList<>();
            expectedResult.add(og.stringSegments[1]);
            expectedResult.add(og.stringSegments[0]);
            expectedResult.add(og.stringSegments[2]);
            ArrayList<GenomeErrorFree.OverlapGraph.StringSegment> actualResult = new ArrayList<>();
            while(!pq.isEmpty()){
                actualResult.add((GenomeErrorFree.OverlapGraph.StringSegment)pq.poll());
            }
            assertArrayEquals(expectedResult.toArray(), actualResult.toArray());
        }

    /**
     * Test of main method, of class GenomeErrorFree.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        GenomeErrorFree.main(args);
        fail("The test case is a prototype.");
    }

    /**
     * tests the greedyHamiltonianPath method
     */
    @Test
    public void testGreedyHamiltonianPath(){
        String originalString = createUnbrokenString(1000, false);
        ReturnGenomeInputAndPath input = new ReturnGenomeInputAndPath(originalString,400,30,10);
        
        GenomeErrorFree instance = new GenomeErrorFree();
        ArrayList<String> inputStrings = new ArrayList<>();
        Integer[][] expectedPath = createExpectedPath(input, inputStrings);
//        input.mixUpArrayListAndPath();
        GenomeErrorFree.OverlapGraph graph = gef.new OverlapGraph(inputStrings);
        graph = instance.findAllOverlaps(graph);
        Integer[][] path = instance.greedyHamiltonianPath(graph);
        for(int i=0;i<path.length;i++){
            String errorString = "Path diverges at index " + i + 
                    " expected: " + expectedPath[i][0] + ", " + expectedPath[i][1] + "\n" +
                    " but got " + path[i][0] + ", " + path[i][1];
            assertArrayEquals(errorString, expectedPath[i], path[i]);
            System.out.println("Path matches at index " + i +
                    " values: " + path[i][0] + ", " + path[i][1]);
        }
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
    
    
    private void testPruneChildNodesToOne(){
        GenomeErrorFree.OverlapGraph og = gef.new OverlapGraph(new ArrayList<>());
        SuffixOverlap mainOverlap = og.new SuffixOverlap(null, 0, 0);
        GenomeErrorFree.SimpleTreeNode rootNode = gef.new SimpleTreeNode(mainOverlap);
    }
   
   private boolean testReturnString(String[] rtrn, String unbrokenString){
       GenomeErrorFree.CircularString cUnbr = gef.new CircularString(unbrokenString);
       for(int i=0;i<rtrn.length;i++){
           String s = rtrn[i];
           System.out.println(s);
           if(!cUnbr.contains(s)){
               return false;
           }
       }
       
       return true;
   }
   
   private ArrayList<String> swapSegments(ArrayList<String> segments, int first, int second){
       String temp = segments.get(second);
       segments.set(second, segments.get(first));
       segments.set(first, temp);
       return segments;
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
        GenomeErrorFree.OverlapGraph gr = null;
        GenomeErrorFree instance = new GenomeErrorFree();
        GenomeErrorFree.OverlapGraph expResult = null;
        GenomeErrorFree.OverlapGraph result = instance.findAllOverlaps(gr);
        assertEquals(expResult, result);
        // TODO LATER review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    //TODO LATER: finish test method and find out what's wrong with findOverlaps
    /**
     * Test of findOverlaps method, of class GenomeErrorFree.
     */
    @Test
    public void testFindOverlaps() {
        System.out.println("findOverlaps");
        for(int i=0;i<1000;i++){
            int strLen = rnd.nextInt(30)+2;
            int olPoint = rnd.nextInt(strLen);
            StringSegment[] segs = mockOverlappingStringSegments(strLen, olPoint);
            StringSegment potentialOverlappedString = segs[0];
            StringSegment potentialOverlappingString = segs[1];
            potentialOverlappedString.addOverlap(potentialOverlappingString.index, potentialOverlappingString.str.length()-olPoint);
            int str1Pos = 0;
            GenomeErrorFree instance = new GenomeErrorFree();
            GenomeErrorFree.OverlapGraph.StringSegment expResult = potentialOverlappedString;
            GenomeErrorFree.OverlapGraph.StringSegment result = instance.findOverlaps(potentialOverlappingString, potentialOverlappedString, str1Pos);

            assertEquals(expResult, result);
        }
     }

    private StringSegment[] mockOverlappingStringSegments(int segLength, int olPoint){
        StringSegment[] stringSegments = new StringSegment[2];
        String str1 = "";
        
        for(int i=0;i<segLength;i++){
            str1+= randChar();
        }
        String str2 = str1.substring(olPoint);
        for(int i=str2.length();i<segLength;i++){
            str2+=randChar();
        }
        GenomeErrorFree.OverlapGraph mockOverlapGraph = gef.new OverlapGraph(new ArrayList<>());
        stringSegments[0] = mockOverlapGraph.new StringSegment(mockOverlapGraph, str1, rnd.nextInt(10));
        stringSegments[1] = mockOverlapGraph.new StringSegment(mockOverlapGraph, str2, rnd.nextInt(10)+10);
        return stringSegments;
    }
    /**
     * Test of matchOverlaps method, of class GenomeErrorFree.
     */
    @Test
    public void testMatchOverlaps() {
        System.out.println("matchOverlaps");
        String potentialOverlappingString = "";
        String potentialOverlappedString = "";
        int overlap = 0;
        boolean expResult = false;
        boolean result = GenomeErrorFree.matchOverlaps(potentialOverlappingString, potentialOverlappedString, overlap);
        assertEquals(expResult, result);
        // TODO LATER review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawPath method, of class GenomeErrorFree.
     */
    @Test
    public void testDrawPath() {
        System.out.println("drawPath");
        PriorityQueue pq = null;
        GenomeErrorFree.OverlapGraph gr = null;
        boolean[] usedNodes = null;
        int pathSize = 0;
        GenomeErrorFree instance = new GenomeErrorFree();
        Integer[][] expResult = null;
        Integer[][] result = instance.drawPath(pq, gr, usedNodes, pathSize);
        assertArrayEquals(expResult, result);
        // TODO LATER review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLargestUnusedOverlaps method, of class GenomeErrorFree.
     */
    @Test
    public void testGetLargestUnusedOverlaps() {
        System.out.println("getLargestUnusedOverlaps");
        GenomeErrorFree.OverlapGraph.StringSegment seg = null;
        boolean[] nodeIsUsed = null;
        GenomeErrorFree instance = new GenomeErrorFree();
        ArrayList<SuffixOverlap> expResult = null;
        ArrayList<SuffixOverlap> result = instance.getLargestUnusedOverlaps(seg, nodeIsUsed);
        assertEquals(expResult, result);
        // TODO LATER review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of combineOverlaps method, of class GenomeErrorFree.
     */
    @Test
    public void testCombineOverlaps() {
        System.out.println("combineOverlaps");
        String overlappingString = "";
        String overlappedString = "";
        int olPoint = 0;
        String expResult = "";
        String result = GenomeErrorFree.combineOverlaps(overlappingString, overlappedString, olPoint);
        assertEquals(expResult, result);
        // TODO LATER review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
