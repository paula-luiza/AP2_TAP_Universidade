import strategy.*;

import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Context context = new Context();

        System.out.println("Digite o primeiro numero:");
        int firstNumber = Integer.valueOf(scanner.nextLine());

        System.out.println("Digite o segundo numero:");
        int secondNumber = Integer.valueOf(scanner.nextLine());

        System.out.println("Digite a operação e pressione enter:(+, -, *, /)");
        String operation = String.valueOf(scanner.nextLine());

        Hashtable<String, IStrategy> operations= new Hashtable<>();
        operations.put("+", new Soma());
        operations.put("-", new Subtrair());
        operations.put("*", new Multiplicar());
        operations.put("/", new Dividir());

        IStrategy strategy = operations.get(operation);
        context.setStrategy(strategy);
        double result = context.executeStrategy(firstNumber, secondNumber);
        System.out.println(result);


        //System.out.println(firstNumber + " " + secondNumber);


    }
}
