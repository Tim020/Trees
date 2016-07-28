package com.tjb.trees.binary;

import java.util.ArrayList;

/**
 * Created by Tim on 28/07/2016.
 */
public interface IBinaryTree<V> extends Iterable<Node<V>>{

    Node<V> getRootNode();

    void insert(int weight, V data);

    Node<V> search(int weight);

    boolean isValidTree();

    Node<V> getMin();

    Node<V> getMax();

    int getHeight();

    void remove(int weight);

    ArrayList<Node<V>> traverseToList();

}
