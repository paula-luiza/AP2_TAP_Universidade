package br.edu.ibmec.service;

import java.util.Collection;

import br.edu.ibmec.dao.AlunoRepository;
import br.edu.ibmec.dao.InscricaoRepository;
import br.edu.ibmec.dao.TurmaRepository;
import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.entity.Turma;
import br.edu.ibmec.exception.DaoException;
import br.edu.ibmec.exception.ServiceException;
import br.edu.ibmec.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.factory.InscricaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public InscricaoDTO buscarInscricao(int matricula, String codigo, int ano, int semestre) throws DaoException {
        try {
            Turma turma = turmaRepository.findByCodigoAndAnoAndSemestre(codigo, ano, semestre)
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            Aluno aluno = alunoRepository.findById(matricula)
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            Inscricao inscricao = inscricaoRepository.findByAlunoAndTurma(aluno, turma)
                    .orElseThrow(() -> new DaoException("Inscrição não encontrada"));

            InscricaoDTO inscricaoDTO = new InscricaoDTO();
            return inscricaoDTO;
        } catch (Exception e) {
            throw new DaoException("Erro ao buscar inscrição: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Collection<Inscricao> listarInscricoes() throws DaoException {
        return inscricaoRepository.findAll();
    }

    @Transactional
    public void cadastrarInscricao(InscricaoDTO inscricaoDTO)
            throws ServiceException, DaoException {

        if ((inscricaoDTO.getIdTurma() < 1) || (inscricaoDTO.getIdTurma() > 999)) {            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }

        try {
            Aluno aluno = alunoRepository.findById(inscricaoDTO.getIdAluno())
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            Turma turma = turmaRepository.findById(inscricaoDTO.getIdTurma())
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            InscricaoFactory inscricaoFactory = new InscricaoFactory();
            Inscricao inscricao = inscricaoFactory.criarInscricaoInicial(aluno, turma);

            inscricaoRepository.save(inscricao);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }

    @Transactional
    public void alterarInscricao(InscricaoDTO inscricaoDTO)
            throws ServiceException, DaoException {


        if ((inscricaoDTO.getIdTurma() < 1) || (inscricaoDTO.getIdTurma() > 999)) {            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }

        try {
            Aluno aluno = alunoRepository.findById(inscricaoDTO.getIdAluno())
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            Turma turma = turmaRepository.findById((long) inscricaoDTO.getIdTurma())
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            Inscricao inscricaoOriginal = inscricaoRepository.findByAlunoAndTurma(aluno, turma)
                    .orElseThrow(() -> new DaoException("Inscrição não encontrada para alteração"));

            inscricaoOriginal.setAvaliacao1(inscricaoDTO.getAvaliacao1());
            inscricaoOriginal.setAvaliacao2(inscricaoDTO.getAvaliacao2());
            inscricaoOriginal.setNumFaltas(inscricaoDTO.getNumFaltas());

            inscricaoRepository.save(inscricaoOriginal);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }

    @Transactional
    public void removerInscricao(int matricula, String codigo, int ano, int semestre) throws DaoException {
        try {
            Aluno aluno = alunoRepository.findById(matricula)
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            Turma turma = turmaRepository.findByCodigoAndAnoAndSemestre(codigo, ano, semestre)
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            Inscricao inscricao = inscricaoRepository.findByAlunoAndTurma(aluno, turma)
                    .orElseThrow(() -> new DaoException("Inscrição não encontrada para remoção"));

            inscricaoRepository.delete(inscricao);

        } catch (Exception e) {
            throw new DaoException("Erro ao remover inscrição: " + e.getMessage());
        }
    }
}