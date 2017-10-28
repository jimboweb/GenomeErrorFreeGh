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
        
        ArrayList<GenomeString> genomeStrings = new ArrayList<>();
        for(String s:input){
            genomeStrings.add(new GenomeString(s));
        }
        genomeStrings = findAllOverlaps(genomeStrings);
        Stack<Overlap> overlaps = getOverlapsByLength(genomeStrings);
        
        ArrayList<AssembledString> assembledStrings = new ArrayList<>();
        while(!overlaps.empty()){
            Overlap nextOverlap = overlaps.pop();
            // get the genome string objects
            GenomeString overlappingGenomeString = genomeStrings.get(nextOverlap.overlappingString);
            GenomeString overlappedGenomeString = genomeStrings.get(nextOverlap.overlappedString);
            // get the index of current assembledString
            // add the assembled string's index and location of each
            // genome string in it
            int asIndex= assembledStrings.size();
            AssembledString newAstr = overlappedGenomeString.assembleGenomeStrings(
                                                        overlappingGenomeString, 
                                                        nextOverlap,
                                                        genomeStrings.size(),
                                                        asIndex
            );
            assembledStrings.add(newAstr);
            int[] firstGsAs = {asIndex, 0};
            overlappedGenomeString.assembledStrings.add(firstGsAs);
            
            int[] secondGsAs = {asIndex, nextOverlap.overlapPoint};
            overlappingGenomeString.assembledStrings.add(secondGsAs);
            
            // loop through the assembledstrings in each first genome string
            ArrayList<int[]> genomeAssembledStringsCopy = new ArrayList<>(overlappedGenomeString.assembledStrings);
            for(int[] nextAssembledStringRef:genomeAssembledStringsCopy){
                //get the assembled string it overlaps at
                AssembledString nextAssembledString = assembledStrings.get(nextAssembledStringRef[0]);
                //the overlap point is where overlappingGenomeString overlaps the assembledString
                //plus where overlappedGenomeString overlaps overlappingGenomeString
                int olPoint = nextAssembledStringRef[1]+nextOverlap.overlapPoint;
                //if we're at the start of the string
                //or if the assembled string is already longer than the length of the 
                //secondGS string, continue
                if(olPoint<=0 || olPoint>nextAssembledString.str.length() || nextAssembledString.str.substring(olPoint).length()>overlappingGenomeString.str.length())
                        continue;
                nextAssembledString.addString(overlappingGenomeString, nextOverlap.overlappingString, asIndex, olPoint);
                //if all of the genomes are completed return nextAssembledString's string
                if(nextAssembledString.checkForCompletion(genomeStrings.size())){
                    return nextAssembledString.str;
                }
            }
        }
        //if we don't find the genome string
        
        return "Genome not found";
    }
    
    /**
     * Find all points where the strings overlap
     * @param gs the genome strings
     * @return the genome strings with the overlaps in each one
     */
    public ArrayList<GenomeString> findAllOverlaps(ArrayList<GenomeString> gs){
        if(findAllOverlapsIsNaive){
            for(int i=0;i<gs.size();i++){
                GenomeString str1 = gs.get(i);
                for(GenomeString str2:gs){
                    findOverlaps(str1, str2, i);
                }
            }
        }
        
        return gs;
    }
    
    /**
     * Find out if two strings overlap at one or more points
     * @param str1 the string that might overlap
     * @param str2 the string that might be overlapped
     * @param str1Pos the index of string 1
     */
    public void findOverlaps(GenomeString str1, GenomeString str2, int str1Pos){
        if(findOverlapsIsNaive){
            int stopPoint = Math.max(0, str1.str.length()-str2.str.length());
            for(int i=str2.str.length()-1;i>stopPoint;i--){
                if(matchOverlaps(str1.str, str2.str, i)){
                    int[] nextOl = new int[2];
                    nextOl[0] = str1Pos;
                    nextOl[1] = i;
                    str2.overlaps.add(nextOl);
                }
            }
            return;
        }
//        TODO: efficient method here
//        return;
    }
    // returned overlap stack will contain:
    // - the reference of the genome that's overlapping
    // - the reference of the genome that's overlapped
    // - where they overlap
    Stack<Overlap> getOverlapsByLength(ArrayList<GenomeString> gs){
        Stack<Overlap> obl = new Stack<>();
        ArrayList<Integer[]> olRef = new ArrayList<>();
        for(int i=0;i<gs.size();i++){
            GenomeString overlappingGenomeString = gs.get(i);
            for(int j=0;j<overlappingGenomeString.overlaps.size();j++){
                int[] overlapReferences = overlappingGenomeString.overlaps.get(j);              
                GenomeString overlappedGenomeString = gs.get(overlapReferences[0]);
                Overlap nextOverlap = new Overlap(overlapReferences[0], i, overlapReferences[1], overlappedGenomeString.str.length() - overlapReferences[1]);
                obl.add(nextOverlap);
            }
        }
        Collections.sort(obl);
        return obl;
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
        int overlapLength = Math.min(potentialOverlappedString.length() - overlap, potentialOverlappingString.length());
        //int polgstrlen = potentialOverlappingString.length();
        String potentialOverlappingStringSub = potentialOverlappingString.substring(0, overlapLength);
        String potentialOverlappedStringSub = potentialOverlappedString.substring( overlap );
                
        return  (potentialOverlappingStringSub.equals(potentialOverlappedStringSub));
    }
    
    /**
     * Combines overlapping strings into a single string
     * @param overlappingString the string that overlaps (starts in the middle of the other one)
     * @param overlappedString the string that's overlapped (the other one starts in the middle)
     * @param olPoint the point where they overlap
     * @return the string combining the two at the overlap point
     */
    protected static String combineOverlaps(String overlappingString, String overlappedString, int olPoint){
        
        if(!matchOverlaps(overlappingString,overlappedString,olPoint)){
            return overlappedString;
            //throw new IllegalArgumentException("string " + overlappingString + " and string " + overlappedString + " do not overlap at point " + olPoint + " !");
        }
        
        return overlappedString.substring(0, olPoint) + overlappingString;
    }
}

