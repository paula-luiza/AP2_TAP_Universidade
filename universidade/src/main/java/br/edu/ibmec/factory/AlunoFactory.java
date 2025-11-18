package br.edu.ibmec.factory;

import br.edu.ibmec.dto.AlunoDTO;
import br.edu.ibmec.entity.Aluno;

public interface AlunoFactory {
    Aluno criarAluno(AlunoDTO dto);
}