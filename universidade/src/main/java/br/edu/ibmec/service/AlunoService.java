package br.edu.ibmec.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import br.edu.ibmec.dao.AlunoRepository;
import br.edu.ibmec.dao.CursoRepository;
import br.edu.ibmec.dto.AlunoDTO;
import br.edu.ibmec.entity.*;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.factory.AlunoFactory;
import br.edu.ibmec.strategy.Desconto;
import br.edu.ibmec.strategy.DescontoRegular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private Map<String, AlunoFactory> fabricasDeAluno;

    public static final Data getData(String dataString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate localDate = LocalDate.parse(dataString, formatter);

            Data dataRetorno = new Data();
            dataRetorno.setAno(localDate.getYear());           // Retorna o ano completo (ex: 2001)
            dataRetorno.setMes(localDate.getMonthValue());     // Retorna o mês correto (ex: 10 para Outubro)
            dataRetorno.setDia(localDate.getDayOfMonth());     // Retorna o dia do mês (ex: 1)

            return dataRetorno;

        } catch (Exception e) {
            System.out.println("Erro na conversão da data: " + e.getMessage());
            return null;
        }
    }

    public AlunoDTO buscarAluno(int matricula) throws DaoException {
        try {
            Aluno aluno = alunoRepository.findById(matricula)
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            AlunoDTO alunoDTO = new AlunoDTO(
                    aluno.getMatricula(),
                    aluno.getNome(),
                    aluno.getDataNascimento().toString(),
                    aluno.isMatriculaAtiva(),
                    null,
                    aluno.getCurso().getCodigo(),
                    aluno.getTelefones(),
                    aluno.getTipoAluno());
            return alunoDTO;
        } catch (Exception e) {
            throw new DaoException("Erro ao buscar aluno");
        }
    }

    public Collection<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }


    @Transactional
    public void cadastrarAluno(AlunoDTO alunoDTO) throws ServiceException,
            DaoException {
        if (alunoRepository.existsById(alunoDTO.getMatricula())) {
            throw new ServiceException("Já existe um aluno com a matrícula " + alunoDTO.getMatricula());
        }

        if ((alunoDTO.getNome().length() < 1)
                || (alunoDTO.getNome().length() > 20)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        if (alunoDTO.getMatricula() < 1) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }

        try {
            Curso curso = cursoRepository.findById(alunoDTO.getCurso())
                    .orElseThrow(() -> new DaoException("Curso não encontrado"));

            String tipo = alunoDTO.getTipoAluno() != null ? alunoDTO.getTipoAluno().toString().toUpperCase() : "REGULAR";

            AlunoFactory factory = fabricasDeAluno.get("FACTORY_" + tipo);

            if (factory == null) {
                factory = fabricasDeAluno.get("FACTORY_REGULAR");
            }

            Aluno novoAluno = factory.criarAluno(alunoDTO);
            novoAluno.setCurso(curso);
            alunoRepository.save(novoAluno);

            curso.getAlunos().add(novoAluno);
            cursoRepository.save(curso);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void alterarAluno(AlunoDTO alunoDTO) throws ServiceException,
            DaoException {
        if (alunoDTO.getMatricula() < 1) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((alunoDTO.getNome() == null) || (alunoDTO.getNome().length() > 100)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            if (!alunoRepository.existsById(alunoDTO.getMatricula())) {
                throw new DaoException("Aluno não encontrado");
            }

            Curso curso = cursoRepository.findById(alunoDTO.getCurso())
                    .orElseThrow(() -> new DaoException("Curso não encontrado"));

            Aluno aluno = new Aluno(
                    alunoDTO.getMatricula(),
                    alunoDTO.getNome(),
                    getData(alunoDTO.getDtNascimento()),
                    alunoDTO.isMatriculaAtiva(),
                    EstadoCivil.solteiro,
                    curso,
                    alunoDTO.getTelefones(),
                    alunoDTO.getTipoAluno());

            alunoRepository.save(aluno);
        } catch (DaoException e) {
            throw new DaoException("erro do dao no service throw");
        }
    }

    @Transactional
    public void removerAluno(int matricula) throws DaoException {
        try {
            if (!alunoRepository.existsById(matricula)) {
                throw new DaoException("Aluno não encontrado");
            }
            alunoRepository.deleteById(matricula);
        } catch (Exception e) {
            throw new DaoException("Erro ao remover aluno");
        }
    }

}