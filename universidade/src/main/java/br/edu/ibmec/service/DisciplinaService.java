package br.edu.ibmec.service;

import java.util.Collection;
import java.util.Optional; // Importação necessária

import br.edu.ibmec.dao.CursoRepository; // Importação necessária
import br.edu.ibmec.dao.DisciplinaRepository; // Importação necessária
import br.edu.ibmec.dto.DisciplinaDTO; // Importação necessária (se você tiver)
import br.edu.ibmec.entity.Curso; // Importação necessária
import br.edu.ibmec.entity.Disciplina; // Importação necessária
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public DisciplinaDTO buscarDisciplina(int codigo) throws DaoException {
        try {
            Disciplina disciplina = disciplinaRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Disciplina não encontrada"));

            DisciplinaDTO disciplinaDTO = new DisciplinaDTO(
                    disciplina.getNome(),
                    disciplina.getCurso().getCodigo()
            );
            return disciplinaDTO;
        } catch (Exception e) {
            throw new DaoException("Erro ao buscar disciplina: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Collection<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    @Transactional
    public void cadastrarDisciplina(DisciplinaDTO disciplinaDTO)
            throws ServiceException, DaoException {

        if ((disciplinaDTO.getCodigo() < 1) || (disciplinaDTO.getCodigo() > 99)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((disciplinaDTO.getNome().length() < 1)
                || (disciplinaDTO.getNome().length() > 20)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            Curso curso = cursoRepository.findById(disciplinaDTO.getCurso())
                    .orElseThrow(() -> new DaoException("Curso não encontrado para associar à disciplina"));

            Disciplina disciplina = new Disciplina(
                    disciplinaDTO.getCodigo(),
                    disciplinaDTO.getNome(),
                    curso
            );

            disciplinaRepository.save(disciplina);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }

    @Transactional
    public void alterarDisciplina(DisciplinaDTO disciplinaDTO)
            throws ServiceException, DaoException {

        if ((disciplinaDTO.getCodigo() < 1) || (disciplinaDTO.getCodigo() > 99)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((disciplinaDTO.getNome().length() < 1)
                || (disciplinaDTO.getNome().length() > 20)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            if (!disciplinaRepository.existsById(disciplinaDTO.getCodigo())) {
                throw new DaoException("Disciplina não encontrada para alteração");
            }

            Curso curso = cursoRepository.findById(disciplinaDTO.getCurso())
                    .orElseThrow(() -> new DaoException("Curso não encontrado"));

            Disciplina disciplina = new Disciplina(
                    disciplinaDTO.getCodigo(),
                    disciplinaDTO.getNome(),
                    curso
            );

            disciplinaRepository.save(disciplina);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }

    @Transactional
    public void removerDisciplina(int codigo) throws DaoException {
        try {
            if (!disciplinaRepository.existsById(codigo)) {
                throw new DaoException("Disciplina não encontrada para remoção");
            }
            disciplinaRepository.deleteById(codigo);
        } catch (Exception e) {
            throw new DaoException("Erro ao remover disciplina: " + e.getMessage());
        }
    }
}