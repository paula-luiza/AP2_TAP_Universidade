package br.edu.ibmec.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    private int matricula;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private Data dataNascimento;

    private int idade;

    private boolean matriculaAtiva;

    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @ElementCollection
    @CollectionTable(name = "aluno_telefones",
            joinColumns = @JoinColumn(name = "aluno_matricula"))
    @Column(name = "telefone")
    private List<String> telefones = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_codigo")
    private Curso curso;

    public Aluno() {

    }

    public Aluno(int matricula, String nome, Data dataNascimento,
                 boolean matriculaAtiva, EstadoCivil estadoCivil, Curso curso,
                 List<String> telefones) {
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.matriculaAtiva = matriculaAtiva;
        this.estadoCivil = estadoCivil;
        this.curso = curso;

        this.idade = 0;
        this.telefones = telefones;
    }


    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Data getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Data dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isMatriculaAtiva() {
        return matriculaAtiva;
    }

    public void setMatriculaAtiva(boolean matriculaAtiva) {
        this.matriculaAtiva = matriculaAtiva;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

}