package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Situacao;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="inscricao")
public class InscricaoDTO {
    private float avaliacao1;
    private float avaliacao2;
    private float media;
    private int numFaltas;
    private Situacao situacao;

    private int aluno;
    private String codigo;
    private int ano;
    private int semestre;


    public InscricaoDTO(float avaliacao1, float avaliacao2, int numFaltas, Situacao situacao, int aluno, String codigoTurma,
                        int ano, int semestre) {
        this.avaliacao1 = avaliacao1;
        this.avaliacao2 = avaliacao2;
        this.numFaltas = numFaltas;
        this.situacao = situacao;
        this.aluno = aluno;
        this.codigo = codigoTurma;
        this.ano = ano;
        this.semestre = semestre;
    }

}