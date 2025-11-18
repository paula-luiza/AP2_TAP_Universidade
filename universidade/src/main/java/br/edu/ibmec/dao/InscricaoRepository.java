package br.edu.ibmec.dao;

import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Adicione este import


public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    Optional<Inscricao> findByAlunoAndTurma(Aluno aluno, Turma turma);
    long countByAlunoMatricula(int matricula);
}