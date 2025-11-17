package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Data;
import br.edu.ibmec.entity.EstadoCivil;
import br.edu.ibmec.entity.Turma;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "professor")
public class ProfessorDTO {
    private String cpf;
    private String nome;
    private Data dataNascimento;
    private EstadoCivil estadoCivil;

    public ProfessorDTO(String cpf, String nome, Data dataNascimento, EstadoCivil estadoCivil) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.estadoCivil = estadoCivil;
    }
}
