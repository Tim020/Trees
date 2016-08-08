package com.tjb.trees.binary;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tim on 28/07/2016.
 */
public class SimpleBinarySearchTree<V> implements IBinaryTree<V> {

    protected SimpleNode<V> root;

    /**
     * Creates an empty Binary Search Tree
     */
    public SimpleBinarySearchTree() {
        this.root = null;
    }

    /**
     * Creates a Binary Search Tree with an initial node
     *
     * @param root The root node of the new tree
     */
    public SimpleBinarySearchTree(SimpleNode<V> root) {
        this.root = root;
    }

    /**
     * Creates a Binary Search Tree with an initial node
     *
     * @param weight The weight of the root node
     * @param data   The contents of the root node
     */
    public SimpleBinarySearchTree(int weight, V data) {
        this.root = new SimpleNode(weight, data);
    }

    /**
     * @return The root node of the tree
     */
    @Override
    public SimpleNode getRootNode() {
        return root;
    }

    /**
     * Inserts a new node into the tree
     *
     * @param weight The weight for the new node
     * @param data   The contents of the new node
     */
    @Override
    public boolean insert(int weight, V data) {
        if (this.root == null) {
            SimpleNode n = new SimpleNode(weight, data);
            this.root = n;
            return true;
        } else {
            SimpleNode current = root;
            SimpleNode parent;
            while (true) {
                if (weight == current.getWeight()) {
                    return false;
                }
                parent = current;
                if (weight < current.getWeight()) {
                    current = current.getLeftChild();
                    if (current == null) {
                        SimpleNode n = new SimpleNode(weight, data, parent);
                        parent.setLeftChild(n);
                        return true;
                    }
                } else {
                    current = current.getRightChild();
                    if (current == null) {
                        SimpleNode n = new SimpleNode(weight, data, parent);
                        parent.setRightChild(n);
                        return true;
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
    public SimpleNode<V> search(int weight) {
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
    private SimpleNode<V> searchRecursive(SimpleNode<V> root, int weight) {
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
    private boolean isValidTreeRecursive(SimpleNode<V> node, int minWeight, int maxWeight) {
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
    public SimpleNode<V> getMin() {
        SimpleNode currentNode = root;
        while (currentNode.getLeftChild() != null) {
            currentNode = currentNode.getLeftChild();
        }
        return currentNode;
    }

    /**
     * @return The node with the maximum weight
     */
    @Override
    public SimpleNode<V> getMax() {
        SimpleNode currentNode = root;
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
            return -1;
        }
        return getHeightRecursive(root);
    }


    /**
     * Recursive function used to calculate the height of the tree
     *
     * @param node The current node
     * @return The height of the tree
     */
    protected int getHeightRecursive(SimpleNode<V> node) {
        if (node == null) {
            return -1;
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
    private void removeRecursive(SimpleBinarySearchTree tree, int weight) {
        if (weight < tree.root.getWeight()) {
            removeRecursive(new SimpleBinarySearchTree(tree.root.getLeftChild()), weight);
        } else if (weight > tree.root.getWeight()) {
            removeRecursive(new SimpleBinarySearchTree(tree.root.getRightChild()), weight);
        } else {
            if (tree.root.getLeftChild() != null && tree.root.getRightChild() != null) {
                SimpleBinarySearchTree successor = new SimpleBinarySearchTree(tree.root.getRightChild());
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
    private void replaceNodeInParent(SimpleNode<V> parent, SimpleNode<V> child) {
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

    public ArrayList<SimpleNode<V>> getPreOrder() {
        ArrayList<SimpleNode<V>> nodeList = new ArrayList<>();
        getPreOrderRecursive(root, nodeList);
        return nodeList;
    }

    private void getPreOrderRecursive(SimpleNode<V> node, ArrayList<SimpleNode<V>> list) {
        if (node != null) {
            list.add(node);
            getPreOrderRecursive(node.getLeftChild(), list);
            getPreOrderRecursive(node.getRightChild(), list);
        }
    }

    /**
     * Traverses the tree in order of weight
     *
     * @return A list of all nodes ordered by weight
     */
    @Override
    public ArrayList<INode<V>> traverseToList() {
        ArrayList<INode<V>> nodes = new ArrayList<>();
        traverseToListRecursive(nodes, root);
        return nodes;
    }

    /**
     * Recursive function to traverse the tree and add the nodes to the list
     *
     * @param nodes The list of nodes
     * @param node  The starting point of the recursive call
     */
    private void traverseToListRecursive(ArrayList<INode<V>> nodes, SimpleNode node) {
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
    public Iterator<INode<V>> iterator() {
        return traverseToList().iterator();
    }
}