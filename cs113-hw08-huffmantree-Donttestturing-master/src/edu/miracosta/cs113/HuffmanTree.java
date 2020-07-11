package edu.miracosta.cs113;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.Serializable;
/**
 * HuffmanTree.java: Class to create a custom HuffmanTree based on a particular file, has encode and decode methods to encode or decode text based on that particular huffman tree
 * Used to compress text data in a file
 *
 * Class Invariant: none
 *
 * @author        Brendan Devlin <Donttestturing@gmail.com>
 * @version       1.0
 **/
public class HuffmanTree extends BinaryTree implements HuffmanInterface {

    //reference to completed huffman tree
    private BinaryTree<HuffmanData> huffmanTree;

    /**
     * Constructor for a HuffmanTree, takes in a String s and calculates the weight (frequency) of each character in imputed String, then stores each character with its respective weight to a HuffmanData object, and
     * stores that object in a HuffmanData array, the constructor ends with a call to buildTree(HuffmanData[] data) method, imputing the built array into that method, which builds the custom HuffmanTree
     *
     * @param s String representing the input in text of which the HuffmanTree will be built
     */
    public HuffmanTree(String s){
        codeLength = s.length();
        HuffmanData[] sData = new HuffmanData[s.length()];
        int[] frequency = new int[s.length()];
        char[] charArray = s.toCharArray();

        int i, j;

        //trying to find the weight of each character in given string
        for (i = 0; i < s.length(); i++){
            frequency[i] = 1;

            for (j = i+1; j < s.length(); j++){
                if (charArray[i] == charArray[j]){
                    //count it
                    frequency[i]++;
                    //set charArray[j] to * to avoid revisiting character
                    charArray[j] = '*';
                }
            }
        }
        for (int k = 0; k < s.length(); k++) {  //building HuffmanData array using found frequency and the character value
            if (charArray[k] != '*'){
                sData[k] = new HuffmanData(frequency[k], charArray[k]);
            }
        }
        buildTree(sData); //this will build the tree using the array I have made from the string

    }
    /**
     * Outputs the message encoded from the generated Huffman tree.
     * pre: the Huffman tree has been built using characters by which the message is only comprised.
     *
     * @param message The message to be encoded
     * @return The given message in its specific Huffman encoding of '1's and '0's
     */
    public String encode(String message){ //this will take in a string, convert that string into a huffman tree unique to that string, then output that string as a sequence of 1s and 0s using the build huffman tree
        StringBuilder sb = new StringBuilder();
        HuffmanTree tree = new HuffmanTree(message);
        String[] charPathArray = tree.printCode(message, tree.getHuffmanTree()).split(" "); //this an array of all the leaf node paths
        Character[] charValueArray = new Character[charPathArray.length];

        for (int i = 0; i <  charPathArray.length; i++) {
            charValueArray[i] = tree.decode(charPathArray[i]).charAt(0); ;// tree.decode(charPathArray[i]);
        }

        while (message.length() > 0) {
            Character comparisonCharacter = message.charAt(0);
            for(int i = 0; i < charValueArray.length; i++){
                if (comparisonCharacter == charValueArray[i]){ //match has been found, code is in slot i
                    sb.append(charPathArray[i]);
                }
            }
            message = message.substring(1);

        }
        return sb.toString(); //going to return a large string of 0s and 1s
    }
    /**
     * Decodes a message using the generated Huffman tree, where each character in the given message ('1's and '0's)
     * corresponds to traversals through said tree.
     *
     * @param codedMessage The compressed message based on this Huffman tree's encoding
     * @return The given message in its decoded form
     */
    public String decode(String codedMessage){
        StringBuilder result = new StringBuilder();

        BinaryTree<HuffmanData> currentTree = huffmanTree;
        for (int i = 0; i < codedMessage.length(); i++) {
            if (codedMessage.charAt(i) == '1'){
                currentTree = currentTree.getRightSubtree();
            } else {
                currentTree = currentTree.getLeftSubtree();
            }
            if (currentTree.isLeaf()){
                HuffmanData theData = currentTree.getData();
                result.append(theData.symbol);
                currentTree = huffmanTree;
            }
        }
        return result.toString();
    }

