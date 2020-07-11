package edu.miracosta.cs113;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Scanner;
/**
 * HuffmanTreeEncoderMain.java: Driver class to demonstrate HuffmanTree class is functional/implemented properly
 *
 * Class Invariant: none
 *
 * @author        Brendan Devlin <Donttestturing@gmail.com>
 * @version       1.0
 **/
public class HuffmanTreeEncoderMain {
    //constants
    private static final String URL = "https://github.com/Donttestturing/resume";  //can be changed based on desire
    private static final String FILE_NAME = "HuffmanFile.txt";
    private static final String ENCODED_FILE_NAME = "EncodedFile.txt";
    private static final String DECODED_FILE_NAME = "DecodedFile.txt";

    public static void main (String[] args){

        StringBuilder stringFileText = new StringBuilder(); //used in building HuffmanTree from a given URL

        System.out.println("Demonstrating HuffmanTree implementation works:");
        System.out.println("Creating Original File: ");
//Part 1 //first taking a URL and output text file name to create the orig. file, use this file to create a huffman tree
        try{
            TextFileGenerator.makeCleanFile(URL, FILE_NAME);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("\nCreated " + FILE_NAME + " from "  + URL);

        //reading clean file and building a large string to feed into the HuffmanTree constructor to build a custom HuffmanTree for that file
        try {
            Scanner marker = new Scanner(new FileInputStream(FILE_NAME));
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            while (marker.hasNextLine()) {
                stringFileText.append(reader.readLine() + "\n");
                marker.nextLine();
            }
            reader.close();
            marker.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Taking text from File " + FILE_NAME + " made from " + URL + " and making a custom Huffman Tree from it");
        HuffmanTree tree = new HuffmanTree(stringFileText.toString()); //tree is now built

//Part 2 //using built huffman tree to create an encoded file
        System.out.println("\nCreating encoded file named: " + ENCODED_FILE_NAME);
        writeEncodedToFile(stringFileText.toString(), tree, ENCODED_FILE_NAME);

//Part 3 //using built Huffman Tree to create a decoded file, using the encoded file
        System.out.println("Creating Decoded file using Encoded file which is named: " + DECODED_FILE_NAME);
        writeDecodedToFile(ENCODED_FILE_NAME, tree, DECODED_FILE_NAME);

////Part 4 and Part 5
        System.out.println("\nOutputting number of bits for each file: ");

        int origFileNumBits = TextFileGenerator.getNumChars(FILE_NAME) * 16;
        int decodedFileNumBits = TextFileGenerator.getNumChars(DECODED_FILE_NAME) * 16;
        System.out.println("Number of bits in Original file is: " + origFileNumBits);
        System.out.println("Number of bits in Encoded file is: " + TextFileGenerator.getNumChars(ENCODED_FILE_NAME));
        System.out.println("Number of bits in Decoded file is: " +  decodedFileNumBits); //should be same as original

        System.out.println("Number of bits in Original is equal to number of bits in Decoded is: " + (origFileNumBits == decodedFileNumBits));

        System.out.println("\nOutputting percentage of compression: ");
        double compressionPercent = (double) origFileNumBits / (double) TextFileGenerator.getNumChars(ENCODED_FILE_NAME);
        System.out.printf("%s %.5f%%","Percentage of compression is:", compressionPercent * 100);

    }
//static methods
    /**
     * Static method to write encoded text using a built HuffmanTree of that text, outputting to a file
     *
     * @param fileText text to be encoded and written to file
     * @param encodingTree tree used in encoding text
     * @param outputFileName name of file to be created that holds encoded text using a HuffmanTree
     * @return true if writing is completed, return false if exception is thrown
     */
    private static boolean writeEncodedToFile(String fileText, HuffmanTree encodingTree, String outputFileName){
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream(outputFileName));
            writer.write(encodingTree.encode(fileText)); //writing 1s and 0s to EncodedFile.txt
            writer.close();
            return true;
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }

    }
    /**
     * Static method to take in an encoded file and write decoded text using a built HuffmanTree of that file, outputting to a file
     *
     * @param inputFileName name of encoded file to be decoded and written to file
     * @param decodingTree tree used in decoding text
     * @param outputFileName name of file to be created that holds decoded text using a HuffmanTree
     * @return true if writing is completed, return false if exception is thrown
     */
    private static boolean writeDecodedToFile(String inputFileName, HuffmanTree decodingTree, String outputFileName){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
            Scanner marker = new Scanner(new FileInputStream(inputFileName));
            PrintWriter writer = new PrintWriter(new FileOutputStream(outputFileName));
            while(marker.hasNextLine()) {
                writer.write(decodingTree.decode(reader.readLine()));
                marker.nextLine();
            }
            reader.close();
            writer.close();
            return true;
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }


    }

}



