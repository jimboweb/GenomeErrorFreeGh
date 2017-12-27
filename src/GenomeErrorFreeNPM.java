/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author jimstewart
 */
public class GenomeErrorFreeNPM {
    boolean findOverlapsIsNaive = true;
    boolean findAllOverlapsIsNaive = true;
    private final int numberOfInputs = 1618; //will be 1618
    private final int minOverlap = 75;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread(null, new MainTask(), "1", 1<<26).start();
    }
    
    static class MainTask implements Runnable {

        @Override
        public void run() {
            new GenomeErrorFreeNPM().run();
        }
        
    }
    
    public GenomeErrorFreeNPM(){
        
    }
    
    private void run(){
        ArrayList<String> input = getInput();
        String genome = returnGenome(input);
        System.out.println(genome);
    }
    
    /**
     * Get the list of genome string sections
     * @return genomes as arrayList
     */
    private ArrayList<String> getInput(){
        ArrayList<String> input = new ArrayList<>();
        FastScanner scanner = new FastScanner();
        for (int i=0; i < numberOfInputs; i++){
            try {
                input.add(scanner.next());
            } catch (IOException ex) {
                Logger.getLogger(GenomeErrorFreeNPM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return input;
    }
    
    //TODO: BUG: (at least) one of the string segments has a very high suffix overlap,
    //like at 96 instead of 2-4 like the rest. That's the one that's getting the null
    //pointer exception most of the time. This only happens when the overlap length is
    //very short. Need to find that string and figure out what's going on with it. 
    /**
     * 
     * @param input the genome strings sampled
     * @return the original genome string
     */
    String returnGenome(ArrayList<String> input){
        input = removeDupes(input);
        OverlapGraph gr = new OverlapGraph(input);
        gr = findAllOverlaps(gr);
        return assembleString(gr).toString();
        
    }
    
    /**
     * remove all inputs that are duplicates
     * @param input the input with the dupes
     * @return the input without the dupes
     * put them in order so the dupes are together
     * then loop through and find anything
     * identical to the previous
     * */
    ArrayList<String> removeDupes(ArrayList<String> input){
        Collections.sort(input); //may not be necessary if input is already sorted
        String prevNonDupe = "";
        ArrayList<String> rtrn = new ArrayList<>();
        for(String inputItem:input){
            if(!prevNonDupe.equals(inputItem)){
                rtrn.add(inputItem);
                prevNonDupe = inputItem;
            } 
        }
        return rtrn;
    }
    
    /**
     * Find all points where the strings overlap
     * @param gr the overlap graph
     * @return the genome strings with the overlaps in each one
     */
    OverlapGraph findAllOverlaps(OverlapGraph gr){
        if(findAllOverlapsIsNaive){
            for(int i=0;i<gr.stringSegments.length;i++){
                OverlapGraph.StringSegment potentialOverlappedString = gr.stringSegments[i];
                for(int j=0;j<gr.stringSegments.length;j++){
                    if(j!=i){
                        OverlapGraph.StringSegment potentialOverlappingString = gr.stringSegments[j];
                        potentialOverlappedString = findOverlaps(potentialOverlappingString, potentialOverlappedString, j);
                    }
                }
            }
        }
        
        return gr;
    }
    
    /**
     * Find out if two strings overlap at one or more points
     * @param potentialOverlappingString the string that might overlap
     * @param potentialOverlappedString the string that might be overlapped
     * @param str1Pos the index of string 1
     * @return StringSegment with the overlaps added
     */
    OverlapGraph.StringSegment findOverlaps(OverlapGraph.StringSegment potentialOverlappingString, OverlapGraph.StringSegment potentialOverlappedString, int str1Pos){
        
        if(findOverlapsIsNaive){
            int startPoint = Math.max(0, potentialOverlappingString.str.length()-potentialOverlappedString.str.length());
            int stopPoint = potentialOverlappedString.str.length()-minOverlap;
            for(int i=startPoint;i<stopPoint;i++){
                if(matchOverlaps(potentialOverlappingString.str, potentialOverlappedString.str, i)){
                    int overlapPoint = i;
                    potentialOverlappedString = potentialOverlappedString.addOverlap(str1Pos, overlapPoint);
                }
                if(potentialOverlappedString.suffixOverlaps.size()>4){
                    break;
                }
            }
        }
        return potentialOverlappedString;
    }
    
    
        /**
     * Determines if potentialOverlappingString overlaps potentialOverLappedString
     * @param potentialOverlappingString the string that is overlapping (starts in middle potentialOverlappedString)
     * @param potentialOverlappedString the string that's being overlapped (potentialOverlappingString starts in middle)
     * @param overlap the point where potentialOverlappingString would match potentialOverlappedString
     * @return true if they overlap, false if they don't 
     */
    static boolean matchOverlaps(String potentialOverlappingString, String potentialOverlappedString, int overlap){
        if(overlap<0)
            return false;
        int overlapLength = potentialOverlappedString.length()-overlap;
        String potentialOverlappingStringSub = "";
        //debug try
        potentialOverlappingStringSub = potentialOverlappingString.substring(0, overlapLength);
        String potentialOverlappedStringSub = potentialOverlappedString.substring( overlap );
        
        boolean rtrn = potentialOverlappingStringSub.equals(potentialOverlappedStringSub);//debug
        return  (potentialOverlappingStringSub.equals(potentialOverlappedStringSub));
    }
    
    /**
     * Create the circular string from the overlap graph
     * @param gr the overlap graph
     * @return the circular string
     */
    private CircularString assembleString(OverlapGraph gr){
        CircularString rtrn = new CircularString("");
        Integer[][] path = greedyHamiltonianPath(gr);
        if(path==null)
            return new CircularString("string not found");
        int nextNodeNumber = 0;

        int currentOverlap = 0;
        int overlap = 0;
        boolean endOfPath = false;
        boolean started = false;
        do{
            
            if(nextNodeNumber==0 && started){
                String rtrnStr = rtrn.toString();
                int wrapLength = gr.stringSegments[0].str.length()-overlap;
                String croppedRtrnStr = rtrnStr.substring(0, rtrnStr.length()-wrapLength);
                rtrn = new CircularString(croppedRtrnStr);
//                rtrn = new CircularString(combineOverlaps(gr.stringSegments[nextNodeNumber].str, rtrn.toString(), currentOverlap));
                endOfPath = true;
            }
            else if(nextNodeNumber!=-1)
            {
                rtrn = new CircularString(combineOverlaps(gr.stringSegments[nextNodeNumber].str, rtrn.toString(), currentOverlap));
            }
            try{ //debug
                overlap = path[nextNodeNumber][1];
            } catch(NullPointerException e){ //debug
                System.out.println(e); //debug
            }
            nextNodeNumber = path[nextNodeNumber][0];
            started = true;            
            currentOverlap += overlap;
         } while (!endOfPath);
        
        return rtrn;
    }        
    
    /**
     * <p>traces through the overlap graph getting the largest 
     * overlap and connecting it to the next largest overlap</p>
     * and so on until the end
     * @param input The overlap graph
     * @return Integer[][] of form {next string, overlap length}
     */
    Integer[][] greedyHamiltonianPath(OverlapGraph input){
        boolean[] usedNodes = new boolean[input.stringSegments.length];
        int nodeNumber = 0;
        for(OverlapGraph.StringSegment nodePath:input.stringSegments){
            Collections.sort(nodePath.suffixOverlaps);
        }
        
        PriorityQueue pq = new PriorityQueue<>();
        pq.addAll(Arrays.asList(input.stringSegments));
        Integer[][] rtrn = drawPath(pq,input,usedNodes,usedNodes.length);
        return rtrn;
    }
    
    /**
     * <p>draws the actual path.</p> 
     * <p>for each item of priority queue:
     *  <ol>
     *      <li>gets largest overlaps</li>
     *      <li>creates node tree of them</li>
     *      <li>prunes from smaller branches until only one is left</li>
     *      <li>makes that the next item on path after item in pq</li>
     *  </ol>
     * </p>
     * @param pq
     * @param gr
     * @param usedNodes
     * @param pathSize
     * @return 
     */
    Integer[][] drawPath (PriorityQueue pq, OverlapGraph gr, boolean[] usedNodes, int pathSize){
        Integer[][] rtrn = new Integer[pathSize][2];
        for(Integer[] node:rtrn){
            for(Integer i:node){
                i=-1;
            }
        }
        while(!pq.isEmpty()){
            OverlapGraph.StringSegment currentSegment = (OverlapGraph.StringSegment)pq.poll();
            rtrn[currentSegment.index] = getLargestUnusedOverlap(currentSegment, usedNodes).getValuesAsArray();
        }
        return rtrn;
    }

    /**
     * <p>Finds the longest unused overlaps.
     *  <ol>
     *      <li>continues until we've found an overlap that isn't used yet.</li>
     *      <li>Assumes the overlaps are sorted greatest length to least so 
     *      the first one it gets will be the biggest. </li>
     *      <li>Sets max overlap to first overlap length then continues and adds 
     *      all overlaps with equal length.</li>
     *  </ol>
     * </p>
     * @param seg segment to look in
     * @param nodeIsUsed nodes that have already been used
     * @return 
     */
    OverlapGraph.SuffixOverlap getLargestUnusedOverlap(OverlapGraph.StringSegment seg, boolean[] nodeIsUsed){
        OverlapGraph.SuffixOverlap rtrn=seg.suffixOverlaps.get(0);
        int maxOverlap = -1;
        for(OverlapGraph.SuffixOverlap ol:seg.suffixOverlaps){
            if(!nodeIsUsed[ol.overlappingString]){
                if(maxOverlap ==-1){
                    maxOverlap = ol.getOverlapLength();
                    rtrn=ol;
                } else {
                    int overlapLength = ol.getOverlapLength();
                    if(overlapLength>=maxOverlap){
                        rtrn=ol;
                    } else {
                        break;
                    }
                }
            }
        }
        return rtrn;
    }




    
    

        
        /**
     * Combines overlapping strings into a single string
     * @param overlappingString the string that overlaps (starts in the middle of the other one)
     * @param overlappedString the string that's overlapped (the other one starts in the middle)
     * @param olPoint the point where they overlap
     * @return the string combining the two at the overlap point
     */
    protected static String combineOverlaps(String overlappingString, String overlappedString, int olPoint){
        if("".equals(overlappedString)){
            return overlappingString;
        }
        if("".equals(overlappingString)){
            return overlappedString;
        }
        if(!matchOverlaps(overlappingString,overlappedString,olPoint)){
            //return overlappedString;
            throw new IllegalArgumentException("string " + overlappingString + " and string " + overlappedString + " do not overlap at point " + olPoint + " !");
        }
        
        return overlappedString.substring(0, olPoint) + overlappingString;
    }
    
}


/**
 * an overlap graph of the strings
 * contains an array of string segments
 * @author jim.stewart
 */
class OverlapGraph{
    StringSegment[] stringSegments;
    public OverlapGraph(ArrayList<String> stringSegments){
        this.stringSegments = new StringSegment[stringSegments.size()];
        for(int i=0;i<stringSegments.size();i++){
            this.stringSegments[i]=new StringSegment(this,stringSegments.get(i),i);
        }
    }
    /**
     * <p>a string segment in the overlap graph
     * contains:</p>
     * <ul>
     * <li> a string</li>
     * <li>the index in the graph</li>
     * <li>a list of suffixOverlaps of strings it overlaps with</li>
     * </ul>
     */
    class StringSegment implements Comparable<StringSegment>{
        private OverlapGraph parent;
        public ArrayList<SuffixOverlap> suffixOverlaps;
        final String str;
        final int index;
        public StringSegment(OverlapGraph parent, String str, int index){
            this.str= str;
            this.suffixOverlaps=new ArrayList<>();
            this.index = index;
            this.parent = parent;
        }
        public StringSegment addOverlap(int overlappingString, int overlapPoint){
            suffixOverlaps.add(new SuffixOverlap(this, overlappingString, overlapPoint));
            return this;
        }

        //BUG??: right now this returns lowest-highest because that's how the 
        //other function did it, which doesn't seem right. But other than that it
        //works so I can flip it around when I need
        @Override
        public int compareTo(StringSegment o) {
            return ((Integer)this.suffixOverlaps.get(0).overlapPoint).compareTo(o.suffixOverlaps.get(0).overlapPoint);      
        }
    }
    /**
     * <p> describes a suffix that overlaps. Contains:</p>
     * <ul>
     * <li>the index of the string that overlaps containing StringSegment</li>
     * <li>the point at which the string segment overlaps</li>
     * </ul>
     */
    class SuffixOverlap implements Comparable<SuffixOverlap> {
        StringSegment parent;
        int overlappingString;
        int overlapPoint;
        public SuffixOverlap(StringSegment parent, int overlappingString,int overlapPoint){
            this.overlappingString=overlappingString;
            this.overlapPoint=overlapPoint;
            this.parent=parent;
        }
        
        public int getOverlapLength(){
            StringSegment seg = parent.parent.stringSegments[this.overlappingString];
            int segLength = seg.str.length();
            return segLength-this.overlapPoint;
        }

        @Override
        public int compareTo(SuffixOverlap o) {
            return ((Integer)this.overlapPoint).compareTo(o.overlapPoint);
        }
        
        public Integer[] getValuesAsArray(){
            Integer[] rtrn = {overlappingString, overlapPoint};
            return rtrn;
        }
    }
    
    
}




/**
 * <p>A character sequence that represents a circular string. Contains:</p>
 * <ul>
 * <li>a character array</li>
 * <li>integer length of the string</li>
 * </ul>
 * @author jim.stewart
 */
class CircularString implements CharSequence {

    private final char[] characters;
    private final int length;
    
    public CircularString(String str){
        length = str.length();
        characters = new char[length];
        for(int i=0;i<length;i++){
            characters[i] = str.charAt(i);
        }
    }
    
    /**
     * 
     * @return as a regular string
     */
    @Override 
    public String toString(){
        return new String(characters);
    }
    
    /**
     * 
     * @return the number of characters
     */
    @Override
    public int length() {
        return characters.length;
    }

    /**
     * finds the character from the beginning of listed string
     * @param index how far in
     * @return the character at that point
     */
    @Override
    public char charAt(int index) {
        index = index%length;
        return characters[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        start=start%length;
        if(end<start)
            end = end+length;
        String cs = "";
        for(int i=start;i<end;i++){
            cs += characters[i%length];
        }
        return cs;
    }
    
    public String subString(int index){
        return subSequence(index,length).toString();
    }
    
    public String subString(int start, int end){
        return subSequence(start,end).toString();
    }
    
    public CircularString concatenate (String str){
        return new CircularString(new String(characters)+str);
    }
    
    public boolean contains(String s){
        int l = s.length();
        for(int i=0;i<length;i++){
            if(subString(i,i+l).equals(s));
                return true;
        }
        return false;
    }
    
    //TODO: BUG: my f*$^ing equality check is returning false positives
    //no wonder I never fail the tests
    /**
     * Check for circular equality
     * 
     * @param other other object, will be false if it's not circular string
     * @return true if any circular version of any other circular string equals this one. So 'ABBA' equals 'BAAB'
     */
    @Override
    public boolean equals(Object other){
        //false if it' not a circular string
        if(!(other instanceof CircularString))
            return false;
        CircularString otherCs = (CircularString)other;
        //otherwise check if they're the same length and then cycle through other string
        return length == otherCs.length && 
                compareStringAtEachOverlap(otherCs);
    }
    
    private boolean compareStringAtEachOverlap(CircularString otherCs){
        for(int i=0;i<length;i++){
            if(compareStringAtOverlap(otherCs, i)){
                return true;
            }
        }
        return false;
    }
    
    private boolean compareStringAtOverlap(CircularString otherCs, int i){
        for(int j=0;j<length;j++){
            //continue if the character at that point isn't the same
            char thisStringChar = charAt(j);
            char otherStringChar = otherCs.charAt(i+j);
            if(thisStringChar!=otherStringChar){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Arrays.hashCode(this.characters);
        hash = 97 * hash + this.length;
        return hash;
    }
}

class FastScanner {
    StringTokenizer tok = new StringTokenizer("");
    BufferedReader in;

    FastScanner() {
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() throws IOException {
        while (!tok.hasMoreElements())
            tok = new StringTokenizer(in.readLine());
        return tok.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
    

}

