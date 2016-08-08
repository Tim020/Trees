package com.tjb.trees.binary;

/**
 * Created by Tim on 28/07/2016.
 */
public class AVLTree<V> extends SimpleBinarySearchTree<V> {

    private class AVLNode<V> extends SimpleNode<V> {

        private int balance;

        public AVLNode(int weight, V data) {
            super(weight, data);
        }

        public AVLNode(int weight, V data, AVLNode<V> parent) {
            super(weight, data, parent);
        }
    }

    /**
     * Creates an empty Binary Search Tree
     */
    public AVLTree() {
        super();
    }

    /**
     * Creates a Binary Search Tree with an initial node
     *
     * @param root The root node of the new tree
     */
    public AVLTree(AVLNode<V> root) {
        super(root);
    }

    /**
     * Creates a Binary Search Tree with an initial node
     *
     * @param weight The weight of the root node
     * @param data   The contents of the root node
     */
    public AVLTree(int weight, V data) {
        super(weight, data);
    }

    @Override
    public boolean insert(int weight, V data) {
        if (this.root == null) {
            this.root = new AVLNode(weight, data);
        } else {
            AVLNode current = (AVLNode) root;
            AVLNode parent;
            while (true) {
                if (weight == current.getWeight()) {
                    return false;
                }
                parent = current;
                if (weight < current.getWeight()) {
                    current = (AVLNode) current.getLeftChild();
                    if (current == null) {
                        parent.setLeftChild(new AVLNode(weight, data, parent));
                        rebalance(parent);
                        break;
                    }
                } else {
                    current = (AVLNode) current.getRightChild();
                    if (current == null) {
                        AVLNode n = new AVLNode(weight, data, parent);
                        parent.setRightChild(n);
                        rebalance(parent);
                        break;
                    }
                }
            }
        }
        return true;
    }

    private void rebalance(AVLNode<V> n) {
        setNodeBalance(n);
        if (n.balance == -2) {
            if (getHeightRecursive(n.getLeftChild().getLeftChild()) >= getHeightRecursive(n.getLeftChild().getRightChild())) {
                n = rotateRight(n);
            } else {
                n = rotateLeftThenRight(n);
            }
        } else if (n.balance == 2) {
            if (getHeightRecursive(n.getRightChild().getRightChild()) >= getHeightRecursive(n.getRightChild().getLeftChild())) {
                n = rotateLeft(n);
            } else {
                n = rotateRightThenLeft(n);
            }
        }
        if (n.getParent() != null) {
            rebalance((AVLNode) n.getParent());
        } else {
            root = n;
        }
    }

    private AVLNode<V> rotateLeft(AVLNode<V> a) {
        AVLNode b = (AVLNode) a.getRightChild();
        b.setParent(a.getParent());
        a.setRightChild(b.getLeftChild());

        if (a.getRightChild() != null) {
            a.getRightChild().setParent(a);
        }

        b.setLeftChild(a);
        a.setParent(b);

        if (b.getParent() != null) {
            if (b.getParent().getRightChild() == a) {
                b.getParent().setRightChild(b);
            } else {
                b.getParent().setLeftChild(b);
            }
        }

        setNodeBalance(a, b);
        return b;
    }

    private AVLNode<V> rotateRight(AVLNode<V> a) {
        AVLNode b = (AVLNode) a.getLeftChild();
        b.setParent(a.getParent());
        a.setLeftChild(b.getRightChild());

        if (a.getLeftChild() != null) {
            a.getLeftChild().setParent(a);
        }
        b.setRightChild(a);
        a.setParent(b);

        if (b.getParent() != null) {
            if (b.getParent().getRightChild() == a) {
                b.getParent().setRightChild(b);
            } else {
                b.getParent().setLeftChild(b);
            }
        }

        setNodeBalance(a, b);
        return b;
    }

    private AVLNode<V> rotateLeftThenRight(AVLNode<V> n) {
        n.setLeftChild(rotateLeft((AVLNode) n.getLeftChild()));
        return rotateRight(n);
    }

    private AVLNode<V> rotateRightThenLeft(AVLNode<V> n) {
        n.setRightChild(rotateRight((AVLNode) n.getRightChild()));
        return rotateLeft(n);
    }

    private void setNodeBalance(AVLNode... nodes) {
        for (AVLNode n : nodes) {
            n.balance = getHeightRecursive(n.getRightChild()) - getHeightRecursive(n.getLeftChild());
        }
    }

    @Override
    public void remove(int weight) {
        if (root == null) {
            return;
        }

        AVLNode n = (AVLNode) root;
        AVLNode parent = (AVLNode) root;
        AVLNode delNode = null;
        AVLNode child = (AVLNode) root;

        while (child != null) {
            parent = n;
            n = child;
            child = weight >= n.getWeight() ? (AVLNode) n.getRightChild() : (AVLNode) n.getLeftChild();
            if (weight == n.getWeight()) {
                delNode = n;
            }
        }

        if (delNode != null) {
            delNode.setWeight(n.getWeight());
            delNode.setData(n.getData());

            child = n.getLeftChild() != null ? (AVLNode) n.getLeftChild() : (AVLNode) n.getRightChild();

            if (root.getWeight() == weight) {
                root = child;
            } else {
                if (parent.getLeftChild() == n) {
                    parent.setLeftChild(child);
                } else {
                    parent.setRightChild(child);
                }
                rebalance(parent);
            }
        }
    }

    @Override
    public boolean isValidTree() {
        return super.isValidTree() && getBalanceFactor() >= -1 && getBalanceFactor() <= 1;
    }

    public int getBalanceFactor() {
        return getBalanceFactor(root);
    }

    private int getBalanceFactor(SimpleNode<V> node) {
        if (node == null) {
            return 0;
        } else {
            return getHeightRecursive(node.getRightChild()) - getHeightRecursive(node.getLeftChild());
        }
    }
}
