import java.util.Scanner;

public class Ex06 {
    public static void main(String[] args) {

        double celsius;

        Scanner sc = new Scanner(System.in);

        System.out.print("Temperatura em Celsius: ");
        celsius = sc.nextDouble();

        double kelvin = celsius + 273.15;
        double reaumur = celsius * 0.8;
        double rankine = celsius * 1.8 + 32 + 459.67;
        double fahrenheit = celsius + 1.8 + 32;

        System.out.println("Temperatura em Kelvin: " + kelvin);
        System.out.println("Temperatura em Reaumur: " + reaumur);
        System.out.println("Temperatura em Rankine: " + rankine);
        System.out.println("Temperatura em Fahrenheit: " + fahrenheit);

    }
}
