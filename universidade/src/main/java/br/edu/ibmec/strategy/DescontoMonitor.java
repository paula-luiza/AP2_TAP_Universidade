package br.edu.ibmec.strategy;

import org.springframework.stereotype.Component;

@Component("MONITOR")
public class DescontoMonitor implements Desconto {
    @Override
    public double aplicar(double valor) {
        return valor * 0.9;
    }
}
