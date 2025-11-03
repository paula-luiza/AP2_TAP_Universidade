import strategy.IStrategy;

public class Context {
    private IStrategy strategy;

     public void setStrategy(IStrategy strategy){
         this.strategy = strategy;
     }

     public double executeStrategy(double a, double b){
         return strategy.executar(a, b);
     }
}
