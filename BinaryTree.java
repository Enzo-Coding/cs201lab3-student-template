import java.util.*;

public class BinaryTree {
    private Node root;
    public int size;

    public BinaryTree(int data) {
        root = new Node(data);
        size = 0;
    }

    public Node getRoot() {
        return root;
    } 

    public void add(int data, Node target) {
        if (data < target.getData()) {
            if (target.getLeft() == null) {
                target.setLeft(new Node(data));
                size++;
            } else {
                add(data, target.getLeft());
            }
        } else if (data > target.getData()) {
            if (target.getRight() == null) {
                target.setRight(new Node(data));
                size++;
            } else {
                add(data, target.getRight());
            }
        }
    }

    public Integer[] postorder() {
        List<Integer> ls = new ArrayList<>();

        addToPostorder(ls, root);
        return ls.toArray(new Integer[size]);
    }

    public void addToPostorder(List<Integer> ls, Node node) {
        if (node == null) {
            return;
        }

        addToPostorder(ls, node.getLeft());
        addToPostorder(ls, node.getRight());
  
        ls.add(node.getData());
    }
}