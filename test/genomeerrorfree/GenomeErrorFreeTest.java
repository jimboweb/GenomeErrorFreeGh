/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
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
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    //TODO: right now I think the mix up function isn't working 
    //right. The overlaps it's giving aren't the right overlaps. 
    @Test
    public void testGreedyHamiltonianPath(){
        String originalString = createUnbrokenString(1000, false);
        ReturnGenomeInputAndPath input = new ReturnGenomeInputAndPath(originalString,200,30,10);
        
        GenomeErrorFree instance = new GenomeErrorFree();
        ArrayList<String> inputStrings = new ArrayList<>();
        Integer[][] expectedPath = new Integer[input.input.size()][2];
        for(int i=0;i<input.input.size();i++){
            InputNode n = input.input.get(i);
            inputStrings.add(n.str);
            expectedPath[i][0] = n.overlaps;
            expectedPath[i][1] = n.overlapPoint;
            
        }
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
        ArrayList<InputNode> input;
        
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
            for(InputNode n:input){
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
            for(int i=0;i<numberOfSegments;i++){
                nextString = multString.substring(lastStrBegin, lastStrBegin+strLen);
                multString = multString.substring(lastStrBegin);
                //First item i path will have overlap of -1.
                //because I don't know where it's going to overlap. 
               input.add(new InputNode(nextString, i-1,lastStrBegin, i+1));
                lastStrBegin =  rnd.nextInt(maxOlPoint);
                if((lastStrBegin+200)>multString.length())
                    multString += unbrokenString;
            }
  
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
        private ArrayList<InputNode> swapSegments(ArrayList<InputNode> segments, int first, int second){
            InputNode firstNode = segments.get(second).copy();
            InputNode secondNode = segments.get(first).copy();
            //TODO: set overlaps and overlapped by for both
            if(firstNode.overlaps!=-1){
                InputNode firstNodeOverlaps = segments.get(firstNode.overlaps);
                firstNodeOverlaps.overlappedBy = second;
            }
            if(secondNode.overlaps!=-1){
                InputNode secondNodeOverlaps = segments.get(secondNode.overlaps);
                secondNodeOverlaps.overlappedBy = first;
            }
            InputNode firstNodeOverlappedBy = segments.get(firstNode.overlappedBy);
            firstNodeOverlappedBy.overlaps = second;
            InputNode secondNodeOverlappedBy = segments.get(secondNode.overlappedBy);
            secondNodeOverlappedBy.overlaps = first;
            segments.set(second, firstNode);
            segments.set(first, secondNode);
            return segments;
        }
        
        
    }
    
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
    
    class StringSegTest {
        String str;
        int index;
        int absLocation;
        ArrayList<StringSegTest> overlappedBy;
        ArrayList<StringSegTest> overlaps;
        
        public StringSegTest(String str, int index, int absLocation){
            this.str = str;
            this.index = index;
            this.absLocation = absLocation;
        }
        
        public void addOverlappedBy(StringSegTest sst){
            overlappedBy.add(sst);
        }
        
        public StringSegTest getOverlappedBy(int index){
            return overlappedBy.get(index);
        }
        
        public int getOverlappedByIndex(int index){
            return overlappedBy.get(index).index;
        }
        
        public ArrayList<Integer> getOverlappedByIndexList(){
            ArrayList<Integer> rtrn = new ArrayList<>();
            for(StringSegTest olby:overlappedBy){
                rtrn.add(olby.index);
            }
            return rtrn;
        }
        
        public ArrayList<StringSegTest> getOverlappedByList(){
            return overlappedBy;
        }
        
        public void addOverlaps(StringSegTest sst){
            overlaps.add(sst);
        }
        
        public StringSegTest getOverlaps(int index){
            return overlaps.get(index);
        }

        public int getOverlapsIndex(int index){
            return overlaps.get(index).index;
        }
        
        public ArrayList<StringSegTest> getOverlapsList(){
            return overlaps;
        }
        
        public ArrayList<Integer> getOverlapsIndexList(){
            ArrayList<Integer> rtrn = new ArrayList<>();
            for(StringSegTest ol:overlaps){
                rtrn.add(ol.index);
            }
            return rtrn;
        }
        
    }
}
