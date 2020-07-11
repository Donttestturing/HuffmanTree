package edu.miracosta.cs113;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Scanner;

/**
 * BinaryTreeTest : Tester class for generic BinaryTree class implementation which employs a pre-order traversal
 * through a given instance of this structure's elements to illustrate its data.
 *
 * @author King
 * @version 2.0
 */
public class BinaryTreeTest {

    // region data

    /** Data to populate the BinaryTree object. */
    private static final char[] CHAR_INPUT = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

    /** Data listed in pre-order traversal order to populate a degenerate BinaryTree. */
    private static final String DEGENERATE_INPUT = "A \nnull \nB \nC \nnull \nD \nnull \nE \nnull \nnull \nnull";
    /** Data listed in pre-order traversal order to populate a perfect BinaryTree. */
    private static final String PERFECT_INPUT = "A \nB \nC \nnull \nnull \nD \nnull \nnull \nE \nF \nnull \nnull " +
            "\nG \nnull \nnull";
    /** Data listed in pre-order traversal order to populate a complete BinaryTree. */
    private static final String COMPLETE_INPUT = "A \nB \nC \nD \nnull \nnull \nE \nnull \nnull \nF \nG \nnull " +
            "\nnull \nnull \nH \nI \nnull \nnull \nJ \nnull \nnull";
    /** Data listed in pre-order traversal order to populate a full BinaryTree. */
    private static final String FULL_INPUT = "A \nB \nnull \nnull \nC \nD \nnull \nnull \nE \nnull \nnull";

    /** Expected value returned by an empty binary tree's toString. */
    private static final String EMPTY_TREE = "null\n";

    /** Expected value returned by a degenerate binary tree's toString. */
    private static final String DEGENERATE_TREE = "A\n null\n B\n  C\n   null\n   D\n    null\n    E\n     " +
            "null\n     null\n  null\n";

    /** Expected value returned by a perfect binary tree's toString. */
    private static final String PERFECT_TREE = "A\n B\n  C\n   null\n   null\n  D\n   null\n   null\n E\n  F\n   " +
            "null\n   null\n  G\n   null\n   null\n";

    /** Expected value returned by a complete binary tree's toString. */
    private static final String COMPLETE_TREE = "A\n B\n  C\n   D\n    null\n    null\n   E\n    null\n    null\n  " +
            "F\n   G\n    null\n    null\n   null\n H\n  I\n   null\n   null\n  J\n   null\n   null\n";

    /** Expected value returned by a full binary tree's toString. */
    private static final String FULL_TREE = "A\n B\n  null\n  null\n C\n  D\n   null\n   null\n  E\n   null\n   " +
            "null\n";

    /** A BinaryTree to contain Node objects of type Character. */
    private BinaryTree<Character> charTree;
    /** A BinaryTree to contain Node objects of type String to demonstrate tests using the readBinaryTree function. */
    private BinaryTree<String> strTree;

    // endregion data

    // region simple BinaryTree tests
    // Tests for public access methods getLeftSubtree, getRightSubtree, getData, and isLeaf.
    // Assumes proper implementation of full and default BinaryTree constructors.

    @Test
    public void testGetLeftSubtreeDefault() {
        // Use default constructor to create a new empty tree.
        charTree = new BinaryTree<Character>();

        // Validate that left child is null using getLeftSubtree.
        assertEquals("Test getLeftSubtree failed - should return null on new empty tree.",
                null,
                charTree.getLeftSubtree());
    }

    @Test
    public void testGetLeftSubtreeData() {
        // Use full constructor to create a tree with a single root 'A' and one left child 'B'.
        charTree = new BinaryTree<Character>(CHAR_INPUT[0],
                (new BinaryTree<Character>(CHAR_INPUT[1], null, null)),
                null);

        // Validate that left child's data is defined by the above instantiation using getLeftSubtree and getData.
        assertEquals("Test getLeftSubtree and getData failed - is there a problem with the full constructor?",
                new Character(CHAR_INPUT[1]),
                charTree.getLeftSubtree().getData());
    }

    @Test
    public void testGetRightSubtreeDefault() {
        // (Mirrors testGerLeftSubtreeDefault)
        // Use default constructor to create a new empty tree.
        charTree = new BinaryTree<Character>();

        // Validate that right child is null using getRightSubtree.
        assertEquals("Test getRightSubtree failed - should return null on new empty tree.",
                null,
                charTree.getRightSubtree());
    }

