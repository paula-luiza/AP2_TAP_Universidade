package br.edu.ibmec.strategy;

import org.springframework.stereotype.Component;

@Component("REGULAR")
public class DescontoRegular implements Desconto{
    @Override
    public double aplicar(double valor) {
        return valor;
    }
}
