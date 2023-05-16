package Clases_auxiliares;

public class AVLTree {
    private AVLNode root;

    public void insert(String value) {
        root = insertHelper(root, value);
    }

    private AVLNode insertHelper(AVLNode node, String value) {
        if (node == null) {
            return new AVLNode(value);
        }
        int compareResult = value.compareTo(node.getValue());
        if (compareResult < 0) {
            node.setLeft(insertHelper(node.getLeft(), value));
        } else if (compareResult > 0) {
            node.setRight(insertHelper(node.getRight(), value));
        } else {
            return node;
        }
        update(node);
        return balance(node);
    }

    private AVLNode balance(AVLNode node) {
        if (node == null) {
            return node;
        }
        if (node.getBalanceFactor() > 1) {
            if (node.getRight() != null && node.getRight().getBalanceFactor() < 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        } else if (node.getBalanceFactor() < -1) {
            if (node.getLeft() != null && node.getLeft().getBalanceFactor() > 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }
        return node;
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        update(node);
        update(newRoot);
        return newRoot;
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        update(node);
        update(newRoot);
        return newRoot;
    }

    private void update(AVLNode node) {
        int leftHeight = (node.getLeft() == null) ? -1 : node.getLeft().getHeight();
        int rightHeight = (node.getRight() == null) ? -1 : node.getRight().getHeight();
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(rightHeight - leftHeight);
    }


    public void delete(String value) {
        root = deleteHelper(root, value);
        if (root == null) {
            // Tree is empty
            return;
        }
    }

    private AVLNode deleteHelper(AVLNode node, String value) {
        if (node == null) {

            return null;
        }
        int compareResult = value.compareTo(node.getValue());
        if (compareResult < 0) {
            node.setLeft(deleteHelper(node.getLeft(), value));
        } else if (compareResult > 0) {
            node.setRight(deleteHelper(node.getRight(), value));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
            } else {
                AVLNode successor = findSuccessor(node.getRight());
                node.setValue(successor.getValue());
                node.setRight(deleteHelper(node.getRight(), successor.getValue()));
            }
        }
        if (node != null) {
            update(node);
            node = balance(node);
        }
        return node;
    }


    private AVLNode findSuccessor(AVLNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public AVLNode find(String value) {
        AVLNode current = root;
        while (current != null) {
            if (current.getValue().equals(value)) {
                return current;
            } else if (value.compareTo(current.getValue()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(root, sb);
        return sb.toString();
    }

    private void toStringHelper(AVLNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        toStringHelper(node.getLeft(), sb);
        sb.append(node.getValue()).append(" ");
        toStringHelper(node.getRight(), sb);
    }
}