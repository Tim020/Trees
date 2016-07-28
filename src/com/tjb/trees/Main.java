package com.tjb.trees;

import com.tjb.trees.binary.BinarySearchTree;
import com.tjb.trees.binary.Node;

/**
 * Created by Tim on 28/07/2016.
 * Testing class
 */
public class Main {

    public static void main(String... args) {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.insert(10, "World");
        tree.insert(5, "Split");
        tree.insert(15, "Cream");
        tree.insert(3, "Banana");
        tree.insert(7, "Hello");
        tree.insert(13, "Ice");
        tree.insert(17, "!");
        tree.remove(15);
        for (Node<String> node : tree.traverseToList()) {
            System.out.println(node.getData());
        }
    }

}
