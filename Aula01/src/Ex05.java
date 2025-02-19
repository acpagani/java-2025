import java.util.Scanner;

public class Ex05 {
    public static void main(String[] args) {

        float minSalary, userSalary;

        Scanner sc = new Scanner(System.in);

        System.out.print("Salário mínimo: ");
        minSalary = sc.nextFloat();

        System.out.print("Seu salário: ");
        userSalary = sc.nextFloat();

        System.out.println("Você ganha um total de " + (userSalary / minSalary) + " salários mínimos");

    }
}
