package br.edu.ibmec.entity;

import java.util.ArrayList;
import java.util.List;

public class Curso {
	private int codigo;
	private String nome;

	private List<Aluno> alunos = new ArrayList<Aluno>();

	public Curso() {
		
	}
	
	public Curso(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	public void removeAluno(Aluno aluno) {
		alunos.remove(aluno);
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
