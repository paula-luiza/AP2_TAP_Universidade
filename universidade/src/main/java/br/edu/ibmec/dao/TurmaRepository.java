package br.edu.ibmec.dao;

import br.edu.ibmec.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Adicione este import

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    Optional<Turma> findByCodigoAndAnoAndSemestre(String codigo, int ano, int semestre);
}