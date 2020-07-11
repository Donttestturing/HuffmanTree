package edu.miracosta.cs113;

/**
 * Class to represent and build a Huffman tree. Implementation code to be derived from Koffman & Wolfgang's Data
 * Structures: Abstraction and Design Using Java (2nd).
 *
 * @author King
 * @version 1.0
 */
public interface HuffmanInterface {

    /**
     * Decodes a message using the generated Huffman tree, where each character in the given message ('1's and '0's)
     * corresponds to traversals through said tree.
     *
     * @param codedMessage The compressed message based on this Huffman tree's encoding
     * @return The given message in its decoded form
     */
    public String decode(String codedMessage);

    /**
     * Outputs the message encoded from the generated Huffman tree.
     * pre: the Huffman tree has been built using characters by which the message is only comprised.
     *
     * @param message The message to be decoded
     * @return The given message in its specific Huffman encoding of '1's and '0's
     */
    public String encode(String message);

} // End of class HuffmanTree