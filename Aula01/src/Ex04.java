import java.util.Scanner;

public class Ex04 {
    public static void main(String[] args) {

        int num;

        Scanner sc = new Scanner(System.in);

        System.out.print("Número: ");
        num = sc.nextInt();

        System.out.println("Antecessor: " + (num - 1));
        System.out.println("Sucessor: " + (num + 1));

    }
}
