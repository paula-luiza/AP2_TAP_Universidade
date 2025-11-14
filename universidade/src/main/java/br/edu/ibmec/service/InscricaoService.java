package br.edu.ibmec.service;

import java.util.Collection;
import java.util.Optional;

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

            // Assumindo que o DTO tem este construtor (baseado no seu código antigo)
            InscricaoDTO inscricaoDTO = new InscricaoDTO(
                    inscricao.getAvaliacao1(),
                    inscricao.getAvaliacao2(),
                    inscricao.getNumFaltas(),
                    inscricao.getSituacao(),
                    inscricao.getAluno().getMatricula(),
                    inscricao.getTurma().getCodigo(),
                    inscricao.getTurma().getAno(),
                    inscricao.getTurma().getSemestre()
            );
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

        if ((Integer.parseInt(inscricaoDTO.getCodigo()) < 1) || (Integer.parseInt(inscricaoDTO.getCodigo()) > 999)) {            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((inscricaoDTO.getAno() < 1900) || (inscricaoDTO.getAno() > 2025)) { // Ano atualizado
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            Aluno aluno = alunoRepository.findById(inscricaoDTO.getAluno())
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            Turma turma = turmaRepository.findByCodigoAndAnoAndSemestre(inscricaoDTO.getCodigo(), inscricaoDTO.getAno(), inscricaoDTO.getSemestre())
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            // Cria a nova entidade Inscricao (usando o construtor da sua Entidade)
            Inscricao inscricao = new Inscricao(
                    inscricaoDTO.getAvaliacao1(),
                    inscricaoDTO.getAvaliacao2(),
                    inscricaoDTO.getNumFaltas(),
                    inscricaoDTO.getSituacao(),
                    aluno,
                    turma
            );

            inscricaoRepository.save(inscricao);

        } catch (Exception e) {
            throw new DaoException("erro do dao no service throw: " + e.getMessage());
        }
    }

    @Transactional
    public void alterarInscricao(InscricaoDTO inscricaoDTO)
            throws ServiceException, DaoException {

        // Validação (mesma do cadastro)
        if ((Integer.parseInt(inscricaoDTO.getCodigo()) < 1) || (Integer.parseInt(inscricaoDTO.getCodigo()) > 999)) {            throw new ServiceException(
                    ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if ((inscricaoDTO.getAno() < 1900) || (inscricaoDTO.getAno() > 2025)) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }

        try {
            Aluno aluno = alunoRepository.findById(inscricaoDTO.getAluno())
                    .orElseThrow(() -> new DaoException("Aluno não encontrado"));

            Turma turma = turmaRepository.findByCodigoAndAnoAndSemestre(inscricaoDTO.getCodigo(), inscricaoDTO.getAno(), inscricaoDTO.getSemestre())
                    .orElseThrow(() -> new DaoException("Turma não encontrada"));

            // Para alterar, primeiro buscamos a inscrição original para pegar o ID
            Inscricao inscricaoOriginal = inscricaoRepository.findByAlunoAndTurma(aluno, turma)
                    .orElseThrow(() -> new DaoException("Inscrição não encontrada para alteração"));

            Inscricao inscricaoAtualizada = new Inscricao(
                    inscricaoDTO.getAvaliacao1(),
                    inscricaoDTO.getAvaliacao2(),
                    inscricaoDTO.getNumFaltas(),
                    inscricaoDTO.getSituacao(),
                    aluno,
                    turma
            );

            // Esta é a parte crucial: setamos o ID original para o save() saber que é um UPDATE
            inscricaoAtualizada.setId(inscricaoOriginal.getId());

            inscricaoRepository.save(inscricaoAtualizada);

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