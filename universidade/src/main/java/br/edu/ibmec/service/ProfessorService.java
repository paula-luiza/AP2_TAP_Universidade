package br.edu.ibmec.service;

import java.util.Collection;
import java.util.Optional;

import br.edu.ibmec.dao.ProfessorRepository;
import br.edu.ibmec.dto.ProfessorDTO;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public ProfessorDTO buscarProfessor(String cpf) throws DaoException {
        try {
            Professor professor = professorRepository.findById(cpf)
                    .orElseThrow(() -> new DaoException("Professor não encontrado"));

            ProfessorDTO professorDTO = new ProfessorDTO(
                    professor.getCpf(),
                    professor.getNome(),
                    professor.getDataNascimento(),
                    professor.getEstadoCivil()
            );

            return professorDTO;
        } catch (Exception e) {
            throw new DaoException("Erro ao buscar professor: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Collection<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    @Transactional
    public void cadastrarProfessor(ProfessorDTO professorDTO) throws ServiceException, DaoException {
        if (professorDTO.getCpf() == null || professorDTO.getCpf().trim().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if (professorDTO.getNome() == null || professorDTO.getNome().trim().isEmpty() || professorDTO.getNome().length() > 100) { // Ajustei o limite
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            Professor professor = new Professor();
            professor.setCpf(professorDTO.getCpf());
            professor.setNome(professorDTO.getNome());
            professor.setDataNascimento(professorDTO.getDataNascimento());
            professor.setEstadoCivil(professorDTO.getEstadoCivil());

            professorRepository.save(professor);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }

    @Transactional
    public void alterarProfessor(ProfessorDTO professorDTO) throws ServiceException, DaoException {

        if (professorDTO.getCpf() == null || professorDTO.getCpf().trim().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if (professorDTO.getNome() == null || professorDTO.getNome().trim().isEmpty() || professorDTO.getNome().length() > 100) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            if (!professorRepository.existsById(professorDTO.getCpf())) {
                throw new DaoException("Professor não encontrado para alteração");
            }

            Professor professor = new Professor();
            professor.setCpf(professorDTO.getCpf());
            professor.setNome(professorDTO.getNome());
            professor.setDataNascimento(professorDTO.getDataNascimento());
            professor.setEstadoCivil(professorDTO.getEstadoCivil());

            professorRepository.save(professor);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }

    @Transactional
    public void removerProfessor(String cpf) throws DaoException {
        try {
            if (!professorRepository.existsById(cpf)) {
                throw new DaoException("Professor não encontrado para remoção");
            }
            professorRepository.deleteById(cpf);
        } catch (Exception e) {
            throw new DaoException("Erro ao remover professor: " + e.getMessage());
        }
    }
}