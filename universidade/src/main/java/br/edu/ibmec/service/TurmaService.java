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
                    turma.getDisciplina().getCodigo(), // Assumindo que Disciplina tem .getCodigo()
                    turma.getProfessor()
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
        if (turmaDTO.getProfessor() == null) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }

        try {
            // 2. Busca as entidades relacionadas (como AlunoService busca o Curso)
            Disciplina disciplina = disciplinaRepository.findById(turmaDTO.getDisciplina())
                    .orElseThrow(() -> new DaoException("Disciplina com ID " + turmaDTO.getDisciplina() + " não encontrada"));

            Professor professor = professorRepository.findById(turmaDTO.getProfessor().getCpf())
                    .orElseThrow(() -> new DaoException("Professor com ID " + turmaDTO.getProfessor().getCpf() + " não encontrado"));

            // 3. Cria a nova entidade Turma (Assumindo que Turma tem setters)
            Turma turma = new Turma();
            turma.setCodigo(turmaDTO.getCodigo());
            turma.setAno(turmaDTO.getAno());
            turma.setSemestre(turmaDTO.getSemestre());
            turma.setDisciplina(disciplina);
            turma.setProfessor(professor);
            // NOTA: A lista de inscrições não é tratada aqui, assim como AlunoService não trata

            // 4. Salva a nova turma
            turmaRepository.save(turma);

            // NOTA: AlunoService salva o outro lado (curso.getAlunos().add(aluno)).
            // Aqui, a relação é gerenciada pela Inscrição, então não precisamos fazer isso.

        } catch (Exception e) {
            // Repassando a exceção como no AlunoService
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }
    
    @Transactional
    public void alterarTurma(TurmaDTO turmaDTO) throws ServiceException, DaoException {
        // 1. Validação (copiada do cadastrarTurma e AlunoService)
        if (turmaDTO.getCodigo() == null || turmaDTO.getCodigo().trim().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }
        if ((turmaDTO.getAno() < 1900) || (turmaDTO.getAno() > 2025)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if (turmaDTO.getProfessor() == null) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }

        try {
            // 2. Verifica se a turma existe (necessário para "alterar" ser lógico)
            // Esta é uma melhoria sobre o AlunoService, que não checa.
            if (!turmaRepository.findByCodigoAndAnoAndSemestre(turmaDTO.getCodigo(), turmaDTO.getAno(), turmaDTO.getSemestre()).isPresent()) {
                throw new DaoException("Turma não encontrada para alteração");
            }

            // 3. Busca as entidades relacionadas
            Disciplina disciplina = disciplinaRepository.findById(turmaDTO.getDisciplina())
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));

            Professor professor = professorRepository.findById(turmaDTO.getProfessor().getCpf())
                    .orElseThrow(() -> new DaoException("Professor não encontrado"));

            // 4. Cria o objeto Turma para salvar (padrão "upsert" do AlunoService)
            Turma turma = new Turma();
            turma.setCodigo(turmaDTO.getCodigo());
            turma.setAno(turmaDTO.getAno());
            turma.setSemestre(turmaDTO.getSemestre());
            turma.setDisciplina(disciplina);
            turma.setProfessor(professor);

            // 5. O save() do JPA vai ATUALIZAR a turma existente (pois a chave já existe)
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

            // 2. Agora podemos deletar a entidade que encontramos
            turmaRepository.delete(turma);

        } catch (Exception e) {
            throw new DaoException("Erro ao remover turma: " + e.getMessage());
        }
    }
}