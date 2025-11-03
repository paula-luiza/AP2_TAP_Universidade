package strategy;

public class Dividir  implements IStrategy {
    public double executar(double a, double b){
        try{
            return a / b;
        }
        catch(Exception e){
            throw e;
        }
    }
}
