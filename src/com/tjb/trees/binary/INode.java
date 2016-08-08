package com.tjb.trees.binary;

/**
 * Created by Tim on 31/07/2016.
 */
public interface INode<V> {

    INode getLeftChild();

    INode getRightChild();

    INode getParent();

    V getData();

}
