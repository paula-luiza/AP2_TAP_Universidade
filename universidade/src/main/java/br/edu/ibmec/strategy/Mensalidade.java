package br.edu.ibmec.strategy;

import br.edu.ibmec.entity.Aluno;

public class Mensalidade {
    double calcular(Aluno aluno, Desconto desconto){
        double mensalidadeBase = aluno.getCurso().getValorMensalidadeBase();
        return desconto.aplicar(mensalidadeBase);
    }
}
