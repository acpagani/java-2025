import java.util.Scanner;

public class Ex02 {
    public static void main(String[] args) {
        
        float x1, x2, x3;
        float y1, y2, y3;

        Scanner sc = new Scanner(System.in);

        System.out.print("Número 1: ");
        x1 = sc.nextFloat();

        System.out.print("Número 2: ");
        x2 = sc.nextFloat();

        System.out.print("Número 3: ");
        x3 = sc.nextFloat();

        System.out.print("Número 4: ");
        y1 = sc.nextFloat();

        System.out.print("Número 5: ");
        y2 = sc.nextFloat();

        System.out.print("Número 6: ");
        y3 = sc.nextFloat();

        float sumX = x1 + x2 + x3;
        float sumY = y1 + y2 + y3;

        float avgX = sumX / 3;
        float avgY = sumY / 3;

        System.out.println("Média dos 3 primeiros: " + avgX);
        System.out.println("Média dos 3 últimos: " + avgY);

        System.out.println("Média das médias: " + (avgX + avgY) / 2);


    }
}
