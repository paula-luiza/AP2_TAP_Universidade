package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float avaliacao1;
    private float avaliacao2;
    private float media;
    private int numFaltas;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @ManyToOne
    @JoinColumn(name = "aluno_matricula")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;


    public Inscricao(float avaliacao1, float avaliacao2, int numFaltas,
                     Situacao situacao, Aluno aluno, Turma turma) {
        this.avaliacao1 = avaliacao1;
        this.avaliacao2 = avaliacao2;
        this.media = (avaliacao1 + avaliacao2) / 2;
        this.numFaltas = numFaltas;
        this.situacao = situacao;
        this.aluno = aluno;
        this.turma = turma;
    }

}