    /**
     * Builds the Huffman tree using the given alphabet and weights
     * post: huffmanTree contains a reference to the built huffman tree
     *
     * @param symbols an array of HuffmanData objects
     */
    public void buildTree(HuffmanData[] symbols){
        Queue<BinaryTree<HuffmanData>> huffmanTreeQueue = new PriorityQueue<BinaryTree<HuffmanData>>(symbols.length, new CompareHuffmanTrees());
        for (HuffmanData nextSymbol: symbols){
            if (nextSymbol != null){
                BinaryTree<HuffmanData> aBinaryTree = new BinaryTree<HuffmanData>(nextSymbol, null, null);
                huffmanTreeQueue.offer(aBinaryTree);
            }
        }
        //Build the tree
        while(huffmanTreeQueue.size() > 1){
            BinaryTree<HuffmanData> left = huffmanTreeQueue.poll();
            BinaryTree<HuffmanData> right = huffmanTreeQueue.poll();
            double leftWeight = left.getData().weight;
            double rightWeight = right.getData().weight;
            HuffmanData sum = new HuffmanData(leftWeight + rightWeight, null);
            BinaryTree<HuffmanData> newTree = new BinaryTree<HuffmanData>(sum, left, right);
            huffmanTreeQueue.offer(newTree);

        }
        //huffmanTreeQueue should now only contain one BinaryTree of HuffmanData
        huffmanTree = huffmanTreeQueue.poll();

    }
    //variables for printCode method, since they will be unique and constant for each HuffmanTree object, one can store references to them
    private int codeLength;
    private StringBuilder stringBuilder = new StringBuilder();
    /**
     * Outputs the resulting code paths of each leaf node from doing a preorder traversal of the binary tree
     *
     * @param code the string that the huffman tree is built upon
     * @param tree the huffman tree that is built from code
     * @return
     */
    public String printCode(String code, BinaryTree<HuffmanData> tree){  //this is doing a preorder traversal and printing path to each leaf node as 0s or 1s
        HuffmanData data = tree.getData();

        if (data.symbol != null){
            if (data.symbol.equals(" ")){
                System.out.println("space: " + code);
            } else{
                stringBuilder.append(code.substring(codeLength) + " ");
            }
        } else {
            printCode(code + "0", tree.getLeftSubtree());
            printCode(code + "1", tree.getRightSubtree());
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return huffmanTree.toString();
    }
    /**
     * Constructs and returns a polymorphic String visualization of this {@link BinaryTree}.
     *
     * @return A String containing a structural visualization of this {@link BinaryTree} and the data stored within
     *         each {@link Node} it contains.
     *
     * @author Christopher Martin
     * @version 1.0
     */
    public String toString2() {
        return huffmanTree.toString2();
    }
    public BinaryTree<HuffmanData> getHuffmanTree(){
        return huffmanTree;
    }
    public int getCodeLength() {
        return codeLength;
    }
//inner classes
    /** datum for a HuffmanTree */
    public static class HuffmanData implements Serializable {
        //probability of character in a tree
        private double weight;
        //the symbol at a leaf node
        private Character symbol;
        public HuffmanData(double weight, Character symbol){
            this.weight = weight;
            this.symbol = symbol;
        }
        @Override
        public String toString() {
            return (symbol + " " + weight);
        }
    }
/** comparator for HuffmanTrees */
    private static class CompareHuffmanTrees implements Comparator<BinaryTree<HuffmanData>> {
    /**
     * Compare two objects
     * @param leftTree the left hand object
     * @param rightTree the right hand object
     * @return -1 if left is less than right, 1 if the opposite, 0 if left equals right
     */
        public int compare(BinaryTree<HuffmanData> leftTree, BinaryTree<HuffmanData> rightTree){
            double leftWeight = leftTree.getData().weight;
            double rightWeight = rightTree.getData().weight;
            return Double.compare(leftWeight, rightWeight);
        }
    }


}


