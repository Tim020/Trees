package com.tjb.trees.binary;

/**
 * Created by Tim on 28/07/2016.
 */
public class SimpleNode<V> implements INode<V> {

    private SimpleNode<V> leftChild;
    private SimpleNode<V> rightChild;
    private SimpleNode<V> parent;

    private int weight;

    private V data;

    public SimpleNode(int weight, V data) {
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
        this.weight = weight;
        this.data = data;
    }

    public SimpleNode(int weight, V data, SimpleNode<V> parent) {
        this.leftChild = null;
        this.rightChild = null;
        this.parent = parent;
        this.weight = weight;
        this.data = data;
    }

    public SimpleNode getLeftChild() {
        return leftChild;
    }

    public SimpleNode getRightChild() {
        return rightChild;
    }

    public SimpleNode getParent() {
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

    public void setLeftChild(SimpleNode<V> leftChild) {
        this.leftChild = leftChild;
        if (leftChild != null) {
            leftChild.parent = this;
        }
    }

    public void setRightChild(SimpleNode<V> rightChild) {
        this.rightChild = rightChild;
        if (rightChild != null) {
            rightChild.parent = this;
        }
    }

    public void setParent(SimpleNode<V> parent) {
        this.parent = parent;
    }

    public void setData(V data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleNode) {
            return ((SimpleNode) obj).getWeight() == this.weight && ((SimpleNode) obj).getParent() == this.parent;
        }
        return false;
    }
}