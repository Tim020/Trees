package com.tjb.trees.binary;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tim on 28/07/2016.
 */
public class BinarySearchTree<V> implements IBinaryTree<V> {

    private Node<V> root;

    /**
     * Creates an empty Binary Search Tree
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Creates a Binary Search Tree with an initial node
     *
     * @param root The root node of the new tree
     */
    public BinarySearchTree(Node<V> root) {
        this.root = root;
    }

    /**
     * Creates a Binary Search Tree with an initial node
     *
     * @param weight The weight of the root node
     * @param data   The contents of the root node
     */
    public BinarySearchTree(int weight, V data) {
        this.root = new Node(weight, data);
    }

    @Override
    public Node getRootNode() {
        return root;
    }

    /**
     * Inserts a new node into the tree
     *
     * @param weight The weight for the new node
     * @param data   The contents of the new node
     */
    @Override
    public void insert(int weight, V data) {
        if (this.root == null) {
            this.root = new Node(weight, data);
        } else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                if (weight < current.getWeight()) {
                    current = current.getLeftChild();
                    if (current == null) {
                        parent.setLeftChild(new Node(weight, data, parent));
                        break;
                    }
                } else {
                    current = current.getRightChild();
                    if (current == null) {
                        parent.setRightChild(new Node(weight, data, parent));
                        break;
                    }
                }
            }
        }
    }

    /**
     * Searches for a node with the given weight within the tree
     *
     * @param weight The weight of the node to search for
     * @return The node with the given weight or null
     */
    @Override
    public Node<V> search(int weight) {
        if (this.root == null) {
            return null;
        } else {
            return searchRecursive(this.root, weight);
        }
    }

    /**
     * Recursive function to search for a node with the given weight
     *
     * @param root   The current node being checked
     * @param weight The weight to check for
     * @return The node with the given weight or null
     */
    private Node searchRecursive(Node<V> root, int weight) {
        if (root == null) {
            return null;
        } else if (root.getWeight() == weight) {
            return root;
        } else if (weight < root.getWeight()) {
            return searchRecursive(root.getLeftChild(), weight);
        } else {
            return searchRecursive(root.getRightChild(), weight);
        }
    }

    /**
     * @return Checks that the invariant of this Binary Search Tree holds true
     */
    @Override
    public boolean isValidTree() {
        return isValidTreeRecursive(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Recursive call to check the validity of the Binary Search Tree
     *
     * @param node      Current node being checked
     * @param minWeight Minimum weight allowed past this point
     * @param maxWeight Maximum weight allowed past this point
     * @return Whether the tree is valid or not
     */
    private boolean isValidTreeRecursive(Node<V> node, int minWeight, int maxWeight) {
        if (node == null) {
            return true;
        } else if (node.getWeight() < minWeight || node.getWeight() > maxWeight) {
            return false;
        } else {
            return isValidTreeRecursive(node.getLeftChild(), minWeight, node.getWeight()) && isValidTreeRecursive(node.getRightChild(), node.getWeight(), maxWeight);
        }
    }

    /**
     * @return The node with the minimum weight
     */
    @Override
    public Node<V> getMin() {
        Node currentNode = root;
        while (currentNode.getLeftChild() != null) {
            currentNode = currentNode.getLeftChild();
        }
        return currentNode;
    }

    /**
     * @return The node with the maximum weight
     */
    @Override
    public Node<V> getMax() {
        Node currentNode = root;
        while (currentNode.getRightChild() != null) {
            currentNode = currentNode.getRightChild();
        }
        return currentNode;
    }

    /**
     * @return The maximum depth of the tree
     */
    @Override
    public int getHeight() {
        if (root == null) {
            return 0;
        }
        return getHeightRecursive(root);
    }


    /**
     * Recursive function used to calculate the height of the tree
     *
     * @param node The current node
     * @return The height of the tree
     */
    private int getHeightRecursive(Node<V> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getHeightRecursive(node.getLeftChild()), getHeightRecursive(node.getRightChild())) + 1;
    }

    /**
     * Remove the node with the given weight from the tree
     *
     * @param weight The node weight to remove
     */
    @Override
    public void remove(int weight) {
        removeRecursive(this, weight);
    }

    /**
     * Recursive method used to remove a given node from the tree
     *
     * @param tree   The subtree being searched through
     * @param weight The weight of the node to remove
     */
    private void removeRecursive(BinarySearchTree tree, int weight) {
        if (weight < tree.root.getWeight()) {
            removeRecursive(new BinarySearchTree(tree.root.getLeftChild()), weight);
        } else if (weight > tree.root.getWeight()) {
            removeRecursive(new BinarySearchTree(tree.root.getRightChild()), weight);
        } else {
            if (tree.root.getLeftChild() != null && tree.root.getRightChild() != null) {
                BinarySearchTree successor = new BinarySearchTree(tree.root.getRightChild());
                tree.root.setWeight(successor.getMin().getWeight());
                tree.root.setData(successor.getMin().getData());
                successor.remove(successor.root.getWeight());
            } else if (tree.root.getLeftChild() != null) {
                tree.replaceNodeInParent(tree.root, tree.root.getLeftChild());
            } else if (tree.root.getRightChild() != null) {
                tree.replaceNodeInParent(tree.root, tree.root.getRightChild());
            } else {
                tree.replaceNodeInParent(tree.root, null);
            }
        }
    }

    /**
     * Method used to replace the parent of a given node
     *
     * @param parent The original node
     * @param child  The new child node
     */
    private void replaceNodeInParent(Node<V> parent, Node<V> child) {
        if (parent.getParent() != null) {
            if (parent == parent.getParent().getLeftChild()) {
                parent.getParent().setLeftChild(child);
            } else {
                parent.getParent().setRightChild(child);
            }
        }
        if (child != null) {
            child.setParent(parent);
        }
    }

    /**
     * Traverses the tree in order of weight
     *
     * @return A list of all nodes ordered by weight
     */
    @Override
    public ArrayList<Node<V>> traverseToList() {
        ArrayList<Node<V>> nodes = new ArrayList<>();
        traverseToListRecursive(nodes, root);
        return nodes;
    }

    /**
     * Recursive function to traverse the tree and add the nodes to the list
     *
     * @param nodes The list of nodes
     * @param node  The starting point of the recursive call
     */
    private void traverseToListRecursive(ArrayList<Node<V>> nodes, Node node) {
        if (node == null) {
            return;
        }
        traverseToListRecursive(nodes, node.getLeftChild());
        nodes.add(node);
        traverseToListRecursive(nodes, node.getRightChild());
    }

    /**
     * @return The iterator of all nodes in weight order
     */
    @Override
    public Iterator<Node<V>> iterator() {
        return traverseToList().iterator();
    }
}