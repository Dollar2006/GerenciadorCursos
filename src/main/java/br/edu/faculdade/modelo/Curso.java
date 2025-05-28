package br.edu.faculdade.modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String nome;
    private int cargaHoraria;
    private int limiteAlunos;
    private boolean ativo;
    private List<Aluno> alunos;

    public Curso() {
        this.alunos = new ArrayList<>();
        this.ativo = true;
    }

    public Curso(String nome, int cargaHoraria, int limiteAlunos) {
        this();
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.limiteAlunos = limiteAlunos;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getLimiteAlunos() {
        return limiteAlunos;
    }

    public void setLimiteAlunos(int limiteAlunos) {
        this.limiteAlunos = limiteAlunos;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public boolean podeAdicionarAluno() {
        return ativo && alunos.size() < limiteAlunos;
    }

    public void adicionarAluno(Aluno aluno) {
        if (podeAdicionarAluno()) {
            alunos.add(aluno);
        } else {
            throw new IllegalStateException("Não é possível adicionar mais alunos ao curso");
        }
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }

    @Override
    public String toString() {
        return nome;
    }
} 