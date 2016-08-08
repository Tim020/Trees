package com.tjb.trees.binary;

import java.util.ArrayList;

/**
 * Created by Tim on 28/07/2016.
 */
public interface IBinaryTree<V> extends Iterable<INode<V>> {

    INode<V> getRootNode();

    boolean insert(int key, V data);

    INode<V> search(int key);

    boolean isValidTree();

    INode<V> getMin();

    INode<V> getMax();

    int getHeight();

    void remove(int key);

    ArrayList<INode<V>> traverseToList();

}
