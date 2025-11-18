package br.edu.ibmec.service;

import br.edu.ibmec.dao.AlunoRepository;
import br.edu.ibmec.dao.InscricaoRepository;
import br.edu.ibmec.dto.MensalidadeDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.strategy.Desconto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MensalidadeService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private Map<String, Desconto> calculoStrategies;

    public MensalidadeDTO calcularMensalidade(int matricula) throws Exception {

        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new Exception("Aluno não encontrado"));


        Double valorMateria = aluno.getCurso().getValorMateriaBase();
        if (valorMateria == null) valorMateria = 0.0; // Proteção contra null


        long qtdMaterias = inscricaoRepository.countByAlunoMatricula(matricula);


        Double valorBruto = valorMateria * qtdMaterias;


        String tipoAluno = aluno.getTipoAluno().toString();

        Desconto strategy = calculoStrategies.get(tipoAluno);
        if (strategy == null) {
            strategy = calculoStrategies.get("REGULAR");
        }

        Double valorFinal = strategy.aplicar(valorBruto);


        return new MensalidadeDTO(
                aluno.getNome(),
                aluno.getCurso().getNome(),
                qtdMaterias,
                valorMateria,
                valorBruto,
                tipoAluno,
                valorFinal
        );
    }
}