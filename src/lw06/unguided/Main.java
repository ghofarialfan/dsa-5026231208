import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner pembaca = new Scanner(Main.class.getResourceAsStream("treenumbers.txt"));
        BinarySearchTree pohon = new BinarySearchTree();

        String[] daftarNomor = pembaca.nextLine().trim().split("\\s+");
        for (String nomor : daftarNomor) {
            pohon.insert(Integer.parseInt(nomor));
        }
        int nomorDicari = pembaca.nextInt();

        System.out.print("Preorder : ");
        pohon.preOrder();
        System.out.print("Inorder : ");
        pohon.inOrder();
        System.out.print("Postorder : ");
        pohon.postOrder();
        System.out.println("Search " + nomorDicari + " : " + pohon.search(nomorDicari));
        System.out.println("Minimum : " + pohon.findMin());
        System.out.println("Maximum : " + pohon.findMax());

        pembaca.close();
    }
}
