package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "professores")

public class Professor {

    @Id
    private String cpf;

    private String nome;

    @Embedded
    private Data dataNascimento;
    
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @OneToMany(mappedBy = "professor")
    private List<Turma> turmas = new ArrayList<Turma>();

    public Professor(String cpf, String nome, Data dataNascimento, EstadoCivil estadoCivil) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.estadoCivil = estadoCivil;
    }
}
