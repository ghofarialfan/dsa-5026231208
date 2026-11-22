package lw06.prelab;

import java.util.Scanner;

public class Main {
    public static Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }

        if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        }
        return root;
    }

    public static boolean search(Node root, int target) {
        if (root == null) {
            return false;
        }

        if (target == root.data) {
            return true;
        } else if (target < root.data) {
            return search(root.left, target);
        } else {
            return search(root.right, target);
        }
    }

    public static void preorder(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }
    }

    public static void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    public static void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }

    public static void main(String[] args) {
        Scanner pembaca = new Scanner(Main.class.getResourceAsStream("treenumbers.txt"));
        Node akar = null;
        String[] daftarNilai = pembaca.nextLine().trim().split("\\s+");
        for (String nilai : daftarNilai) {
            akar = insert(akar, Integer.parseInt(nilai));
        }
        int nilaiDicari = pembaca.nextInt();

        System.out.print("Preorder : ");
        preorder(akar);
        System.out.println();
        System.out.print("Inorder   : ");
        inorder(akar);
        System.out.println();
        System.out.print("Postorder : ");
        postorder(akar);
        System.out.println();
        System.out.println("Search " + nilaiDicari + " : " + search(akar, nilaiDicari));

        pembaca.close();
    }
}
