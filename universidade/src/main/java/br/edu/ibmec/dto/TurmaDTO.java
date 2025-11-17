package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Professor;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name="turma")
public class TurmaDTO {
    private String codigo;
    private int ano;
    private int semestre;
    private int disciplina;
    private String cpfProfessor;

    @Override
    public String toString() {
        return "TurmaDTO [ano=" + ano + ", codigo=" + codigo + ", disciplina="
                + disciplina + ", semestre=" + semestre + "]";
    }

}