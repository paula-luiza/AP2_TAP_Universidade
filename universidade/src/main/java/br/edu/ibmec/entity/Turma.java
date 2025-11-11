package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "turmas")

public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String codigo;

    private int ano;
    private int semestre;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @OneToMany(
            mappedBy = "turma",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Inscricao> inscricoes = new ArrayList<Inscricao>();

    public Turma(int id, String codigo, int ano, int semestre, Disciplina disciplina) {
        this.id = id;
        this.codigo = codigo;
        this.ano = ano;
        this.semestre = semestre;
        this.disciplina = disciplina;
    }

    public void addInscricao(Inscricao inscricao) {
        this.inscricoes.add(inscricao);
        inscricao.setTurma(this);
    }

    public void removeInscricao(Inscricao inscricao) {
        this.inscricoes.remove(inscricao);
        inscricao.setTurma(null);
    }

}