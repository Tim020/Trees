package com.tjb.binarytrees;

/**
 * Created by Tim on 28/07/2016.
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
