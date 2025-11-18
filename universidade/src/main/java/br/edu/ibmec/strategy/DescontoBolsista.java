package br.edu.ibmec.strategy;

import org.springframework.stereotype.Component;

@Component("BOLSISTA")
public class DescontoBolsista implements Desconto {
    @Override
    public double aplicar(double valor) {
        return valor * 0.3;
    }
}