    @Test
    public void testGetRightSubtreeData() {
        // (Mirrors testGetLeftSubtreeData)
        // Use full constructor to create a tree with a single root 'C' and one right child 'D'.
        charTree = new BinaryTree<Character>(CHAR_INPUT[2],
                null,
                (new BinaryTree<Character>(CHAR_INPUT[3], null, null)));

        // Validate that right child's data is defined by the above instantiation using getRightSubtree and getData.
        assertEquals("Test getRightSubtree and getData failed - is there a problem with the full constructor?",
                new Character(CHAR_INPUT[3]),
                charTree.getRightSubtree().getData());
    }

    @Test
    public void testIsLeaf() {
        // Create left child-to-be with as a single root 'F'. This will be a leaf node.
        BinaryTree<Character> leftChild = new BinaryTree<Character>(CHAR_INPUT[5], null, null);

        // Create right child-to-be as a single root 'G' and one left child 'H'. This will be an internal node.
        BinaryTree<Character> rightChild = new BinaryTree<Character>(CHAR_INPUT[6],
                (new BinaryTree<Character>(CHAR_INPUT[7], null, null)),
                null);

        // Use full constructor to create a tree with a parent root 'E' and its designated children.
        charTree = new BinaryTree<Character>(CHAR_INPUT[4], leftChild, rightChild);

        // Validate that parent node is not a leaf.
        assertFalse("Test isLeaf failed - should return false for a tree that is not empty.",
                charTree.isLeaf());

        // Validate that the left child is a leaf.
        assertTrue("Test isLeaf failed - should return true for a node without children.",
                charTree.getLeftSubtree().isLeaf());

        // Validate that the right child is not a leaf.
        assertFalse("Test isLeaf failed - should return false for a tree that is not empty.",
                charTree.getRightSubtree().isLeaf());
    }

    @Test
    public void testIsLeafError() {
        // Use default constructor to create a new empty tree
        charTree = new BinaryTree<Character>();

        // Validate that a call to isLeaf on an empty tree throws a NullPointerException
        try {
            charTree.isLeaf();
            fail("Test isLeaf failed - call to an empty tree throws a NullPointerException.");
        }
        catch (NullPointerException npe) { /* Test passed. */ }
    }

    // endregion simple BinaryTree tests

    // region toString, preOrderTraverse, and readBinaryTree tests
    // The following tests will validate proper construction of the common types of binary trees (degenerate, full,
    // complete, perfect) using the BinaryTree's toString and its constituent preOrderTraverse function.
    // In addition, the readBinaryTree function will be used to create these trees.

    @Test
    public void testEmptyTree() {
        // Use default constructor to create a new empty tree.
        strTree = new BinaryTree<String>();

        // Validate equivalency with toString method
        assertEquals("Test empty tree with toString failed.", EMPTY_TREE, strTree.toString());
    }

    @Test
    public void testDegenerateTrees() {
        // Each node has only 1 child.
        strTree = BinaryTree.readBinaryTree(new Scanner(DEGENERATE_INPUT));

        assert strTree != null;
        assertEquals("Test toString failed - might there be an error within the " +
                "readBinaryTree method?", DEGENERATE_TREE, strTree.toString());
    }

    @Test
    public void testPerfectTree() {
        // All internal nodes have two children and all leaves are at the same level.
        strTree = BinaryTree.readBinaryTree(new Scanner(PERFECT_INPUT));

        assert strTree != null;
        assertEquals("Test toString failed - might there be an error within the " +
                "readBinaryTree method?", PERFECT_TREE, strTree.toString());
    }

    @Test
    public void testCompleteTree() {
        // Every level is filled with the possible exception of the last, where its nodes are to the left as possible.
        strTree = BinaryTree.readBinaryTree(new Scanner(COMPLETE_INPUT));

        assert strTree != null;
        assertEquals("Test toString failed - might there be an error within the " +
                "readBinaryTree method?", COMPLETE_TREE, strTree.toString());
    }

    @Test
    public void testFullTree() {
        // Every node has 0 or 2 children.
        strTree = BinaryTree.readBinaryTree(new Scanner(FULL_INPUT));

        assert strTree != null;
        assertEquals("Test toString failed - might there be an error within the " +
                "readBinaryTree method?", FULL_TREE, strTree.toString());
    }

    // endregion toString, preOrderTraverse, and readBinaryTree tests

} // End of class BinaryTreeTest