// GenomeString & AssembledString might be able to be combined into 
// classes implementing interface. Or maybe not. 


class GenomeString{
    String str;
    // [0] = the other GenomeString it overlaps with and 
    // [1] = the point where it overlaps
    ArrayList<int[]> overlaps;
    // [0] = the other AssembledString it overlaps with and 
    // [1] = the point where it overlaps
    ArrayList<int[]> assembledStrings;
    int location = -1; //location -1 means I don't know it yet
    public GenomeString(String str){
        this.str=str;
        this.overlaps = new ArrayList<>();
        this.assembledStrings = new ArrayList<>();
    }
    public GenomeString(String str, int location){
        this.str = str;
        this.location = location;
        this.overlaps = new ArrayList<>();
        this.assembledStrings = new ArrayList<>();
    }
    public AssembledString assembleGenomeStrings(GenomeString overlappingGenomeString, 
                                                    Overlap nextOverlap,
                                                    int genomeStringsSize,
                                                    int asIndex){
            // get the strings in them
            String firstString = overlappingGenomeString.str;
            String secondString = this.str;
            // get the overlap point
            int overlapPoint = nextOverlap.overlapPoint;
            // create the assembled string
            // assuming we didn't screw up the overlap before
            String newAstr = combineOverlaps(firstString, secondString, overlapPoint);
            //array of the genome string references that will be added to the assembledString
            Integer[] genomeStringsUsed = {nextOverlap.overlappedString, nextOverlap.overlappingString};
            // add the assembled string to arraylist
            AssembledString rtrn = new AssembledString(newAstr, genomeStringsSize,genomeStringsUsed,asIndex);
            rtrn.setGenomeStringUsed(nextOverlap.overlappedString);
            rtrn.setGenomeStringUsed(nextOverlap.overlappingString);
//            rtrn.numberOfGenomeStrings +=2;
            return rtrn;
    }
    
}

class AssembledString{
    public String str;
    public BitSet genomeStringIsUsed;
    public int numberOfGenomeStrings;
    public int index;
    
    public AssembledString(String str, int genomeStringsAvailable, int index){
        this.str=str;
        genomeStringIsUsed=new BitSet(genomeStringsAvailable);
        //Arrays.fill(genomeStringIsUsed, false); //probably don't need this
        numberOfGenomeStrings = genomeStringIsUsed.cardinality();
        this.index=index;
    }
    public AssembledString(String str, int genomeStringsAvailable, Integer [] gStrsUsed, int index){
        this.str=str;
        genomeStringIsUsed=new BitSet(genomeStringsAvailable);
        //Arrays.fill(genomeStringIssed, false); //probably don't need this
        numberOfGenomeStrings = 0;
        for(int gStrUsed:gStrsUsed){
            setGenomeStringUsed(gStrUsed);
        }
        this.index=index;
    }
    
    /**
     * Adds a string to the assembled string and marks that it's used
     * @param gs the string to add
     * @param gsRef the reference of the assembled string 
     * @param asRef the reference of the assembled string
     * @param olPoint the point where it overlaps the string
     */
    protected void addString(GenomeString gs, int gsRef, int asRef, int olPoint){
                String newStr = GenomeErrorFree.combineOverlaps(gs.str, this.str, olPoint);
//                if (newStr.equals(this.str))
//                        return;
                this.str = newStr;
                int[] newAsRef = {asRef,olPoint};
                gs.assembledStrings.add(newAsRef);                
                this.genomeStringIsUsed.set(gsRef);
                this.numberOfGenomeStrings = genomeStringIsUsed.cardinality();
    }
    
    protected boolean checkForCompletion(int totalGenomes){
        int gStrUsed=genomeStringIsUsed.cardinality();
        return genomeStringIsUsed.cardinality() == totalGenomes; 
    }
    
    protected void setGenomeStringUsed(int genomeStringNumber){
        genomeStringIsUsed.set(genomeStringNumber);
        numberOfGenomeStrings = genomeStringIsUsed.cardinality();
    }
    
}
class Overlap implements Comparable<Overlap>{
    public int overlappingString;
    public int overlappedString;
    public int overlapPoint;
    public int overlapLength;
    public Overlap(int overlappingString, int overlappedString, int overlapPoint, int overlapLength){
        this.overlappingString=overlappingString;
        this.overlappedString=overlappedString;
        this.overlapPoint=overlapPoint;
        this.overlapLength = overlapLength;
    }

    @Override
    public int compareTo(Overlap o) {
        Integer thisOverlapLength = this.overlapLength;
        Integer otherOverlapLength = o.overlapLength;
        return  otherOverlapLength.compareTo(thisOverlapLength);
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

