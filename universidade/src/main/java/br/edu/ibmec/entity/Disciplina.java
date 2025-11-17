package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Turma> turmas = new ArrayList<Turma>();
    //private List<AlunoMonitor> monitores = new ArrayList<AlunoMonitor>();


    public Disciplina(int codigo, String nome, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.curso = curso;
    }

    public void addTurma(Turma turma) {
        turmas.add(turma);
    }

    public void removeTurma(Turma turma) {
        turmas.remove(turma);
    }

}