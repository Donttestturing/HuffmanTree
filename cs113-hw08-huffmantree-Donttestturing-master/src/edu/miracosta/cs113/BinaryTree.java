package edu.miracosta.cs113;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * BinaryTree : Class for a binary tree that stores objects of type E. Code derived from Koffman & Wolfgang's Data
 * Structures: Abstraction and Design Using Java (2nd).
 *
 * @param <E> Generic to hold any data type
 */
public class BinaryTree<E> implements Serializable {
    /** The root node of this tree. */
    protected Node<E> root;

    /**
     * Default constructor to build an empty BinaryTree.
     */
    public BinaryTree() {
        root = null;
    }

    /**
     * Constructor to build a BinaryTree with the given Node shallow copied and stored as the root.
     *
     * @param root the root node of this tree
     */
    protected BinaryTree(Node<E> root) {
        this.root = root;
    }

    /**
     * Full constructor to build a BinaryTree from provided data (for root) and left + right children/subtrees.
     *
     * @param data datum stored for this tree's root node
     * @param leftTree left subtree of root
     * @param rightTree right subtree of root
     */
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        // Set root to a new Node with the given data
        root = new Node<E>(data);

        // Set left subtree
        if (leftTree != null) {
            root.left = leftTree.root;
        }
        else {
            root.left = null;
        }

