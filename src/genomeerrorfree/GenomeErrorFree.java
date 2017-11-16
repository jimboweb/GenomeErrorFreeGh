/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import static genomeerrorfree.GenomeErrorFree.combineOverlaps;
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


//TODO: 
// 1) Make overlap sorter only get largest overlaps, discard the rest (1st bookmark) 
// 2) Build a tree of max overlap length. If two nodes have equal max overlap length,
// add them to the tree. When a node's child is below max overlap, prune the whole 
// branch. 

/**
 *
 * @author jimstewart
 */
public class GenomeErrorFree {
    boolean findOverlapsIsNaive = true;
    boolean findAllOverlapsIsNaive = true;
    private final int numberOfInputs = 1618; //will be 1618
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread(null, new Runnable(){
            @Override
            public void run(){
                new GenomeErrorFree().run();
            }

        }, "1", 1<<26).start();
    }
    //TODO: insert the code to get the input and run the 
    //returnGenome method
    public GenomeErrorFree(){
        
    }
    
    private void run(){
        ArrayList<String> input = getInput();
        String genome = returnGenome(input);
//        for(int i=0;i<100000;i++){//debug
//            genome+="A";//debug
//        }//debug
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
                Logger.getLogger(GenomeErrorFree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return input;
    }
    /**
     * 
     * @param input the genome strings sampled
     * @return the original genome string
     */
    public String returnGenome(ArrayList<String> input){
        
        OverlapGraph gr = new OverlapGraph(input);
        gr = findAllOverlaps(gr);
        return assembleString(gr).toString();
        
    }
    
    /**
     * Find all points where the strings overlap
     * @param gr the overlap graph
     * @return the genome strings with the overlaps in each one
     */
    public OverlapGraph findAllOverlaps(OverlapGraph gr){
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
    public OverlapGraph.StringSegment findOverlaps(OverlapGraph.StringSegment potentialOverlappingString, OverlapGraph.StringSegment potentialOverlappedString, int str1Pos){
        
        if(findOverlapsIsNaive){
            int stopPoint = Math.max(0, potentialOverlappingString.str.length()-potentialOverlappedString.str.length());
            for(int i=potentialOverlappedString.str.length()-1;i>stopPoint;i--){
                if(matchOverlaps(potentialOverlappingString.str, potentialOverlappedString.str, i)){
                    int overlapPoint = i;
                    potentialOverlappedString = potentialOverlappedString.addOverlap(str1Pos, overlapPoint);
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
    protected static boolean matchOverlaps(String potentialOverlappingString, String potentialOverlappedString, int overlap){
        if(overlap<0)
            return false;
        int overlapLength = potentialOverlappedString.length()-overlap;
        String potentialOverlappingStringSub = "";
        //debug try
        try{
        potentialOverlappingStringSub = potentialOverlappingString.substring(0, overlapLength);
        } catch (IndexOutOfBoundsException e){
            System.out.println(e);
        }
        String potentialOverlappedStringSub = "";//debug
        try{//debug
            potentialOverlappedStringSub = potentialOverlappedStringSub = potentialOverlappedString.substring( overlap );
        } catch (StringIndexOutOfBoundsException e){//debug
            System.out.println(e);//debug
        }     //debug
        
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
            else
            {
                rtrn = new CircularString(combineOverlaps(gr.stringSegments[nextNodeNumber].str, rtrn.toString(), currentOverlap));
            }
            overlap = path[nextNodeNumber][1];
            currentOverlap += overlap;
            nextNodeNumber = path[nextNodeNumber][0];
            started = true;            
        } while (!endOfPath);
        
        return rtrn;
    }

    /**
     * Filters the overlaps in the string segment so that only the
     * ones with maximum overlap length are in there. 
     * @param segment the segment whose overlaps we're searching
     * @return a new ArrayList of the suffix overlaps
     */
    public ArrayList<OverlapGraph.SuffixOverlap> filterOverlaps(OverlapGraph.StringSegment segment){
        ArrayList<OverlapGraph.SuffixOverlap> maxOverlaps = new ArrayList<>();
        int maxOverlap = 0;
        for(OverlapGraph.SuffixOverlap ol:segment.suffixOverlaps){
            int overlapLength = segment.str.length() - ol.overlapPoint;
            if(overlapLength == maxOverlap){
                maxOverlaps.add(ol);
            } else if (overlapLength>maxOverlap){
                maxOverlaps.clear();
                maxOverlaps.add(ol);
                maxOverlap=overlapLength;
            }
        }
        return maxOverlaps;
    }
    
    
    
    /**
     * traces through the overlap graph getting the largest 
     * overlap and connecting it to the next largest overlap
     * and so on until the end
     * @param input The overlap graph
     * @return Integer[][] of form {next string, overlap length}
     */
    public Integer[][] greedyHamiltonianPath(OverlapGraph input){
        Integer[][] rtrn = new Integer[input.stringSegments.length][2];
        boolean[] usedNodes = new boolean[input.stringSegments.length];
        int nodeNumber = 0;
        for(OverlapGraph.StringSegment nodePath:input.stringSegments){
            nodePath.suffixOverlaps = filterOverlaps(nodePath);
        }
        
        PriorityQueue pq = new PriorityQueue<>(
            //TODO: put this in a separate comparator function
            // in suffixOverlap
            (OverlapGraph.StringSegment o1, OverlapGraph.StringSegment o2) 
                    -> ((Integer)o1.suffixOverlaps.get(0).overlapPoint)
                            .compareTo(o2.suffixOverlaps.get(0).overlapPoint));
        pq.addAll(Arrays.asList(input.stringSegments));
        //TODO: create a branch every time multiple nodes have an equal overlap
        while(!pq.isEmpty()){
            OverlapGraph.StringSegment nextStringSeg = (OverlapGraph.StringSegment) pq.poll();
            rtrn[nextStringSeg.index]=findNextPath(nextStringSeg, usedNodes);
            if (rtrn[nextStringSeg.index] == null){
                //then there was no further path available
                //return null;
                System.out.println("Incomplete path");
                return rtrn; //DEBUG while I'm running test
            }
        }
        return rtrn;
    }
    
    /**
     * Find the next path for each string
     * @param nextStringSeg
     * @param usedNodes
     * @return an Integer {the next node, the length of the overlap}
     */
    private Integer[] findNextPath(OverlapGraph.StringSegment nextStringSeg, boolean[] usedNodes){
            Integer nextNodeNumber;
            int olLength;
            int iterator = 0;
            do{
                if(iterator>=nextStringSeg.suffixOverlaps.size())
                    //that means that there are no available strings that 
                    //overlap with this
                    return null;
                OverlapGraph.SuffixOverlap overlap = nextStringSeg.suffixOverlaps.get(iterator);
                nextNodeNumber = overlap.overlappingString;
                olLength = overlap.overlapPoint;
                iterator++;
            } while (usedNodes[nextNodeNumber]);
            usedNodes[nextNodeNumber] = true;
            Integer[] addedOverlap = {nextNodeNumber, olLength};
            return addedOverlap;

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
            return overlappedString;
            //throw new IllegalArgumentException("string " + overlappingString + " and string " + overlappedString + " do not overlap at point " + olPoint + " !");
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
            this.stringSegments[i]=new StringSegment(stringSegments.get(i),i);
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
    class StringSegment{
        public ArrayList<SuffixOverlap> suffixOverlaps;
        final String str;
        final int index;
        public StringSegment(String str, int index){
            this.str= str;
            this.suffixOverlaps=new ArrayList<>();
            this.index = index;
        }
        public StringSegment addOverlap(int overlappingString, int lengthOfOverlap){
            suffixOverlaps.add(new SuffixOverlap(overlappingString, lengthOfOverlap));
            return this;
        }
    }
    
    /**
     * <p> describes a suffix that overlaps. Contains:</p>
     * <ul>
     * <li>the index of the string that overlaps containing StringSegment</li>
     * <li>the point at which the string segment overlaps</li>
     * </ul>
     */
    class SuffixOverlap{
        int overlappingString;
        int overlapPoint;
        public SuffixOverlap(int overlappingString,int lengthOfOverlap){
            this.overlappingString=overlappingString;
            this.overlapPoint=lengthOfOverlap;
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
        return new CircularString(characters.toString()+str);
    }
    
    public boolean contains(String s){
        int l = s.length();
        for(int i=0;i<length;i++){
            if(subString(i,i+l).equals(s));
                return true;
        }
        return false;
    }
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
        if(this.length == otherCs.length)
        {
            int i,j;
        //otherwise cycle through other string
            for(i=0;i<otherCs.length;i++){
                //then cycle through this one
                for(j=0;j<length;j++){
                    //continue if the character at that point isn't the same
                    char charJ = charAt(j);
                    char charI = otherCs.charAt(i+j);
                    if(!(charAt(j)==otherCs.charAt(i+j))){
                        break;
                    }
                }
                //if I got all the way through the word, then they're equal
                if(i==length-1){
                    return true;
                }
            }
        }
          return false;
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

