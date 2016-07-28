package com.tjb.trees.binary;

/**
 * Created by Tim on 28/07/2016.
 */
public class Node<V> {

    private Node<V> leftChild;
    private Node<V> rightChild;
    private Node<V> parent;

    private int weight;

    private V data;

    public Node(int weight, V data) {
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
        this.weight = weight;
        this.data = data;
    }

    public Node(int weight, V data, Node<V> parent) {
        this.leftChild = null;
        this.rightChild = null;
        this.parent = parent;
        this.weight = weight;
        this.data = data;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Node getParent() {
        return parent;
    }

    public int getWeight() {
        return weight;
    }

    public V getData() {
        return data;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setLeftChild(Node<V> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node<V> rightChild) {
        this.rightChild = rightChild;
    }

    public void setParent(Node<V> parent) {
        this.parent = parent;
    }

    public void setData(V data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            return ((Node) obj).getWeight() == this.weight && ((Node) obj).getParent() == this.parent;
        }
        return false;
    }
}
