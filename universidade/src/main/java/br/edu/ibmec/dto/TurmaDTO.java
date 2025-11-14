package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Professor;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="turma")
public class TurmaDTO {
    private String codigo;
    private int ano;
    private int semestre;
    private int disciplina;
    private List<InscricaoDTO> inscricoes = new ArrayList<InscricaoDTO>();
    private Professor professor;

    public TurmaDTO(String codigo, int ano, int semestre, int disciplina, Professor professor) {
        this.codigo = codigo;
        this.ano = ano;
        this.semestre = semestre;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "TurmaDTO [ano=" + ano + ", codigo=" + codigo + ", disciplina="
                + disciplina + ", semestre=" + semestre + "]";
    }

}