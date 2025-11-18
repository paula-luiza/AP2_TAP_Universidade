package br.edu.ibmec.service;

import java.util.Collection;

import br.edu.ibmec.dao.CursoRepository;
import br.edu.ibmec.dto.CursoDTO;
import br.edu.ibmec.entity.Curso;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoDTO buscarCurso(int codigo) throws DaoException {
        try{
            Curso curso = cursoRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Curso não encontrado"));

            CursoDTO cursoDTO = new CursoDTO(curso.getNome());
            return cursoDTO;
        }
        catch(DaoException e)
        {
            throw new DaoException("Erro ao buscar curso");
        }
    }

    public Collection<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @Transactional
    public void cadastrarCurso(CursoDTO cursoDTO) throws ServiceException, DaoException {

        if ((cursoDTO.getNome() == null) || (cursoDTO.getNome().length() < 1) || (cursoDTO.getNome().length() > 20)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        Curso curso = new Curso();
        curso.setNome(cursoDTO.getNome());

        curso.setValorMateriaBase(cursoDTO.getValorMateriaBase() != null ? cursoDTO.getValorMateriaBase() : 0.0);

        try {
            cursoRepository.save(curso);
            cursoDTO.setCodigo(curso.getCodigo());
        } catch (Exception e) {
            throw new DaoException("Erro ao salvar curso: " + e.getMessage());
        }
    }

    @Transactional
    public void alterarCurso(CursoDTO cursoDTO) throws ServiceException,
            DaoException {
        if ((cursoDTO.getCodigo() < 1) || (cursoDTO.getCodigo() > 99)) {
            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((cursoDTO.getNome().length() < 1)
                || (cursoDTO.getNome().length() > 20)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        Curso curso = new Curso(cursoDTO.getCodigo(), cursoDTO.getNome());
        curso.setNome(cursoDTO.getNome());

        if (cursoDTO.getValorMateriaBase() != null) {
            curso.setValorMateriaBase(cursoDTO.getValorMateriaBase());
        }

        try {
            if (!cursoRepository.existsById(curso.getCodigo())) {
                throw new DaoException("Curso não encontrado");
            }
            cursoRepository.save(curso);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void removerCurso(int codigo) throws DaoException {
        try {
            Curso curso = cursoRepository.findById(codigo)
                    .orElseThrow(() -> new DaoException("Curso não encontrado"));

            if (!curso.getAlunos().isEmpty()) {
                throw new DaoException("Não é possível remover curso com alunos");
            }

            cursoRepository.deleteById(codigo);
        }
        catch(DaoException e)
        {
            throw new DaoException("Erro ao remover curso");
        }
    }
}