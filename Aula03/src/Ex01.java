import java.util.ArrayList;
import java.util.Scanner;

public class Ex01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o 1º valor: ");
        int v1 = sc.nextInt();

        System.out.println("Digite o 2º valor: ");
        int v2 = sc.nextInt();

        System.out.println("Digite o 3º valor: ");
        int v3 = sc.nextInt();

        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(v1);
        arr.add(v2);
        arr.add(v3);

        int menor = 0, medio = 0, maior = 0;

        for (int i = 0; i < 3; i++) {
            int value = arr.get(i);

            if (i == 0) {
                menor = value;
                maior = value;
                medio = value;
                continue;
            }

            if (value > maior) {
                maior = value;
            }
            else if (value < menor) {
                menor = value;
            }
            else {
                medio = value;
            }
        }

        System.out.println("Menor valor: " + menor);
        System.out.println("Valor do meio: " + medio);
        System.out.println("Maior valor: " + maior);
    }
}
