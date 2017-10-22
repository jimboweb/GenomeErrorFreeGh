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
import java.util.BitSet;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jimstewart
 */
public class GenomeErrorFree {
    boolean findOverlapsIsNaive = true;
    boolean findAllOverlapsIsNaive = true;
    private final int numberOfInputs = 5; //will be 1618
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
    
    public GenomeErrorFree(){
        
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
<<<<<<< HEAD
        
<<<<<<< HEAD
        return assembleString(gr).toString();
=======
         
>>>>>>> parent of 4259b9e... OverlapGraph written but untested
=======
        return assembleString(gr);
>>>>>>> parent of e0d3656... failed fix
        
        
        return "Genome not found";
    }
    
    /**
     * Find all points where the strings overlap
     * @param gr the overlap graph
     * @return the genome strings with the overlaps in each one
     */
    public OverlapGraph findAllOverlaps(OverlapGraph gr){
        if(findAllOverlapsIsNaive){
            for(int i=0;i<gr.stringSegments.length;i++){
                OverlapGraph.StringSegment str1 = gr.stringSegments[i];
                for(int j=0;j<gr.stringSegments.length;j++){
                    if(j!=i){
                        OverlapGraph.StringSegment str2 = gr.stringSegments[j];;
                        str1 = findOverlaps(str1, str2, i);
                    }
                }
            }
        }
        
        return gr;
    }
    
    /**
     * Find out if two strings overlap at one or more points
     * @param str1 the string that might overlap
     * @param str2 the string that might be overlapped
     * @param str1Pos the index of string 1
     * @return StringSegment with the overlaps added
     */
    public OverlapGraph.StringSegment findOverlaps(OverlapGraph.StringSegment str1, OverlapGraph.StringSegment str2, int str1Pos){
        
        if(findOverlapsIsNaive){
            int stopPoint = Math.max(0, str1.str.length()-str2.str.length());
            for(int i=str2.str.length()-1;i>stopPoint;i--){
                if(matchOverlaps(str2.str, str1.str, i)){
                    str1 = str2.addOverlap(str1Pos, i);
                }
            }
        }
        return str1;
    }
//        TODO: efficient method here
//        return;
    
    
        /**
     * Determines if potentialOverlappingString overlaps potentialOverLappedString
     * @param potentialOverlappingString the string that is overlapping (starts in middle potentialOverlappedString)
     * @param potentialOverlappedString the string that's being overlapped (potentialOverlappingString starts in middle)
     * @param overlap the point where potentialOverlappingString would match potentialOverlappedString
     * @return true if they overlap, false if they don't 
     */
    protected static boolean matchOverlaps(CircularString potentialOverlappingString, CircularString potentialOverlappedString, int overlap){
        if(overlap<0)
            return false;
        int overlapLength = potentialOverlappingString.length();
        //int polgstrlen = potentialOverlappingString.length();
        String potentialOverlappingStringSub = potentialOverlappingString.subString(0, overlapLength);
        String potentialOverlappedStringSub = potentialOverlappedString.subString( overlap );
                
        return  (potentialOverlappingStringSub.equals(potentialOverlappedStringSub));
    }
<<<<<<< HEAD
    
    private CircularString assembleString(OverlapGraph gr){
        CircularString rtrn = new CircularString("");
        Integer[][] path = greedyHamiltonianPath(gr);
        if(path==null)
            return new CircularString("string not found");
        int nextNodeNumber = 0;
        do{
            nextNodeNumber = path[nextNodeNumber][0];
            rtrn = combineOverlaps(gr.stringSegments[nextNodeNumber].str, rtrn, path[nextNodeNumber][1]);
        } while (nextNodeNumber!=0);
        
        return rtrn;
    }

