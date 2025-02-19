import java.util.Scanner;

public class Ex01 {
    public static void main(String[] args) {

        int anos, meses, dias;
        Scanner sc = new Scanner(System.in);
        System.out.print("Anos: ");
        anos = sc.nextInt();
        System.out.print("Meses: ");
        meses = sc.nextInt();
        System.out.print("Dias: ");
        dias = sc.nextInt();

        int total = anos * 365 + meses * 30 + dias;

        System.out.println("Você tem " + total + " dias de vida");
    }
}
