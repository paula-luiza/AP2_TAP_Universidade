package br.edu.ibmec.factory;

import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.entity.Situacao;
import br.edu.ibmec.entity.Turma;
import org.springframework.stereotype.Component;

@Component
public class InscricaoFactory {

    /**
     * Método responsável por fabricar uma inscrição "zerada" pronta para começar.
     */
    public Inscricao criarInscricaoInicial(Aluno aluno, Turma turma) {
        Inscricao inscricao = new Inscricao();

        inscricao.setAluno(aluno);
        inscricao.setTurma(turma);

        inscricao.setAvaliacao1(0.0f);
        inscricao.setAvaliacao2(0.0f);
        inscricao.setMedia(0.0f);
        inscricao.setNumFaltas(0);
        inscricao.setSituacao(null);

        return inscricao;
    }
}