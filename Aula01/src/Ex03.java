import java.util.Scanner;

public class Ex03 {
    public static void main(String[] args) {
        float saldo;

        Scanner sc = new Scanner(System.in);

        System.out.print("Saldo atual: ");
        saldo = sc.nextFloat();

        System.out.println("Saldo com reajuste: " + saldo * 1.01);
    }
}