        // Set right subtree
        if (rightTree != null) {
            root.right = rightTree.root;
        }
        else {
            root.right = null;
        }
    }
    public Node<E> getLeft(){
        return root.left;
    }
    public Node<E> getRight(){
        return root.right;
    }
    /**
     * Returns the left subtree.
     *
     * @return The left subtree, or null if either the root or left subtree is null
     */
    public BinaryTree<E> getLeftSubtree() {
        if (root != null && root.left != null) {
            return new BinaryTree<E>(root.left);
        }
        else {
            return null;
        }
    }

    /**
     * Returns the right subtree.
     *
     * @return The right subtree, or null if either the root or left subtree is null
     */
    public BinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null){
            return new BinaryTree<E>(root.right);
        }
        else {
            return null;
        }
    }

    /**
     * Determines whether this tree is a leaf.
     *
     * @return true if the root has no children
     */
    public boolean isLeaf() {
        return (root.left == null && root.right == null);
    }

    /**
     * Returns the data associated with the root Node.
     *
     * @return The data stored in the root
     */
    public E getData() {
        return root.data;
    }

    /**
     * Constructs a binary tree by reading its data with the given Scanner object.
     *
     * pre: The input consists of a pre-order traversal of the binary tree, with each Node on its own line.
     * The line "null" indicates a null tree.
     *
     * @param scan The Scanner attached to the input file
     * @return The binary tree constructed from the given input
     */
    public static BinaryTree<String> readBinaryTree(Scanner scan) {
        // Read a line and trim leading and trailing spaces.
        if (!scan.hasNext()) {
            return null;
        }
        else {
            String data = scan.next();

            if (data.equals("null")) {
                return null;
            }
            else {
                BinaryTree<String> leftTree = readBinaryTree(scan);
                BinaryTree<String> rightTree = readBinaryTree(scan);

                return new BinaryTree<String>(data, leftTree, rightTree);
            }
        }
    }

    /**
     * Performs a pre-order traversal of this tree.
     *
     * @param node The local root
     * @param depth The current level in depth of this tree
     * @param sb The String buffer which accumulates the output
     */
    private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append(" ");
        }
        if (node == null) {
            sb.append("null\n");
        }
        else {
            sb.append(node.toString());
            sb.append("\n");

            preOrderTraverse(node.left, depth+1, sb);
            preOrderTraverse(node.right, depth+1, sb);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, 1, sb);
        return sb.toString();
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
        // Use StringBuilder to save memory/time while dynamically building up the output String.
        StringBuilder output = new StringBuilder();

        // Store each line of output initially instead of directly putting them in the StringBuilder because the node
        // data needs to be inserted after the tree structure has been generated.
        List<List<String>> lines = new ArrayList<List<String>>();

        // Store the current height level of the BinaryTree to log its data and construct the tree structure.
        List<Node<E>> currentLevel = new ArrayList<Node<E>>();
        currentLevel.add(root);

        // Store the children of the nodes in the current level to log next.
        List<Node<E>> nextLevel = new ArrayList<Node<E>>();

        // Store the longest encountered node toString to help calculate a visually appealing offset for each tree
        // level.
        int widestNodeStringLength = 0;

        boolean hasMoreNodes = false;
        do {
            // Store all the node data at the current level in order to add it to the List of all lines after
            // completion.
            List<String> currentLine = new ArrayList<String>();

            // Log the toString and children of each node on the current level.
            for (Node<E> currentNode : currentLevel) {
                if (currentNode == null) {
                    currentLine.add(null);
                    nextLevel.add(null);
                    nextLevel.add(null);
                } else {
                    // Log the current nodes toString.
                    String currentNodeString = currentNode.toString();
                    currentLine.add(currentNodeString);

                    // Check if the current node's toString is the longest encountered.
                    if (currentNodeString.length() > widestNodeStringLength) {
                        widestNodeStringLength = currentNodeString.length();
                    }

                    nextLevel.add(currentNode.left);
                    nextLevel.add(currentNode.right);

                    // Set the sentinel value
                    hasMoreNodes = (currentNode.left != null || currentNode.right != null);
                }
            }

            // Ensure that the widestNodeStringLength is even.
            if (widestNodeStringLength % 2 == 1) {
                widestNodeStringLength++;
            }

            // Log the results of all the nodes in the current line.
            lines.add(currentLine);

            // Swap the data from nextLevel to currentLevel, then clear the old data from nextLevel.
            List<Node<E>> tmp = currentLevel;
            currentLevel = nextLevel;
            nextLevel = tmp;
            nextLevel.clear();
        } while (hasMoreNodes);

        // Calculate the number of spaces from the start of the line to the first node's location.
        int levelLeadingSpaces = lines.get(lines.size() - 1).size() * (widestNodeStringLength + 4);
        for (int i = 0; i < lines.size(); i++) {
            // Access the current line and begin building the tree branches to accommodate the stored data.
            List<String> currentLine = lines.get(i);

            if (i > 0) {
                for (int j = 0; j < currentLine.size(); j++) {
                    // Determine which junction character is necessary between the current root and it's leaf nodes.
                    char junctionCharacter = ' ';
                    if (j % 2 == 1) {
                        if (currentLine.get(j - 1) != null) {
                            junctionCharacter = (currentLine.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < currentLine.size() && currentLine.get(j) != null) {
                                junctionCharacter = '└';
                            }
                        }
                    }

                    // Draw the junction character to the output.
                    output.append(junctionCharacter);

                    // Draw and align the tree branches.
                    if (currentLine.get(j) == null) {
                        for (int k = 0; k < levelLeadingSpaces - 1; k++) {
                            // Draw spaces before the current node to move it into proper position.
                            output.append(' ');
                        }
                    } else {
                        // Calculate the distance from the left/right most node to the root.
                        int branchDistance = (int) Math.floor(levelLeadingSpaces / 2.0) - 1;

                        // Extend the right branches of the current line.
                        for (int k = 0; k < branchDistance; k++) {
                            output.append(j % 2 == 0 ? ' ' : '─');
                        }

                        // Draw the branch right angles.
                        output.append(j % 2 == 0 ? '┌' : '┐');

                        // Extend the left branches of the current line.
                        for (int k = 0; k < branchDistance; k++) {
                            output.append(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }

                output.append('\n');
            }

            // Insert nodes into valid locations under the tree branches.
            for (int j = 0; j < currentLine.size(); j++) {
                // Get the toString of the current Node on the current line.
                String currentNodeToString = currentLine.get(j);
                if (currentNodeToString == null) {
                    currentNodeToString = "";
                }

                // Calculate the gap before and after the given Node so it aligns with the tree branches.
                int leftGap = (int) Math.ceil(levelLeadingSpaces / 2.0 - currentNodeToString.length() / 2.0);
                int rightGap = (int) Math.floor(levelLeadingSpaces / 2.0 - currentNodeToString.length() / 2.0);

                // Draw spaces before the current node to move it into proper position. (_____Node)
                for (int x = 0; x < leftGap; x++) {
                    output.append(' ');
                }

                // Draw the current node's toString.
                output.append(currentNodeToString);

                // Draw spaces after the current node to move following nodes into proper position. (Node_____)
                for (int x = 0; x < rightGap; x++) {
                    output.append(' ');
                }
            }

            // Reduce the number of leading spaces by half so the following line can accommodate potentially twice
            // the number of nodes.
            levelLeadingSpaces /= 2;
            output.append('\n');
        }

        return output.toString();
    }

    /**
     * The inner class for the BinaryTree, a specialized node which may hold any data type.
     *
     * @param <E> Generic to hold any data type
     */
    protected static class Node<E> implements Serializable {
        /** The constituent data for this Node. */
        protected E data;
        /** The Node's left subtree. */
        protected Node<E> left;
        /** The Node's right subtree. */
        protected Node<E> right;

        /**
         * Constructor which stores the given data in this Node.
         *
         * @param data The data to hold within the node
         */
        public Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }

    } // End of class Node

} // End of class BinaryTree