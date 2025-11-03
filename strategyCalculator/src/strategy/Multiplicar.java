package strategy;

public class Multiplicar  implements IStrategy {
    public double executar(double a, double b){
        return a * b;
    }
}
