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
    private int idTurma;

    public InscricaoDTO(float avaliacao1, float avaliacao2, int numFaltas, Situacao situacao, int aluno, int idTurma) {
        this.avaliacao1 = avaliacao1;
        this.avaliacao2 = avaliacao2;
        this.numFaltas = numFaltas;
        this.situacao = situacao;
        this.aluno = aluno;
        this.idTurma = idTurma;
    }

}