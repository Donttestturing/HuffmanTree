package edu.miracosta.cs113;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * HuffmanTreeTest : Tester class for Huffman tree implementation.
 *
 * @author King
 * @version 2.0
 */
public class HuffmanTreeTest {

    /** Original and expected decoded values. */
    private static final String[] DECODED = {"Hello World!!! Ready for Spring 2019?",
                                        "the\tquick\tbrown\tfox\tjumps\tover\tthe\tlazy\tdog\t\t?!\n\n",
                                        "while walking wearily home...\ni wondered where wally was.\n",
                                        "Mike made mellow music with his nice new Neumann microphone.",
                                        "As  the  unit  tests  were  distributed...  the  girls  and  guys  " +
                                                "grimaced  and  groaned.",
                                        "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890 \t\n!.?"};

    /** Encoded values' String lengths based on their own Huffman tree. */
    private static final int[] ENCODED_SIZE = {159, 222, 227, 242, 341, 416};

    /** A HuffmanTree to be built for each new String value. */
    HuffmanTree tree;

    @Test
    public void testEncodedValuesBySize() {
        for (int i = 0; i < DECODED.length; i++) {
            tree = new HuffmanTree(DECODED[i]);
            assertEquals(ENCODED_SIZE[i], tree.encode(DECODED[i]).length());
        }
    }

    @Test
    public void testDecodedValuesAfterTranslation() {
        for (int i = 0; i < DECODED.length; i++) {
            tree = new HuffmanTree(DECODED[i]);

            String encodedValue = tree.encode(DECODED[i]),
            decodedValue = tree.decode(encodedValue);

            assertEquals(DECODED[i], decodedValue);
        }
    }

} // End of class HuffmanTreeTest
