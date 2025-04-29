//Ivan Vasilev 
import java.util.Scanner;

public class AVLTree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfCommands = sc.nextInt();
        SuperAVLTree<Integer> tree = new SuperAVLTree<>();
        for (int i = 0; i < numberOfCommands; i++) {
            String command = sc.next();
            if (command.equals("ADD")) {
                int key = sc.nextInt();
                int value = sc.nextInt();
                tree.ADD(key, value);
            } else if (command.equals("DELETE")) {
                int key = sc.nextInt();
                tree.DELETE(key);
            } else if (command.equals("LOOKUP")) {
                int key = sc.nextInt();
                tree.LOOKUP(key);
            } else if (command.equals("PRINT_ROTATIONS")) {
                tree.PRINTROTATIONS();
            }
        }
    }
}

class Node<T> {
    T key;
    T value;
    int high;
    Node<T> leftChild, rightChild;

    public Node(T key, T value) {
        this.key = key;
        this.value = value;
        this.high = 1;
    }
}

class SuperAVLTree<T extends Comparable<T>> {
    Node<T> root;
    int amountOfRotations = 0;

    void ADD(T key, T value) {
        root = adding(root, key, value);
    }

    Node<T> adding(Node<T> node, T key, T value) {
        if (node == null) {
            return new Node<>(key, value);
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.leftChild = adding(node.leftChild, key, value);
        } else if (compare > 0) {
            node.rightChild = adding(node.rightChild, key, value);
        } else {
            System.out.println("KEY ALREADY EXISTS");
            return node;
        }
        node.high = 1 + Math.max(isValidHigh(node.leftChild), isValidHigh(node.rightChild));
        return balanceTheTree(node);
    }

    public Node<T> balanceTheTree(Node<T> node) {
        if (node == null) {
            return node;
        }
        int balance = checkBalance(node);
        if (balance > 1 && checkBalance(node.leftChild) >= 0){
            return rotation(node, true);
        }
        if (balance > 1 && checkBalance(node.leftChild) < 0) {
            node.leftChild = rotation(node.leftChild, false);
            return rotation(node, true);
        }
        if (balance < -1 && checkBalance(node.rightChild) <= 0){
            return rotation(node, false);
        }
        if (balance < -1 && checkBalance(node.rightChild) > 0) {
            node.rightChild = rotation(node.rightChild, true);
            return rotation(node, false);
        }
        return node;
    }

    public int checkBalance(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return isValidHigh(node.leftChild) - isValidHigh(node.rightChild);
        }
    }

    int isValidHigh(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return node.high;
        }
    }

    public Node<T> rotation(Node<T> node, boolean isRight) {
        Node<T> child;
        if (isRight) {
            child = node.leftChild;
            node.leftChild = child.rightChild;
            child.rightChild = node;
        } else {
            child = node.rightChild;
            node.rightChild = child.leftChild;
            child.leftChild = node;
        }

        node.high = 1 +  Math.max(isValidHigh(node.leftChild), isValidHigh(node.rightChild));
        child.high = 1 + Math.max(isValidHigh(child.leftChild), isValidHigh(child.rightChild));

        amountOfRotations++;
        return child;
    }

    public void DELETE(T key) {
        root = deleteNode(root, key);
    }

    public Node<T> deleteNode(Node<T> node, T key) {
        if (node == null) {
            System.out.println("KEY NOT FOUND");
            return null;
        }
        int comparing = key.compareTo(node.key);
        if (comparing < 0) {
            node.leftChild = deleteNode(node.leftChild, key);
        } else if (comparing > 0) {
            node.rightChild = deleteNode(node.rightChild, key);
        } else {
            if (node.leftChild == null && node.rightChild == null) {
                node = null;
            } else if (node.leftChild == null) {
                node = node.rightChild;
            } else if (node.rightChild == null) {
                node = node.leftChild;
            } else {
                Node<T> changing = minimumSearch(node.rightChild);
                node.key = changing.key;
                node.value = changing.value;
                node.rightChild = deleteNode(node.rightChild, changing.key);
            }
        }
        if (node == null) {
            return node;
        }
        node.high = 1 + Math.max(isValidHigh(node.leftChild), isValidHigh(node.rightChild));
        return balanceTheTree(node);
    }

    public Node<T> minimumSearch(Node<T> node) {
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    public void PRINTROTATIONS() {
        System.out.println(amountOfRotations);
    }

    public void LOOKUP(T key) {
        Node<T> node = lookUpRec(root, key);
        if (node == null) {
            System.out.println("KEY NOT FOUND");
        } else {
            System.out.println(node.value);
        }
    }

    public Node<T> lookUpRec(Node<T> node, T key) {
        if (node == null) {
            return node;
        }
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return node;
        } else if (compare < 0) {
            return lookUpRec(node.leftChild, key);
        } else {
            return lookUpRec(node.rightChild, key);
        }
    }
}