    /**
     * 
     * @param input The overlap graph
     * @return Integer[][] of form {next string, overlap length}
     */
    private Integer[][] greedyHamiltonianPath(OverlapGraph input){
        Integer[][] rtrn = new Integer[input.stringSegments.length][2];
        boolean[] usedNodes = new boolean[input.stringSegments.length];
        int nodeNumber = 0;
        for(OverlapGraph.StringSegment nodePaths:input.stringSegments){
            Collections.sort(
                    nodePaths.suffixOverlaps, 
                    (OverlapGraph.SuffixOverlap o1, OverlapGraph.SuffixOverlap o2) 
                            -> ((Integer)o1.lengthOfOverlap).compareTo(o2.lengthOfOverlap)
            );
        }
        
        PriorityQueue pq = new PriorityQueue<>(
            (OverlapGraph.StringSegment o1, OverlapGraph.StringSegment o2) 
                    -> ((Integer)o1.suffixOverlaps.get(0).lengthOfOverlap)
                            .compareTo(o2.suffixOverlaps.get(0).lengthOfOverlap));
        pq.addAll(Arrays.asList(input.stringSegments));
        while(!pq.isEmpty()){
            OverlapGraph.StringSegment nextStringSeg = (OverlapGraph.StringSegment) pq.poll();
            rtrn[nextStringSeg.index]=findNextPath(nextStringSeg, usedNodes);
            if (rtrn[nextStringSeg.index] == null)
                //then there was no further path available
                return null;
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
                olLength = overlap.lengthOfOverlap;
                iterator++;
            } while (usedNodes[nextNodeNumber]);
            usedNodes[nextNodeNumber] = true;
            Integer[] addedOverlap = {nextNodeNumber, olLength};
            return addedOverlap;

    }
=======

    
>>>>>>> parent of 4259b9e... OverlapGraph written but untested
    
        /**
     * Combines overlapping strings into a single string
     * @param overlappingString the string that overlaps (starts in the middle of the other one)
     * @param overlappedString the string that's overlapped (the other one starts in the middle)
     * @param olPoint the point where they overlap
     * @return the string combining the two at the overlap point
     */
    protected static CircularString combineOverlaps(CircularString overlappingString, CircularString overlappedString, int olPoint){
        
        //TODO: exception coming here because we're getting overaps that aren't
        //actually overlapping
        if(!matchOverlaps(overlappingString,overlappedString,olPoint)){
            return overlappedString;
            //throw new IllegalArgumentException("string " + overlappingString + " and string " + overlappedString + " do not overlap at point " + olPoint + " !");
        }
        
        return new CircularString(overlappedString.subString(0, olPoint) + overlappingString);
    }
}

// GenomeString & AssembledString might be able to be combined into 
// classes implementing interface. Or maybe not. 


class OverlapGraph{
    StringSegment[] stringSegments;
    public OverlapGraph(ArrayList<String> stringSegments){
        this.stringSegments = new StringSegment[stringSegments.size()];
        for(int i=0;i<stringSegments.size();i++){
            this.stringSegments[i]=new StringSegment(stringSegments.get(i));
        }
    }
    class StringSegment{
        ArrayList<SuffixOverlap> suffixOverlaps;
<<<<<<< HEAD
<<<<<<< HEAD
        final String str;
        final int index;
        public StringSegment(String str, int index){
            this.str= str;
=======
        String str;
        public StringSegment(String str){
            this.str=str;
>>>>>>> parent of 4259b9e... OverlapGraph written but untested
=======
        final CircularString str;
        final int index;
        public StringSegment(String str, int index){
            this.str=new CircularString(str);
>>>>>>> parent of e0d3656... failed fix
            this.suffixOverlaps=new ArrayList<>();
        }
        public StringSegment addOverlap(int overlappingString, int lengthOfOverlap){
            suffixOverlaps.add(new SuffixOverlap(overlappingString, lengthOfOverlap));
            return this;
        }
    }
    
    class SuffixOverlap{
        int overlappingString;
        int lengthOfOverlap;
        public SuffixOverlap(int overlappingString,int lengthOfOverlap){
            this.overlappingString=overlappingString;
            this.lengthOfOverlap=lengthOfOverlap;
        }
    }
    
    
}





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
    
    @Override
    public int length() {
        return characters.length;
    }

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

