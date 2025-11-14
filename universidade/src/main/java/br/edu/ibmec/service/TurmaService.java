package br.edu.ibmec.service;

import br.edu.ibmec.dao.DisciplinaRepository;
import br.edu.ibmec.dao.ProfessorRepository;
import br.edu.ibmec.dao.TurmaRepository;
import br.edu.ibmec.dto.TurmaDTO;
import br.edu.ibmec.entity.Disciplina;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.entity.Turma;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional; // Verifique se esta importação existe

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;


    @Transactional(readOnly = true)
    public TurmaDTO buscarTurma(String codigo, int ano, int semestre) throws DaoException {
        try {
            Turma turma = turmaRepository.findByCodigoAndAnoAndSemestre(codigo, ano, semestre)
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            // Constrói o DTO (baseado no construtor do seu TurmaDTO)
            TurmaDTO turmaDTO = new TurmaDTO(
                    turma.getCodigo(),
                    turma.getAno(),
                    turma.getSemestre(),
                    turma.getDisciplina().getCodigo()// Assumindo que Disciplina tem .getCodigo()
                 //   turma.getProfessor()
            );
            return turmaDTO;
        } catch (Exception e) {
            throw new DaoException("Erro ao buscar turma: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Collection<Turma> listarTurmas() throws DaoException {
        return turmaRepository.findAll();
    }


    @Transactional
    public void cadastrarTurma(TurmaDTO turmaDTO) throws ServiceException, DaoException {
        // 1. Validação (usando as mesmas exceções do AlunoService para compilar)
        if (turmaDTO.getCodigo() == null || turmaDTO.getCodigo().trim().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }
        if ((turmaDTO.getAno() < 1900) || (turmaDTO.getAno() > 2025)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        /*
        if (turmaDTO.getProfessor() == null) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        */


        try {
            Disciplina disciplina = disciplinaRepository.findById(turmaDTO.getDisciplina())
                    .orElseThrow(() -> new DaoException("Disciplina com ID " + turmaDTO.getDisciplina() + " não encontrada"));
/*
            Professor professor = professorRepository.findById(turmaDTO.getProfessor().getCpf())
                    .orElseThrow(() -> new DaoException("Professor com ID " + turmaDTO.getProfessor().getCpf() + " não encontrado"));
*/
            Turma turma = new Turma();
            turma.setCodigo(turmaDTO.getCodigo());
            turma.setAno(turmaDTO.getAno());
            turma.setSemestre(turmaDTO.getSemestre());
            turma.setDisciplina(disciplina);
          //  turma.setProfessor(professor);

            turmaRepository.save(turma);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }
    
    @Transactional
    public void alterarTurma(TurmaDTO turmaDTO) throws ServiceException, DaoException {
        if (turmaDTO.getCodigo() == null || turmaDTO.getCodigo().trim().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }
        if ((turmaDTO.getAno() < 1900) || (turmaDTO.getAno() > 2025)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        /*
        if (turmaDTO.getProfessor() == null) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        */


        try {
            if (!turmaRepository.findByCodigoAndAnoAndSemestre(turmaDTO.getCodigo(), turmaDTO.getAno(), turmaDTO.getSemestre()).isPresent()) {
                throw new DaoException("Turma não encontrada para alteração");
            }

            Disciplina disciplina = disciplinaRepository.findById(turmaDTO.getDisciplina())
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));

            //Professor professor = professorRepository.findById(turmaDTO.getProfessor().getCpf())
            //        .orElseThrow(() -> new DaoException("Professor não encontrado"));


            Turma turma = new Turma();
            turma.setCodigo(turmaDTO.getCodigo());
            turma.setAno(turmaDTO.getAno());
            turma.setSemestre(turmaDTO.getSemestre());
            turma.setDisciplina(disciplina);
        //    turma.setProfessor(professor);

            turmaRepository.save(turma);

        } catch (DaoException e) {
            throw e; // Repassa a exceção
        } catch (Exception e) {
            throw new DaoException("Erro ao alterar turma: " + e.getMessage());
        }
    }


    @Transactional
    public void removerTurma(String codigo, int ano, int semestre) throws DaoException {
        try {
            Turma turma = turmaRepository.findByCodigoAndAnoAndSemestre(codigo, ano, semestre)
                    .orElseThrow(() -> new DaoException("Turma não encontrada para remoção"));

            turmaRepository.delete(turma);

        } catch (Exception e) {
            throw new DaoException("Erro ao remover turma: " + e.getMessage());
        }
    }
}