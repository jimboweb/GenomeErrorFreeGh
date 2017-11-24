/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import genomeerrorfree.GenomeErrorFreeTest.TestStringSeg;
import genomeerrorfree.OverlapGraph.SuffixOverlap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
        for(int i=0;i<10;i++){
            int numberOfSegments = 1618;
            int strLen = 100;
            int maxOlPoint = 50;
            System.out.println("returnGenome");
            String unbrokenString = createUnbrokenString(0, true);
            ReturnGenomeInputAndPath giap = new ReturnGenomeInputAndPath(unbrokenString, numberOfSegments, strLen, maxOlPoint);
            ArrayList<String> input = giap.inputAsStringList();
            GenomeErrorFree instance = new GenomeErrorFree();
            CircularString expResult = new CircularString(unbrokenString);
            String result = instance.returnGenome(input);
            CircularString cResult = new CircularString(result);
            assertEquals("Failed test number " + i + " got string " + result, expResult, result);
        }
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
        for(int i=0;i<1000;i++){
            int start = rnd.nextInt(str1.length());
            int end = rnd.nextInt(str1.length());
            String testContainsStr = cStr1.subString(start, start+end);
            assertTrue(cStr1.contains(testContainsStr));
        }
    }
        @Test
        public void testStringSegment(){
            ArrayList<String> l = new ArrayList<>();
            l.add("foo");
            l.add("bar");
            l.add("qaz");
            OverlapGraph og = new OverlapGraph(l);
            og.stringSegments[0].suffixOverlaps.add(og.new SuffixOverlap(og.stringSegments[0],2,4));
            og.stringSegments[1].suffixOverlaps.add(og.new SuffixOverlap(og.stringSegments[1], 1,3));
            og.stringSegments[2].suffixOverlaps.add(og.new SuffixOverlap(og.stringSegments[2], 2,5));
            PriorityQueue pq = new PriorityQueue<>();
            pq.addAll(Arrays.asList(og.stringSegments));
            ArrayList<OverlapGraph.StringSegment> expectedResult = new ArrayList<>();
            expectedResult.add(og.stringSegments[1]);
            expectedResult.add(og.stringSegments[0]);
            expectedResult.add(og.stringSegments[2]);
            ArrayList<OverlapGraph.StringSegment> actualResult = new ArrayList<>();
            while(!pq.isEmpty()){
                actualResult.add((OverlapGraph.StringSegment)pq.poll());
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

    @Test
    public void testGreedyHamiltonianPath(){
        String originalString = createUnbrokenString(1000, false);
        ReturnGenomeInputAndPath input = new ReturnGenomeInputAndPath(originalString,200,30,10);
        
        GenomeErrorFree instance = new GenomeErrorFree();
        ArrayList<String> inputStrings = new ArrayList<>();
        Integer[][] expectedPath = createExpectedPath(input, inputStrings);
        OverlapGraph graph = new OverlapGraph(inputStrings);
        //Well this isn't so greate because I am not just testing a single function 
        //but as far as I know findAllOverlaps always works
        //so I'm not going to worry about it
        graph = instance.findAllOverlaps(graph);
        Integer[][] path = instance.greedyHamiltonianPath(graph);
        for(int i=1;i<path.length;i++){
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
            return "gagttttatcgcttccatgacgcagaagttaacactttcggatatttctgatgagtcgaaaaattatcttgataaagcaggaattactactgcttgtttacgaattaaatcgaagtggactgctggcggaaaatgagaaaattcgacctatccttgcgcagctcgagaagctcttactttgcgacctttcgccatcaactaacgattctgtcaaaaactgacgcgttggatgaggagaagtggcttaatatgcttggcacgttcgtcaaggactggtttagatatgagtcacattttgttcatggtagagattctcttgttgacattttaaaagagcgtggattactatctgagtccgatgctgttcaaccactaataggtaagaaatcatgagtcaagttactgaacaatccgtacgtttccagaccgctttggcctctattaagctcattcaggcttctgccgttttggatttaaccgaagatgatttcgattttctgacgagtaacaaagtttggattgctactgaccgctctcgtgctcgtcgctgcgttgaggcttgcgtttatggtacgctggactttgtgggataccctcgctttcctgctcctgttgagtttattgctgccgtcattgcttattatgttcatcccgtcaacattcaaacggcctgtctcatcatggaaggcgctgaatttacggaaaacattattaatggcgtcgagcgtccggttaaagccgctgaattgttcgcgtttaccttgcgtgtacgcgcaggaaacactgacgttcttactgacgcagaagaaaacgtgcgtcaaaaattacgtgcggaaggagtgatgtaatgtctaaaggtaaaaaacgttctggcgctcgccctggtcgtccgcagccgttgcgaggtactaaaggcaagcgtaaaggcgctcgtctttggtatgtaggtggtcaacaattttaattgcaggggcttcggccccttacttgaggataaattatgtctaatattcaaactggcgccgagcgtatgccgcatgacctttcccatcttggcttccttgctggtcagattggtcgtcttattaccatttcaactactccggttatcgctggcgactccttcgagatggacgccgttggcgctctccgtctttctccattgcgtcgtggccttgctattgactctactgtagacatttttactttttatgtccctcatcgtcacgtttatggtgaacagtggattaagttcatgaaggatggtgttaatgccactcctctcccgactgttaacactactggttatattgaccatgccgcttttcttggcacgattaaccctgataccaataaaatccctaagcatttgtttcagggttatttgaatatctataacaactattttaaagcgccgtggatgcctgaccgtaccgaggctaaccctaatgagcttaatcaagatgatgctcgttatggtttccgttgctgccatctcaaaaacatttggactgctccgcttcctcctgagactgagctttctcgccaaatgacgacttctaccacatctattgacattatgggtctgcaagctgcttatgctaatttgcatactgaccaagaacgtgattacttcatgcagcgttaccatgatgttatttcttcatttggaggtaaaacctcttatgacgctgacaaccgtcctttacttgtcatgcgctctaatctctgggcatctggctatgatgttgatggaactgaccaaacgtcgttaggccagttttctggtcgtgttcaacagacctataaacattctgtgccgcgtttctttgttcctgagcatggcactatgtttactcttgcgcttgttcgttttccgcctactgcgactaaagagattcagtaccttaacgctaaaggtgctttgacttataccgatattgctggcgaccctgttttgtatggcaacttgccgccgcgtgaaatttctatgaaggatgttttccgttctggtgattcgtctaagaagtttaagattgctgagggtcagtggtatcgttatgcgccttcgtatgtttctcctgcttatcaccttcttgaaggcttcccattcattcaggaaccgccttctggtgatttgcaagaacgcgtacttattcgccaccatgattatgaccagtgtttccagtccgttcagttgttgcagtggaatagtcaggttaaatttaatgtgaccgtttatcgcaatctgccgaccactcgcgattcaatcatgacttcgtgataaaagattgagtgtgaggttataacgccgaagcggtaaaaattttaatttttgccgctgaggggttgaccaagcgaagcgcggtaggttttctgcttaggagtttaatcatgtttcagacttttatttctcgccataattcaaactttttttctgataagctggttctcacttctgttactccagcttcttcggcacctgttttacagacacctaaagctacatcgtcaacgttatattttgatagtttgacggttaatgctggtaatggtggttttcttcattgcattcagatggatacatctgtcaacgccgctaatcaggttgtttctgttggtgctgatattgcttttgatgccgaccctaaattttttgcctgtttggttcgctttgagtcttcttcggttccgactaccctcccgactgcctatgatgtttatcctttgaatggtcgccatgatggtggttattataccgtcaaggactgtgtgactattgacgtccttccccgtacgccgggcaataacgtttatgttggtttcatggtttggtctaactttaccgctactaaatgccgcggattggtttcgctgaatcaggttattaaagagattatttgtctccagccacttaagtgaggtgatttatgtttggtgctattgctggcggtattgcttctgctcttgctggtggcgccatgtctaaattgtttggaggcggtcaaaaagccgcctccggtggcattcaaggtgatgtgcttgctaccgataacaatactgtaggcatgggtgatgctggtattaaatctgccattcaaggctctaatgttcctaaccctgatgaggccgcccctagttttgtttctggtgctatggctaaagctggtaaaggacttcttgaaggtacgttgcaggctggcacttctgccgtttctgataagttgcttgatttggttggacttggtggcaagtctgccgctgataaaggaaaggatactcgtgattatcttgctgctgcatttcctgagcttaatgcttgggagcgtgctggtgctgatgcttcctctgctggtatggttgacgccggatttgagaatcaaaaagagcttactaaaatgcaactggacaatcagaaagagattgccgagatgcaaaatgagactcaaaaagagattgctggcattcagtcggcgacttcacgccagaatacgaaagaccaggtatatgcacaaaatgagatgcttgcttatcaacagaaggagtctactgctcgcgttgcgtctattatggaaaacaccaatctttccaagcaacagcaggtttccgagattatgcgccaaatgcttactcaagctcaaacggctggtcagtattttaccaatgaccaaatcaaagaaatgactcgcaaggttagtgctgaggttgacttagttcatcagcaaacgcagaatcagcggtatggctcttctcatattggcgctactgcaaaggatatttctaatgtcgtcactgatgctgcttctggtgtggttgatatttttcatggtattgataaagctgttgccgatacttggaacaatttctggaaagacggtaaagctgatggtattggctctaatttgtctaggaaataaccgtcaggattgacaccctcccaattgtatgttttcatgcctccaaatcttggaggcttttttatggttcgttcttattacccttctgaatgtcacgctgattattttgactttgagcgtatcgaggctcttaaacctgctattgaggcttgtggcatttctactctttctcaatccccaatgcttggcttccataagcagatggataaccgcatcaagctcttggaagagattctgtcttttcgtatgcagggcgttgagttcgataatggtgatatgtatgttgacggccataaggctgcttctgacgttcgtgatgagtttgtatctgttactgagaagttaatggatgaattggcacaatgctacaatgtgctcccccaacttgatattaataacactatagaccaccgccccgaaggggacgaaaaatggtttttagagaacgagaagacggttacgcagttttgccgcaagctggctgctgaacgccctcttaaggatattcgcgatgagtataattaccccaaaaagaaaggtattaaggatgagtgttcaagattgctggaggcctccactatgaaatcgcgtagaggctttgctattcagcgtttgatgaatgcaatgcgacaggctcatgctgatggttggtttatcgtttttgacactctcacgttggctgacgaccgattagaggcgttttatgataatcccaatgctttgcgtgactattttcgtgatattggtcgtatggttcttgctgccgagggtcgcaaggctaatgattcacacgccgactgctatcagtatttttgtgtgcctgagtatggtacagctaatggccgtcttcatttccatgcggtgcactttatgcggacacttcctacaggtagcgttgaccctaattttggtcgtcgggtacgcaatcgccgccagttaaatagcttgcaaaatacgtggccttatggttacagtatgcccatcgcagttcgctacacgcaggacgctttttcacgttctggttggttgtggcctgttgatgctaaaggtgagccgcttaaagctaccagttatatggctgttggtttctatgtggctaaatacgttaacaaaaagtcagatatggaccttgctgctaaaggtctaggagctaaagaatggaacaactcactaaaaaccaagctgtcgctacttcccaagaagctgttcagaatcagaatgagccgcaacttcgggatgaaaatgctcacaatgacaaatctgtccacggagtgcttaatccaacttaccaagctgggttacgacgcgacgccgttcaaccagatattgaagcagaacgcaaaaagagagatgagattgaggctgggaaaagttactgtagccgacgttttggcggcgcaacctgtgacgacaaatctgctcaaatttatgcgcgcttcgataaaaatgattggcgtatccaacctgca";
           
        String unbrokenString = "";
        for(int i=0;i<unbrokenStrLen;i++){
            unbrokenString+=randChar();
        }
        return unbrokenString;
    }
    
    private void testPruneChildNodesToOne(){
        OverlapGraph og = new OverlapGraph(new ArrayList<>());
        SuffixOverlap mainOverlap = og.new SuffixOverlap(null, 0, 0);
        SimpleTreeNode rootNode = new SimpleTreeNode(mainOverlap);
        //TODO: make three levels of overlap graphs with different string
        //segments. Maybe just create an overlap graph from an actual string
        //instead of trying to mock it. 
    }
   
   private boolean testReturnString(String[] rtrn, String unbrokenString){
       CircularString cUnbr = new CircularString(unbrokenString);
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
            mixUpArrayListAndPath();
        }
        
        public ArrayList<String> inputAsStringList(){
            ArrayList<String> rtrn = new ArrayList<>();
            for(TestStringSeg n:input){
                rtrn.add(n.str);
            }
            return rtrn;
        }
        
        // TODO: change this by giving everything absolute locations rather than relative
        // links to other files. Then I can go ahead and put in whatever overlaps I need.
        // also should maybe do file links rather than indexes so I don't have to keep 
        // track of what index goes with what
        
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
            ArrayList<TestStringSeg> stringsToOverlap = new ArrayList<>();
            for(int i=0;i<numberOfSegments;i++){
                nextString = multString.substring(lastStrBegin, lastStrBegin+strLen);
                multString = multString.substring(lastStrBegin);
                //First item i path will have overlap of -1.
                //because I don't know where it's going to overlap. 
                TestStringSeg nextStringSeg = new TestStringSeg(nextString, lastStrBegin, absLocation);
                input.add(nextStringSeg);
                stringsToOverlap=addOverlaps(stringsToOverlap,nextStringSeg,strLen,absLocation);
                lastStrBegin =  rnd.nextInt(maxOlPoint);
                absLocation+= lastStrBegin;
                if((lastStrBegin+200)>multString.length())
                    multString += unbrokenString;
            }
            
        }
        
        /**
         * adds the overlaps to all the string segments
         * @param stringsToOverlap a collection with all the string segments that could overlap
         * @param nextStringSeg the next string segment that can overlap
         * @param strLen the string length
         * @param absLocation the absolute location
         * @return new 
         */
        private ArrayList<TestStringSeg> addOverlaps(ArrayList<TestStringSeg> stringsToOverlap, TestStringSeg nextStringSeg, int strLen, int absLocation){
                stringsToOverlap.add(nextStringSeg);
                ArrayList<TestStringSeg> newStringsToOverlap = new ArrayList<>();
                Collections.copy(stringsToOverlap, newStringsToOverlap);
                Stream<TestStringSeg> stringsStream = stringsToOverlap.stream();
                Stream<TestStringSeg> newStringsStream = stringsStream
                        .filter(tStrSeg->tStrSeg.absLocation+strLen>absLocation);
                newStringsToOverlap = (ArrayList<TestStringSeg>)newStringsStream.collect(Collectors.toList());
                stringsToOverlap=newStringsToOverlap;
                for(TestStringSeg sto:stringsToOverlap){
                    nextStringSeg.addOverlaps(sto, nextStringSeg.absLocation-sto.absLocation);
                    sto.addOverlappedBy(nextStringSeg, nextStringSeg.absLocation-sto.absLocation);
                }
                return stringsToOverlap;
        }
        
        private void mixUpArrayListAndPath(){
            for(int i=0;i<input.size();i++){
                int swapWith = rnd.nextInt(input.size());
                input = swapSegments(input,i, swapWith);
            }

        }
        
        
           
        //TODO: need to fix this so that the overlappedby works on the 
        //last item of the list. Maybe this would be easier by absolute position
        //rather than relative
        private ArrayList<TestStringSeg> swapSegments(ArrayList<TestStringSeg> segments, int first, int second){
            TestStringSeg firstNode = segments.get(first).copy();
            //TODO: set overlaps and overlapped by for both
            segments.set(first, segments.get(second));
            segments.set(second, firstNode);
            return segments;
        }
        
        
    }
    
    /**
     * @deprecated 
     * The older input node that only kept one overlap and one
     * overlappedBy
     */
    private class InputNode{
        String str;
        Integer overlaps;
        Integer overlapPoint;
        Integer overlappedBy;
        
        public InputNode(String str, int overlaps, int overlapPoint, int overlappedBy){
            this.str = str;
            this.overlaps = overlaps;
            this.overlapPoint = overlapPoint;
            this.overlappedBy = overlappedBy;
        }
        
        public InputNode copy(){
            return new InputNode(this.str,this.overlaps,this.overlapPoint,this.overlappedBy);
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
}
